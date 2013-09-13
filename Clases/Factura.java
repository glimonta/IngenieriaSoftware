
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase factura: contiene los metodos necesarios para manejar los contenidos de
 * la tabla factura en la base de datos.
 */
public class Factura {
    Date fecha; // Fecha de la factura
    double montoTotal; // monto de la factura
    ArrayList<String> comentarios; // comentarios asociados a la factura
    Producto producto; //producto asociado a la factura
    
    /**
     * Constructor de la clase factura
     * @param fecha fecha en la que se expide la factura
     * @param montoTotal monto que englobatodos los consumos y tarifas por servicios prestados
     * @param comentarios detalles de facturacion
     * @param producto producto asociado a la factura
     */
    public Factura(Date fecha, double montoTotal, ArrayList<String> comentarios, Producto producto) {
        this.fecha = fecha;
        this.montoTotal = montoTotal;
        this.comentarios = comentarios;
        this.producto = producto;
    }
    
    /**
     * Permite obtener una representacion en string un elemento de la clase Factura.
     * @return String retorna el string que representa al objeto.
     */
    @Override
    public String toString() {
        String comentarios = "";
        
        // se obtienen todos los comentarios de la factura
        for (int i=0; i < this.comentarios.toArray().length; ++i) {
                comentarios = comentarios + this.comentarios.get(i) + " ";
                if (this.comentarios.toArray().length -1 > i) {
                    comentarios = comentarios + ", ";
                }
            }
        // se devuelve el string de formato
        return "Fecha: " + this.fecha.toString() + ", Monto: " + 
                this.montoTotal + ", Comentarios: [" + comentarios + "} , Producto: ["
                + producto.toString() + "]";
    }
    
    /**
     * Permite agregar una factura nuevo a la base de datos.
     * @throws SQLException puede lanzar una excepcion de sql
     */
    void registrarFactura() {
        // Se conecta a la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            // Se agrega la nueva factura a la tabla factura y se agregan sus
            // comentarios a la tabla comentario.
            st.execute("insert into factura values ('"+ this.producto.codigoProd + 
                    "', '" + this.fecha.toString() + "', '" + this.montoTotal + "');");
            
            for (int i=0; i < comentarios.toArray().length; ++i) {
                st.execute("insert into comentario values ('" + this.producto.codigoProd +
                    "', '" + this.fecha.toString() + "', '" +
                    this.comentarios.get(i).toString() + "');");
            }
            
            
            // Se cierra la conexion con la base de datos
            conn.close();
            
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Metodo que permite devolver una instancia de factura hecha objeto
     * @param producto producto asociado a la factura
     * @param fecha fecha en la que se expide la factura
     * @return una instancia de la clase factura
     */
    static Factura consultarFactura(Producto producto, Date fecha) {
        // Inicializamos un objeto factura en null
        Factura factura = null;

        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            // Buscamos los comentarios de la factura en la base de datos
            ResultSet rs = st.executeQuery("select valor from comentario where"
                    + " id = '" + producto.codigoProd + "' and fecha ='"
                    + fecha.toString() + "';");
            
            // Si los conseguimos es porque existe la factura
            if (rs != null) {
                // Creamos una nueva lista para los comentarios
                ArrayList<String> comentarios = new ArrayList<>();
                
                // Metemos en la lista los comentarios.
                while (rs.next()) {
                    String comentario = rs.getString(1);
                    System.out.println(comentario);
                    comentarios.add(comentario);
                }
                
                // Cerramos el ResultSet
                rs.close();
                
              // Buscamos a la factura en la base de datos
                rs = st.executeQuery("select monto_total from factura where "
                    + "id = '" + producto.codigoProd + "' and fecha ='"
                    + fecha.toString() + "';");
            
                rs.next();
              
                // Creamos un nuevo objeto factura con los datos obtenidos.
                factura = new Factura(fecha, rs.getDouble(1), comentarios, producto);
                        
            }
            // cerramos la conexion
            conn.close();
            
        } catch (SQLException ex) {
           // Si hay una excepcion se imprime un mensaje
            System.err.println(ex.getMessage());
        }
        // Retorna a la factura que saco de la base de datos y en caso de no
        // conseguirlo retorna null.
        return factura;
    }
    
    /**
     * Metodo que permite modificar una factura en la tabla factura de la base de datos
     */
    void modificarFactura() {
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
             // Actualizamos los datos de la factura en la base de datos
            st.executeUpdate("update factura set monto_total ='"
                    + this.montoTotal +"' where id ='"
                    + this.producto.codigoProd
                    + "' and fecha ='" + this.fecha.toString() +"';");
             // Se borran los comentarios de la factura
            st.execute("delete from comentario where id ='" 
                    + this.producto.codigoProd + "' and fecha ='"
                    + this.fecha.toString() + "';");
             // Actualizamos los comentarios de la factura en la base de datos
            for (int i=0; i < this.comentarios.toArray().length; ++i) {
                st.execute("insert into comentario values ('" +
                        this.producto.codigoProd + "', '" +
                        this.fecha.toString() + "', '" +
                        this.comentarios.get(i).toString() +"');");
                }                  
            
          } catch (SQLException ex) {
              // Si hay una excepcion se imprime un mensaje
              System.err.println(ex.getMessage());
          }
    }
    
    /**
     * Metodo que permite eliminar una factura de la tabla factura de la base de datos
     */
    void eliminarFactura() {
          // Conectamos con la base de datos
          try (Connection conn = Conexion.obtenerConn()) {
             Statement st; 
             st = conn.createStatement();
            // Eliminamos los comentarios de la factura en la tabla de comentario
             st.execute("delete from comentario where id ='"
                      + this.producto.codigoProd 
                      + "' and fecha ='" + this.fecha.toString() +"';");
             // Se elimina la factura de la tabla factura
             st.execute("delete from factura where id ='"
                      + this.producto.codigoProd
                      + "' and fecha ='" + this.fecha.toString() +"';");                         
            
          } catch (SQLException ex) {
              // Si hay una excepcion se imprime un mensaje
              System.err.println(ex.getMessage());
          }
    }
   
}
