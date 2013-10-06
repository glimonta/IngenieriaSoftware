
import java.sql.Date;


/**
 * Interfaz para la estrategia de facturacion en planes prepago y postpago.
 */

public interface ComoFacturar {

    /**
     * Metodo para facturar un producto dado en una fecha dada.
     * @param producto: Producto a facturar.
     * @param fecha: Fecha en la cual se factura.
     * @return Devuelve la factura del producto en la fecha dada. 
     */
    public Factura facturar(Producto producto,Date fecha);
           
}
