/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class FormaPago {
    String tipoDePago;
    
    public FormaPago(String tipoDePago) {
        this.tipoDePago = tipoDePago;
    }
    
    @Override
    public String toString() {
        return this.tipoDePago;
    }
}
