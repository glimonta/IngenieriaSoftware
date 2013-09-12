
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Efectivo extends FormaPago {
    Integer numeroPago;
    
    public Efectivo() {
        super();
        this.numeroPago = null;
    }
    
    public Efectivo(Integer id, Integer numPago) {
        super(id);
        this.numeroPago = numPago;
    }
    
    void registrarEfectivo() {
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("select max(postiza_pago) from forma_pago;");
            
            rs.next();
            
            Integer max = rs.getInt(1) + 1;
            
            st.execute("insert into forma_pago values (" + max.toString() + ");");
                    
            st.execute("insert into efectivo values ("+ this.numeroPago + ", " 
                    + max.toString() + ");");
            
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    static Efectivo consultarEfectivo(Integer numPago) {
        Efectivo efectivo = new Efectivo();
        
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
             
            ResultSet rs = st.executeQuery("select * from efectivo where "
                    + " nro_pago = " + numPago + ";");
            
            rs.next();
              
            efectivo = new Efectivo(Integer.parseInt(rs.getString(2)),
                     Integer.parseInt(rs.getString(1)));
            
          conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return efectivo;
    }
    
    void modificarEfectivo() {
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
                 
            st.executeUpdate("update efectivo set postiza_pago ='"
                     + this.id + "' where nro_pago ='" + this.numeroPago + "';");
            
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
    }
    
    void eliminarEfectivo() {

        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
                        
            st.execute("delete from efectivo where nro_pago ='" + 
                    this.numeroPago + "';");
        
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return "ID de Pago: " + this.id.toString() + ", Numero de Pago: " + this.numeroPago.toString();
    }
    
    /*public static void main(String[] args) {
        Efectivo e = new Efectivo(46, 165);
        e.registrarEfectivo();
        
        e = consultarEfectivo(165);
        System.out.println(e.toString());
        
        e.id = 10;
        System.out.println(e.id.toString());
        
        e.modificarEfectivo();
        
        e.consultarEfectivo(165);
        System.out.println(e.toString());
        
        e.eliminarEfectivo();
    }*/
}
