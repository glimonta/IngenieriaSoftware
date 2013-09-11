
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
public class Plan {
    String nombre;
    String descripcion;
    String tipoPlan;
    
    public Plan(String nombre, String descripcion, String tipoPlan) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoPlan = tipoPlan;
    }
    
    @Override
    public String toString() {
        return "Nombre: " + this.nombre + ", Descripcion: " + this.descripcion +
                ", Tipo de Plan: " + this.tipoPlan;
    }
    
    // no he probado que este metodo funcione.
    void RegistrarPlan() {
        
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
            Connection conn = DriverManager.getConnection(url,props);
            
            Statement st;
            st = conn.createStatement();
            
            st.executeUpdate("insert into plan values ('"+ this.nombre + 
                    "', '" + this.tipoPlan + "', '" + this.descripcion + "');");
            
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
    void ConsultarPlan() {
        
    }
    
    //Este metodo no esta probado.
    ArrayList<Paquete> ListarPaquetes() {
        
        ArrayList<Paquete> paquetes = new ArrayList<>();
        
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
            Connection conn = DriverManager.getConnection(url,props);
            
            Statement st;
            st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("select nombre_paquete, descripcion from "
                    + "paquete natural join tiene where nombre_plan = '" + 
                    this.nombre + "';");
            
            while (rs.next()) {
                Paquete paq = new Paquete(rs.getString(1), rs.getString(2));
                paquetes.add(paq);
            }
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return paquetes;
        
        
    }
}
