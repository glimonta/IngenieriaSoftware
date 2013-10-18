/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

/**
 *
 */
public class ServicioDisponiblePrepago {
    String nombreServicio;
    int cantidadDisponible;

    public ServicioDisponiblePrepago(String nombreServicio, int cantidadDisponible) {
        this.nombreServicio = nombreServicio;
        this.cantidadDisponible = cantidadDisponible;
    }
    
    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
    
}
