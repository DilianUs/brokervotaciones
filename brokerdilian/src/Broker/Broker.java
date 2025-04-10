/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Broker;

/**
 *
 * @author braul
 */
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Broker {
    private static final String HOST = "192.168.100.5";
    private static final int PUERTO = 5000;

    private static List<ServidorConectado> servidores = new ArrayList<>();
    private static List<ObjectOutputStream> clientesConectados = new ArrayList<>();
    private static int servidorActual = 0;

    public static void main(String[] args) {
        try (ServerSocket brokerSocket = new ServerSocket(PUERTO, 50, InetAddress.getByName(HOST))) {
            System.out.println("Broker iniciado en " + HOST + ":" + PUERTO);

            // Hilo para aceptar servidores
            new Thread(() -> {
                try (ServerSocket serverListener = new ServerSocket(PUERTO + 1)) {
                    System.out.println("Esperando servidores en el puerto " + (PUERTO + 1));
                    while (true) {
                        Socket socketServidor = serverListener.accept();
                        ObjectOutputStream out = new ObjectOutputStream(socketServidor.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(socketServidor.getInputStream());

                        ServidorConectado servidor = new ServidorConectado(socketServidor, in, out);
                        synchronized (servidores) {
                            servidores.add(servidor);
                        }
                        System.out.println("Servidor conectado: " + socketServidor.getInetAddress());
                    }
                } catch (IOException e) {
                    System.err.println("Error al aceptar servidores: " + e.getMessage());
                }
            }).start();

            // Hilo para limpiar servidores muertos
            new Thread(() -> {
                while (true) {
                    synchronized (servidores) {
                        servidores.removeIf(s -> s.socket.isClosed());
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }).start();

            // Escuchar clientes
            while (true) {
                Socket cliente = brokerSocket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());
                new Thread(new ClienteHandler(cliente)).start();
            }

        } catch (IOException e) {
            System.err.println("Error iniciando el broker: " + e.getMessage());
        }
    }

    private static ServidorConectado obtenerServidor() {
        synchronized (servidores) {
            if (servidores.isEmpty()) {
                throw new IllegalStateException("No hay servidores disponibles.");
            }

            for (int i = 0; i < servidores.size(); i++) {
                ServidorConectado servidor = servidores.get(servidorActual);
                if (!servidor.socket.isClosed() && servidor.socket.isConnected()) {
                    servidorActual = (servidorActual + 1) % servidores.size();
                    return servidor;
                } else {
                    System.err.println("Servidor no disponible. Eliminando...");
                    servidores.remove(servidorActual);
                }
            }

            throw new IllegalStateException("Todos los servidores están inactivos.");
        }
    }

    private static void enviarATodosLosClientes(String mensaje) {
        synchronized (clientesConectados) {
            Iterator<ObjectOutputStream> iterator = clientesConectados.iterator();
            while (iterator.hasNext()) {
                ObjectOutputStream clienteOut = iterator.next();
                try {
                    clienteOut.writeObject(mensaje);
                    clienteOut.flush();
                } catch (IOException e) {
                    System.err.println("Error al enviar a un cliente, eliminando de la lista.");
                    iterator.remove(); // eliminar si está muerto
                }
            }
        }
    }

    private static class ClienteHandler implements Runnable {
        private Socket cliente;

        public ClienteHandler(Socket cliente) {
            this.cliente = cliente;
        }

        @Override
        public void run() {
            ObjectOutputStream salidaCliente = null;
            ObjectInputStream entradaCliente = null;
            try {
                salidaCliente = new ObjectOutputStream(cliente.getOutputStream());
                salidaCliente.flush(); // previene bloqueos
                entradaCliente = new ObjectInputStream(cliente.getInputStream());

                // Guardar salida para broadcast
                synchronized (clientesConectados) {
                    clientesConectados.add(salidaCliente);
                }

                while (!cliente.isClosed()) {
                    String mensajeCliente = (String) entradaCliente.readObject();
                    System.out.println("Mensaje recibido del cliente: " + mensajeCliente);

                    ServidorConectado servidor = obtenerServidor();

                    synchronized (servidor) {
                        servidor.out.writeObject(mensajeCliente);
                        servidor.out.flush();

                        String respuestaServidor = (String) servidor.in.readObject();
                        System.out.println("Respuesta del servidor: " + respuestaServidor);

                        // Enviar respuesta a TODOS los clientes
                        enviarATodosLosClientes(respuestaServidor);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error con cliente: " + e.getMessage());
            } finally {
                synchronized (clientesConectados) {
                    clientesConectados.remove(salidaCliente);
                }
                try {
                    if (entradaCliente != null) entradaCliente.close();
                    if (salidaCliente != null) salidaCliente.close();
                    if (cliente != null && !cliente.isClosed()) cliente.close();
                } catch (IOException e) {
                    System.err.println("Error cerrando recursos del cliente: " + e.getMessage());
                }
            }
        }
    }

    // Clase para representar un servidor con sus streams
    private static class ServidorConectado {
        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;

        public ServidorConectado(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
            this.socket = socket;
            this.in = in;
            this.out = out;
        }
    }
}





