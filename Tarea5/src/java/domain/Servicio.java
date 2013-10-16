
package domain;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase Servicio: Representa a la entidad Servicio en la base de datos como
 * objeto facilitando su uso a nivel de software.
 */

public class Servicio {

    public String nombre;        //Nombre del servicio
    public String descripcion;   //Descripcion del servicio
    public String tipoServicio;  //Tipo del servicio

   /**
    * Constructor de servicio.
    * @param nom nombre del servicio.
    * @param desc descripcion del servicio.
    * @param tipo tipo del servicio.
    */
    public Servicio(String nom, String desc, String tipo) {

        this.nombre = nom;
        this.descripcion = desc;
        this.tipoServicio = tipo;
    }

   /**
    * Metodo para agregar un servicio que no este presente en la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void registrarServicio() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion a la Base de Datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            String insert = "insert into SERVICIO values ('" + nombre + "', '" +
                            descripcion + "', '" + tipoServicio + "');";
            //Se inserta el servicio a la Base de Datos
            stmt.executeUpdate(insert);

        }catch (SQLException e) {

            //Si hay un error se imprime en pantalla
            System.out.println(e.getMessage());

        }finally {

            //La conexion se cierra
            conexion.close();
        }
    }

   /**
    * Metodo para extraer un servicio de la base de datos dado su nombre.
    * @param nom nombre del servicio a buscar.
    * @return Devulve un objeto que representa el servicio almacenado en la base
    * de datos, en caso de no existir devuelve null.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public static Servicio consultarServicio(String nom) throws SQLException {

        Servicio servicio = null;

        //Se crea la conexion
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null) {

            Statement stmt = null;
            String query = "select DESCRIPCION, NOMBRE_TIPO_SERVICIO " +
                           "from SERVICIO where NOMBRE_SERVICIO = '" + nom + "'";

            try {

                //Se buscan los datos del servicio en la base de datos
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                //Si el servicio existe, se crea
                if (rs.next()) 
                    servicio = new Servicio(nom,rs.getString("DESCRIPCION"),
                                            rs.getString("NOMBRE_TIPO_SERVICIO"));

            } catch (SQLException e) {

                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());

            } finally {

                //La conexion se cierra
                conexion.close();
            }
        }

        return servicio;
    }

   /**
    * Metodo para eliminar un servicio de la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void eliminarServicio() throws SQLException{

        Connection conexion = Conexion.obtenerConn();

        try{

            //Se crea la conexion de la base de datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se elimina el servicio de la base de datos
            String delete = "delete from SERVICIO where NOMBRE_SERVICIO ='"
                    + this.nombre + "';";
            stmt.executeUpdate(delete);

        } catch (SQLException e) {

            //Si hay un error se imprime en pantalla
            System.out.println(e.getMessage());

        } finally {

            //La conexion se cierra
            conexion.close();
        }
    }

   /**
    * Metodo para modificar un servicio ya existente en la base de datos
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void modificarServicio() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion a la base de datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se modifica el servicio en la base de datos
            String update = "update SERVICIO set DESCRIPCION = '" 
                    + this.descripcion + "' where NOMBRE_SERVICIO = '" 
                    + this.nombre + "';";
            stmt.executeUpdate(update);

        }catch (SQLException e) {

            //Si hay un error se imprime en pantalla
            System.out.println(e.getMessage());

        }finally {

            //La conexion se cierra
            conexion.close();
        }
    }


   /**
    * Convierte un objeto de tipo Servicio a String.
    * @return un String que representa al servicio.
    */
    @Override
    public String toString() {

        return "Nombre: " + nombre + ", Descripcion: " + descripcion 
                + ", Tipo de servicio: " + tipoServicio;
    }
    
    /**
     * Verifica si un servicio es igual a this.
     * @return Regresa true si el servicio pasado como parametro tiene los
     * mismos atributos que this.
     */
    @Override
    public boolean equals(Object obj) {
        
        Servicio serv = (Servicio) obj;
        
        return (serv.descripcion.equals(this.descripcion)) &
               (serv.nombre.equals(this.nombre)) &
               (serv.tipoServicio.equals(this.tipoServicio));
    }
}
