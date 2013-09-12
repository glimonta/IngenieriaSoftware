
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    public Posee() {
        fechaInicio = null;
        adicional = new ServicioAdicional();
        producto = new Producto();
    }
    
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
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
            Connection conn = DriverManager.getConnection(url,props);
            
            Statement st;
            st = conn.createStatement();
            
            st.execute("insert into posee values ('"+ this.producto.codigoProd +
                    "', '" + this.servicio.nombre + "', '" + this.fecha.toString() + 
                    "', '" + this.cantidad + "');");
            
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
}
