
package domain;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Clase factura: contiene los metodos necesarios para manejar los contenidos de
 * la tabla factura en la base de datos.
 */
public class Factura {
    Date fecha; // Fecha de la factura
    double costoPlan; // monto correspondiente al costo del plan
    double montoTotal; // monto total de la factura
    ArrayList<String> comentarios; // comentarios asociados a la factura
    Producto producto; //producto asociado a la factura
    
    /**
     * Asigna la fecha de la factura
     * @param fecha fecha de la factura
     */ 
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Asigna el costo del plan
     * @param costo costo del plan
     */
    public void setCostoPlan(double costo) {
        this.costoPlan = costo;
    }
    
    /**
     * Asigna el monto total de la factura
     * @param monto monto total de la factura
     */
    public void setMontoTotal(double monto) {
        this.montoTotal = monto;
    }
    
    /**
     * Asigna los comentarios de la factura
     * @param comentarios comentarios de la factura
     */
    public void setComentarios(ArrayList<String> comentarios) {
        this.comentarios = comentarios;
    }
    
    /**
     * Asigna el producto de la factura
     * @param producto producto de la factura
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    /**
     * Retorna la fecha de la factura
     * @return fecha de la factura
     */
    public Date getFecha() {
        return fecha;
    }
    
    /**
     * Retorna el costo del plan 
     * @return costo del plan
     */
    public double getCostoPlan() {
        return costoPlan;
    }
    
    /**
     * Retorna el monto total de la factura
     * @return monto total de la factura
     */
    public double getMontoTotal() {
        return this.montoTotal + this.costoPlan;
    }
    
    /**
     * Retorna los comentarios de la factura
     * @return comentarios de la factura
     */
    public ArrayList<String> getComentarios() {
        return comentarios;
    }
    
    /**
     * Retorna el producto de la factura
     * @return producto de la factura
     */
    public Producto getProducto() {
        return producto;
    }
    
    /**
     * Constructor de la clase factura
     * @param fecha fecha en la que se expide la factura
     * @param costoPlan costo del plan del producto
     * @param montoTotal monto de la factura
     * @param comentarios detalles de facturacion
     * @param producto producto asociado a la factura
     */
    public Factura(Date fecha, double costoPlan, double montoTotal, ArrayList<String> comentarios, Producto producto) {
        this.fecha = fecha;
        this.costoPlan = costoPlan;
        this.montoTotal = montoTotal;
        this.comentarios = comentarios;
        this.producto = producto;
    }
    
    /**
     * Permite obtener el monto total de la factura, sumando el monto total mas
     * el costo del plan.
     * @return retorna el monto total de la factura mas el costo del plan.
     */
    double calcularMontoTotal() {
        return this.montoTotal + this.costoPlan;
    }
    
    
    /**
     * Permite obtener una representacion en string un elemento de la clase Factura.
     * @return String retorna el string que representa al objeto.
     */
    @Override
    public String toString() {
        String comentarios = "";
        
        // se obtienen todos los comentarios de la factura
        for (int i=0; i < this.comentarios.toArray().length; ++i) {
                comentarios = comentarios + this.comentarios.get(i) + " ";
                if (this.comentarios.toArray().length -1 > i) {
                    comentarios = comentarios + ", ";
                }
            }
        // se devuelve el string de formato
        return "Fecha: " + this.fecha.toString() + ", Monto: " + 
                this.calcularMontoTotal() + ", Comentarios: [" + comentarios +
                "] , Producto: [" + producto.toString() + "]";
    }
    
    /**
     * Permite agregar una factura nuevo a la base de datos.
     * @throws SQLException puede lanzar una excepcion de sql
     */
    void registrarFactura() {
        // Se conecta a la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            // Se agrega la nueva factura a la tabla factura y se agregan sus
            // comentarios a la tabla comentario.
            st.execute("insert into factura values ('"+ this.producto.codigoProd + 
                    "', '" + this.fecha.toString() + "', '" + this.montoTotal + "');");
            
            for (int i=0; i < comentarios.toArray().length; ++i) {
                st.execute("insert into comentario values ('" + this.producto.codigoProd +
                    "', '" + this.fecha.toString() + "', '" +
                    this.comentarios.get(i).toString() + "');");
            }
            
            
            // Se cierra la conexion con la base de datos
            conn.close();
            
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * Metodo que permite devolver una instancia de factura hecha objeto
     * @param producto producto asociado a la factura
     * @param fecha fecha en la que se expide la factura
     * @return una instancia de la clase factura
     */
    static Factura consultarFactura(Producto producto, Date fecha) throws ParseException {
        // Inicializamos un objeto factura en null
        Factura factura = null;

        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            // Buscamos los comentarios de la factura en la base de datos
            ResultSet rs = st.executeQuery("select valor from comentario where"
                    + " id = '" + producto.codigoProd + "' and fecha ='"
                    + fecha.toString() + "';");
            // Si los conseguimos es porque existe la factura
            if (rs.next()) {

                // Creamos una nueva lista para los comentarios
                ArrayList<String> comentarios = new ArrayList<>();
                
                //Agregamos el primer comentario a la lista
                String comentario = rs.getString(1);
                comentarios.add(comentario);
                
                // Metemos en la lista los comentarios.
                while (rs.next()) {
                    comentario = rs.getString(1);
                    comentarios.add(comentario);
                }
                
                // Cerramos el ResultSet
                rs.close();
                
               //Buscamos a la factura en la base de datos
                rs = st.executeQuery("select monto_total from factura where "
                    + "id = '" + producto.codigoProd + "' and fecha ='"
                    + fecha.toString() + "';");

                rs.next();
                // Almacenamos el monto de la factura
                double montoTotal = rs.getDouble(1);
                rs.close();
                
                // Buscamos en la base de datos el plan al que esta asociado
                // el producto
                
                rs = st.executeQuery("select nombre_plan, tipo_plan"
                        + " from esta_afiliado where id = '" + producto.codigoProd
                        + "' and fecha_inic <= DATE '" + fecha.toString() +
                        "' and (fecha_fin is null or fecha_fin > DATE '" +
                        fecha.toString() + "');");
                
                rs.next();
                //Almacenamos los valores del nombre del plan y el tipo
                String nombPlan = rs.getString(1);
                String tipoPlan = rs.getString(2);
                
                Plan plan = Plan.consultarPlan(nombPlan, tipoPlan);
                // Cerramos el ResultSet
                rs.close();

                // Buscamos los montos asociados a costo del plan en la base de datos

                double costoPlan = plan.obtenerCosto(fecha);
                                
                // Creamos un nuevo objeto factura con los datos obtenidos.
                factura = new Factura(fecha, costoPlan, montoTotal,
                        comentarios, producto);
            
                
            /*Puede que la factura todavia no este generada pero haya un plan 
            asociado que tenga renta basica.  */  
            } else {
                
                rs = st.executeQuery("select nombre_plan, tipo_plan"
                        + " from esta_afiliado where id = " + producto.codigoProd
                        + " and fecha_inic <= DATE '" + fecha.toString() +
                        "' and (fecha_fin is null or fecha_fin > DATE '" +
                        fecha.toString() + "');");
                
                if (rs.next()){
                    //Almacenamos los valores del nombre del plan y el tipo
                    String nombPlan = rs.getString(1);
                    String tipoPlan = rs.getString(2);

                    Plan plan = Plan.consultarPlan(nombPlan, tipoPlan);
                    
                    Double costoPlan = plan.obtenerCosto(fecha);
                    
                    ArrayList<String> comentarios = new ArrayList();
                    
                    if (costoPlan != null)
                        factura = new Factura(fecha, costoPlan, 0.0, comentarios,
                                producto);
                    
                }
                
            }
            // cerramos la conexion
            conn.close();
            
        } catch (SQLException ex) {
           // Si hay una excepcion se imprime un mensaje
            System.err.println(ex.getMessage());
        }
        // Retorna a la factura que saco de la base de datos y en caso de no
        // conseguirlo retorna null.
        return factura;
    }
    
    /**
     * Metodo que permite modificar una factura en la tabla factura de la base de datos
     */
    void modificarFactura() {
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
             // Actualizamos los datos de la factura en la base de datos
            st.executeUpdate("update factura set monto_total ='"
                    + this.montoTotal +"' where id ='"
                    + this.producto.codigoProd
                    + "' and fecha ='" + this.fecha.toString() +"';");
             // Se borran los comentarios de la factura
            st.execute("delete from comentario where id ='" 
                    + this.producto.codigoProd + "' and fecha ='"
                    + this.fecha.toString() + "';");
             // Actualizamos los comentarios de la factura en la base de datos
            for (int i=0; i < this.comentarios.toArray().length; ++i) {
                st.execute("insert into comentario values ('" +
                        this.producto.codigoProd + "', '" +
                        this.fecha.toString() + "', '" +
                        this.comentarios.get(i).toString() +"');");
                }                  
            
          } catch (SQLException ex) {
              // Si hay una excepcion se imprime un mensaje
              System.err.println(ex.getMessage());
          }
    }
    
    /**
     * Metodo que permite eliminar una factura de la tabla factura de la base de datos
     */
    void eliminarFactura() {
          // Conectamos con la base de datos
          try (Connection conn = Conexion.obtenerConn()) {
             Statement st; 
             st = conn.createStatement();

             st.execute("delete from comentario where id ='"
                      + this.producto.codigoProd 
                      + "' and fecha ='" + this.fecha.toString() +"';");
             // Se elimina la factura de la tabla factura
             st.execute("delete from factura where id ='"
                      + this.producto.codigoProd
                      + "' and fecha ='" + this.fecha.toString() +"';");                         
            
          } catch (SQLException ex) {
              // Si hay una excepcion se imprime un mensaje
              System.err.println(ex.getMessage());
          }
    }
    
     /**
     * Verifica si una factura es igual a this.
     * @return Regresa true si la factura pasado como parametro tiene los
     * mismos atributos que this.
     */
    @Override
    public boolean equals(Object obj) {
        
        Factura plan = (Factura) obj;
        
        return (plan.producto.equals(this.producto)) &
               (plan.costoPlan == this.costoPlan) &
               (plan.comentarios.equals(this.comentarios)) &
               (plan.fecha.equals(this.fecha)) &
               (plan.montoTotal == this.montoTotal);
    }
}
