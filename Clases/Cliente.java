
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Cliente {
    public Integer cedula;
    public String nombre;
    public String direccion;
    public ArrayList<Long> telefonos;
    
    public Cliente() {
        this.cedula = null;
        this.nombre = null;
        this.direccion = null;
        this.telefonos = null;
    }
    
    public Cliente(Integer cedula, String nombre, String direccion, ArrayList<Long> telefonos) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefonos = telefonos;
    }
    
    void registrarCliente() throws SQLException {
        
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            st.executeUpdate("insert into cliente values ('" + this.cedula.toString() + "', '"
                    + this.nombre + "', '" + this.direccion + "');");
            
            for (int i=0; i < telefonos.toArray().length; ++i) {
                st.executeUpdate("insert into telefono values ('" + this.cedula.toString() 
                        + "', '" + this.telefonos.get(i).toString() + "');");
            }
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
          
    }
    
    static Cliente consultarCliente(Integer cedula) {
        
        Cliente cliente = new Cliente();
        
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("select numero from telefono where "
                    + "ci = '" + cedula.toString() + "';");
            
            if (rs != null) {

                ArrayList<Long> tlfs = new ArrayList<>();

                while (rs.next()) {
                    Long telefono = Long.valueOf(rs.getString(1));
                    tlfs.add(telefono);
                }

                rs.close();
                
                rs = st.executeQuery("select ci, nombre, direccion from cliente where ci = '" + 
                    cedula.toString() + "';");
            
                rs.next();
              
                cliente = new Cliente(rs.getInt(1),
                        rs.getString(2), rs.getString(3), tlfs);
                        
            }
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return cliente;
        
    }
    
    
    void modificarCliente() {
         try (Connection conn = Conexion.obtenerConn()) {
            
                 Statement st;
                 st = conn.createStatement();
                 
                 st.executeUpdate("update cliente set nombre ='"
                         +this.nombre+"', direccion ='"+this.direccion+"'"
                         + "where ci ='"+this.cedula+"';");
       
                 st.execute("delete from Telefono where ci ='"+this.cedula+"';");
                 
                for (int i=0; i < this.telefonos.toArray().length; ++i) {
                  st.execute("insert into Telefono values ("+ this.cedula +"','"
                          +this.telefonos.get(i).toString()+"');");
                 }                  
    
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
        
    }
   
    void eliminarCliente() {
          
      ArrayList<Producto> productos = this.obtenerProductos();

             try (Connection conn = Conexion.obtenerConn()) {
                 Statement st;
                 st = conn.createStatement();
                        
                 st.execute("delete from Telefono where ci ='"+this.cedula+"';");

                 st.execute("delete from Cliente where ci ='"+this.cedula+"';");

                 for (int i=0; i < productos.toArray().length; ++i) {
                   ((Producto) productos.get(i)).eliminarProducto();
                 }            
                 
            
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
        
    }
      
    
    ArrayList<Producto> obtenerProductos() {
        
        ArrayList<Producto> productos = new ArrayList<>();
        
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("select id, nombre_modelo from "
                    + "producto natural join es_duenio where ci = '" + 
                    this.cedula.toString() + "';");
            
            while (rs.next()) {
                Producto prod = new Producto(Integer.parseInt(rs.getString(1)), rs.getString(2), this);
                productos.add(prod);
            }
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return productos;
        
    }
    
    @Override
    public String toString() {
        String tlfs = "";
        
        for (int i=0; i < this.telefonos.toArray().length; ++i) {
                tlfs = tlfs + this.telefonos.get(i).toString() + " ";
                if (this.telefonos.toArray().length -1 > i) {
                    tlfs = tlfs + ", ";
                }
            }
        
        return "Cedula: " + this.cedula.toString() + ", Nombre: " + 
                this.nombre + ", Direccion: " + this.direccion + ", Telefonos: "
                + tlfs;
    }
    
   /* public static void main(String[] args) throws SQLException {
        
        Cliente c = consultarCliente(21030282);
        
        System.out.println(c.toString());
        ArrayList<Producto> productos = c.obtenerProductos();
        
        for (int i=0; i < productos.toArray().length; ++i) {
            System.out.println(productos.get(i).toString());
        }
        
    }*/
}
