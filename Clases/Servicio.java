import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Servicio {
    
    public String nombre;
    public String descripcion;
    public String tipoServicio;
    
    
    public Servicio(String nom, String desc, String tipo) {
        
        this.nombre = nom;
        this.descripcion = desc;
        this.tipoServicio = tipo;
    }
            
    public void resgistrarServicio() 
                   throws Exception{
        
        Connection conexion = null;

        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String insert = "insert into SERVICIO values ('" + nombre + "', '" +
                            descripcion + "', '" + tipoServicio + "');";
            stmt.executeUpdate(insert);
            
        }catch (SQLException e) {            
            System.out.println(e.getMessage());

            
        }finally {                       
            conexion.close();

        }     
    }
    
    public static Servicio consultarServicio(String nom) throws SQLException {
        
        Servicio servicio = null;
        
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null) {
            
            Statement stmt = null;
            
            String query = "select DESCRIPCION, NOMBRE_TIPO_SERVICIO " +
                           "from SERVICIO where NOMBRE_SERVICIO = '" + nom + "'";
            
            try {
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
            
                if (rs.next()) {
                    
                    servicio = new Servicio(nom,rs.getString("DESCRIPCION"),
                                            rs.getString("NOMBRE_TIPO_SERVICIO"));

                    
                }
            } catch (SQLException e) {
                
                System.out.println("SQL EXCEPTION: ConsultarServicio");
                
            } finally {
                
                if (stmt != null)
                    stmt.close();               
            }            
        }
        return servicio;
    }
    
    
    public void eliminarServicio() throws SQLException{
        
        Connection conexion = Conexion.obtenerConn();
        
        try{
            
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String delete = "delete from SERVICIO where NOMBRE_SERVICIO ='" 
                    + this.nombre + "';";
            stmt.executeUpdate(delete);  
            
        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
            
        } finally {                       
            conexion.close();

        }
        
        
    }
    
    public void modificarServicio() throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String update = "update SERVICIO set DESCRIPCION = '" 
                    + this.descripcion + "' where NOMBRE_SERVICIO = '" 
                    + this.nombre + "';";
            stmt.executeUpdate(update);
            
        }catch (SQLException e) {            
            System.out.println(e.getMessage());

            
        }finally {                       
            conexion.close();

        }     
        
        
    }
    
    
    @Override
    public String toString() {
        
        return "Nombre: " + nombre + ", Descripcion: " + descripcion 
                + ", Tipo de servicio: " + tipoServicio;
        
    }
    
    
}


