/**
 * Clase ServicioAdicionalDecorador: Representa el decorador en el patron
 * decorador. Decora con servicios adicionales.
 */
public abstract class ServicioAdicionalDecorador extends Facturable {
    // Elemento facturable que va a decorar
    public Facturable facturable;
    
    /**
     * Constructor para la clase
     * @param facturable elemento facturable que se va a decorar.
     */
    public ServicioAdicionalDecorador(Facturable facturable) {
        this.facturable = facturable;
    }

    /**
     * Se encarga de obtener la descripción del servicio adicional.
     * @return retorna el string correspondiente a la descripcion del servicio
     * adicional.
     */
    @Override
    abstract public String obtenerDescripcion();
    
    /**
     * Se encarga de obtener el precio de un servicio adicional.
     * @return retorna el precio del servicio adicional.
     */
    @Override
    abstract public double obtenerPrecio();
}
