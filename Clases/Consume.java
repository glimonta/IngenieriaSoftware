
import java.util.Date;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Consume {
    Integer cantidad;
    Date fecha;
    
    public Consume(Integer cantidad, Date fecha) {
        this.cantidad = cantidad;
        this.fecha = fecha;
    }
    
    @Override
    public String toString() {
        return "Cantidad: " + this.cantidad.toString() + "Fecha: " + this.fecha.toString();
    }
}
