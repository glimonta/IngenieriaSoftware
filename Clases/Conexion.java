
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Conexion {
    
    private static String url = "jdbc:postgresql://localhost/marcos";
    private static String user = "marcos";
    private static String pass = "postgres";
    
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
