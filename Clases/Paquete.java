import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Clase Paquete: Representa a la entidad Paquete en la base de datos como
 * objeto facilitando su uso a nivel de software.
 */

public class Paquete {

    public String nombre;        //Nombre del paquete
    public String descripcion;   //Descripcion del paquete


   /**
    * Constructor de paquete.
    * @param nom nombre del paquete.
    * @param desc descripcion del paquete.
    */

    public Paquete(String nom, String desc) {

        this.nombre = nom;
        this.descripcion = desc;

    }

   /**
    * Metodo para agregar un paquete que no este presente en la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void registrarPaquete() throws SQLException {

        Connection conexion = null;  

        try {

            //Se crea la conexion a la Base de Datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            String insert = "insert into PAQUETE values ('" + nombre + "', '" +
                            descripcion + "');";

            //Se inserta el paquete a la Base de Datos
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
    * Metodo para extraer un paquete de la base de datos dado su nombre.
    * @param nom nombre del paquete a buscar.
    * @return Devulve un objeto que representa el paquete almacenado en la base de
    * datos, en caso de no existir devuelve null.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public static Paquete consultarPaquete(String nom)throws SQLException{

        Paquete paquete = null;

        //Se crea la conexion
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null) {

            Statement stmt = null;
            String query = "select DESCRIPCION from PAQUETE where " + 
                           "NOMBRE_PAQUETE = '" + nom + "'";

            try {

                //Se buscan los datos del paquete en la base de datos
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                //Si el paquete existe, se crea
                if (rs.next())
                    paquete = new Paquete(nom,rs.getString("DESCRIPCION"));

            } catch (SQLException e) {

                //Si hay un error se imprime en pantalla
                System.out.println("SQL EXCEPTION: ConsultarPaquete");

            } finally {

                //La conexion se cierra
                conexion.close();
            }
        }

        return paquete;
    }

   /**
    * Metodo para eliminar un paquete de la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void eliminarPaquete() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se elimina el paquete de la base de datos
            String delete = "delete from PAQUETE where NOMBRE_PAQUETE ='" 
                    + this.nombre + "';";
            stmt.executeUpdate(delete);

        }catch (SQLException e) {
            //Si hay un error se imprime en pantalla
            System.out.println(e.getMessage());

        }finally {

            //La conexion se cierra
            conexion.close();
        }
    }

   /**
    * Metodo para modificar un paquete ya existente en la base de datos
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */

    public void modificarPaquete() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se modifica el paquete en la base de datos
            String update = "update PAQUETE set DESCRIPCION = '" 
                    + this.descripcion + "' where NOMBRE_PAQUETE = '" 
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
    * Metodo que lista todos los servicios asociados a un paquete.
    * @return Una lista con los servicios asociados al paquete, si no hay
    * servicos o el paquete no existe devuelve una lista vacia.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public ArrayList<Servicio> ListarServicios() throws SQLException {

        //Se crea una lista vacia
        ArrayList<Servicio> lista = new ArrayList();
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null) {

            Statement stmt = null;
            String query = "select NOMBRE_SERVICIO from CONTIENE where " 
                    + "NOMBRE_PAQUETE = '" + nombre + "';";

            try {

                //Se obtienen los nombres de los servicios asociados al paquete
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                /*Por cada servicio se obtienen los datos faltantes para crear
                   un objeto y agregarlo a la lista*/
                while (rs.next()) {

                    String nomServicio = rs.getString("NOMBRE_SERVICIO");
                    Servicio serv = Servicio.consultarServicio(nomServicio);
                    lista.add(serv);
                }

            } catch (SQLException e) {

                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());

            } finally {

               //La conexion se cierra
               conexion.close();
            }
        }

        return lista;
    }

   /**
    * Convierte un objeto de tipo Paquete a String.
    * @return un String que representa al paquete.
    */
    @Override
    public String toString() {

        return "Nombre: " + nombre + ", Descrpcion: " + descripcion; 
    }
}
