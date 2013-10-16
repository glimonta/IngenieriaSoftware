
package domain;
/**
 * Clase FormaPago: Es la clase en la que converge Tarjeta y Efectivo
 */
public class FormaPago {
    Integer id; // identificador de la forma de pago
    
    /**
     * Constructor de FormaPago
     * @param id Identificador de la forma de pago
     */
    public FormaPago(Integer id) {
        this.id = id; 
    }   
    
     /**
     * Permite obtener una representacion en string un elemento de la clase FormaPago.
     * @return String retorna el string que representa al objeto.
     */    @Override
    public String toString() {
         // se retorna el formato de string
        return this.id.toString();
    }
}
