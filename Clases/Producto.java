import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producto {
    
    public int codigoProd = 0; 
    public String modelo;
    public Cliente cliente;
    
    
    public Producto (int codigo, String model, Cliente cliente) {
        
        this.codigoProd = codigo;
        this.modelo = model;
        this.cliente = cliente;

    }
    
    public void registrarProducto() throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String insert = "insert into PRODUCTO values ("+ codigoProd +
                    ", '" + modelo + "');";
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
            
            String query = "select P.NOMBRE_MODELO, C.CI from PRODUCTO P, "
                    + "ES_DUENIO E, CLIENTE C where P.ID = " + codigo 
                    + " and E.ID =  P.ID and C.CI = E.CI;";
            
            try{
                Cliente client = null;
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                if (rs.next())
                    client = Cliente.consultarCliente(
                            Integer.parseInt(rs.getString("CI")));
                    
                    product = new Producto(codigo, 
                            rs.getString("NOMBRE_MODELO"), client);
                 
            } catch (SQLException e){
            
                System.out.println(e.getMessage());
            
            } finally {
                
                conexion.close();
            }
        }
        
        return product;
        
    }
    
    public void eliminarProducto() throws SQLException{
        
        Connection conexion = null;
        
        try {
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            
            String delete = "delete from PRODUCTO where ID = " +
                    this.codigoProd + ";";
            stmt.executeUpdate(delete);
            
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        } finally {
            conexion.close();
            
        } 
    }
    
    public void modificarProducto() throws SQLException{
        
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
    
    
    public Plan obtenerPlanActual() throws SQLException{

        Plan plan = null;
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null){
            
            Statement stmt = null;
            
            String query = "select NOMBRE_PLAN, TIPO_PLAN from ESTA_AFILIADO " +
                    "where ID = " + this.codigoProd + " and FECHA_FIN is null;";
            
            try{
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                if (rs.next()){
                    
                    String nom = rs.getString("NOMBRE_PLAN");
                    String tipo = rs.getString("TIPO_PLAN");
                    plan = Plan.consultarPlan(nom, tipo);
                    
                    
                } else {
                    
                    
                    //Obteniene la fecha actual del sistema
                    Calendar calendar = new GregorianCalendar();
                    
                    //Paso la fecha actual a una fecha de sql para comparar.
                    Date fechaActual = new Date(calendar.getTimeInMillis());
                    
                    query = "select NOMBRE_PLAN, TIPO_PLAN, FECHA_INIC, FECHA_FIN " +
                        "from ESTA_AFILIADO where ID = " + this.codigoProd + ";";
                    
                    rs = stmt.executeQuery(query);
                    
                    while (rs.next()){
                        
                        Date fecha_inic = Date.valueOf(rs.getString("FECHA_INIC"));
                        Date fecha_fin = Date.valueOf(rs.getString("FECHA_FIN"));
                        
                        if (fecha_inic.compareTo(fechaActual) <= 0 && 
                                fecha_fin.compareTo(fechaActual) > 0){
                            
                            plan = Plan.consultarPlan(rs.getString("NOMBRE_PLAN")
                                    , rs.getString("TIPO_PLAN"));
                            
                            return plan; 
                        }
                    } 
                }
            } catch (SQLException e){
            
                System.out.println(e.getMessage());
            } finally {
                
                conexion.close();
                
            }
        }
        
        return plan;
        
    }
    

    public Factura obtenerFacturaActual() throws SQLException{
        
        Factura factura = null;
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
                        
                        factura = Factura.consultarFactura(this, fechaFact);
                    }
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            } finally {
                
                conexion.close();
                
            }
        }
        
        return factura;
        
    }
    
    public ArrayList<Consumo> listarConsumos(Date inicio, Date fin) 
            throws SQLException {
        
        ArrayList<Consumo> list = new ArrayList();
        Connection conexion = Conexion.obtenerConn();
        
        
        if(conexion != null){
            
            Statement stmt = null;
            
            String query = "select FECHA, CANTIDAD, NOMBRE_SERVICIO from "+
                    "CONSUME where ID = " + this.codigoProd + ";";
            
            try {
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                while (rs.next()){
                    
                    Date fechaConsumo = Date.valueOf(rs.getString("FECHA"));
                    
                    if (inicio.compareTo(fechaConsumo) <= 0 &&
                            fin.compareTo(fechaConsumo) > 0) {
                    
                        Servicio serv = Servicio.consultarServicio(
                                rs.getString("NOMBRE_SERVICIO"));

                        Consumo cons = new Consumo(
                                Integer.parseInt(rs.getString("CANTIDAD")), 
                                Date.valueOf(rs.getString("FECHA")), this, serv);

                        list.add(cons);
                    }
                }
                             
            } catch (SQLException e){
                System.out.println(e.getMessage());
                
            } finally {
                
                conexion.close();     
            }  
        }
        return list;
    }
    
    
    public ArrayList<Afiliacion> listarPlanesAfiliados() throws SQLException{
        
        ArrayList <Afiliacion> list = new ArrayList();
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null) {
            
            Statement stmt = null;
            String query = "select NOMBRE_PLAN, TIPO_PLAN, FECHA_INIC," 
                    + "FECHA_FIN from ESTA_AFILIADO where ID = " + codigoProd + ";";
            
            try {
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                while (rs.next()){
                    
                    Plan plan = Plan.consultarPlan(rs.getString("NOMBRE_PLAN"), 
                            rs.getString("TIPO_PLAN"));
                    Date fechaI = Date.valueOf(rs.getString("FECHA_INIC"));
                    Date fechaF = Date.valueOf(rs.getString("FECHA_FIN"));
                    
                    
                    Afiliacion afil = new Afiliacion(fechaI,fechaF, plan, this);
                    
                    list.add(afil);
                }
            } catch (SQLException e) {
                
                System.out.println(e.getMessage());
                
            } finally {
                
                conexion.close(); 
            }
        }
        return list;
      }
    
    
    public ArrayList<Factura> listarFacturas() throws SQLException{
       
        ArrayList <Factura> list = new ArrayList();
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null) {
            
            Statement stmt = null;
            String query = "select FECHA from FACTURA where ID = " 
                    + codigoProd + ";";
            
            try {
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                while (rs.next()){
                    
                    Date fecha = Date.valueOf(rs.getString("FECHA"));
                    Factura factura = Factura.consultarFactura(this, fecha);
                    list.add(factura);
                    
                }
            } catch (SQLException e) {
                
                System.out.println(e.getMessage());
                
            } finally {
                
                conexion.close(); 
            }
        }
        return list;
    }
    
    
    public ArrayList<Posee> listarServiciosAdicionalesContratados() 
            throws SQLException{
        
        ArrayList <Posee> list = new ArrayList();
        Connection conexion = Conexion.obtenerConn();
        
        if (conexion != null) {
            
            Statement stmt = null;
            String query = "select NOMBRE_SERVICIO, FECHA_INIC from " 
                    + "POSEE where ID = " + codigoProd + ";";
            
            try {
                
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                
                while (rs.next()){
                    
                    Date fecha = Date.valueOf(rs.getString("FECHA_INIC"));
                    Posee pos = Posee.consultarPosee(codigoProd, 
                            rs.getString("NOMBRE_SERVICIO"), fecha);
                    list.add(pos);
                    
                }
            } catch (SQLException e) {
                
                System.out.println(e.getMessage());
                
            } catch (ParseException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                
                conexion.close(); 
            }
        }
        return list;
        
    }
    
    @Override
    public String toString() {
        
        return "Codigo del producto: " + codigoProd + ", Modelo: " + modelo 
                + ", Cliente: " + cliente.toString();
        
    }
    
}
