
package domain;
/**
 * Clase Facturable: Representa a un objeto que puede ser facturable,
 * dentro del patron decorador este sería el componente.
 */
public abstract class Facturable {
    public String descripcion; // Descripcion del producto
    public double precio; // Precio del producto
    
    /**
     * Se encarga de obtener la descripcion de un elemento facturable.
     * @return retorna el string correspondiente a la descripcion del elemento.
     */
    abstract public String obtenerDescripcion();
    
    /**
     * Se encarga de obtener el precio de un elemento facturable.
     * @return retorna el numero correspondiente al precio del elemento.
     */
    abstract public double obtenerPrecio();
    
}
