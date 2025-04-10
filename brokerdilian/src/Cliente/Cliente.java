/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

/**
 *
 * @author braul
 */
import Controlador.Controlador;
import Vista.VistaVotacion;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Dirección del Broker y puerto
            String hostBroker = "192.168.100.5";
            int puertoBroker = 5000;

            // Crear la vista
            VistaVotacion vista = new VistaVotacion();

            // Inicializar el controlador con la vista y el proxy conectado al broker
            Controlador controlador = new Controlador(vista, hostBroker, puertoBroker);

            // Hacer visible la interfaz
            vista.setVisible(true);

        } catch (Exception e) {
            System.err.println("Error iniciando el cliente: " + e.getMessage());
        }
    }
}
