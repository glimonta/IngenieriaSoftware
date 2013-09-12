import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ServicioAdicional extends Servicio {
    
    public float tarifa = 0;
    public int cantidadAdicional = 0;
    public String tipoPlan;
    
    
    public ServicioAdicional(String nom, String desc, String tipo, float tarifa, 
                             int cantAd, String tipoPlan) {
     
        super(nom,desc,tipo);
        this.tarifa = tarifa;
        this.cantidadAdicional = cantAd;
        this.tipoPlan = tipoPlan;
    }
    
    /** SI EXPLOTA PUEDE SER POR EL FLOAT **/
    public static void registrarServicioAd(String nom, float tarifa, int cantAd, 
                                       String tipoPlan) throws Exception {
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String insert = "insert into ADICIONAL values ('" + nom + "', '" +
                            tarifa + "', '" + cantAd + "', '" + tipoPlan + "');";
            stmt.executeUpdate(insert);
            
        }catch (SQLException e) {            
            System.out.println(e.getMessage());
            
        }finally {                       
            conexion.close();
        }   
    }
    
    public static ServicioAdicional consultarServicioAd(String nom) throws SQLException {
        
        Servicio servicio = consultarServicio(nom);
        
        if (servicio != null) {
            
            ServicioAdicional servAd = null;
        
            Connection conexion = Conexion.obtenerConn();

            if (conexion != null) {

                Statement stmt = null;

                String query = "select TARIFA, CANTIDAD_ADICIONAL, TIPO_PLAN " +
                               "from ADICIONAL where NOMBRE_SERVICIO = '" + nom + "'";

                try {

                    stmt = conexion.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String desc = servicio.descripcion;
                    String tipoServ = servicio.tipoServicio;
                    

                    if (rs.next()) {                   

                        servAd = new ServicioAdicional(nom,desc,tipoServ,
                                     rs.getFloat("TARIFA"),
                                     rs.getInt("CANTIDAD_ADICIONAL"),
                                     rs.getString("TIPO_PLAN"));

                    }
                } catch (SQLException e) {

                    System.out.println("SQL EXCEPTION: ConsultarServicioAd");

                } finally {

                    if (stmt != null)
                        stmt.close();               
                }            
            }
            
            return servAd;
        }
        
        
        return null;
        
        
    }
    
    public static void eliminarServicioAd(String nom) throws Exception {
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String delete = "delete from ADICIONAL where NOMBRE_SERVICIO = '" +
                    nom + "';";
            stmt.executeUpdate(delete);
            
        }catch (SQLException e) {            
            System.out.println(e.getMessage());
            
        }finally {                       
            conexion.close();
        }   
    }
    
    public void actualizarServicioAd() throws Exception {
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String update = "update ADICIONAL set TARIFA = " +
                    this.tarifa + ", CANTIDAD_ADICIONAL = " + 
                    this.cantidadAdicional + ", TIPO_PLAN = '" +
                    this.tipoPlan + "' where NOMBRE_SERVICIO = '" +
                    this.nombre +"';";
            stmt.executeUpdate(update);
            
        } catch (SQLException e) {            
            System.out.println(e.getMessage());
            
        } finally {                       
            conexion.close();
        }   
    }
    
    
}
