
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Clase Efectivo: contiene los metodos necesarios para manejar los contenidos de
 * la tabla efectivo en la base de datos.
 */
public class Efectivo extends FormaPago {
    Integer numeroPago; //numero de pago
    
    /**
     * Constructor de la clase efectivo
     * @param id identificador del efectivo
     * @param numPago numero de pago asociado al efectivo
     */
    public Efectivo(Integer id, Integer numPago) {
        super(id);
        this.numeroPago = numPago;
    }
    
    /**
     * Metodo que permite registrar efectivo en la base de datos
     */
    void registrarEfectivo() {
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            // Se moviliza la clave postiza del efectivo para la foma de pago
            ResultSet rs = st.executeQuery("select max(postiza_pago) from forma_pago;");
            
            rs.next();
            
            Integer max = rs.getInt(1) + 1;
            
            // Se inserta en la tabla forma de pago la nueva postiza asociada al efectivo
            st.execute("insert into forma_pago values (" + max.toString() + ");");
            // Se agrega el nuevo efectivo a la tabla efectivo
            st.execute("insert into efectivo values ("+ this.numeroPago + ", " 
                    + max.toString() + ");");
            
            // Se cierra la conexion a la base de datos
            conn.close();
            
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Metodo que devuelve una instancia de la base de datos en un objeto de la
     * clase efectivo
     * @param numPago numero del pago
     * @return una instancia de efectivo o null si no se encontro dicha instancia
     */
    static Efectivo consultarEfectivo(Integer numPago) {
        // Inicializamos un objeto Efectivo en null
        Efectivo efectivo = null;
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            // Buscamos el efectivo en la base de datos
            ResultSet rs = st.executeQuery("select * from efectivo where "
                    + " nro_pago = " + numPago + ";");
            
            rs.next();
            // Creamos un nuevo objeto efectivo con los datos obtenidos.
            efectivo = new Efectivo(Integer.parseInt(rs.getString(2)),
                     Integer.parseInt(rs.getString(1)));
          // Se cierra la conexion de la base de datos
          conn.close();
            
        } catch (SQLException ex) {
           // Si hay una excepcion se imprime un mensaje
            System.err.println(ex.getMessage());
        }
        
        // Retorna el efectivo que saco de la base de datos y en caso de no
        // conseguirlo retorna null.
        return efectivo;
    }
    
    /**
     * Permite modificar la informacion del efectivo en la base de datos.
     */
    void modificarEfectivo() {
        // Establecemos la conexion con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            // Modificamos el efectivo de la base de datos            
            st.executeUpdate("update efectivo set postiza_pago ='"
                     + this.id + "' where nro_pago ='" + this.numeroPago + "';");
            
          } catch (SQLException ex) {
            // Si hay una excepcion se imprime un mensaje
              System.err.println(ex.getMessage());
          }
    }
    
    /**
     * Permite eliminar la informacion del efectivo en la base de datos.
     */
    void eliminarEfectivo() {
        // Establecemos la conexion con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
            // Eliminamos el efectivo de la base de datos            
            st.execute("delete from efectivo where nro_pago ='" + 
                    this.numeroPago + "';");
        
        } catch (SQLException ex) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(ex.getMessage());
        }
    }
    
        
    /**
     * Permite obtener una representacion en string un elemento de la clase Efectivo.
     * @return String retorna el string que representa al objeto.
     */
    @Override
    public String toString() {
        // Se devuelve el formato de salida
        return "ID de Pago: " + this.id.toString() + ", Numero de Pago: " + this.numeroPago.toString();
    }

}
