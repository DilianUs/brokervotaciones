/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author braul
 */
public class Producto {
    private  String nombre;
    private int votos;
    
    public Producto(String nombre, int votos){
        this.nombre = nombre;
        this.votos = votos;
    }
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
    
    public void votar(){
        this.votos++;
    }
    
    @Override
    public String toString(){
        return "Producto:" + this.getNombre() + ", Votos:" + this.getVotos();
    }
    
}
