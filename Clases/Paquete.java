import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Paquete {
    
    public String nombre;
    public String descripcion;
    
    
    public Paquete(String nom, String desc) {
        
        this.nombre = nom;
        this.descripcion = desc;
        
    }
    
    
    public static void registrarPaquete(String nom, String desc) throws Exception {
        
                
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String insert = "insert into PAQUETE values ('" + nom + "', '" +
                            desc + "');";
            stmt.executeUpdate(insert);
            
        }catch (SQLException e) {            
            System.out.println(e.getMessage());

            
        }finally {                       
            conexion.close();

        }
        
    }
    
    public static Paquete consultarPaquete(String nom)throws SQLException{
    
        Paquete paquete = null;
        
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null) {
            
            Statement stmt = null;
            
            String query = "select DESCRIPCION from PAQUETE where " + 
                           "NOMBRE_PAQUETE = '" + nom + "'";
            
            try {
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
            
                if (rs.next()) {
                    
                    paquete = new Paquete(nom,rs.getString("DESCRIPCION"));                    
                    
                }
                
            } catch (SQLException e) {
                
                System.out.println("SQL EXCEPTION: ConsultarPaquete");
                
            } finally {
                
                if (stmt != null)
                    stmt.close();               
            }            
        }
        
        return paquete;
    
    
    }
    
    
    public static void eliminarPaquete(String nom) throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String delete = "delete from PAQUETE where NOMBRE_PAQUETE ='" 
                    + nom + "';";
            stmt.executeUpdate(delete);
            
        }catch (SQLException e) {            
            System.out.println(e.getMessage());

            
        }finally {                       
            conexion.close();

        }

    }
    
    public void actualizarPaquete() throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String update = "update PAQUETE set DESCRIPCION = '" 
                    + this.descripcion + "' where NOMBRE_PAQUETE = '" 
                    + this.nombre + "';";
            stmt.executeUpdate(update);
            
        }catch (SQLException e) {            
            System.out.println(e.getMessage());

            
        }finally {                       
            conexion.close();

        }     
        
        
    }
    
    /*Imprime los servicios asociados a un Paquete*/
    // Falta pasarlo a lista.
    public static void ListarServicios(String nom) throws SQLException {
        
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null) {
            
            Statement stmt = null;
            
            String query = "select NOMBRE_SERVICIO from CONTIENE where " +
                           "NOMBRE_PAQUETE = '" + nom + "'";            
            
            try {
            
                 
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                System.out.println("Lista de Servicios: ");
                
                while (rs.next()) {
                    

                    String nomServicio = rs.getString("NOMBRE_SERVICIO");
                    System.out.println(nomServicio);
                }
                
            } catch (SQLException e) {
                System.out.println("SQL Exception: ListarServicios");
                
            } finally {
                
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    }   

    
}
