
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Consumo {
    Integer cantidad;
    Date fecha;
    Producto producto;
    Servicio servicio;
    
    public Consumo() {
        this.cantidad = null;
        this.fecha = null;
        this.producto = new Producto();
        this.servicio = new Servicio();
    }
    
    public Consumo(Integer cantidad, Date fecha, Producto producto, Servicio servicio) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.producto = producto;
        this.servicio = servicio;
    }
    
    @Override
    public String toString() {
        return "Cantidad: " + this.cantidad.toString() + ", Fecha: " + 
                this.fecha.toString() + ", Producto: [" +
                this.producto.toString() + "], Servicio: [" +
                this.servicio.toString() + "]";
    }
    
    void registrarConsumo() {
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            st.execute("insert into consume values ('"+ this.producto.codigoProd +
                    "', '" + this.servicio.nombre + "', '" + this.fecha.toString() + 
                    "', '" + this.cantidad + "');");
            
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    static Consumo consultarConsumo(Integer id, String nombre_servicio, java.sql.Date fecha) throws ParseException {
        Consumo consumo = new Consumo();
        
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
             
            ResultSet rs = st.executeQuery("select cantidad, fecha "
                    + "from consume where id = '" + id 
                    + "' and nombre_servicio = '" + nombre_servicio + "' and"
                    + " fecha = '" + fecha.toString()
                    + "';");
            
            rs.next();
              
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(2));
            fecha = new java.sql.Date(utilDate.getTime());
            
            
            Producto producto = Producto.consultarProducto(id);
            Servicio servicio = Servicio.consultarServicio(nombre_servicio);
            
            consumo = new Consumo(rs.getInt(1), fecha, producto, servicio);
            
            
          conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return consumo;
    }
    
    void eliminarConsmo() {
        
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
                        
            st.execute("delete from consume where id =' " + 
                    this.producto.codigoProd + "' and nombre_servicio ='" + this.servicio.nombre + 
                    "' and fecha = '" + this.fecha + "';");
        
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    void modificarConsumo() {
        try {
             try (Connection conn = Conexion.obtenerConn()) {
                 Statement st;
                 st = conn.createStatement();
                 
                 st.executeUpdate("update consume set cantidad ='"
                         + this.cantidad + "' " + "where id ='"
                         + this.producto.codigoProd + "' and nombre_servicio ='" 
                         + this.servicio.nombre
                         + "' and fecha ='" + this.fecha + "';");
                         
            }
            
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
    }
    
    /*public static void main(String[] args) {
        Date fecha = new Date(2015-1900,04,26);
        Cliente cliente = Cliente.consultarCliente(20);
        Producto producto = new Producto(1, "modelo2", cliente);
        Servicio servicio = new Servicio("Segundos a moviles MOCEL", "Servicio 1", "TipoServicio1");
        
        Consumo consumo = new Consumo(50, fecha, producto, servicio);
        consumo.eliminarConsmo();
        consumo.registrarConsumo();
        consumo.cantidad = 23423;
        consumo.modificarConsumo();

    } */
}
