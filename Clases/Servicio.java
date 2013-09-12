/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
class Servicio {
    String nombre;
    String descripcion;
    String tipoServicio;
    
    public Servicio() {
        this.nombre = null;
        this.descripcion = null;
        this.tipoServicio = null;
    }
    
    public Servicio(String nombre, String descripcion, String tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoServicio = tipo;
    }
    
        static Servicio consultarServicio(String nombre) {
        return null;
    }
    
}
