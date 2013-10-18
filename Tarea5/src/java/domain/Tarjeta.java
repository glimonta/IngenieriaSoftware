
package domain;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Clase Tarjeta: contiene los metodos necesarios para manejar los contenidos de
 * la tabla tarjeta en la base de datos.
 */
public class Tarjeta extends FormaPago {
    String numero; //numero de la tarjeta de creditos
    String banco; //banco emisor de a tareta de credito
    Date fechaVenc; //fecha de vencimiento de la tarjeta
    String tipoTarjeta; // tipo de la tarjeta
    Integer codSeguridad; //codigo de seguridad de la tarjeta
    String cedulaTitular; //cedula del titular
    String marca; //marca de la tarjeta
    
    /**
     * Constructor de Tarjeta
     * @param id id asociado a la tarjeta
     * @param numero numero de la tarjeta
     * @param banco banco asociado a la tarjeta
     * @param fechaVenc fecha de vencmiento de la tarjeta
     * @param tipoTarjeta tipo de la tarjeta
     * @param codSeguridad codigo de seguridad de la tarjeta
     * @param cedulaTitular cedula del titular
     * @param marca marca de la tarjeta
     */
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
    
    /**
     * Permite obtener una representacion en string un elemento de la clase Factura.
     * @return String retorna el string que representa al objeto.
     */
    @Override
    public String toString() {
        //Se devuelve el string de formato
        return "ID de Pago: " + this.id + ", Numero de Tarjeta: "
                + this.numero + ", Banco: " + this.banco
                + ", Fecha de Vencimiento: " + this.fechaVenc.toString()
                + ", Tipo de Tarjeta: " + this.tipoTarjeta
                + ", Codigo de Seguridad: " + this.codSeguridad.toString()
                + ", Cedula del Titular: " + this.cedulaTitular
                + ", Marca: " + this.marca;
    }
    
    /**
     * Metodo que permite agregar una tarjeta a la base de datos
     */
    void RegistrarTarjeta() {
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
             // Se moviliza la clave postiza del tarjeta para la foma de pago
            ResultSet rs = st.executeQuery("select max(postiza_pago) from forma_pago;");
            
            rs.next();
            
            Integer max = rs.getInt(1) + 1;
            // Se inserta en la tabla forma de pago la nueva postiza asociada a la tarjeta
            st.execute("insert into forma_pago values (" + max.toString() + ");");
            // Se agrega la nueva tarjeta a la tabla tarjeta
            st.execute("insert into tarjeta values ('"+ this.numero + "', '" 
                    + this.marca + "', '" + this.banco + "', '" + this.codSeguridad
                    + "', '" + this.fechaVenc.toString() + "', '" 
                    + this.tipoTarjeta + "', '" + max + "', '"
                    + this.cedulaTitular + "');");
            
            // Cerramos la conexion
            conn.close();
            
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Metodo que genera una instancia de la clase Tarjeta
     * @param numero numero de la tarjeta
     * @return devuelve una instancia de la base de datos hecha objeto
     * @throws ParseException para las excepciones de parseo
     */
    static Tarjeta consultarTarjeta(String numero) throws ParseException {
       
        Tarjeta tarjeta = null;
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            // Buscamos la tarjeta en la base de datos
            ResultSet rs = st.executeQuery("select * from tarjeta where "
                    + " numero = '" + numero + "';");
            
            // Verificamos que el query no sea nulo
            if (rs.next()) {
                // Creamos un nuevo objeto del tipo Date con la fecha de
                // consumo conseguida
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(5));
                java.sql.Date fechaV = new java.sql.Date(utilDate.getTime());
                
                // Creamos un nuevo objeto tarjeta con los datos obtenidos.
                tarjeta = new Tarjeta(rs.getInt(7), rs.getString(1), rs.getString(3),
                        fechaV, rs.getString(6), rs.getInt(4), rs.getString(8),
                        rs.getString(2));
            }
            
            //Se cierra la conexion de la base de datos
            conn.close();
            
        } catch (SQLException ex) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(ex.getMessage());
        }
        
        return tarjeta;
    }
    
    /**
     * Metodo que permite modificar una tarjeta de la tabla tarjeta
     */
    void modificarTarjeta() {
         // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
            // Modificamos la tarjeta de la base de datos            
            st.executeUpdate("update tarjeta set marca ='"
                    + this.marca + "', banco ='" + this.banco + "', cod_seguridad = '"
                    + this.codSeguridad + "', fecha_venc = '" + this.fechaVenc.toString()
                    + "', tipo_tarjeta ='" + this.tipoTarjeta + "', postiza_pago = '"
                    + this.id + "', ci_titular ='" + this.cedulaTitular
                    + "' where numero ='" + this.numero + "';");
            
          } catch (SQLException ex) {
            // Si hay una excepcion se imprime un mensaje
              System.err.println(ex.getMessage());
          }
    }
    
    /**
     * Metodo que permite eliminar una tarjeta de la tabla tarjeta
     */
    void eliminarTarjeta() {
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
            // Eliminamos la tarjeta de la base de datos            
            st.execute("delete from tarjeta where numero ='" + 
                    this.numero + "';");
        
        } catch (SQLException ex) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(ex.getMessage());
        }
    }
    

}
