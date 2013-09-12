
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class Conexion {
    
    private static String url = "jdbc:postgresql:innova";
    private static String user = "gabriela";
    private static String pass = "wennicheinjungewar";
    
    /* Obtiene la conexion a la base de datos */
    
    public static Connection obtenerConn(){
        
        try{        
            Class.forName("org.postgresql.Driver");
            
        } catch (ClassNotFoundException e){
        
            System.out.println("El driver JDBC no esta instalado.");
            return null;
        }
                
        Connection conexion;
        
        try{             
            conexion = DriverManager.getConnection(url, user,pass);
            
        } catch (SQLException e){
            
            System.out.println("No se pudo conectar a la base de datos.");
            return null;   
        }
       
        return conexion;        
    } 
}
