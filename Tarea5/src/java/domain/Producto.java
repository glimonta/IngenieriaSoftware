
package domain;
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
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Clase Servicio: Representa a la entidad Producto en la base de datos como
 * objeto facilitando su uso a nivel de software.
 */

public class Producto extends Facturable {
    //El minimo del codigo de producto es -1
    @Min(-1)
    public int codigoProd = 0;  //Codigo del producto
    
    public String modelo;       //Modelo del producto
    
    public Cliente cliente;     //Cliente que al quien le pertenece el producto

    /**
     * Constructor vacio
     */
    public Producto() {};    
    
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
     * Asigna el codigo del producto
     * @param codigo codigo del producto
     */
    public void setCodigoProd(int codigo) {
        this.codigoProd = codigo;
    }
    
    /**
     * Asigna el modelo del producto
     * @param mod modelo del producto
     */
    public void setModelo(String mod) {
        this.modelo = mod;
    }
    
    /**
     * Asigna al cliente dueño del producto
     * @param cliente cliente dueño del producto
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    /**
     * Retorna el codigo del producto
     * @return codigo del producto
     */
    public int getCodigoProd() {
        return codigoProd;
    }
    
    /**
     * Retorna el modelo del producto
     * @return modelo del producto
     */
    public String getModelo(){
        return modelo;
    }
    
    /**
     * Retorna el cliente dueño del producto
     * @return cliente dueño del producto
     */
    public Cliente getCliente() {
        return cliente;        
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

               insert = "insert into ES_DUENIO values("+ codigoProd +
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

                if (rs.next()) {

                    //Se buscan los datos del cliente para agregarlos al objeto
                    client = Cliente.consultarCliente(
                            Integer.parseInt(rs.getString("CI")));

                    //Se crea el producto
                    product = new Producto(codigo, 
                            rs.getString("NOMBRE_MODELO"), client);
                }
                
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

            String delete = "delete from Es_DUENIO where ID = " + this.codigoProd + ";";
            stmt.executeUpdate(delete);
            
            //Se elimina el producto de la base de datos
            delete = "delete from PRODUCTO where ID = " +
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
    * Convierte un objeto de tipo Producto a String.
    * @return un String que representa al servicio.
    */
    @Override
    public String toString() {

        return "Codigo del producto: " + this.codigoProd + ", Modelo: " +
                this.modelo + ", Cliente: [" + this.cliente.toString() + "]";
    }
    
    /**
     * Se encarga de retornar la descripcion de un producto.
     * @return Si este producto no esta decorado debe retornar un string vacio,
     * en caso de estar decorado este sería el caso base de la recursión.
     */
    public String obtenerDescripcion() {
        return "";    
    };

    /**
     * Se encarga de retornar el precio de un producto.
     * @return Si este producto no esta decorado debe retornar 0 como precio, en
     * caso de estar decorado este sería el caso base de la recursión.
     */
    public double obtenerPrecio() {
        return 0;
    };
    
    /**
     * Verifica si un producto es igual a this.
     * @return Regresa true si el producto pasado como parametro tiene los
     * mismos atributos que this.
     */
    @Override
    public boolean equals(Object obj) {
        
        Producto prod = (Producto) obj;
        return (prod.codigoProd == this.codigoProd) &
               (prod.modelo.equals(this.modelo)) &
               (prod.cliente.equals(this.cliente));
    }
    
 public static ArrayList<Producto> listarProductos() throws SQLException {

        ArrayList <Producto> list = new ArrayList();

        //Se crea la conexion de la base de datos
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null) {

            Statement stmt = null;
            String query = "select ID from producto;";

            try {
      
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Producto producto;
          
                while (rs.next()){                
                        
                    producto = consultarProducto(Integer.parseInt(rs.getString(1)));
                    
                    if (producto != null)
                        list.add(producto);
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
}
