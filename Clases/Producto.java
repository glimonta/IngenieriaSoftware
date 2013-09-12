import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class Producto {
    
    public int codigoProd = 0; 
    public String modelo;
    //public Cliente cliente;
    
    
    public Producto (int codigo, String model) {
        
        this.codigoProd = codigo;
        this.modelo = model;

    }
    
    public static void registrarProducto(int codigo, String model) throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String insert = "insert into PRODUCTO values ("+ codigo +
                    ", '" + model + "');";
            stmt.executeUpdate(insert);
            
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
    }
    
    public static Producto consultarProducto(int codigo) throws SQLException{
        
        Producto product = null;
        
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null){
            
            Statement stmt = null;
            
            String query = "select NOMBRE_MODELO from PRODUCTO where " +
                    "ID = " + codigo + ";";
            
            try{
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                if (rs.next())
                    product = new Producto(codigo, 
                            rs.getString("NOMBRE_MODELO"));
                 
            } catch (SQLException e){
            
                System.out.println(e.getMessage());
            
            } finally {
                
                conexion.close();
            }
        }
        
        return product;
        
    }
    
    public static void eliminarProducto(int codigo) throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String delete = "delete from PRODUCTO where ID = " +
                    codigo + ";";
            stmt.executeUpdate(delete);
            
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
    }
    
    public void actualizarProducto() throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String update = "update PRODUCTO set NOMBRE_MODELO = '"+
                    this.modelo + "' where ID = " + this.codigoProd + ";";
            stmt.executeUpdate(update);
            
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
    }
    
    //Debería devolver un Plan como objeto, I guess.
    
    //TERMINARRRRRRRRRRRRRRRRR
    
    public void obtenerPlanActual() throws SQLException{

        //Plan plan = null;
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null){
            
            Statement stmt = null;
            
            String query = "select NOMBRE_PAN, TIPO_PLAN from ESTA_AFILIADO " +
                    "where ID = " + this.codigoProd + " and FECHA_FIN is null;";
            
            try{
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                if (rs.next()){
                    /*Aquí crearía el plan para devolberlo*/
                    
                    String nom = rs.getString("NOMBRE_PLAN");
                    String tipo = rs.getString("TIPO_PLAN");
                    query = "select DESCRIPCION from PLAN where NOMBRE_PLAN = '" +
                            nom + "' and TIPO_PLAN = '" + tipo + "';";
                    
                    stmt = conexion.createStatement();
                    rs = stmt.executeQuery(query);
                    
                    /*if (rs.next())
                      //Creo el plan
                      plan = new Plan()
                    */
                    
                } else {
                    
                    
                    //Obteniene la fecha actual del sistema
                    Calendar calendar = new GregorianCalendar();
                    
                    //Paso la fecha actual a una fecha de sql para comparar.
                    Date fechaActual = new Date(calendar.getTimeInMillis());
                    
                    
                    query = "select NOMBRE_PAN, TIPO_PLAN, FECHA_INIC, FECHA_FIN" +
                        "from ESTA_AFILIADO where ID = " + this.codigoProd + ";";
                    
                    while (rs.next()){
                        
                        Date fecha_inic = Date.valueOf(rs.getString("FECHA_INIC"));
                        Date fecha_fin = Date.valueOf(rs.getString("FECHA_FIN"));
                        
                        if (fecha_inic.compareTo(fechaActual) <= 0 && 
                                fecha_fin.compareTo(fechaActual) > 0){
                            
                            /* Creo el plan*/
                            
                            return;
                            
                        }
                        
                    }
                    
                    
                }
            } catch (SQLException e){
            
                System.out.println(e.getMessage());
            
            }
        }        
    }
    
    //Devuelve la factura como objeto, I guess
    //TERMINARRRRRRR
    public void obtenerFacturaActual() throws SQLException{
        
        //Factura factura = null;
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null){
            
            Statement stmt = null;
            String query = "select FECHA, MONTO_TOTAL from FACTURA " +
                    "where ID = " + this.codigoProd + ";";
            
            try{
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                Calendar calendar = new GregorianCalendar();
                
                Date fechaActual = new Date (calendar.get(Calendar.YEAR), 
                        calendar.get(Calendar.MONTH) + 1, 1);

                
                while (rs.next()){
                    
                    Date fechaFact = Date.valueOf(rs.getString("FECHA"));
                    
                    if (fechaFact.compareTo(fechaActual) == 0){
                        
                        //factura = new Factura()   !!!!!!!!!!
                    }
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }  
    }
    
    /*public List<Consumo> listarConsumos(Calendar inicio, Calendar fin){
        return null;
    }*/
    
    /*public List<Plan> listarPlanesAfiliados(){
        return null;
      }
     */
    
    /*public List<Factura> listarFacturas(){
       
        return null;
       }
     */
    
    /*public List<Posee> listarServiciosAdicionalesContratados(){
        
        return null;
        
    }*/
    
    
    
}
