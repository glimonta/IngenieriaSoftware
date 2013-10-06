
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 */
public class Facturador {
    
/**
 * Permite listar los productos de los que es dueño el cliente.
 * @return retorna null si no es dueño de algun producto y retorna una lista
 * de productos en caso de que existan.
 */
public static ArrayList<Producto> obtenerProductos(Cliente cliente) {
    ArrayList<Producto> productos = new ArrayList<>();

    // Conectamos con la base de datos
    try (Connection conn = Conexion.obtenerConn()) {

        Statement st;
        st = conn.createStatement();

        // Buscamos en la base de datos los productos de los cuales el
        // cliente es dueño.
        ResultSet rs = st.executeQuery("select id, nombre_modelo from "
                + "producto natural join es_duenio where ci = '" + 
                cliente.cedula.toString() + "';");

        if (rs.next()) {

            // Agregamos el primer producto a la lista productos
            Producto prod = new Producto(Integer.parseInt(rs.getString(1)), rs.getString(2), cliente);
            productos.add(prod);

            // Agregamos los productos restantes a la lista productos
            while (rs.next()) {
                prod = new Producto(Integer.parseInt(rs.getString(1)), rs.getString(2), cliente);
                productos.add(prod);
            }
        }

        // Cerramos la conexion
        conn.close();

    } catch (SQLException ex) {
        // Si hay una excepcion se imprime un mensaje
        System.err.println(ex.getMessage());
    }

    // Retorna la lista de productos en caso de que el cliente sea dueño de
    // al menos un producto, en caso contrario retorna null.
    return productos;

}
    
/**
 * Metodo para obtener el plan actual segun la fecha del sistema.
 * @return Un objeto plan que representa el plan actual del producto.
 * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
 * la conexion.
 */
public static Plan obtenerPlanActual(Producto producto) throws SQLException{

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
                "where ID = " + producto.codigoProd + " and FECHA_FIN is null "
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
                       "from ESTA_AFILIADO where ID = " + producto.codigoProd + 
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
public static Factura obtenerFacturaActual(Producto producto) throws SQLException, ParseException{

    Factura factura = null;

    //Se crea la conexion de la base de datos
    Connection conexion = Conexion.obtenerConn();

    if (conexion != null){

        Statement stmt = null;
        String query = "select FECHA, MONTO_TOTAL from FACTURA " +
                "where ID = " + producto.codigoProd + ";";
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
                    factura = Factura.consultarFactura(producto, fechaFact);
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
 * Metodo para listar las afiliaciones de un producto determinado.
 * @return Una lista de afiliaciones.
 * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
 * la conexion.
 */
public static ArrayList<Afiliacion> listarPlanesAfiliados(Producto producto) throws SQLException{

    ArrayList <Afiliacion> list = new ArrayList();

    //Se crea la conexion de la base de datos
    Connection conexion = Conexion.obtenerConn();

    if (conexion != null) {

        Statement stmt = null;
        String query = "select NOMBRE_PLAN, TIPO_PLAN, FECHA_INIC," 
                + "FECHA_FIN from ESTA_AFILIADO where ID = " + producto.codigoProd + ";";

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
                Afiliacion afil = new Afiliacion(fechaI,fechaF, plan, producto);

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
public static ArrayList<Factura> listarFacturas(Producto producto) throws SQLException, ParseException{

    ArrayList <Factura> list = new ArrayList();

    //Se crea la conexion de la base de datos
    Connection conexion = Conexion.obtenerConn();

    if (conexion != null) {

        Statement stmt = null;
        try {

            //Se buscan las facturas del producto
            stmt = conexion.createStatement();

            String query = "select date_part('month', min(FECHA_INIC)) as mes,"
                    + "date_part('year', min(FECHA_INIC)) as year "
                    + "from ESTA_AFILIADO where ID = " + producto.codigoProd + ";";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()){

                int year = Integer.parseInt(rs.getString("year")) - 1900;
                int mes = Integer.parseInt(rs.getString("mes")) -1;
                Date fechaMinima = new Date (year, mes, 1);
                Calendar calendar = new GregorianCalendar();
                Date fechaActual = new Date(calendar.getTimeInMillis());
                /* Para cada factura se crea un objeto y se agrega a la lista */
                
                while (fechaMinima.compareTo(fechaActual) <= 0){

                    //Se busca los datos faltantes de la factura en la base de datos
                    Factura factura = Factura.consultarFactura(producto, fechaMinima);
                    
                    if (factura != null) {
                        list.add(factura);
                    }
                    mes++;
                    if (mes == 12){
                        mes = 0;
                        year++;

                    }

                    fechaMinima = new Date (year, mes, 1);

                }
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
 * Metodo para listar los consumo realizados por un producto durante un
 * periodo determinado.
 * @return Una lista de los consumos realizados.
 * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
 * la conexion.
 */
public static ArrayList<Consumo> listarConsumos(Producto producto,Date inicio, Date fin) 
        throws SQLException {

    ArrayList<Consumo> list = new ArrayList();

    //Se crea la conexion de la base de datos
    Connection conexion = Conexion.obtenerConn();

    if(conexion != null){

        Statement stmt = null;
        String query = "select FECHA, CANTIDAD, NOMBRE_SERVICIO from "+
                "CONSUME where ID = " + producto.codigoProd + ";";

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
                            Date.valueOf(rs.getString("FECHA")), producto, serv);

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
 * Metodo para listar las servicios adicionales contratados de un producto 
 * determinado.
 * @return Una lista de objetos Posee.
 * @throws SQLException puede lanzar un excepcion si hay un error al cerrar
 * la conexion.
 */
public static ArrayList<Posee> listarServiciosAdicionalesContratados(Producto producto) 
        throws SQLException{

    ArrayList <Posee> list = new ArrayList();

    //Se crea la conexion de la base de datos
    Connection conexion = Conexion.obtenerConn();

    if (conexion != null) {

        Statement stmt = null;
        String query = "select NOMBRE_SERVICIO, FECHA_INIC from " 
                + "POSEE where ID = " + producto.codigoProd + ";";

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
                Posee pos = Posee.consultarPosee(producto.codigoProd, 
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

    public static Factura comoFacturar(Producto producto, Date fecha) {
        
        try {
            if (Producto.consultarProducto(producto.codigoProd) == null)               
            return null;

                Plan plan = buscarPlan(producto,fecha);

                if (plan == null)
                    return null;
                
                ComoFacturar facturar = null;
                
                if (plan.tipoPlan.equals("PREPAGO"))
                    //facturar = new FacturarPrepago();
                    System.out.println("No implementado todavia");
                else 
                    facturar = new FacturarPostpago();
                
                Factura fact = facturar.facturar(producto,fecha);
                return fact;
                
        } catch (SQLException ex) {
            Logger.getLogger(Facturador.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public static Plan buscarPlan(Producto producto, Date fecha) throws SQLException {
        
        ArrayList<Afiliacion> afiliaciones = Facturador.listarPlanesAfiliados(producto);
        
        for (Afiliacion afil : afiliaciones) {

            if (((afil.fechaInicio.compareTo(fecha)) <= 0) 
               && (afil.fechaFin == null || (fecha.compareTo(afil.fechaFin)) < 0)) {

                return afil.plan;      
            } 
        }        
        
        return null;  
    }
}
