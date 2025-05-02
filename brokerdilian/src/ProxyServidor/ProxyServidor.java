/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// ProxyServidor.java
package ProxyServidor;

import java.io.*;
import java.net.*;

public class ProxyServidor {
    private Socket socketBroker;
    private ObjectInputStream entradaBroker;
    private ObjectOutputStream salidaBroker;

    public ProxyServidor(String hostBroker, int puertoBroker) throws IOException {
        this.socketBroker = new Socket(hostBroker, puertoBroker);
        this.salidaBroker = new ObjectOutputStream(socketBroker.getOutputStream());
        this.entradaBroker = new ObjectInputStream(socketBroker.getInputStream());
        System.out.println("ProxyServidor conectado al broker en " + hostBroker + ":" + puertoBroker);
    }

    // ✅ NUEVO: Enviar al broker el servicio ofrecido
    public void registrarServicio(String nombreServicio) {
        try {
            String mensajeRegistro = "REGISTRO_SERVICIO:" + nombreServicio;
            salidaBroker.writeObject(mensajeRegistro);
            salidaBroker.flush();
            System.out.println("Servicio registrado al broker: " + nombreServicio);
        } catch (IOException e) {
            System.err.println("Error al registrar el servicio: " + e.getMessage());
        }
    }

    public void escucharSolicitudes() {
        try {
            while (true) {
                String mensajeBroker = (String) entradaBroker.readObject();
                System.out.println("Solicitud recibida del broker: " + mensajeBroker);

                String respuestaServidor = procesarSolicitud(mensajeBroker);
                String respuestaInterpretada = interpretarRespuesta(respuestaServidor);

                salidaBroker.writeObject(respuestaInterpretada);
                salidaBroker.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error manejando la conexión con el broker: " + e.getMessage());
        }
    }

    protected String procesarSolicitud(String solicitud) {
        return "OK"; // Implementación por defecto (puede ser sobreescrita)
    }

    private String interpretarRespuesta(String respuesta) {
        return switch (respuesta) {
            case "OK" -> "200: Voto registrado correctamente";
            case "NO_ENCONTRADO" -> "404: Candidato no encontrado";
            case "FRUTA_DESCONOCIDA" -> "400: Fruta desconocida";
            case "ERROR_DB" -> "500: Error en la base de datos";
            default -> "500: Error desconocido";
        };
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

