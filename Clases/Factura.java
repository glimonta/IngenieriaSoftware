
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class Factura {
    Date fecha;
    double montoTotal;
    ArrayList<String> comentarios;
    Producto producto;
    
    public Factura(Date fecha, double montoTotal, ArrayList<String> comentarios, Producto producto) {
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.comentarios = comentarios;
        this.producto = producto;
    }
    
    @Override
    public String toString() {
        String comentarios = "";
        
        for (int i=0; i < this.comentarios.toArray().length; ++i) {
                comentarios = comentarios + this.comentarios.get(i) + " ";
                if (this.comentarios.toArray().length -1 > i) {
                    comentarios = comentarios + ", ";
                }
            }
        
        return "Fecha: " + this.fecha.toString() + ", Monto: " + 
                this.montoTotal + ", Comentarios: [" + comentarios + "} , Producto: ["
                + producto.toString() + "]";
    }
    
    void registrarFactura() {
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            st.execute("insert into factura values ('"+ this.producto.codigoProd + 
                    "', '" + this.fecha.toString() + "', '" + this.montoTotal + "');");
            
            for (int i=0; i < comentarios.toArray().length; ++i) {
                st.execute("insert into comentario values ('" + this.producto.codigoProd +
                    "', '" + this.fecha.toString() + "', '" +
                    this.comentarios.get(i).toString() + "');");
            }
            
            
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    static Factura consultarFactura(Producto producto, Date fecha) {
        Factura factura = null;
        
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("select valor from comentario where"
                    + " id = '" + producto.codigoProd.toString() + "' and fecha ='"
                    + fecha.toString() + "';");
            
            if (rs != null) {

                ArrayList<String> comentarios = new ArrayList<>();
                
                while (rs.next()) {
                    String comentario = rs.getString(1);
                    System.out.println(comentario);
                    comentarios.add(comentario);
                }
                
                rs.close();
                
                rs = st.executeQuery("select monto_total from factura where "
                    + "id = '" + producto.codigoProd.toString() + "' and fecha ='"
                    + fecha.toString() + "';");
            
                rs.next();
              
                factura = new Factura(fecha, rs.getDouble(1), comentarios, producto);
                        
            }
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return factura;
    }
    
    void modificarFactura() {
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
               
            st.executeUpdate("update factura set monto_total ='"
                    + this.montoTotal +"' where id ='"
                    + this.producto.codigoProd.toString()
                    + "' and fecha ='" + this.fecha.toString() +"';");
       
            st.execute("delete from comentario where id ='" 
                    + this.producto.codigoProd.toString() + "' and fecha ='"
                    + this.fecha.toString() + "';");
                
            for (int i=0; i < this.comentarios.toArray().length; ++i) {
                st.execute("insert into comentario values ('" +
                        this.producto.codigoProd.toString() + "', '" +
                        this.fecha.toString() + "', '" +
                        this.comentarios.get(i).toString() +"');");
                }                  
            
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
    }
    
    void eliminarFactura() {

             try (Connection conn = Conexion.obtenerConn()) {
                 Statement st;
                 st = conn.createStatement();
                        
                 st.execute("delete from comentario where id ='"
                         + this.producto.codigoProd.toString() 
                         + "' and fecha ='" + this.fecha.toString() +"';");

                 st.execute("delete from factura where id ='"
                         + this.producto.codigoProd.toString() 
                         + "' and fecha ='" + this.fecha.toString() +"';");                         
            
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
    }
    
    /*public static void main(String[] args) {
        Date fecha = new Date(2015-1900,04,26);
        ArrayList<String> comentarios = new ArrayList<>();
        comentarios.add("holis soy un comentario!");
        comentarios.add("holiiis y yo otro comentariooo! :D");
        Cliente cliente = Cliente.consultarCliente(2);
        Producto producto = new Producto(94, "Gaby", cliente);
        
        Factura factura = new Factura(fecha, 156.36, comentarios, producto);
        
        factura.registrarFactura();
        
        factura = consultarFactura(producto, fecha);
        System.out.println(factura.toString());
        
        factura.comentarios.remove(1);
        
        System.out.println(factura.comentarios.toString());
        factura.modificarFactura();
        
        factura = consultarFactura(producto, fecha);
        System.out.println(factura.toString());
        
        factura.eliminarFactura();
        
    }*/
}
