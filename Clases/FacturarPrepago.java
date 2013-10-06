import java.sql.SQLException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;



/**
* Clase de facturar postpago para implementar el patron estrategia.
*/

public class FacturarPrepago implements ComoFacturar {
     
    @Override
    public Factura facturar(Producto producto, Date fecha) {
        
        try {
            
            if (Producto.consultarProducto(producto.codigoProd) == null)
                return null;
            
            Plan plan = Facturador.buscarPlan(producto,fecha);
            
            if (plan == null)
                return null;
            
            float costoPlan = 0;
            
            Tiene tiene = buscarPaquete(plan,fecha);
            
            if (tiene == null)
                return null;
            
            costoPlan = tiene.costo;

            ArrayList<String> comentarios = new ArrayList();
            
            Factura factura = new Factura(fecha,costoPlan,costoPlan,comentarios,producto);
            return factura;

  
        } catch (SQLException ex) {
            Logger.getLogger(FacturarPrepago.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
        public Tiene buscarPaquete(Plan plan, Date fecha) {

        ArrayList<Tiene> tienePaquetes = plan.listarTiene();
        for (Tiene tiene : tienePaquetes) {

            if (((tiene.fechaInicio.compareTo(fecha)) <= 0)
               && (tiene.fechaFin != null || (fecha.compareTo(tiene.fechaFin)) < 0)) {

                return tiene;
            }
        }
        
        return null;
    }
}
