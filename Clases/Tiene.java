import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Tiene {
    
    public Date fechaInicio;
    public Date fechaFin;
    public float costo;
    public Plan plan;
    public Paquete paquete;
    
    public Tiene (Date fechaI, Date fechaF, float costo, Plan plan, 
            Paquete pack){
        
        this.fechaInicio = fechaI;
        this.fechaFin = fechaF;
        this.costo = costo;
        this.paquete = pack;
        this.plan = plan;
        
    }
    
    public void registrarTiene() throws SQLException {
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            
             
            String insert = "insert into TIENE values ('" + plan.nombre +
                    "', '" + plan.tipoPlan + "', '" + paquete.nombre 
                    + "', " + costo + ", DATE '" + fechaInicio.toString() 
                    + "', ";
            
            Tiene t = null;
            
            if (fechaFin != null)
                insert = insert + "DATE '" + fechaFin.toString() + "');";
            else
                insert = insert + "null);";
            
            stmt.executeUpdate(insert);
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
          
    }
    
    public static Tiene consultarTiene(String nomPlan, String nomPack,
            String tipoPlan, Date fechaI) throws SQLException{
        
        Tiene tiene = null;
        
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null){
            
            Statement stmt = null;
            
            String query = "select COSTO, FECHA_FIN from TIENE " +
                    "where NOMBRE_PLAN = '" + nomPlan + "' and " +
                    "TIPO_PLAN = '" + tipoPlan + "' and "  +
                    "NOMBRE_PAQUETE = '" + nomPack  +  "' and " +
                    "FECHA_INIC = DATE '" + fechaI.toString() + "';";
            
            try{
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                Plan plan = Plan.consultarPlan(nomPlan, tipoPlan);
                Paquete pack = Paquete.consultarPaquete(nomPack);
                
                
                String fechaFinString = null;
                if (rs.next())
                    
                    fechaFinString = rs.getString("FECHA_FIN");
                    
                    if (fechaFinString != null)
                    
                        tiene = new Tiene(fechaI, 
                                Date.valueOf(fechaFinString), 
                                Float.parseFloat(rs.getString("COSTO")), plan, 
                                pack);
                    
                    else
                        
                        tiene = new Tiene(fechaI, null, 
                                Float.parseFloat(rs.getString("COSTO")), plan, 
                                pack);
                    
                 
            } catch (SQLException e){
            
                System.out.println(e.getMessage());
            
            } finally {
                
                conexion.close();
            }
        }
        
        return tiene;
        
    }
    
    public void eliminarTiene() throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String delete = "delete from TIENE where NOMBRE_PLAN = '" +
                   this.plan.nombre + "' and TIPO_PLAN = '" + 
                   this.plan.tipoPlan + "' and NOMBRE_PAQUETE = '" +
                   this.paquete.nombre + "' and FECHA_INIC = DATE '" +  
                   this.fechaInicio.toString() + "';";
            stmt.executeUpdate(delete);
            
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
    }
    
    
    public void modificarTiene() throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            String update;
            
            
            if (fechaFin != null)
            
                update = "update TIENE set COSTO = "+
                        this.costo + ", FECHA_FIN = DATE '" + 
                        this.fechaFin.toString() + "' where NOMBRE_PLAN = '" + 
                        this.plan.nombre + "' and NOMBRE_PAQUETE = '" + 
                        this.paquete.nombre + "' and TIPO_PLAN = '" +  
                        this.plan.tipoPlan + "' and FECHA_INIC = DATE '" + 
                        this.fechaInicio.toString()  + "';";
            
            else
                
                update = "update TIENE set COSTO = "+
                        this.costo + ", FECHA_FIN = NULL where NOMBRE_PLAN = '" + 
                        this.plan.nombre + "' and NOMBRE_PAQUETE = '" + 
                        this.paquete.nombre + "' and TIPO_PLAN = '" +  
                        this.plan.tipoPlan + "' and FECHA_INIC = DATE '" + 
                        this.fechaInicio.toString()  + "';";
            
            stmt.executeUpdate(update);
            
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
    }
    
    
    @Override
    public String toString(){
        
        String res = "Nombre del Plan: " + plan.nombre + ", Tipo del plan: " +
                plan.tipoPlan + ", Nombre del paquete: " + paquete.nombre + 
                ", Costo: " + costo + ", Fecha de inicio: " + 
                fechaInicio.toString();
        
        if (fechaFin != null)
            res = res + ", Fecha fin: " + fechaFin.toString();
        
        return res;
        
        
    }
    
    
}
