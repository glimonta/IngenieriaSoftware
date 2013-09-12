
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class Tarjeta extends FormaPago {
    String numero;
    String banco;
    Date fechaVenc;
    String tipoTarjeta;
    Integer codSeguridad;
    String cedulaTitular;
    String marca;
    
    public Tarjeta() {
        super();
        this.numero = null;
        this.banco = null;
        this.fechaVenc = null;
        this.tipoTarjeta = null;
        this.codSeguridad = null;
        this.cedulaTitular = null;
        this.marca = null;
    }
    
    public Tarjeta(Integer id, String numero, String banco,
            Date fechaVenc, String tipoTarjeta, Integer codSeguridad,
            String cedulaTitular, String marca) {
        super(id);
        this.numero = numero;
        this.banco = banco;
        this.fechaVenc = fechaVenc;
        this.tipoTarjeta = tipoTarjeta;
        this.codSeguridad = codSeguridad;
        this.cedulaTitular = cedulaTitular;
        this.marca = marca;
    }
    
    @Override
    public String toString() {
        return "ID de Pago: " + this.id + ", Numero de Tarjeta: "
                + this.numero + ", Banco: " + this.banco
                + ", Fecha de Vencimiento: " + this.fechaVenc.toString()
                + ", Tipo de Tarjeta: " + this.tipoTarjeta
                + ", Codigo de Seguridad: " + this.codSeguridad.toString()
                + ", Cedula del Titular: " + this.cedulaTitular
                + ", Marca: " + this.marca;
    }
    
    void RegistrarTarjeta() {
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
            Connection conn = DriverManager.getConnection(url,props);
            
            Statement st;
            st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("select max(postiza_pago) from forma_pago;");
            
            rs.next();
            
            Integer max = rs.getInt(1) + 1;
            
            st.execute("insert into forma_pago values (" + max.toString() + ");");
                    
            st.execute("insert into tarjeta values ('"+ this.numero + "', '" 
                    + this.marca + "', '" + this.banco + "', '" + this.codSeguridad
                    + "', '" + this.fechaVenc.toString() + "', '" 
                    + this.tipoTarjeta + "', '" + max + "', '"
                    + this.cedulaTitular + "');");
            
            
            conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    static Tarjeta consultarTarjeta(String numero) throws ParseException {
        Tarjeta tarjeta = new Tarjeta();
        
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
            Connection conn = DriverManager.getConnection(url,props);
            
            Statement st;
            st = conn.createStatement();
             
            ResultSet rs = st.executeQuery("select * from tarjeta where "
                    + " numero = '" + numero + "';");
            
            rs.next();
            
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(5));
            java.sql.Date fechaV = new java.sql.Date(utilDate.getTime());
              
            tarjeta = new Tarjeta(rs.getInt(7), rs.getString(1), rs.getString(3),
                    fechaV, rs.getString(6), rs.getInt(4), rs.getString(8),
                    rs.getString(2));
            
          conn.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        
        return tarjeta;
    }
    
    void modificarTarjeta() {
        try {
            String url = "jdbc:postgresql:innova";
            Properties props = new Properties();
            props.setProperty("user","gabriela");
            props.setProperty("password","wennicheinjungewar");
             try (Connection conn = DriverManager.getConnection(url,props)) {
                 Statement st;
                 st = conn.createStatement();
                 
                 st.executeUpdate("update tarjeta set marca ='"
                         + this.marca + "', banco ='" + this.banco + "', cod_seguridad = '"
                         + this.codSeguridad + "', fecha_venc = '" + this.fechaVenc.toString()
                         + "', tipo_tarjeta ='" + this.tipoTarjeta + "', postiza_pago = '"
                         + this.id + "', ci_titular ='" + this.cedulaTitular
                         + "' where numero ='" + this.numero + "';");
                         
            }
            
          } catch (SQLException ex) {
              System.err.println(ex.getMessage());
          }
    }
    
    void eliminarTarjeta() {
        String url = "jdbc:postgresql:innova";
        Properties props = new Properties();
        props.setProperty("user","gabriela");
        props.setProperty("password","wennicheinjungewar");
       
        try (Connection conn = DriverManager.getConnection(url,props)) {
            Statement st;
            st = conn.createStatement();
                        
            st.execute("delete from tarjeta where numero ='" + 
                    this.numero + "';");
        
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
   /* public static void main(String[] args) throws ParseException {
        Date fechaV = new Date(2015-1900,04,26);
        Tarjeta t = new Tarjeta(46, "4567894652365", "mercantil", fechaV,
                "CREDITO", 456, "21030282", "Visa");
        
        t.RegistrarTarjeta();
        
        t = new Tarjeta();
        
        t = consultarTarjeta("4567894652365");
        System.out.println(t.toString());
        
        t.cedulaTitular = "23056946";
        
        t.modificarTarjeta();
        
        t = consultarTarjeta("4567894652365");
        System.out.println(t.toString());
        
        t.eliminarTarjeta();
    }*/
}
