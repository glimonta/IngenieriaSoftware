
package domain;
import java.sql.Date;
import java.sql.SQLException;



public class test {
    
    
    public static void main(String args[]) throws SQLException {
        
        Producto producto = Producto.consultarProducto(1);

        ComoFacturar facturar = new FacturarPostpago();
        
        Date fecha = new Date(113,4,1);
        System.out.println(fecha);
        Factura factura = Facturador.comoFacturar(producto, fecha);
        
        System.out.println(factura);
                
    }
            
    
    
    
    
}
