
package domain;

/**
 * Clase ConferenciaTripartita: Representa uno de los decoradores concretos utilizados.
 */
public class ConferenciaTripartita extends ServicioAdicionalDecorador {
    
    /**
     * Constructor para la clase
     * @param facturable producto que vamos a decorar con la conferencia tripartita
     */
    public ConferenciaTripartita(Facturable facturable) {
        super(facturable);
    }
    
    /**
     * Se encarga de obtener la descripción del servicio adicional.
     * @return retorna el string correspondiente a la descripcion de la
     * conferencia tripartita mas el resultado de obtenerDescripcion del
     * elemento facturable.
     */
    public String obtenerDescripcion() {
        return "Conferencia tripartita " + this.facturable.obtenerDescripcion();
    };
    
    /**
     * Se encarga de obtener el precio de un servicio adicional.
     * @return retorna el precio correspondiente a la conferencia tripartita mas
     * el resultado de obtenerDescripcion del elemento facturable.
     */
    public double obtenerPrecio() {
        return 126 + this.facturable.obtenerPrecio();
    };
}
