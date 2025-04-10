/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;



/**
 *
 * @author braul
 */
import ProxyServidor.ProxyServidor;
import java.io.IOException;
import java.sql.*;

public class Servidor {

    // Conexión JDBC
    private static final String DB_URL = "jdbc:postgresql://db.vozkclloliljktnautbu.supabase.co:5432/postgres"; // reemplaza con la tuya
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "@Paranga123";
    private static Connection connection;

    public static void main(String[] args) {
        String hostBroker = "192.168.100.5";
        int puertoBroker = 5001;

        try {
            // Establecer conexión a la base de datos
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Conectado a la base de datos.");

            // Iniciar proxy y escuchador
           ProxyServidor proxyServidor = new ProxyServidor(hostBroker, puertoBroker) {
                @Override
                protected String procesarSolicitud(String solicitud) {
                    System.out.println("Texto en solicitud: " + solicitud);

                    String resultado;
                    try {
                        resultado = registrarVoto(solicitud);
                    } catch (Exception e) {
                        e.printStackTrace();
                        resultado = "500: Error al procesar solicitud";
                    }

                    return resultado;
                }
            };


            proxyServidor.escucharSolicitudes();

        } catch (IOException | SQLException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    // Método para registrar el voto
        private static String registrarVoto(String fruta) {
            int id;

            String frutaLimpia = fruta.trim().toLowerCase();
            System.out.println("Fruta recibida: >" + frutaLimpia + "<");

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
                    return "400: Fruta desconocida -> " + fruta;
            }
            
             System.out.println("Fentrando a db con id:"+id);
            String query = "UPDATE candidatos SET votos = votos + 1 WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0 ? "200: Voto registrado correctamente" : "404: Candidato no encontrado";
            } catch (SQLException e) {
                e.printStackTrace();
                return "500: Error al registrar voto";
            }
        }

}

