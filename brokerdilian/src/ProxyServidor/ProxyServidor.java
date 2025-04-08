/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProxyServidor;

/**
 *
 * @author braul
 */
import java.io.*;
import java.net.*;



/**
 * Proxy del Servidor: Gestiona la conexión con el broker.
 */


public class ProxyServidor {
    private Socket socketBroker;
    private ObjectInputStream entradaBroker;
    private ObjectOutputStream salidaBroker;

    public ProxyServidor(String hostBroker, int puertoBroker) throws IOException {
        // Conectar al broker
        this.socketBroker = new Socket(hostBroker, puertoBroker);
        this.salidaBroker = new ObjectOutputStream(socketBroker.getOutputStream());
        this.entradaBroker = new ObjectInputStream(socketBroker.getInputStream());
        System.out.println("ProxyServidor conectado al broker en " + hostBroker + ":" + puertoBroker);
    }

    public void escucharSolicitudes() {
        try {
            while (true) {
                // Leer solicitudes del broker
                String mensajeBroker = (String) entradaBroker.readObject();
                System.out.println("Solicitud recibida del broker: " + mensajeBroker);

                // Procesar la solicitud
                String respuesta = procesarSolicitud(mensajeBroker);

                // Enviar respuesta al broker
                salidaBroker.writeObject(respuesta);
                salidaBroker.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error manejando la conexión con el broker: " + e.getMessage());
        }
    }

    protected String procesarSolicitud(String solicitud) {
        // Lógica básica del servidor: responder con código 200
        return "200: Solicitud procesada exitosamente";
    }

    public void cerrarConexion() {
        try {
            if (entradaBroker != null) entradaBroker.close();
            if (salidaBroker != null) salidaBroker.close();
            if (socketBroker != null) socketBroker.close();
            System.out.println("Conexión cerrada con el broker.");
        } catch (IOException e) {
            System.err.println("Error cerrando conexión: " + e.getMessage());
        }
    }
}