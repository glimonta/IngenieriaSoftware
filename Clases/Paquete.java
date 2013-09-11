/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Paquete {
    String nombre;
    String descripcion;
    
    public Paquete(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return "Nombre: " + this.nombre + ", Descripcion: " + this.descripcion;
    }
    
    //faltan los metodos
    
}
