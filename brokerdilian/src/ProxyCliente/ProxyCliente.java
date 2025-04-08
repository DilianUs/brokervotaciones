/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProxyCliente;

/**
 *
 * @author braul
 */

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

    public ProxyCliente(String host, int puerto) throws IOException {
        this.socket = new Socket(host, puerto);
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.entrada = new ObjectInputStream(socket.getInputStream());
        System.out.println("Conectado al broker en " + host + ":" + puerto);
    }

    public synchronized String votar(String producto) throws IOException {
        try {
            // Enviar mensaje
            salida.writeObject(producto);
            salida.flush();

            // Leer respuesta
            Object respuesta = entrada.readObject();
            if (respuesta instanceof String) {
                return (String) respuesta;
            } else {
                return "Respuesta inválida";
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Error leyendo la respuesta del broker", e);
        }
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
