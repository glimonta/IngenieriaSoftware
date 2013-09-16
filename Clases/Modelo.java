import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Clase Modelo: contiene los metodos necesarios para manejar los contenidos de
 * la tabla modelo en la base de datos.
 */
public class Modelo {
    public String nombre;             // Nombre del modelo
    
    /**
     * Constructor para la clase
     * @param nombre nombre del modelo
     */
    public Modelo(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Permite agregar un modelo nuevo a la base de datos.
     * @throws SQLException puede lanzar una excepcion de sql
     */
    void registrarModelo(){
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            // Se agrega el nuevo modelo a la tabla modelo y se agregan sus
            
            st.execute("insert into modelo values ('"
                    + this.nombre + "');");
            
            // Cerramos la conexion
            conn.close();
            
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
          
    }
    
    /**
     * Permite, dada el nombre del  modelo, buscar al mismo en la base de datos.
     * @param nombre_modelo nombre del modelo
     * @return retorna un objeto Modelo en caso de conseguirlo en la base de
     * datos y si no lo consigue retorna null
     */
    static Modelo consultarModelo(String nombre_modelo) {
        
      // Inicializamos un objeto Modelo en null
      Modelo modelo = null;

      // Conectamos con la base de datos
      try (Connection conn = Conexion.obtenerConn()) {

        Statement st;
        st = conn.createStatement();

        // Buscamos al modelo en la base de datos
        ResultSet rs = st.executeQuery("select nombre_modelo from " +
            "MODELO where NOMBRE_MODELO = '"+nombre_modelo + "';");

        // Creamos un nuevo objeto modelo con los datos obtenidos.
        modelo = new Modelo(rs.getString(1));
        
        conn.close();

      // cerramos la conexion
      

    } catch (SQLException ex) {
      // Si hay una excepcion se imprime un mensaje
      System.err.println(ex.getMessage());
    }

    // Retorna al modelo que saco de la base de datos y en caso de no
    // conseguirlo retorna null.
    return modelo;

}
    
    /**
     * Permite eliminar a un modelo de la base de datos
     */
    void eliminarModelo() {
          
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
                        

            // Eliminamos al modelo
            st.execute("delete from Modelo where nombre_modelo = '"+this.nombre+"';");
            
          } catch (SQLException ex) {
              // Si hay una excepcion se imprime un mensaje
              System.err.println(ex.getMessage());
          }
        
    }
      
    /**
     * Permite obtener una representacion en string un elemento de la clase Modelo.
     * @return String retorna el string que representa al objeto.
     */
    @Override
    public String toString() {
        String tlfs = "";
        
        // Regresamos el string con el formato de salida
        return "Nombre: " + this.nombre;
    }

}
