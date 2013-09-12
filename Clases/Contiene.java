import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Contiene {
    
    public int cantidad;
    public float costoAdicional;
    public Paquete paquete;
    public Servicio servicio;
    
    public Contiene(int cant, float costo, Paquete paquete, Servicio serv){
        this.cantidad = cant;
        this.costoAdicional = costo;
        this.paquete = paquete;
        this.servicio = serv;
    }
    
    public static void registrarContiene(int cant, float costo, Paquete pack, 
            Servicio serv) throws SQLException {
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String insert = "insert into CONTIENE values ('"+ pack.nombre +
                    "', '" + serv.nombre + "', " + cant + ", " + costo + ");";
            stmt.executeUpdate(insert);
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
        
        
    }
    
    
    public static Contiene consultarContiene(Paquete pack, Servicio serv) 
            throws SQLException{
        
        Contiene cont = null;
        
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null){
            
            Statement stmt = null;
            
            String query = "select CANTIDAD, COSTO_ADICIONAL from CONTIENE " +
                    "where NOMBRE_PAQUETE = '" + pack.nombre + "' and " +
                    "NOMBRE_SERVICIO = '" + serv.nombre + "';";
            
            try{
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                if (rs.next())
                    cont = new Contiene(Integer.parseInt(rs.getString("CANTIDAD")),
                            Float.parseFloat(rs.getString("COSTO_ADICIONAL")),
                            pack, serv);
                 
            } catch (SQLException e){
            
                System.out.println(e.getMessage());
            
            } finally {
                
                conexion.close();
            }
        }
        
        return cont;
        
    }
    
    public static void eliminarContiene(Paquete pack, Servicio serv) 
            throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String delete = "delete from CONTIENE where NOMBRE_PAQUETE = '" +
                    pack.nombre + "' and NOMBRE_SERVICIO = '" + 
                    serv.nombre + "';";
            stmt.executeUpdate(delete);
            
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
    }
    
    
    public void actualizarContiene() throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String update = "update CONTIENE set CANTIDAD = "+
                    this.cantidad + ", COSTO_ADICIONAL = " + this.costoAdicional + 
                    " where NOMBRE_PAQUETE = '" + this.paquete.nombre + 
                    "' and NOMBRE_SERVICIO = '" + this.servicio.nombre + "';";
            stmt.executeUpdate(update);
            
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
    }
    
    
    
}
