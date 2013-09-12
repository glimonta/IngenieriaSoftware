
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Afiliacion {
    Date fechaInicio;
    Date fechaFin;
    Plan plan;
    Producto producto;
    
    public Afiliacion(Date fechaI, Date fechaF, Plan plan, Producto producto) {
        this.fechaInicio = fechaI;
        this.fechaFin = fechaF;
        this.plan = plan;
        this.producto = producto;
    }
    
    @Override
    public String toString() {
        return "Fecha de Inicio: " + this.fechaInicio.toString() +
                "Fecha de Fin: " + this.fechaFin.toString() +
                "ID de producto: " + this.producto.codigoProd.toString() +
                "Nombre del Plan: " + this.plan.nombre +
                "Tipo de Plan: " + this.plan.tipoPlan;
    }
    
    void registrarAfiliacion() {
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            st.execute("insert into esta_afiliado values ('"+ this.producto.codigoProd +
                    "', '" + this.plan.nombre + "', '" + this.plan.tipoPlan + "', '" +
                    this.fechaInicio.toString() + "', '" + this.fechaFin.toString() + "');");
            
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    static Afiliacion consultarAfiliacion(Integer id, String nombre_plan, String tipo_plan, Date fechaInicio) throws ParseException {
        Afiliacion afiliacion = null;
        
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
             
            ResultSet rs = st.executeQuery("select fecha_inic, fecha_fin "
                    + "from esta_afiliado where id = '" + id 
                    + "' and nombre_plan = '" + nombre_plan + "' and tipo_plan = '"
                    + tipo_plan + "' and fecha_inic = '" + fechaInicio.toString()
                    + "';");
            
            if (rs != null) {
                rs.next();
              
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(1));
                java.sql.Date fechaI = new java.sql.Date(utilDate.getTime());
            
                utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(2));
                java.sql.Date fechaF = new java.sql.Date(utilDate.getTime());
            
                Plan plan = Plan.consultarPlan(rs.getString(2), rs.getString(3));
                Producto producto = Producto.consultarProducto(rs.getInt(1));
            
                afiliacion = new Afiliacion(fechaI, fechaF, plan, producto);
            }
                conn.close();

            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return afiliacion;
    }
    
    void eliminarAfiliacion() {
        
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
                        
            st.execute("delete from esta_afiliado where nombre_plan ='" + 
                    this.plan.nombre + "' and tipo_plan ='" + this.plan.tipoPlan + 
                    "' and id = '" + this.producto.codigoProd + "' and fecha_inic = '" +
                    this.fechaInicio.toString() + "';");
        
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    void modificarAfiliacion() {
        
             try (Connection conn = Conexion.obtenerConn()) {
                 Statement st;
                 st = conn.createStatement();
                 
                 st.executeUpdate("update esta_afiliado set fecha_fin ='"
                         + this.fechaFin.toString() + "' " + "where id ='"
                         + this.producto.codigoProd + "' and nombre_plan ='" 
                         + this.plan.nombre
                         + "' and tipo_plan ='" + this.plan.tipoPlan
                         + "' and fecha_inic ='" + this.fechaInicio.toString() +"';");
            
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
    }
    
    /*public static void main(String[] args) throws ParseException {
        Date fechaInicio = new Date(2015-1900,04,26);
        System.out.println(fechaInicio.toString());
        
        Date fechaFin = new Date(2016-1900,04,26);
        System.out.println(fechaFin.toString());
        Cliente cliente = Cliente.consultarCliente(2);
        Plan plan = new Plan("Plan Mocel 2000", "Plan 1", "PREPAGO");
        Producto producto = new Producto(94, "Gaby", cliente);
        registrarAfiliacion(fechaInicio, fechaFin, plan, producto);
        
        Afiliacion afiliacion = consultarAfiliacion(94, "Plan Mocel 2000", "PREPAGO", fechaInicio);
        System.out.println(afiliacion.toString());
        
        afiliacion.fechaFin = new Date(2017-1900,04,26);
        
        afiliacion.modificarAfiliacion(94, "Plan Mocel 2000", "PREPAGO");
        
        afiliacion = consultarAfiliacion(94, "Plan Mocel 2000", "PREPAGO", fechaInicio);
        System.out.println(afiliacion.toString());
        
        afiliacion.eliminarAfiliacion(94, "Plan Mocel 2000", "PREPAGO");
    }*/
}
