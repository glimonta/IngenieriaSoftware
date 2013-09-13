import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase Servicio: Representa a la interrelacion Contiene en la base de datos como
 * objeto facilitando su uso a nivel de software.
 */
public class Contiene {

    public int cantidad;         //Cantidad del servicio que contiene el paquete
    public float costoAdicional; //Costo adicional del servicio
    public Paquete paquete;      //Paquete que contiene al servicio
    public Servicio servicio;    //Servicio que es contenido por el paquete


   /**
    * Constructor de contiene.
    * @param cant Cantidad del servicio que contiene el paquete.
    * @param costo Costo adicional del servicio.
    * @param paquete Paquete que contiene al servicio.
    * @param serv Servicio que es contenido por el paquete.
    */
    public Contiene(int cant, float costo, Paquete paquete, Servicio serv){
        this.cantidad = cant;
        this.costoAdicional = costo;
        this.paquete = paquete;
        this.servicio = serv;
    }

   /**
    * Metodo para agregar una tupla a la iterrelacion Contiene que no este presente 
    * en la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void registrarContiene() throws SQLException {

        Connection conexion = null;

        try {

            //Se crea la conexion a la Base de Datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se inserta la tupla Contiene a la Base de Datos
            String insert = "insert into CONTIENE values ('"+ paquete.nombre +
                    "', '" + servicio.nombre + "', " + cantidad + ", " 
                    + costoAdicional + ");";
            stmt.executeUpdate(insert);

        } catch (SQLException e){

            //Si hay un error se imprime en pantalla
            System.out.println(e.getMessage());

        } finally {

            //La conexion se cierra
            conexion.close();
        } 
    }

   /**
    * Metodo para extraer una tupla de Contiene de la base de datos dado el nombre 
      del paquete, y el nombre del servicio.
    * @param nomPack nombre del paquete a buscar.
    * @param nomServ nombre del servicio a buscar.
    * @return Devulve un objeto que representa a la tupla contiene almacenada en
    * la base de datos, en caso de no existir devuelve null.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public static Contiene consultarContiene(String nomPack, String nomServ) 
            throws SQLException{

        Contiene cont = null;

        //Se crea la conexion a la base de datos
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null){

            Statement stmt = null;
            String query = "select CANTIDAD, COSTO_ADICIONAL from CONTIENE " +
                    "where NOMBRE_PAQUETE = '" + nomPack + "' and " +
                    "NOMBRE_SERVICIO = '" + nomServ + "';";

            try{
                //Se buscan los datos de la tupla contiene en la base de datos
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                /*Se buscan el servicio y el paquete en la base de datos para
                  agregarlos al objeto Contiene */
                Paquete pack = Paquete.consultarPaquete(nomPack);
                Servicio serv = Servicio.consultarServicio(nomServ);

                //Si la tupla Tiene existe, se crea
                if (rs.next())
                    cont = new Contiene(Integer.parseInt(rs.getString("CANTIDAD")),
                            Float.parseFloat(rs.getString("COSTO_ADICIONAL")),
                            pack, serv);

            } catch (SQLException e){

                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());

            } finally {

                //La conexion se cierra
                conexion.close();
            }
        }

        return cont;
    }

   /**
    * Metodo para eliminar una tupla de Contiene de la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */

    public void eliminarContiene() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se elimina la tupla Contiene de la base de datos
            String delete = "delete from CONTIENE where NOMBRE_PAQUETE = '" +
                   this.paquete.nombre + "' and NOMBRE_SERVICIO = '" + 
                    this.servicio.nombre + "';";
            stmt.executeUpdate(delete);

        } catch (SQLException e){

            //Si hay un error se imprime en pantalla
            System.out.println(e.getMessage());

        } finally {

            //La conexion se cierra
            conexion.close();
        } 
    }


   /**
    * Metodo para modificar una tupla de Contiene ya existente en la base de datos
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void modificarContiene() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion a la base de datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            String update = "update CONTIENE set CANTIDAD = "+
                    this.cantidad + ", COSTO_ADICIONAL = " + this.costoAdicional + 
                    " where NOMBRE_PAQUETE = '" + this.paquete.nombre + 
                    "' and NOMBRE_SERVICIO = '" + this.servicio.nombre + "';";

            //Se modifica la tupla de Contiene en la base de datos
            stmt.executeUpdate(update);

        } catch (SQLException e){

            //Si hay un error se imprime en pantalla
            System.out.println(e.getMessage());

        } finally {

            //La conexion se cierra
            conexion.close();
        } 
    }

   /**
    * Convierte un objeto de tipo Contiene a String.
    * @return un String que representa al objeto Tiene.
    */
    @Override
    public String toString(){

        return "Cantidad: " + cantidad + ", Costo Adicional: " + costoAdicional
                + ", Paquete: " + paquete.toString() + ", Servicio: " 
                + servicio.toString();
    }
}
