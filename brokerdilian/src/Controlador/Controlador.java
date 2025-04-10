/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author braul
 */

import Modelo.Producto;
import ProxyCliente.ProxyCliente;
import Vista.Grafica;
import Vista.VistaVotacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controlador implements ActionListener {
     private static final String DB_URL = "jdbc:postgresql://db.vozkclloliljktnautbu.supabase.co:5432/postgres"; // reemplaza con la tuya
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "@Paranga123";
    private static Connection connection;
    private VistaVotacion ventana;
    private ProxyCliente proxyCliente;
     private ArrayList<Producto> productos;
     // graficas
    private ControlGrafica grafica;
    private Grafica ventanaPastel;
    private Grafica ventanaBarras;

    public Controlador(VistaVotacion ventana, String host, int puerto) throws IOException {
        this.ventana = ventana;
        this.proxyCliente = new ProxyCliente(host, puerto,this);
        this.ventana.getButton1().addActionListener(this);
        this.ventana.getButton2().addActionListener(this);
        this.ventana.getButton3().addActionListener(this);
        this.ventana.setResizable(false);
        this.nombresBotones();
         try {
             this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         } catch (SQLException ex) {
             Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
         }
         this.nombresBotones();
         this.productos = obtenerVotosActuales();
         this.ventana.getGraficasbtn().addActionListener(this);
        ventanaPastel = new Grafica();
        ventanaBarras = new Grafica();
        grafica = new ControlGrafica(this.productos, this.ventanaBarras, this.ventanaPastel);
        this.ventanaBarras.setVisible(false);
        this.ventanaPastel.setVisible(false);
          this.votosActuales();    
    }
    public void nombresBotones() {
        
            this.ventana.getButton1().setText("mandarina");
            this.ventana.getButton2().setText("pera");
            this.ventana.getButton3().setText("manzana");
            this.ventana.getGraficasbtn().setText("Generar Graficas");
        
    }
    public void votosActuales() {
        if (this.productos.size() >= 3) {
            System.out.println(this.productos);
            this.ventana.getTextoTotalP1().setText(Integer.toString(this.productos.get(0).getVotos()));
            this.ventana.getTextoTotalP2().setText(Integer.toString(this.productos.get(1).getVotos()));
            this.ventana.getTextoTotalP3().setText(Integer.toString(this.productos.get(2).getVotos()));
        }
    }
    @Override
    public void actionPerformed(ActionEvent actionUser) {
        try {
            String producto = null;

            if (actionUser.getSource() == this.ventana.getButton1()) {
                producto = "Mandarinas";
            } else if (actionUser.getSource() == this.ventana.getButton2()) {
                producto = "Pera";
            } else if (actionUser.getSource() == this.ventana.getButton3()) {
                producto = "Manzana";
            }

            if (producto != null) {
                // Enviar solicitud de votación al broker
                proxyCliente.votar(producto);
                System.out.println("Respuesta del broker: " );

                // Actualizar estado local (pendiente: lógica para actualizar gráficas)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (actionUser.getSource() == this.ventana.getGraficasbtn()) {
            this.ventanaBarras.setVisible(true);
            this.ventanaPastel.setVisible(true);
        }
    }
    
    public static ArrayList<Producto> obtenerVotosActuales() {
            ArrayList<Producto> productos = new ArrayList<>();
            String query = "SELECT name, votos FROM candidatos ORDER BY id ASC";

            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String nombre = rs.getString("name");
                    int votos = rs.getInt("votos");
                    productos.add(new Producto(nombre, votos));
                }
                System.out.println("Votos obtenidos");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("fallo al obtener los votos");
                // Puedes loggear o lanzar una excepción según el manejo de errores de tu app
            }

            return productos;
    }
    
    public void actualizarVotosGeneral(){
          this.productos = obtenerVotosActuales();
          votosActuales();
          this.grafica.actualizarGrafica(productos);
    }

}
