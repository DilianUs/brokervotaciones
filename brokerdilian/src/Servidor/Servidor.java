/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Servidor.java
package Servidor;

import ProxyServidor.ProxyServidor;
import java.io.IOException;
import java.sql.*;

public class Servidor {
    private static final String DB_URL = "jdbc:postgresql://db.vozkclloliljktnautbu.supabase.co:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "@Paranga123";
    private static Connection connection;

    public static void main(String[] args) {
        String hostBroker = "127.0.0.1";
        int puertoBroker = 5001;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Conectado a la base de datos.");

            ProxyServidor proxyServidor = new ProxyServidor(hostBroker, puertoBroker) {
                @Override
                protected String procesarSolicitud(String solicitud) {
                    System.out.println("Texto en solicitud: " + solicitud);
                    return registrarVoto(solicitud);  // sin códigos de estado
                }
            };

            // ✅ Usamos el método ya definido en ProxyServidor
            proxyServidor.registrarServicio("votacion");

            proxyServidor.escucharSolicitudes();
        } catch (IOException | SQLException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static String registrarVoto(String fruta) {
        int id;
        String frutaLimpia = fruta.trim().toLowerCase();

        switch (frutaLimpia) {
            case "mandarinas":
                id = 1;
                break;
            case "pera":
                id = 2;
                break;
            case "manzana":
                id = 3;
                break;
            default:
                return "FRUTA_DESCONOCIDA";
        }

        String query = "UPDATE candidatos SET votos = votos + 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0 ? "OK" : "NO_ENCONTRADO";
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR_DB";
        }
    }
}
