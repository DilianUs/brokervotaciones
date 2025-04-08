/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author braul
 */

import ProxyCliente.ProxyCliente;
import Vista.VistaVotacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controlador implements ActionListener {
    private VistaVotacion ventana;
    private ProxyCliente proxyCliente;

    public Controlador(VistaVotacion ventana, String host, int puerto) throws IOException {
        this.ventana = ventana;
        this.proxyCliente = new ProxyCliente(host, puerto);
        this.ventana.getButton1().addActionListener(this);
        this.ventana.getButton2().addActionListener(this);
        this.ventana.getButton3().addActionListener(this);
        this.ventana.setResizable(false);
        this.nombresBotones();
    }
    public void nombresBotones() {
        
            this.ventana.getButton1().setText("mandarina");
            this.ventana.getButton2().setText("pera");
            this.ventana.getButton3().setText("manzana");
            this.ventana.getGraficasbtn().setText("Generar Graficas");
        
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
                String respuesta = proxyCliente.votar(producto);
                System.out.println("Respuesta del broker: " + respuesta);

                // Actualizar estado local (pendiente: lógica para actualizar gráficas)
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
