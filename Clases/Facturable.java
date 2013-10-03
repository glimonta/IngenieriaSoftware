/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 */
// Esto es una interfaz y no una cosa abstracta.
public abstract class Facturable {
    public String descripcion;
    public double precio;
    
    abstract public void obtenerDescripcion();
    abstract public void obtenerPrecio();
    
}
