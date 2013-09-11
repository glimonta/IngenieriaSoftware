/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Efectivo extends FormaPago {
    Integer numeroPago;
    
    public Efectivo(String tipoDePago, Integer numPago) {
        super(tipoDePago);
        this.numeroPago = numPago;
    }
    
    //Aun este metodo esta sujeto a la pregunta acerca del tipo de pago.
    @Override
    public String toString() {
        return "Tipo de Pago: " + this.tipoDePago + ", Numero de Pago: " + this.numeroPago.toString();
    }
}
