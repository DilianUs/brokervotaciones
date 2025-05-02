/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Broker;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Broker {
    private static final String HOST = "127.0.0.1";
    private static final int PUERTO = 5000;

    private static List<ServidorConectado> servidores = new ArrayList<>();
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

                        // Leer el mensaje de registro de servicio
                        String mensaje = (String) in.readObject();
                        if (mensaje.startsWith("REGISTRO_SERVICIO:")) {
                            String nombreServicio = mensaje.substring("REGISTRO_SERVICIO:".length());

                            ServidorConectado servidor = new ServidorConectado(socketServidor, in, out, nombreServicio);
                            synchronized (servidores) {
                                servidores.add(servidor);
                            }

                            System.out.println("Servidor registrado con servicio: " + nombreServicio +
                                               " desde " + socketServidor.getInetAddress());
                        } else {
                            System.err.println("Mensaje inválido al registrar servidor: " + mensaje);
                            socketServidor.close(); // Desconectar si no envía correctamente el servicio
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
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
                salidaCliente.flush();
                entradaCliente = new ObjectInputStream(cliente.getInputStream());

                while (!cliente.isClosed()) {
                    String mensajeCliente = (String) entradaCliente.readObject();
                    System.out.println("Mensaje recibido del cliente: " + mensajeCliente);

                    String[] partes = mensajeCliente.split(":", 2);
                    if (partes.length != 2) {
                        salidaCliente.writeObject("400: Formato de mensaje inválido.");
                        salidaCliente.flush();
                        continue;
                    }

                    String servicioSolicitado = partes[0];
                    String contenido = partes[1];

                    ServidorConectado servidor = obtenerServidorPorServicio(servicioSolicitado);

                    if (servidor == null) {
                        salidaCliente.writeObject("404: Servicio no disponible.");
                        salidaCliente.flush();
                        continue;
                    }

                    synchronized (servidor) {
                        servidor.out.writeObject(contenido); // reenviar todo el mensaje
                        servidor.out.flush();

                        String respuestaServidor = (String) servidor.in.readObject();
                        System.out.println("Respuesta del servidor [" + servidor.servicio + "]: " + respuestaServidor);

                        salidaCliente.writeObject(respuestaServidor);
                        salidaCliente.flush();
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error con cliente: " + e.getMessage());
            } finally {
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

    // Clase para representar un servidor con sus streams y el servicio que ofrece
    private static class ServidorConectado {
        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;
        String servicio;

        public ServidorConectado(Socket socket, ObjectInputStream in, ObjectOutputStream out, String servicio) {
            this.socket = socket;
            this.in = in;
            this.out = out;
            this.servicio = servicio;
        }
    }
    
    private static ServidorConectado obtenerServidorPorServicio(String servicioSolicitado) {
            synchronized (servidores) {
                for (ServidorConectado servidor : servidores) {
                    if (servidor.servicio.equals(servicioSolicitado) &&
                        !servidor.socket.isClosed() &&
                        servidor.socket.isConnected()) {
                        return servidor;
                    }
                }
            }
            return null;
        }

}







