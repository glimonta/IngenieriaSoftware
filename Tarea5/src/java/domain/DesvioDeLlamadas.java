
package domain;
/**
 * Clase DesvioDeLlamadas: Representa uno de los decoradores concretos utilizados.
 */
public class DesvioDeLlamadas extends ServicioAdicionalDecorador {
    
    /**
     * Constructor para la clase
     * @param facturable producto que vamos a decorar con el desvio de llamadas
     */
    public DesvioDeLlamadas(Facturable facturable) {
        super(facturable);
    }
    
    /**
     * Se encarga de obtener la descripción del servicio adicional.
     * @return retorna el string correspondiente a la descripcion del desvio de
     * llamadas mas el resultado de obtenerDescripcion del
     * elemento facturable.
     */
    public String obtenerDescripcion() {
        return "Desvio de Llamadas " + this.facturable.obtenerDescripcion();
    };
    
    /**
     * Se encarga de obtener el precio de un servicio adicional.
     * @return retorna el precio correspondiente al desvio de llamadas mas el
     * resultado de obtenerDescripcion del elemento facturable.
     */
    public double obtenerPrecio() {
        return 84 + this.facturable.obtenerPrecio();
    };
}
