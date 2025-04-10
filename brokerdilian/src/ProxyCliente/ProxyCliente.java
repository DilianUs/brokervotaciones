/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProxyCliente;

/**
 *
 * @author braul
 */

import Controlador.Controlador;
import Modelo.Producto;
import java.io.*;
import java.net.*;
import java.util.List;

/**
 * Proxy del Cliente: Intermediario entre el cliente y el broker.
 */

public class ProxyCliente {
    private Socket socket;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Thread listenerThread;

    private Controlador controlador;  // Referencia al controlador

    public ProxyCliente(String host, int puerto, Controlador controlador) throws IOException {
        this.socket = new Socket(host, puerto);
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.entrada = new ObjectInputStream(socket.getInputStream());
        this.controlador = controlador;

        System.out.println("Conectado al broker en " + host + ":" + puerto);

        // Escuchar mensajes en segundo plano
        iniciarListener();
    }

    private void iniciarListener() {
        listenerThread = new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    Object mensaje = entrada.readObject();
                    if (mensaje instanceof String) {
                        System.out.println("Mensaje recibido del broker: " + mensaje);
                        controlador.actualizarVotosGeneral();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error en el listener del cliente: " + e.getMessage());
                    break;
                }
            }
        });
        listenerThread.start();
    }

    public synchronized void votar(String producto) throws IOException {
        salida.writeObject(producto);
        salida.flush();
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

