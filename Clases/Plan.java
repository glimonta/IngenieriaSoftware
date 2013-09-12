
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
    
    public Plan() {
        this.descripcion = null;
        this.nombre = null;
        this.tipoPlan = null;
    }
    
    @Override
    public String toString() {
        return "Nombre: " + this.nombre + ", Descripcion: " + this.descripcion +
                ", Tipo de Plan: " + this.tipoPlan;
    }
    
    void modificarPlan() {
         try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
             try (Connection conn = DriverManager.getConnection(url,props)) {
                 Statement st;
                 st = conn.createStatement();
                 
                 st.executeUpdate("update plan set nombre ='"
                         +this.nombre+"', descripcion ='"+this.descripcion+"'"
                         + "where nombre ='"+this.nombre+"' and tipo_plan='"
                         + this.tipoPlan +"';");
                         
            }
            
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
        
    }   
    
    void registrarPlan() {
        
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
            Connection conn = DriverManager.getConnection(url,props);
            
            Statement st;
            st = conn.createStatement();
            
            st.execute("insert into plan values ('"+ this.nombre + 
                    "', '" + this.tipoPlan + "', '" + this.descripcion + "');");
            
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
    void eliminarPlan() {
        String url = "jdbc:postgresql:innova";
        Properties props = new Properties();
        props.setProperty("user","gabriela");
        props.setProperty("password","wennicheinjungewar");
       
        try (Connection conn = DriverManager.getConnection(url,props)) {
            Statement st;
            st = conn.createStatement();
                        
            st.execute("delete from esta_afiliado where nombre_plan ='" + 
                    this.nombre + "' and tipo_plan ='" + this.tipoPlan + "';");
            
            st.execute("delete from tiene where nombre_plan ='" + 
                    this.nombre + "' and tipo_plan ='" + this.tipoPlan + "';");

            st.execute("delete from Plan where nombre_plan ='" + 
                    this.nombre + "' and tipo_plan ='" + this.tipoPlan + "';");
        
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    static Plan consultarPlan(String nombre, String tipo_plan) {
             
        Plan plan = new Plan();
        
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
            Connection conn = DriverManager.getConnection(url,props);
            
            Statement st;
            st = conn.createStatement();
             
            ResultSet rs = st.executeQuery("select nombre_plan, tipo_plan, "
                    + "descripcion from plan where nombre = '" + 
                        nombre + "' and tipo_plan = '" +tipo_plan+ "';");
            
            rs.next();
              
            plan = new Plan(rs.getString(1),
                     rs.getString(2), rs.getString(3));
                        
            
            
          conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return plan;
          
    }
    
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
