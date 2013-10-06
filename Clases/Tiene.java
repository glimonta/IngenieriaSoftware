import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase Servicio: Representa a la interrelacion Tiene en la base de datos como
 * objeto facilitando su uso a nivel de software.
 */

public class Tiene {

    public Date fechaInicio; //fecha de inicio en la que el plan tiene el paquete
    public Date fechaFin;    //fecha fin en la que el plan tiene un paquete.
    public float costo;      //costo del plan durante el periodo.
    public Plan plan;        //El plan que tiene al paquete.
    public Paquete paquete;  //El paquete del plan.

   /**
    * Constructor de tiene.
    * @param fechaI fecha de inicio en la que el plan tiene un paquete.
    * @param fechaF fecha fin en la que el plan tiene un paquete.
    * @param costo costo del plan durante el periodo.
    * @param plan El plan que tiene al paquete.
    * @param pack El paquete del plan.
    */
    public Tiene (Date fechaI, Date fechaF, float costo, Plan plan, 
            Paquete pack){

        this.fechaInicio = fechaI;
        this.fechaFin = fechaF;
        this.costo = costo;
        this.paquete = pack;
        this.plan = plan;
    }


   /**
    * Metodo para agregar una tupla a la iterrelacion Tiene que no este presente 
    * en la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void registrarTiene() throws SQLException {

        Connection conexion = null;

        try {

            //Se crea la conexion a la Base de Datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            String insert = "insert into TIENE values ('" + plan.nombre +
                    "', '" + plan.tipoPlan + "', '" + paquete.nombre 
                    + "', " + costo + ", DATE '" + fechaInicio.toString() 
                    + "', ";

            /* Dependiendo de si fechaFin es null se concatena al query lo que
               sea necesario */
            if (fechaFin != null)
                insert = insert + "DATE '" + fechaFin.toString() + "');";
            else
                insert = insert + "null);";

            //Se inserta la tupla Tiene a la Base de Datos
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
    * Metodo para extraer una tupla de Tiene de la base de datos dado el nombre 
      del plan, el tipo del plan, el paquete  y la fecha inicial.
    * @param nomPlan nombre del plan a buscar.
    * @param nomPack nombre del paquete a buscar
    * @param tipoPlan tipo del plan a buscar
    * @param fechaI fecha inicial en la que el plan tiene al paquete.
    * @return Devulve un objeto que representa a la tupla tiene almacenada en
    * la base de datos, en caso de no existir devuelve null.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */

    public static Tiene consultarTiene(String nomPlan, String nomPack,
            String tipoPlan, Date fechaI) throws SQLException{

        Tiene tiene = null;
        //Se crea la conexion a la base de datos
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null){

            Statement stmt = null;
            String query = "select COSTO, FECHA_FIN from TIENE " +
                    "where NOMBRE_PLAN = '" + nomPlan + "' and " +
                    "TIPO_PLAN = '" + tipoPlan + "' and "  +
                    "NOMBRE_PAQUETE = '" + nomPack  +  "' and " +
                    "FECHA_INIC = DATE '" + fechaI.toString() + "';";

            try{

                //Se buscan los datos de la tupla tiene en la base de datos
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                /*Se buscan el plan y el paquete en la base de datos para
                  agregarlos al objeto Tiene */
                Plan plan = Plan.consultarPlan(nomPlan, tipoPlan);
                Paquete pack = Paquete.consultarPaquete(nomPack);

                String fechaFinString = null;

                //Si la tupla Tiene existe, se crea
                if (rs.next())

                    //Se verifica si fecha_fin es null
                    fechaFinString = rs.getString("FECHA_FIN");

                    //Si es null se agrega en el objeto como null
                    if (fechaFinString != null)
                        tiene = new Tiene(fechaI, 
                                Date.valueOf(fechaFinString), 
                                Float.parseFloat(rs.getString("COSTO")), plan, 
                                pack);
                    else
                        tiene = new Tiene(fechaI, null, 
                                Float.parseFloat(rs.getString("COSTO")), plan, 
                                pack);

            } catch (SQLException e){

                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());

            } finally {

                //La conexion se cierra
                conexion.close();
            }
        }

        return tiene;
    }

   /**
    * Metodo para eliminar una tupla de Tiene de la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void eliminarTiene() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se elimina la tupla Tiene de la base de datos
            String delete = "delete from TIENE where NOMBRE_PLAN = '" +
                   this.plan.nombre + "' and TIPO_PLAN = '" + 
                   this.plan.tipoPlan + "' and NOMBRE_PAQUETE = '" +
                   this.paquete.nombre + "' and FECHA_INIC = DATE '" +  
                   this.fechaInicio.toString() + "';";
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
    * Metodo para modificar una tupla de Tiene ya existente en la base de datos
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */

    public void modificarTiene() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion a la base de datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();
            String update;

            /*Se crea el query dependiendo si fecha_fin es null */
            if (fechaFin != null)
                update = "update TIENE set COSTO = "+
                        this.costo + ", FECHA_FIN = DATE '" + 
                        this.fechaFin.toString() + "' where NOMBRE_PLAN = '" + 
                        this.plan.nombre + "' and NOMBRE_PAQUETE = '" + 
                        this.paquete.nombre + "' and TIPO_PLAN = '" +  
                        this.plan.tipoPlan + "' and FECHA_INIC = DATE '" + 
                        this.fechaInicio.toString()  + "';";

            else
                update = "update TIENE set COSTO = "+
                        this.costo + ", FECHA_FIN = NULL where NOMBRE_PLAN = '" + 
                        this.plan.nombre + "' and NOMBRE_PAQUETE = '" + 
                        this.paquete.nombre + "' and TIPO_PLAN = '" +  
                        this.plan.tipoPlan + "' and FECHA_INIC = DATE '" + 
                        this.fechaInicio.toString()  + "';";

            //Se modifica la tupla de Tiene en la base de datos
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
    * Convierte un objeto de tipo Tiene a String.
    * @return un String que representa al objeto Tiene.
    */
    @Override
    public String toString(){

        String res = "Nombre del Plan: " + plan.nombre + ", Tipo del plan: " +
                plan.tipoPlan + ", Nombre del paquete: " + paquete.nombre + 
                ", Costo: " + costo + ", Fecha de inicio: " + 
                fechaInicio.toString();

        //Si fecha fin no es null se concatena su informacion
        if (fechaFin != null)
            res = res + ", Fecha fin: " + fechaFin.toString();

        return res;
    }
}
