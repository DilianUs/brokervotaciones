/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProxyCliente;

import Controlador.Controlador;
import java.io.*;
import java.net.*;

public class ProxyCliente {
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Thread listenerThread;

    private Controlador controlador;

    public ProxyCliente(String host, int puerto, Controlador controlador) throws IOException {
        this.socket = new Socket(host, puerto);
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.entrada = new ObjectInputStream(socket.getInputStream());
        this.controlador = controlador;

        System.out.println("Conectado al broker en " + host + ":" + puerto);
        iniciarListener();
    }

    private void iniciarListener() {
        listenerThread = new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    Object mensaje = entrada.readObject();
                    if (mensaje instanceof String respuesta) {
                        System.out.println("Respuesta recibida del broker: " + respuesta);
                        controlador.manejarRespuesta(respuesta);  // Nueva llamada
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error en el listener del cliente: " + e.getMessage());
                    break;
                }
            }
        });
        listenerThread.start();
    }

    public synchronized void ejecutarServicio(String servicio, String solicitud) throws IOException {
        String mensaje = servicio + ":" + solicitud;
        salida.writeObject(mensaje);
        salida.flush();
        System.out.println("Mensaje enviado al broker: " + mensaje);
    }

    public void cerrar() {
        try {
            if (entrada != null) entrada.close();
            if (salida != null) salida.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Error cerrando conexión: " + e.getMessage());
        }
    }
}
