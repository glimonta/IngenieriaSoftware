import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Servicio: Representa a la entidad Producto en la base de datos como
 * objeto facilitando su uso a nivel de software.
 */

public class Producto {

    public int codigoProd = 0;  //Codigo del producto
    public String modelo;       //Modelo del producto
    public Cliente cliente;     //Cliente que al quien le pertenece el producto


   /**
    * Constructor de servicio.
    * @param codigo Codigo del producto.
    * @param model Modelo del producto.
    * @param cliente Cliente que al quien le pertenece el producto.
    */
    public Producto (int codigo, String model, Cliente cliente) {

        this.codigoProd = codigo;
        this.modelo = model;
        this.cliente = cliente;
    }

   /**
    * Metodo para agregar un producto que no este presente en la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void registrarProducto() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion a la Base de Datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            if (Cliente.consultarCliente(cliente.cedula) != null){

               String insert = "insert into PRODUCTO values ("+ codigoProd +
                     ", '" + modelo + "');";
               //Se inserta el producto a la Base de Datos
               stmt.executeUpdate(insert);

               String insert = "insert into ES_DUNIEO values("+ codigoProd +
                     ", " + cliente.cedula + ");";

               //Se inserta la relacion a la Base de Datos
               stmt.executeUpdate(insert);
            } else
               System.out.println("El cliente al que le pertenece este " + 
                                 " producto no existe");


        } catch (SQLException e){

            //Si hay un error se imprime en pantalla
            System.out.println(e.getMessage());

        } finally {

            //La conexion se cierra
            conexion.close();
        } 
    }

   /**
    * Metodo para extraer un producto de la base de datos dado su codigo.
    * @param codigo codigo del servicio a buscar.
    * @return Devulve un objeto que representa el producto almacenado en la base
    * de datos, en caso de no existir devuelve null.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */

    public static Producto consultarProducto(int codigo) throws SQLException{

        Producto product = null;

        //Se crea la conexion
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null){

            Statement stmt = null;
            String query = "select P.NOMBRE_MODELO, C.CI from PRODUCTO P, "
                    + "ES_DUENIO E, CLIENTE C where P.ID = " + codigo 
                    + " and E.ID =  P.ID and C.CI = E.CI;";

            try{

                Cliente client = null;

                //Se buscan los datos del producto en la base de datos
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next())

                    //Se buscan los datos del cliente para agregarlos al objeto
                    client = Cliente.consultarCliente(
                            Integer.parseInt(rs.getString("CI")));

                    //Se crea el producto
                    product = new Producto(codigo, 
                            rs.getString("NOMBRE_MODELO"), client);

            } catch (SQLException e){

                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());

            } finally {

                //La conexion se cierra
                conexion.close();
            }
        }

        return product;
    }

   /**
    * Metodo para eliminar un producto de la base de datos.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void eliminarProducto() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion de la base de datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se elimina el producto de la base de datos
            String delete = "delete from PRODUCTO where ID = " +
                    this.codigoProd + ";";
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
    * Metodo para modificar un servicio ya existente en la base de datos
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public void modificarProducto() throws SQLException{

        Connection conexion = null;

        try {

            //Se crea la conexion a la base de datos
            conexion = Conexion.obtenerConn();
            Statement stmt = null;
            stmt = conexion.createStatement();

            //Se modifica el producto en la base de datos
            String update = "update PRODUCTO set NOMBRE_MODELO = '"+
                    this.modelo + "' where ID = " + this.codigoProd + ";";
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
    * Metodo para obtener el plan actual segun la fecha del sistema.
    * @return Un objeto plan que representa el plan actual del producto.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public Plan obtenerPlanActual() throws SQLException{

        Plan plan = null;

        //Se crea la conexion a la base de datos
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null){

            //Obteniene la fecha actual del sistema
            Calendar calendar = new GregorianCalendar();

            //Pasa la fecha actual a una fecha de sql para comparar.
            Date fechaActual = new Date(calendar.getTimeInMillis());
            
            Statement stmt = null;
            String query = "select NOMBRE_PLAN, TIPO_PLAN from ESTA_AFILIADO " +
                    "where ID = " + this.codigoProd + " and FECHA_FIN is null "
                    + "and FECHA_INIC <= DATE '" + fechaActual.toString() + "';";
            try{

                //Busca los datos de aquel plan que no tenga fecha final
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                //Si el plan existe devuelve este plan
                if (rs.next()){

                    String nom = rs.getString("NOMBRE_PLAN");
                    String tipo = rs.getString("TIPO_PLAN");
                    plan = Plan.consultarPlan(nom, tipo);

                } else {

                    query = "select NOMBRE_PLAN, TIPO_PLAN, FECHA_INIC, FECHA_FIN " +
                           "from ESTA_AFILIADO where ID = " + this.codigoProd + 
                           "and FECHA_FIN is not null;";

                    //Busca los planes afiliados que no tengan fecha fin null
                    rs = stmt.executeQuery(query);

                    /*Revisa si la fecha actual esta comprendida entre las 
                      fechas de afiliacion de los planes*/
                    while (rs.next()){

                        Date fecha_inic = Date.valueOf(rs.getString("FECHA_INIC"));
                        Date fecha_fin = Date.valueOf(rs.getString("FECHA_FIN"));

                        //Si la fecha esta comprendida se crea el objeto
                        if (fecha_inic.compareTo(fechaActual) <= 0 && 
                                fecha_fin.compareTo(fechaActual) > 0){

                            plan = Plan.consultarPlan(rs.getString("NOMBRE_PLAN")
                                    , rs.getString("TIPO_PLAN"));
                            break;
                        }
                    }
                }
            } catch (SQLException e){

                System.out.println(e.getMessage());
            } finally {

                conexion.close();
            }
        }

        return plan;
    }

   /**
    * Metodo para obtener la factura actual segun la fecha del sistema.
    * @return Un objeto plan que representa la factura actual del producto.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public Factura obtenerFacturaActual() throws SQLException, ParseException{

        Factura factura = null;

        //Se crea la conexion de la base de datos
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null){

            Statement stmt = null;
            String query = "select FECHA, MONTO_TOTAL from FACTURA " +
                    "where ID = " + this.codigoProd + ";";
            try{

                //Se obitenen los datos de las facturas asociadas al producto
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                /*Se obtiene la fecha actual sin tomar en cuenta el dia 
                  y se convierte a formato sql*/
                Calendar calendar = new GregorianCalendar();
                Date fechaActual = new Date (calendar.get(Calendar.YEAR)-1900, 
                        calendar.get(Calendar.MONTH), 1);

                while (rs.next()){

                    /* Se verifica si la fecha es igual, de ser asi, 
                     se crea la factura*/
                    Date fechaFact = Date.valueOf(rs.getString("FECHA"));

                    if (fechaFact.compareTo(fechaActual) == 0)
                        factura = Factura.consultarFactura(this, fechaFact);
                }
            } catch (SQLException e){

                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());
            } finally {

                //La conexion se cierra
                conexion.close();
            }
        }

        return factura;
    }

   /**
    * Metodo para listar los consumo realizados por un producto durante un
    * periodo determinado.
    * @return Una lista de los consumos realizados.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public ArrayList<Consumo> listarConsumos(Date inicio, Date fin) 
            throws SQLException {

        ArrayList<Consumo> list = new ArrayList();

        //Se crea la conexion de la base de datos
        Connection conexion = Conexion.obtenerConn();

        if(conexion != null){

            Statement stmt = null;
            String query = "select FECHA, CANTIDAD, NOMBRE_SERVICIO from "+
                    "CONSUME where ID = " + this.codigoProd + ";";

            try {

                //Se buscan los consumos realizados por el producto
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                /*Revisa todos los consumos y almacena solo los que esten
                  comprendidos en las fechas dadas*/
                while (rs.next()){

                    Date fechaConsumo = Date.valueOf(rs.getString("FECHA"));

                    /*Si cumple las condiciones se crea el objeto Consumo y 
                     se agrega a la lista */
                    if (inicio.compareTo(fechaConsumo) <= 0 &&
                            fin.compareTo(fechaConsumo) > 0) {

                        //Se busca la informacion del servicio para agregarla al objeto
                        Servicio serv = Servicio.consultarServicio(
                                rs.getString("NOMBRE_SERVICIO"));

                        Consumo cons = new Consumo(
                                Integer.parseInt(rs.getString("CANTIDAD")), 
                                Date.valueOf(rs.getString("FECHA")), this, serv);

                        list.add(cons);
                    }
                }

            } catch (SQLException e){

                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());

            } finally {

                //La conexion se cierra
                conexion.close();
            }
        }
        return list;
    }

   /**
    * Metodo para listar las afiliaciones de un producto determinado.
    * @return Una lista de afiliaciones.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public ArrayList<Afiliacion> listarPlanesAfiliados() throws SQLException{

        ArrayList <Afiliacion> list = new ArrayList();

        //Se crea la conexion de la base de datos
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null) {

            Statement stmt = null;
            String query = "select NOMBRE_PLAN, TIPO_PLAN, FECHA_INIC," 
                    + "FECHA_FIN from ESTA_AFILIADO where ID = " + codigoProd + ";";

            try {

                //Busca las afiliaciones del prodcuto
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()){

                    //Obtiene la informacion del plan
                    Plan plan = Plan.consultarPlan(rs.getString("NOMBRE_PLAN"), 
                            rs.getString("TIPO_PLAN"));
                    Date fechaI = Date.valueOf(rs.getString("FECHA_INIC"));
                    String fechaFinStr = rs.getString("FECHA_FIN");
                    Date fechaF = null;

                    //Si la fecha fin no es null agrega su valor como fecha.
                    if (fechaFinStr != null)
                       fechaF = Date.valueOf(fechaFinStr);

                    //Se crea la afiliacion
                    Afiliacion afil = new Afiliacion(fechaI,fechaF, plan, this);

                    //Se agrega a la lista
                    list.add(afil);
                }

            } catch (SQLException e) {

                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());

            } finally {

                //La conexion se cierra
                conexion.close(); 
            }
        }
        return list;
      }

   /**
    * Metodo para listar las facturas de un producto determinado.
    * @return Una lista de facturas.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public ArrayList<Factura> listarFacturas() throws SQLException, ParseException{

        ArrayList <Factura> list = new ArrayList();

        //Se crea la conexion de la base de datos
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null) {

            Statement stmt = null;
            String query = "select FECHA from FACTURA where ID = " 
                    + codigoProd + ";";

            try {

                //Se buscan las facturas del producto
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                /* Para cada factura se crea un objeto y se agrega a la lista */
                while (rs.next()){

                    Date fecha = Date.valueOf(rs.getString("FECHA"));

                    //Se busca los datos faltantes de la factura en la base de datos
                    Factura factura = Factura.consultarFactura(this, fecha);
                    list.add(factura);
                }

            } catch (SQLException e) {

                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());

            } finally {

                //La conexion se cierra
                conexion.close(); 
            }
        }
        return list;
    }

   /**
    * Metodo para listar las servicios adicionales contratados de un producto 
    * determinado.
    * @return Una lista de objetos Posee.
    * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
    * la conexion.
    */
    public ArrayList<Posee> listarServiciosAdicionalesContratados() 
            throws SQLException{

        ArrayList <Posee> list = new ArrayList();

        //Se crea la conexion de la base de datos
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null) {

            Statement stmt = null;
            String query = "select NOMBRE_SERVICIO, FECHA_INIC from " 
                    + "POSEE where ID = " + codigoProd + ";";

            try {

                /*Se buscan los nombres de los servicios contratados y la fecha
                  de adquisicion */
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                /* Para todos los servicios adicionales contratados se crea un 
                   objeto y se agrega a la lista */
                while (rs.next()){

                    Date fecha = Date.valueOf(rs.getString("FECHA_INIC"));

                    //Se consultan los datos restantes para crear el objeto
                    Posee pos = Posee.consultarPosee(codigoProd, 
                            rs.getString("NOMBRE_SERVICIO"), fecha);
                    list.add(pos);

                }

            } catch (SQLException e) {
                //Si hay un error se imprime en pantalla
                System.out.println(e.getMessage());

            } catch (ParseException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

                //La conexion se cierra
                conexion.close(); 
            }
        }
        return list;
    }

   /**
    * Convierte un objeto de tipo Producto a String.
    * @return un String que representa al servicio.
    */
    @Override
    public String toString() {

        return "Codigo del producto: " + this.codigoProd + ", Modelo: " +
                this.modelo + ", Cliente: [" + this.cliente.toString() + "]";
    }
}
