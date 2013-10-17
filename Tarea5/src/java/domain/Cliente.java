
package domain;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Clase Cliente: contiene los metodos necesarios para manejar los contenidos de
 * la tabla cliente en la base de datos.
 */
public class Cliente {
    //El minimo de la cedula es 1
    @Min(1)
    public Integer cedula;            // Cedula del cliente
    //El nombre no puede ser vacio
    @NotEmpty
    public String nombre;             // Nombre del cliente
    //La direccion no puede ser vacia
    @NotEmpty
    public String direccion;          // Direccion del cliente
    //Los telefonos no pueden ser vacios
    @NotEmpty
    public ArrayList<Long> telefonos; // telefonos del cliente
    
    /**
     * Constructor para la clase
     * @param cedula cedula del cliente
     * @param nombre nombre del cliente
     * @param direccion direccion del cliente
     * @param telefonos lista de telefonos del cliente
     */
    public Cliente(Integer cedula, String nombre, String direccion, ArrayList<Long> telefonos) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefonos = telefonos;
    }
    
    /**
     * Constructor vacio para la clase
     */
    public Cliente() {
        
    }
    
    /**
     * Asigna la cedula del cliente
     * @param cedula cedula del cliente
     */
    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }
    
    /**
     * Asigna el nombre del cliente
     * @param nombre nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Asigna la direccion del cliente
     * @param direccion direccion del cliente
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    /**
     * Asigna los telefonos del cliente
     * @param telefonos telefonos del cliente
     */
    public void setTelefonos(ArrayList<Long> telefonos) {
        this.telefonos = telefonos;
    }
    
    /**
     * Retorna la cedula del cliente
     * @return cedula del cliente
     */
    public Integer getCedula() {
        return this.cedula;
    }
    
    /**
     * Retorna el nombre del cliente
     * @return nombre del cliente
     */
    public String getNombre() {
        return this.nombre;
    }
    
    /**
     * Retorna la direccion del cliente
     * @return direccion del cliente
     */
    public String getDireccion() {
        return this.direccion;
    }
    
    /**
     * Retorna los telefonos del cliente
     * @return telefonos del cliente
     */
    public ArrayList<Long> getTelefonos() {
        return this.telefonos;
    }
    
    /**
     * Permite agregar un cliente nuevo a la base de datos.
     * @throws SQLException puede lanzar una excepcion de sql
     */
    public void registrarCliente(){
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            // Se agrega el nuevo cliente a la tabla cliente y se agregan sus
            // telefonos a la tabla telefono.
            
            st.execute("insert into cliente values ('" + this.cedula.toString() + "', '"
                    + this.nombre + "', '" + this.direccion + "');");

            for (int i=0; i < telefonos.toArray().length; ++i) {
                st.executeUpdate("insert into telefono values ('" + this.cedula.toString() 
                        + "', '" + this.telefonos.get(i).toString() + "');");
            }
            
            // Cerramos la conexion
            conn.close();
            
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
          
    }
    
    /**
     * Permite, dada la cedula del cliente, buscar al mismo en la base de datos.
     * @param cedula cedula del cliente
     * @return retorna un objeto Cliente en caso de conseguirlo en la base de
     * datos y si no lo consigue retorna null
     */
    public static Cliente consultarCliente(Integer cedula) {
        // Inicializamos un objeto Cliente en null
        Cliente cliente = null;
        
        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            
            Statement st;
            st = conn.createStatement();
            
            // Buscamos los telefonos del cliente en la base de datos
            ResultSet rs = st.executeQuery("select numero from telefono where "
                    + "ci = '" + cedula.toString() + "';");

            // Si los conseguimos es porque existe el cliente
            if (rs.next()) {

                // Creamos una nueva lista para los telefonos
                ArrayList<Long> tlfs = new ArrayList<>();

                // Agregamos el primer telefono a la lista de tlfs
                Long telefono = Long.valueOf(rs.getString(1));
                tlfs.add(telefono);
                    
                // Metemos en la lista los telefonos.
                while (rs.next()) {
                    telefono = Long.valueOf(rs.getString(1));
                    tlfs.add(telefono);
                }
                
                // Cerramos el ResultSet
                rs.close();
                
                // Buscamos al cliente en la base de datos
                rs = st.executeQuery("select ci, nombre, direccion from cliente where ci = '" + 
                    cedula.toString() + "';");
            
                rs.next();
              
                // Creamos un nuevo objeto cliente con los datos obtenidos.
                cliente = new Cliente(rs.getInt(1),
                        rs.getString(2), rs.getString(3), tlfs);
                        
            }
            
            // cerramos la conexion
            conn.close();
            
        } catch (SQLException ex) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(ex.getMessage());
        }
        
            // Retorna al cliente que saco de la base de datos y en caso de no
            // conseguirlo retorna null.
        return cliente;
        
    }
    
    /**
     * Permite modificar la informacion del cliente en la base de datos.
     */
    public void modificarCliente() {
        // Establecemos la conexion con la base de datos
         try (Connection conn = Conexion.obtenerConn()) {
         
             Statement st;
             st = conn.createStatement();
                 
             // Actualizamos los datos del cliente en la base de datos
             st.executeUpdate("update cliente set nombre ='"
                    +this.nombre+"', direccion ='"+this.direccion+"'"
                    + " where ci ='"+this.cedula+"';");
       
             // Eliminamos los telefonos asociados al cliente
             st.execute("delete from Telefono where ci ='"+this.cedula+"';");
                
             // Agregamos los telefonos asociados al cliente que se encuentran
             // en el atributo telefonos
             for (int i=0; i < this.telefonos.toArray().length; ++i) {
                st.execute("insert into Telefono values ('"+ this.cedula +"', '"
                        +this.telefonos.get(i).toString()+"');");
             }                  
    
          } catch (SQLException ex) {
              // Si hay una excepcion se imprime un mensaje
              System.err.println(ex.getMessage());
          }
        
    }
   
    /**
     * Permite eliminar a un cliente de la base de datos
     */
    public void eliminarCliente() {
          
        // Buscamos los productos de los que un cliente es dueño
        ArrayList<Producto> productos = Facturador.obtenerProductos(this);

        // Conectamos con la base de datos
        try (Connection conn = Conexion.obtenerConn()) {
            Statement st;
            st = conn.createStatement();
                        
            // Eliminamos los telefonos del cliente
            st.execute("delete from Telefono where ci ='"+this.cedula+"';");

            // Eliminamos los productos que un clinte posee
            for (int i=0; i < productos.toArray().length; ++i) {
                ((Producto) productos.get(i)).eliminarProducto();
            }            
            
            // Eliminamos al cliente
            st.execute("delete from Cliente where ci ='"+this.cedula+"';");
            
          } catch (SQLException ex) {
              // Si hay una excepcion se imprime un mensaje
              System.err.println(ex.getMessage());
          }
        
    }
    
    /**
     * Permite obtener una representacion en string un elemento de la clase Cliente.
     * @return String retorna el string que representa al objeto.
     */
    @Override
    public String toString() {
        String tlfs = "";
        
        // se obtienen todos los telefonos del cliente
       /* for (int i=0; i < this.telefonos.toArray().length; ++i) {
                tlfs = tlfs + this.telefonos.get(i).toString() + " ";
                if (this.telefonos.toArray().length -1 > i) {
                    tlfs = tlfs + ", ";
                }
            }*/
        // Regresamos el string con el formato de salida
        return "Cedula: " + this.cedula.toString() + ", Nombre: " + 
                this.nombre + ", Direccion: " + this.direccion + ", Telefonos: "
                + tlfs;
    }
    
    /**
     * Verifica si un cliente es igual a this.
     * @return Regresa true si el cliente pasado como parametro tiene los
     * mismos atributos que this.
     */
    @Override
    public boolean equals(Object obj) {
        
        Cliente cliente = (Cliente) obj;

        return ((cliente.cedula).equals(this.cedula)) &
               (cliente.direccion.equals(this.direccion)) &
               (cliente.nombre.equals(this.nombre)) &
               (cliente.telefonos.equals(this.telefonos));
    }

    
    public static ArrayList<Cliente> listarClientes() throws SQLException {

        ArrayList <Cliente> list = new ArrayList();

        //Se crea la conexion de la base de datos
        Connection conexion = Conexion.obtenerConn();

        if (conexion != null) {

            Statement stmt = null;
            String query = "select CI from cliente;";

            try {
      
                stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                Cliente cliente;
          
                while (rs.next()){                
                        
                    cliente = consultarCliente(Integer.parseInt(rs.getString(1)));
                    
                    if (cliente != null)
                        list.add(cliente);
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
