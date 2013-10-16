
package domain;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Clase Afiliacion: Contiene metodos necesarios para manejar los contenidos de
 * la tabla esta_afiliado de la base de datos.
 */
public class Afiliacion {
    Date fechaInicio;    // Fecha de inicio de la afiliacion.
    Date fechaFin;       // Fecha de fin de la afiliacion.
    Plan plan;           // Plan al que esta asociado.
    Producto producto;   // Producto que esta asociado.

    /**
     * Constructor para la clase
     * @param fechaI fecha de inicio de la afiliacion.
     * @param fechaF fecha de fin de la afiliacion.
     * @param plan plan que esta afiliado
     * @param producto producto que esta afiliado al plan
     */
    public Afiliacion(Date fechaI, Date fechaF, Plan plan, Producto producto) {
        this.fechaInicio = fechaI;
        this.fechaFin = fechaF;
        this.plan = plan;
        this.producto = producto;
    }

    /**
     * Permite obtener una representacion en string un elemento de la clase Afiliacion.
     * @return String retorna el string que representa al objeto.
     */
    @Override
    public String toString() {
        if (this.fechaFin == null) {
            return "Fecha de Inicio: " + this.fechaInicio.toString() +
                    ", Producto: [ " + this.producto.toString() +
                    "], Plan: [" + this.plan.toString() + "]";
        }
        else {
            return "Fecha de Inicio: " + this.fechaInicio.toString() +
                    ", Fecha de Fin: " + this.fechaFin.toString() +
                    ", Producto: [ " + this.producto.toString() +
                    "], Plan: [" + this.plan.toString() + "]";
        }
    }

    /**
     * Permite registrar una afiliacion nueva a la base de datos.
     */
    void registrarAfiliacion() {
        // Intentamos establecer conexion con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {

            Statement st;
            st = conn.createStatement();

            // Se ejecuta el insert a la base de datos de los valores correspondientes
            // diferenciando el caso donde haya una fecha de fin y donde no.
            if (this.fechaFin == null) {
                st.execute("insert into esta_afiliado values ('"+ this.producto.codigoProd +
                    "', '" + this.plan.nombre + "', '" + this.plan.tipoPlan + "', '" +
                    this.fechaInicio.toString() + "');");
            }
            else {
                st.execute("insert into esta_afiliado values ('"+ this.producto.codigoProd +
                        "', '" + this.plan.nombre + "', '" + this.plan.tipoPlan + "', '" +
                        this.fechaInicio.toString() + "', '" + this.fechaFin.toString() + "');");
            }
            
            // Cerramos la conexion
            conn.close();

        } catch (SQLException ex) {
            // En caso de una excepcion se imprime el mensaje de error.
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Permite, dada la clave de la afiliacion, buscar en la base de datos la misma.
     * @param id id del producto
     * @param nombre_plan nombre del plan 
     * @param tipo_plan tipo de plan
     * @param fechaInicio fecha de inicio del plan
     * @return retorna una instancia de la clase Afiliacion con los datos correspondientes
     * a la entrada en la base de datos, retorna null si no consigue la entrada
     * en la base de datos.
     * @throws ParseException puede tirar una excepcion de parsing.
     */
    static Afiliacion consultarAfiliacion(Integer id, String nombre_plan, String tipo_plan, Date fechaInicio) throws ParseException {
        // Inicializamos un objeto de la clase afiliacion en null
        Afiliacion afiliacion = null;

        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {

            Statement st;
            st = conn.createStatement();

            // Ejecutamos el query buscando la entrada de afiliacion deseada.
            ResultSet rs = st.executeQuery("select fecha_fin "
                    + "from esta_afiliado where id = '" + id 
                    + "' and nombre_plan = '" + nombre_plan + "' and tipo_plan = '"
                    + tipo_plan + "' and fecha_inic = '" + fechaInicio.toString()
                    + "';");

            // Si el query retorna un resultado 
            if (rs.next()) {

                // Creamos una nueva fechaF
                java.sql.Date fechaF = null;
                
                // Si hay una fecha fin para la afiliacion se asigna a fechaF
                // sino, se mantiene null.
                if (null != rs.getString(1)) {
                    // Creamos un nuevo objeto del tipo Date con la fecha de
                    // fin conseguida
                    java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(1));
                    fechaF = new java.sql.Date(utilDate.getTime());
                }

                // Buscamos en la base de datos el plan correspondiente a la
                // combinacion de nombre y tipo de plan dada
                Plan plan = Plan.consultarPlan(nombre_plan, tipo_plan);
                
                // Buscamos en la base de datos el producto correspondiente a
                // ese id y creamos un objeto con el.
                Producto producto = Producto.consultarProducto(id);

                // Creamos un nuevo objeto afiliacion para retornar
                afiliacion = new Afiliacion(fechaInicio, fechaF, plan, producto);
            }
            // Cerramos la conexion.
            conn.close();


        } catch (SQLException ex) {
            // Si hay una excepcion se imprime el mensaje de la misma.
            System.err.println(ex.getMessage());
        }

        // Se retorna afiliacion, si el query dio vacio se retornaria null.
        return afiliacion;
    }

    /**
     * Se encarga de eliminar una entrada de la tabla esta_afiliado en la base
     * de datos correspondiente al objeto.
     */
    void eliminarAfiliacion() {

        // Establecemos conexion con la base de datos.
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();

            // Ejecutamos el delete.
            st.execute("delete from esta_afiliado where nombre_plan ='" + 
                    this.plan.nombre + "' and tipo_plan ='" + this.plan.tipoPlan + 
                    "' and id = '" + this.producto.codigoProd + "' and fecha_inic = '" +
                    this.fechaInicio.toString() + "';");
            
        } catch (SQLException ex) {
            // Si hay una excepcion se imprime el mensaje de la misma.
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Se encarga de modificar la informacion de la afiliacion en la base
     * de datos.
     */
    void modificarAfiliacion() {
        // Establecemos conexion con la base de datos.
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();

            if (null != this.fechaFin) {
                // Ejecutamos el uptdate en la base de datos
                st.executeUpdate("update esta_afiliado set fecha_fin ='"
                        + this.fechaFin.toString() + "' " + "where id ='"
                        + this.producto.codigoProd + "' and nombre_plan ='" 
                        + this.plan.nombre
                        + "' and tipo_plan ='" + this.plan.tipoPlan
                        + "' and fecha_inic ='" + this.fechaInicio.toString() +"';");
            }
            else {
                // Ejecutamos el uptdate en la base de datos
                st.executeUpdate("update esta_afiliado set fecha_fin = null"
                        + " where id ='" + this.producto.codigoProd 
                        + "' and nombre_plan ='" + this.plan.nombre
                        + "' and tipo_plan ='" + this.plan.tipoPlan
                        + "' and fecha_inic ='" + this.fechaInicio.toString() +"';");
            }

        } catch (SQLException ex) {
            // Si hay una excepcion se imprime el mensaje de la misma.
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Verifica si una afiliacion es igual a this.
     * @return Regresa true si la afiliacion pasada como parametro tiene los
     * mismos atributos que this.
     */
    @Override
    public boolean equals(Object obj) {
        
        Afiliacion afilia = (Afiliacion) obj;
        
        return ((fechaFin != null && afilia.fechaFin != null && 
                afilia.fechaFin.equals(this.fechaFin)) || 
                fechaFin == afilia.fechaFin) &&
               (afilia.fechaInicio.equals(this.fechaInicio)) &
               (afilia.plan.equals(this.plan)) &
               (afilia.producto.equals(this.producto));
    }
}
