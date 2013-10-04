/**
 * Clase BuzonDeMensajes: Representa uno de los decoradores concretos utilizados.
 */
public class BuzonDeMensajes extends ServicioAdicionalDecorador {
    
    /**
     * Constructor para la clase
     * @param facturable producto que vamos a decorar con el buzon de mensajes
     */
    public BuzonDeMensajes(Facturable facturable) {
        super(facturable);
    }
    
    /**
     * Se encarga de obtener la descripción del servicio adicional.
     * @return retorna el string correspondiente a la descripcion del buzon de
     * mensajes mas el resultado de obtenerDescripcion del elemento facturable.
     */
    public String obtenerDescripcion() {
        return "Buzon de mensajes " + this.facturable.obtenerDescripcion();
    };
    
    /**
     * Se encarga de obtener el precio de un servicio adicional.
     * @return retorna el precio correspondiente al buzon de mensajes mas el
     * resultado de obtenerDescripcion del elemento facturable.
     */
    public double obtenerPrecio() {
        return 42 + this.facturable.obtenerPrecio();
    };
}
