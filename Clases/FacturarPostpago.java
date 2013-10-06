
import java.sql.SQLException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;



/**
 * Clase de facturar postpago para implementar el patron estrategia. 
 */

public class FacturarPostpago implements ComoFacturar {
    
    public FacturarPostpago() {}    
     
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
            
            ArrayList<Contiene> listaContiene = tiene.paquete.ListarContiene();
            
            int anio = fecha.getYear();
            int mes = fecha.getMonth();
            mes++;
            
            if (mes == 12) {
                mes = 0;
                anio++;
            }
            
            Date fechaMax = new Date(anio, mes, 1);
            
            float costoTotal = calcularCostoTotal(producto,fecha,fechaMax,listaContiene);
       
            ArrayList<String> comentarios = new ArrayList();
            
            Factura factura = new Factura(fecha,costoPlan,costoTotal,comentarios,producto);
            return factura;

  
        } catch (SQLException ex) {
            Logger.getLogger(FacturarPostpago.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public float calcularCostoTotal(Producto producto, Date fecha, Date fechaMax, 
                              ArrayList<Contiene> listaContiene) throws SQLException {
        
        ArrayList<Consumo> consumos = Facturador.listarConsumos(producto, fecha, fechaMax);

        float costoTotal = 0;

        for (Contiene contiene : listaContiene) {

            for (Consumo consumo : consumos) {

                if (contiene.servicio.equals(consumo.servicio) &&
                    contiene.cantidad < consumo.cantidad) 
                    costoTotal += contiene.costoAdicional*(consumo.cantidad-contiene.cantidad);
            }              
        }
        return costoTotal;
    }

}
