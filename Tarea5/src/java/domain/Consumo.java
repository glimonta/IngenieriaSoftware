
package domain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase Consumo: Contiene metodos necesarios para manejar los contenidos de
 * la tabla consumo de la base de datos.
 */
public class Consumo {
    Integer cantidad;   // Cantidad que se ha consumido
    Date fecha;         // Fecha en la que se incurre al cosumo
    Producto producto;  // Referencia al producto que realiza el consumo
    Servicio servicio;  // Servicio asociado al consumo realizado
    
    /**
     * Constructor asociado a la clase Consumo
     * @param cantidad  Cantidad que se ha consumido
     * @param fecha     Fecha en donde se realiza el consumo
     * @param producto  Producto que realiza el consumo
     * @param servicio  Servicio asociado al consumo
     */
    public Consumo(Integer cantidad, Date fecha, Producto producto, Servicio servicio) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.producto = producto;
        this.servicio = servicio;
    }
    
   /**
     * Permite obtener una representacion en string un elemento de la clase Consumo.
     * @return String retorna el string que representa al objeto.
     */
    @Override
    public String toString() {
        return "Cantidad: " + this.cantidad.toString() + ", Fecha: " + 
                this.fecha.toString() + ", Producto: [" +
                this.producto.toString() + "], Servicio: [" +
                this.servicio.toString() + "]";
    }
    
    
    /**
     * Permite registrar un consumo nuevo a la base de datos.
     */
    void registrarConsumo() {
        // Intentamos establecer conexion con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            // Se ejecuta el insert a la base de datos de los valores correspondientes
            st.execute("insert into consume values ('"+ this.producto.codigoProd +
                    "', '" + this.servicio.nombre + "', '" + this.fecha.toString() + 
                    "', '" + this.cantidad + "');");
            
             // Cerramos la conexion
            conn.close();
            
        } catch (SQLException ex) {
            // En caso de una excepcion se imprime el mensaje de error.
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Permite, dada la clave del consumo, buscar en la base de datos una instancia de la clase consumo.
     * @param id id asociado al producto
     * @param nombre_servicio nombre del servicio que genero el consumo
     * @param fecha fecha en la que se realizo el consumo
     * @return una instancia de la clase consumo con los datos obtenidos en el query
     * de la base de datos en caso de existir, sino retorna null.
     * @throws ParseException puede tirar una excepcion de parseo
     */
    static Consumo consultarConsumo(Integer id, String nombre_servicio, java.sql.Date fecha) throws ParseException {
      // Inicializamos un objeto de la clase consumo en null
       Consumo consumo = null;
       
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            // Ejecutamos el query buscando la entrada de afiliacion deseada.
            ResultSet rs = st.executeQuery("select cantidad, fecha "
                    + "from consume where id = '" + id 
                    + "' and nombre_servicio = '" + nombre_servicio + "' and"
                    + " fecha = '" + fecha.toString()
                    + "';");
            
            // Verificamos que el consumo exista en la base de datos.
            if (rs.next()) {
                // Creamos un nuevo objeto del tipo Date con la fecha de
                // consumo conseguida
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(2));
                fecha = new java.sql.Date(utilDate.getTime());
            
                // Buscamos en la base de datos el producto correspondiente a
                // ese id y creamos un objeto con el. 
                Producto producto = Producto.consultarProducto(id);
            
                // Buscamos en la base de datos el servicio correspondiente a
                // ese id y creamos un objeto con el. 
                Servicio servicio = Servicio.consultarServicio(nombre_servicio);
            
                // Creamos un nuevo objeto consumo para retornar
                consumo = new Consumo(rs.getInt(1), fecha, producto, servicio);
            }
            
          // Cerramos la conexion.
          conn.close();
            
        } catch (SQLException ex) {
            // Si hay una excepcion se imprime el mensaje de la misma.
            System.err.println(ex.getMessage());
        }
        // Se retorna consumo, si el query dio vacio se retornaria null.
        return consumo;
    }
    
    /**
     * Se encarga de eliminar una entrada de la tabla consumo en la base
     * de datos correspondiente al objeto.
     */
    void eliminarConsumo() {
        
        // Establecemos conexion con la base de datos.
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();

            // Ejecutamos el delete.
            st.execute("delete from consume where id =' " + 
                    this.producto.codigoProd + "' and nombre_servicio ='" + this.servicio.nombre + 
                    "' and fecha = '" + this.fecha + "';");
            
        // Si hay una excepcion se imprime el mensaje de la misma.
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Se encarga de modificar la informacion del consumo en la base
     * de datos.
     */
    void modificarConsumo() {
        try {
             // Establecemos conexion con la base de datos.
             try (Connection conn = Conexion.obtenerConn()) {
                 Statement st;
                 st = conn.createStatement();
                 
                // Ejecutamos el uptdate en la base de datos
                 st.executeUpdate("update consume set cantidad ='"
                         + this.cantidad + "' " + "where id ='"
                         + this.producto.codigoProd + "' and nombre_servicio ='" 
                         + this.servicio.nombre
                         + "' and fecha ='" + this.fecha + "';");
                         
            }
            
          } catch (SQLException ex) {
              // Si hay una excepcion se imprime el mensaje de la misma.
              System.err.println(ex.getMessage());
          }
    }
    
    /**
     * Verifica si un consumo es igual a this.
     * @return Regresa true si el consumo pasado como parametro tiene los
     * mismos atributos que this.
     */
    @Override
    public boolean equals(Object obj) {
        
        Consumo cons = (Consumo) obj;
        
        return (cons.cantidad == this.cantidad) &
               (cons.fecha.equals(this.fecha))  &
               (cons.producto.equals(this.producto)) &
               (cons.servicio.equals(this.servicio));
    }

}
