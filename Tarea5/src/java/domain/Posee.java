
package domain;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase Posee: Contiene metodos necesarios para manejar los contenidos de
 * la tabla posee de la base de datos.
 */
public class Posee {
    Date fechaInicio;            // Fecha de adquision del servico adicional
    ServicioAdicional adicional; // Servicio Adicional contratado por el producto
    Producto producto;           // Producto asociado a la relacion posee

    /**
     * Retorna la fecha de inicio de la posesion
     * @return fecha de inicio de la posesion
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Asigna la fecha de inicio de la posesion
     * @param fechaInicio fecha de inicio de la posesion
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Retorna el servicio adicional de la posesion
     * @return servicio adicional de la posesion
     */
    public ServicioAdicional getAdicional() {
        return adicional;
    }

    /**
     * Asigna el servicio adicional de la posesion
     * @param adicional servicio adicional de la posesion
     */
    public void setAdicional(ServicioAdicional adicional) {
        this.adicional = adicional;
    }

    /**
     * Retorna el producto de la posesion
     * @return producto de la posesion
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Asigna el producto de la posesion
     * @param producto producto de la posesion
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    /**
     * Constructor asociado a la clase posee
     * @param fechaInicio fecha en la que se adquiere el servicio adicional
     * @param adicional plan adicional 
     * @param producto producto asociado al plan adicional
     */
    public Posee(Date fechaInicio, ServicioAdicional adicional, Producto producto) {
        this.fechaInicio = fechaInicio;
        this.adicional = adicional;
        this.producto = producto;
    }
    
    /**
     * Constructor vacio
     */
    public Posee() {
        
    }
    
     /**
     * Permite obtener una representacion en string un elemento de la clase posee.
     * @return String retorna el string que representa al objeto.
     */
    @Override
    public String toString() {
        return "Fecha de Inicio: " + this.fechaInicio.toString() +
                ", Nombre de servicio: [" + this.adicional.toString() +
                "], ID: [" + this.producto.toString() + "]";
    }
    
    /**
     * Permite registrar un posee nuevo a la base de datos.
     */
    public void registrarPosee() {
        // Intentamos establecer conexion con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            // Se ejecuta el insert a la base de datos de los valores correspondientes
            st.execute("insert into posee values ('"+ this.producto.codigoProd +
                    "', '" + this.adicional.nombre + "', '" + this.fechaInicio.toString() + 
                     "');");
            
             // Cerramos la conexion
            conn.close();
            
        } catch (SQLException ex) {
            // En caso de una excepcion se imprime el mensaje de error.
            System.err.println(ex.getMessage());
        }
    }
     
    /**
     * Dado un id permite retorna una instancia de la clase posee con la informacion
     * obtenida de esa intancia de la base de datos
     * @param id id asociado al producto
     * @param nombre_servicio nombre del servicio adicional
     * @param fecha fecha de la adquisicion del servicio adicional por parte del producto
     * @return se retorna una instancia en forma de objeto de la clase posee en
     * caso de que exista, sino se retorna null
     * @throws ParseException devolvemos una excepcion de parseo
     */
    static Posee consultarPosee(Integer id, String nombre_servicio, java.sql.Date fecha) throws ParseException {
      // Inicializamos un objeto de la clase consumo en null
        Posee posee = null;

        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            // Ejecutamos el query buscando la entrada de afiliacion deseada.
            ResultSet rs = st.executeQuery("select * "
                    + "from posee where id = '" + id 
                    + "' and nombre_servicio = '" + nombre_servicio + "' and"
                    + " fecha_inic= '" + fecha.toString()
                    + "';");
            
            // Verificamos que el query no sea vacio
            if (rs.next()) {
                // Creamos un nuevo objeto del tipo Date con la fecha de
                // consumo conseguida
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(3));
                fecha = new java.sql.Date(utilDate.getTime());
            
                // Buscamos en la base de datos el producto correspondiente a
                // ese id y creamos un objeto con el. 
                Producto producto = Producto.consultarProducto(rs.getInt(1));
                ServicioAdicional servicioAdicional = ServicioAdicional.consultarServicioAd(rs.getString(2));
                
                // Creamos un nuevo objeto posee para retornar
                posee = new Posee(fecha, servicioAdicional, producto);
            }
            
          // Cerramos la conexion.
          conn.close();
            
        } catch (SQLException ex) {
            // Si hay una excepcion se imprime el mensaje de la misma.
            System.err.println(ex.getMessage());
        }
        // Se retorna consumo, si el query dio vacio se retornaria null.
        return posee;
    }
     
    /**
     * Se encarga de eliminar una entrada de la tabla posee en la base
     * de datos correspondiente al objeto.
     */
    void eliminarPosee() {

        // Establecemos conexion con la base de datos.
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
            
            // Ejecutamos el delete.
            st.execute("delete from posee where id =' " + 
                    this.producto.codigoProd + "' and nombre_servicio ='" + this.adicional.nombre + 
                    "' and fecha_inic = '" + this.fechaInicio + "';");
        
        // Si hay una excepcion se imprime el mensaje de la misma.
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Verifica si un posee es igual a this.
     * @return Regresa true si el posee pasado como parametro tiene los
     * mismos atributos que this.
     */
    @Override
    public boolean equals(Object obj) {
        
        Posee posee = (Posee) obj;
        return (posee.adicional.equals(this.adicional)) &
               (posee.fechaInicio.equals(this.fechaInicio)) &
               (posee.producto.equals(this.producto));
    }
}
