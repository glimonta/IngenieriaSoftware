import java.sql.SQLException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
* Clase de facturar postpago para implementar el patron estrategia.
*/

public class FacturarPostpago implements ComoFacturar {
    
    /**
* Constructor vacio de FacturarPostpago
*/
    public FacturarPostpago() {}
    
    /**
* Metodo para facturar un producto con un plan postpago.
* @param producto: Producto a facturar.
* @param fecha: Fecha en la cual se factura.
* @return Factura resultante de calcular el costo del plan mas el costo de
* los consumos adicionales.
*/
    @Override
    public Factura facturar(Producto producto, Date fecha) {
        
        try {
            
            // Busca el plan asociado al producto en la fecha dada
            Plan plan = Facturador.buscarPlan(producto,fecha);
            
            // Si el producto no tiene un plan en la fecha dada, devuelve nulo
            if (plan == null)
                return null;
            
            float costoPlan = 0;
            
            // Busca el paquete asociado al plan del producto
            Tiene tiene = buscarPaquete(plan,fecha);
            
            // Si el plan no tiene un paquete, devuelve nulo
            if (tiene == null)
                return null;
            
            costoPlan = tiene.costo;
            
            // Busca los servicios del paquete
            ArrayList<Contiene> listaContiene = tiene.paquete.ListarContiene();
            
            int anio = fecha.getYear();
            int mes = fecha.getMonth();
            mes++;
            
            if (mes == 12) {
                mes = 0;
                anio++;
            }
            
            // Fecha tope para calcular la factura mensual
            Date fechaMax = new Date(anio, mes, 1);
            
            // Calcula el costo de los servicios consumidos por exceso
            float costoTotal = calcularCostoTotal(producto,fecha,fechaMax,listaContiene);
       
            ArrayList<String> comentarios = new ArrayList();
            
            /* Crea una nueva factura con el costo total de los servicios
* consumidos por exceso */
            Factura factura = new Factura(fecha,costoPlan,costoTotal,comentarios,producto);
            return factura;

  
        } catch (SQLException ex) {
            Logger.getLogger(FacturarPostpago.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /**
* Busca el paquete asociado al plan en la fecha dada.
* @param plan: Plan a buscar los paquetes asociados.
* @param fecha: Fecha en la cual el plan tiene el paquete a buscar.
* @return Tiene: Devuelve un objeto con el paquete y su costo.
*/
    public Tiene buscarPaquete(Plan plan, Date fecha) {

        // Devuelve la lista de paquetes que tiene el plan
        ArrayList<Tiene> tienePaquetes = plan.listarTiene();
        
        // Busca cual paquete tiene el plan en la fecha dada
        for (Tiene tiene : tienePaquetes) {

            if (((tiene.fechaInicio.compareTo(fecha)) <= 0)
               && (tiene.fechaFin != null || (fecha.compareTo(tiene.fechaFin)) < 0)) {

                return tiene;
            }
        }
        return null;
    }
    
    /**
* Calcula el costo de los servicios consumidos por exceso en un
* plan postpago.
* @param producto: Producto a calcular el costo.
* @param fecha: Fecha a calcular el costo.
* @param fechaMax: Fecha tope para calcular el costo.
* @param listaContiene: Servicios que ofrece el paquete.
* @return Costo de los servicios consumidos por exceso en la fecha dada.
* @throws SQLException
*/
    public float calcularCostoTotal(Producto producto, Date fecha, Date fechaMax,
                              ArrayList<Contiene> listaContiene) throws SQLException {
        
        // Devuelve los consumos del producto en la fecha dada
        ArrayList<Consumo> consumos = Facturador.listarConsumos(producto, fecha, fechaMax);

        float costoTotal = 0;

        /* Verifica cuales servicios fueron consumidos por exceso y calcula el
* costo adicional y lo agrega al costo total*/
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