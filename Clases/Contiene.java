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
    
    public void registrarContiene() throws SQLException {
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String insert = "insert into CONTIENE values ('"+ paquete.nombre +
                    "', '" + servicio.nombre + "', " + cantidad + ", " 
                    + costoAdicional + ");";
            stmt.executeUpdate(insert);
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
        
        
    }
    
    
    public static Contiene consultarContiene(String nomPack, String nomServ) 
            throws SQLException{
        
        Contiene cont = null;
        
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null){
            
            Statement stmt = null;
            
            String query = "select CANTIDAD, COSTO_ADICIONAL from CONTIENE " +
                    "where NOMBRE_PAQUETE = '" + nomPack + "' and " +
                    "NOMBRE_SERVICIO = '" + nomServ + "';";
            
            try{
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                Paquete pack = Paquete.consultarPaquete(nomPack);
                Servicio serv = Servicio.consultarServicio(nomServ);
                
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
    
    public void eliminarContiene() throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String delete = "delete from CONTIENE where NOMBRE_PAQUETE = '" +
                   this.paquete.nombre + "' and NOMBRE_SERVICIO = '" + 
                    this.servicio.nombre + "';";
            stmt.executeUpdate(delete);
            
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
    }
    
    
    public void modificarContiene() throws SQLException{
        
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
    
    @Override
    public String toString(){
        
        return "Cantidad: " + cantidad + ", Costo Adicional: " + costoAdicional
                + ", Paquete: " + paquete.toString() + ", Servicio: " 
                + servicio.toString();
        
    }
    
    
    
}
