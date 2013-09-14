
import java.sql.SQLException;

public class GestionarProductos {
    
    static Integer leerEnteroEntrada(String mensajeError) {
        Integer numero;
        
        while (true) {
            try {

                String numeroStr = Main.scanner.nextLine();
                numero = Integer.parseInt(numeroStr);
                break;

            } catch (Exception e) {
                System.out.println(mensajeError);
            }
        }
        return numero;
    }
    
    public static void registrarProducto() {
        
        System.out.println("Por favor ingrese el ID del producto");
        
        Integer id = leerEnteroEntrada("Ingrese el ID de nuevo");
        
        System.out.println("Ingrese el nombre del producto");

        String nombre = Main.scanner.nextLine();
        
        System.out.println("Ingrese la cedula del cliente");
        
        Integer cedula = leerEnteroEntrada("Ingrese la cedula de nuevo");
        
        Cliente cliente = Cliente.consultarCliente(cedula);
        Producto producto = new Producto(id, nombre, cliente);

        try {
            producto.registrarProducto();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
    public static void consultarProducto() throws SQLException {
        
        System.out.println("Ingrese el ID del producto a consultar.");
        
        Integer id = leerEnteroEntrada("Ingrese el ID de nuevo");
        
        Producto producto = Producto.consultarProducto(id);
        
        if (null != producto) {
            System.out.println(producto.toString());
            
            while (true) {
                System.out.println("Que desea hacer ahora?");
                System.out.println("1. Modificar el producto\n2. Eliminar el producto\n" +
                                "3. Salir");

                String input = Main.scanner.nextLine();
                
                switch (input) {
                    case "1":
                        modificarProducto(producto);
                        break;
                    case "2":
                        producto.eliminarProducto();
                        break;
                    case "3":
                        break;
                }
            }
        }
        else {
            System.out.println("El producto no existe");
            gestionProductos();
        }
    }
    
    public static void modificarProducto(Producto producto) throws SQLException {        
            
        while (true) {
            System.out.println("Que desea modificar?");
            System.out.println("1. ID\n2. Nombre del modelo\n3. Salir");
            String input = Main.scanner.nextLine();

            if (input.equals("1")) {

                System.out.println("Ingrese el nuevo ID del cliente.");

                Integer id = leerEnteroEntrada("Ingrese el ID de nuevo");

                producto.codigoProd = id;

            } else if (input.equals("2")) {
                
                System.out.println("Ingrese el nuevo nombre del modelo del producto");
                
                String nombre = Main.scanner.nextLine();
                
                producto.modelo = nombre;
                                
            } else if (input.equals("3")) {
                
                producto.modificarProducto();
                break;
            }
        }
        
        
        
        
        
        
        String input;
        do {
            System.out.println("Que desea modificar?");
            System.out.println("1. ID\n2. Nombre del modelo\n3. Salir");
            input = Main.scanner.nextLine();
            
            
            
            switch (input) {
                case "1":
                    System.out.println("Ingrese el nuevo ID del producto.");
                    Integer id = leerEnteroEntrada("Ingrese el ID de nuevo");
                    producto.codigoProd = id;
                    break;
                case "2":
                    System.out.println("Ingrese el nuevo nombre de modelo del producto");
                    String nombre = Main.scanner.nextLine();
                    producto.modelo = nombre;
                    break;
                case "3":
                    producto.modificarProducto();
                    break;
            }
        } while (!input.equals("3"));
                
        
        
    }
    
    public static void gestionProductos() throws SQLException {
            OUTER:
            while (true) {
                System.out.println("1. Registrar producto\n2. Consultar "
                        + "producto\n 3. Salir");
                String input = Main.scanner.nextLine();
                switch (input) {
                    case "1":
                        registrarProducto();
                        break;
                    case "2":
                        consultarProducto();
                        break;
                    case "3":
                        break OUTER;
                }
            }
        
        
        
    }
    
}
 