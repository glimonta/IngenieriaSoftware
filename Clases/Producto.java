/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Producto {
    Integer codigoProd;
    String modelo;
    Cliente cliente;
    
    public Producto() {
        this.codigoProd = null;
        this.modelo = null;
        this.cliente = null;
    }
    
    public Producto(Integer codigo, String modelo, Cliente cliente) {
        this.codigoProd = codigo;
        this.modelo = modelo;
        this.cliente = cliente;
    }
    
    public Integer getCod() {
        return this.codigoProd;
    }
    
    public void eliminarProducto() {
    }
    
    static Producto consultarProducto(Integer id) {
        return null;
    }
    
    @Override
    public String toString() {
        return "Codigo: " + this.codigoProd.toString() + ", Modelo: " + 
                this.modelo + ", Cliente: [" + this.cliente.toString()+ "]";
    }
    // faltan los metodos.
}
