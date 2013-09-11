
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Posee {
    Date fechaInicio;
    Date fechaFin;
    
    public Posee(Date fechaInicio, Date fechaFin) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    @Override
    public String toString() {
        return "Fecha de Inicio: " + this.fechaInicio.toString() +
                "Fecha de Fin: " + this.fechaFin.toString();
    }
}
