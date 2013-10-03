
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Clase TipoServicio: contiene los metodos necesarios para manejar los contenidos de
 * la tabla tipo_servicio en la base de datos.
 */
public class TipoServicio {
  String nombre;

  /**
   * Constructor para la clase
   * @param nombre nombre del TipoServicio
   */
  public TipoServicio(String nombre) {
    this.nombre = nombre;            // Nombre del TipoServicio;
  }

  /**
   * Permite obtener una representacion en string un elemento de la clase Modelo.
   * @return String retorna el string que representa al objeto.
   */
  @Override
    public String toString() {
      // Regresamos el string con el formato de salida
      return "Nombre: " + this.nombre;
    }

  /**
   * Permite agregar un tipo_servicio nuevo a la base de datos.
   * @throws SQLException puede lanzar una excepcion de sql
   */
  void registrarTipoServicio() {
    // Conectamos con la base de datos
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();

      // Se agrega el nuevo TipoServicio a la tabla modelo y se agregan sus
      st.execute("insert into tipo_servicio values ('" + this.nombre + "');");
      // Cerramos la conexion
      conn.close();
    } catch (SQLException ex) {
      // En caso de haber una excepcion se imprime el mensaje
      System.err.println(ex.getMessage());
    }
  }


  /**
   * Permite, dada el nombre del  TipoServicio, buscar al mismo en la base de datos.
   * @param nombreo nombre del TipoServicio
   * @return retorna un objeto Modelo en caso de conseguirlo en la base de
   * datos y si no lo consigue retorna null
   */
  static TipoServicio consultarTipoServicio(String nombre) {
    // Inicializamos un objeto Modelo en null
    TipoServicio tipoServicio = null;
    // Conectamos con la base de datos
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();

      // Buscamos al TipoServicio en la base de datos
      ResultSet rs = st.executeQuery("select * from tipo_servicio where nombre = '" + nombre + "';");

      if (rs.next()) {
        tipoServicio = new TipoServicio(rs.getString(1));
      }
      // cerramos la conexion
      conn.close();
    } catch (SQLException ex) {
      // Si hay una excepcion se imprime un mensaje
      System.err.println(ex.getMessage());
    }
    // Retorna al TipoServicio que saco de la base de datos y en caso de no
    // conseguirlo retorna null.
    return tipoServicio;
  }

  /**
   * Permite eliminar a un TipoServicio de la base de datos
   */
  void eliminarTipoServicio() {
    // Conectamos con la base de datos
    try (Connection conn = Conexion.obtenerConn()) {
      Statement st;
      st = conn.createStatement();

      // Eliminamos al TipoServicio
      st.execute("delete from tipo_servicio where nombre = '" + this.nombre + "';");
      conn.close();
    } catch (SQLException ex) {
      // Si hay una excepcion se imprime un mensaje
      System.err.println(ex.getMessage());
    }
  }

}
