
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class EstaAfiliado {
    Date fechaInicio;
    Date fechaFin;
    
    public EstaAfiliado(Date fechaI, Date fechaF) {
        this.fechaInicio = fechaI;
        this.fechaFin = fechaF;
    }
    
    @Override
    public String toString() {
        return "Fecha de Inicio: " + this.fechaInicio.toString() +
                "Fecha de Fin: " + this.fechaFin.toString();
    }
}
