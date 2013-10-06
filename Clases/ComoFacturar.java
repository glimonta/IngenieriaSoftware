
import java.sql.Date;

/*
 * Interfaz de estrategias para la facturacion de planes
 * 
 */

/**
 *
 * @author federio
 */
public interface ComoFacturar {
    
    //metodo abstracto que factura un plan 
    public Factura facturar(Producto producto, Date fecha);
    
}
