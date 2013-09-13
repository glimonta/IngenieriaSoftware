import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Clase ServicioAdicional: Representa a la entidad Adicional en la base de
 * datos como objeto facilitando su uso a nivel de software.
 */

public class ServicioAdicional extends Servicio {

    public float tarifa = 0;           //La tarifa del servicio Adicional
    public int cantidadAdicional = 0;  //Cantidad que ofrece el servicio Adicional
    public String tipoPlan;            //Tipo de Plan con el que se puede aquirir
                                       //el servicio adicional

   /**
    * Constructor de servicio.
    * @param nom nombre del servicio adicional.
    * @param desc descripcion del servicio adicional.
    * @param tipo tipo del servicio adicional.
    * @param tarifa tarfifa del servicio adicional.
    * @param cantAd cantidad adicional que ofrece
    * @param tipoPlan Tpo del plan con el que se puede adquirir el servicio
    */
    public ServicioAdicional(String nom, String desc, String tipo, float tarifa, 
                             int cantAd, String tipoPlan) {
        super(nom,desc,tipo);
        this.tarifa = tarifa;
        this.cantidadAdicional = cantAd;
        this.tipoPlan = tipoPlan;
    }

   /**
    * Metodo para agregar un servicio adicional que no este presente en la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void registrarServicioAd() throws Exception {

        Connection conexion = null;

        try {

            //Se crea la conexion a la Base de Datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            String insert = "insert into ADICIONAL values ('" + nombre + "', '" 
                    + tarifa + "', '" + cantidadAdicional + "', '" 
                    + tipoPlan + "');";
            //Se inserta el servicio adicional a la Base de Datos
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
    * Metodo para extraer un servicio adicional de la base de datos dado su nombre.
    * @param nom nombre del servicio a buscar.
    * @return Devulve un objeto que representa el servicio almacenado en la base
    * de datos, en caso de no existir devuelve null.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */

    public static ServicioAdicional consultarServicioAd(String nom) throws SQLException {

        Servicio servicio = consultarServicio(nom);

        if (servicio != null) {

            ServicioAdicional servAd = null;

            //Se crea la conexion
            Connection conexion = Conexion.obtenerConn();

            if (conexion != null) {

                Statement stmt = null;

                String query = "select TARIFA, CANTIDAD_ADICIONAL, TIPO_PLAN " +
                               "from ADICIONAL where NOMBRE_SERVICIO = '" + nom + "'";

                try {

                    //Se buscan los datos del servicio adicional en la base de datos
                    stmt = conexion.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String desc = servicio.descripcion;
                    String tipoServ = servicio.tipoServicio;

                    //Si el servicio existe, se crea
                    if (rs.next()) 
                        servAd = new ServicioAdicional(nom,desc,tipoServ,
                                     rs.getFloat("TARIFA"),
                                     rs.getInt("CANTIDAD_ADICIONAL"),
                                     rs.getString("TIPO_PLAN"));

                } catch (SQLException e) {

                    //Si hay un error se imprime en pantalla
                    System.out.println(e.getMessage());

                } finally {

                    //La conexion se cierra
                    conexion.close();
                }
            }

            return servAd;
        }

        return null;
    }


   /**
    * Metodo para eliminar un servicio adicional de la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void eliminarServicioAd() throws Exception {

        Connection conexion = null;

        try {
            //Se crea la conexion
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se elimina el servicio adicional de la base de datos
            String delete = "delete from ADICIONAL where NOMBRE_SERVICIO = '" +
                    this.nombre + "';";
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
    * Metodo para modificar un servicio adicional ya existente en la base de datos
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void modificarServicioAd() throws Exception {

        Connection conexion = null;

        try {

            //Se crea la conexion
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se modifica el servicio adicional en la base de datos
            String update = "update ADICIONAL set TARIFA = " +
                    this.tarifa + ", CANTIDAD_ADICIONAL = " + 
                    this.cantidadAdicional + ", TIPO_PLAN = '" +
                    this.tipoPlan + "' where NOMBRE_SERVICIO = '" +
                    this.nombre +"';";
            stmt.executeUpdate(update);

        } catch (SQLException e) {

            //Si hay un error se imprime en pantalla
            System.out.println(e.getMessage());

        } finally {

            //La conexion se cierra
            conexion.close();
        }
    }

   /**
    * Convierte un objeto de tipo ServicioAdicional a String.
    * @return un String que representa al servicio adicional.
    */
    @Override
    public String toString() {

        return "Nombre: " + nombre + ", Descripcion: " + descripcion 
                + "Tipo de Servicio: " + tipoServicio + ", Tarifa: " + tarifa 
                + ", Cantidad Adicional: " + cantidadAdicional 
                + ", Tipo de plan : " + tipoPlan;
    }
}
