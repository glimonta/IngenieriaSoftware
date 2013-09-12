
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
public class Posee {
    Date fechaInicio;
    ServicioAdicional adicional;
    Producto producto;
    
    public Posee(Date fechaInicio, ServicioAdicional adicional, Producto producto) {
        this.fechaInicio = fechaInicio;
        this.adicional = adicional;
        this.producto = producto;
    }
    
    @Override
    public String toString() {
        return "Fecha de Inicio: " + this.fechaInicio.toString() +
                ", Nombre de servicio: [" + this.adicional.toString() +
                "], ID: [" + this.producto.toString() + "]";
    }
    
     void registrarPosee() {
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            st.execute("insert into posee values ('"+ this.producto.codigoProd +
                    "', '" + this.adicional.nombre + "', '" + this.fechaInicio.toString() + 
                     "');");
            
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
     
    static Posee consultarPosee(Integer id, String nombre_servicio, java.sql.Date fecha) throws ParseException {
        Posee posee = null;
        
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
             
            ResultSet rs = st.executeQuery("select * "
                    + "from posee where id = '" + id 
                    + "' and nombre_servicio = '" + nombre_servicio + "' and"
                    + " fecha_inic= '" + fecha.toString()
                    + "';");
            
            rs.next();
              
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(3));
            fecha = new java.sql.Date(utilDate.getTime());
            
            
            Producto producto = Producto.consultarProducto(rs.getInt(1));
            ServicioAdicional servicioAdicional = ServicioAdicional.consultarServicioAd(rs.getString(2));
            
            posee = new Posee(fecha, servicioAdicional, producto);
            
            
          conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return posee;
    }
     
    void eliminarPosee() {
        
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
                        
            st.execute("delete from posee where id =' " + 
                    this.producto.codigoProd + "' and nombre_servicio ='" + this.adicional.nombre + 
                    "' and fecha_inic = '" + this.fechaInicio + "';");
        
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
}
