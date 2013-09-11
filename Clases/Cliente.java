
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
    Integer cedula;
    String nombre;
    String direccion;
    ArrayList<Integer> telefonos;
    
    public Cliente(Integer cedula, String nombre, String direccion, ArrayList<Integer> telefonos) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefonos = telefonos;
    }
    
    void RegistrarCliente() throws SQLException {
        
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
            Connection conn = DriverManager.getConnection(url,props);
            
            Statement st;
            st = conn.createStatement();
            
            st.executeUpdate("insert into cliente values ('" + this.cedula.toString() + "', '"
                    + this.nombre + "', '" + this.direccion + "');");
            
            for (int i=0; i < this.telefonos.toArray().length; ++i) {
                st.executeUpdate("insert into telefono values ('" + this.cedula.toString() 
                        + "', '" + this.telefonos.get(i).toString() + "');");
            }
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
          
    }
    
    void ConsultarCliente() {
        // Consultar clientes que es? es consultar segun el nombre? segun la cedula? no se que debe hacer el metodo :S
    }
    
    ArrayList<Producto> ObtenerProductos() {
        
        ArrayList<Producto> productos = new ArrayList<>();
        
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
            Connection conn = DriverManager.getConnection(url,props);
            
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
        String retorno = "Cedula: " + this.cedula.toString() + ", Nombre: " + 
                this.nombre + ", Direccion: " + this.direccion + ", Telefonos: ";
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
    
    public static void main(String[] args) {
        ArrayList<Integer> telefonosG = new ArrayList<>();
        telefonosG.add(2122564041);
        telefonosG.add(2122344229);
        Cliente c = new Cliente(21030282, "Gabriela", "Macaracuay", telefonosG);
        
        
        ArrayList<Producto> productos = c.ObtenerProductos();
        
        for (int i=0; i < productos.toArray().length; ++i) {
                System.out.println(productos.get(i).toString());
            }
        
    }
}
