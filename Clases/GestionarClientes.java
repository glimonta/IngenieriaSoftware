
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionarClientes {
    
    /**
     * Metodo que interactua con el usuario para registrar un cliente.
     */
    public static void registrarCliente() {
        
        System.out.println("Por favor ingrese el nombre del cliente.");

        String nombre = Main.scanner.nextLine();

        System.out.println("Ingrese la cedula del cliente.");

        int cedula;

        //El programa pide la cedula hasta que sea valida
        while (true) {
            try {

                String cedulaStr = Main.scanner.nextLine();
                cedula = Integer.parseInt(cedulaStr);
                break;

            } catch (Exception e) {
                System.out.println("Ingrese la cedula de nuevo");
            }
        }

        System.out.println("Ingrese la direccion del cliente.");

        String direccion = Main.scanner.nextLine();

        System.out.println("Ingrese el telefono del cliente." +
                           "Terminar de ingresar presione solamente enter.");

        ArrayList<Long> telefonos = new ArrayList();

        //Registra los telefonos y verifica que sean validos
        while (true) {
            try {

                String telefonoStr = Main.scanner.nextLine();

                /* Si el String de entrada es vacio ya no se registran 
                 * mas telefonos */
                if (telefonoStr.isEmpty())
                    break;
                long telefono = Long.parseLong(telefonoStr);
                telefonos.add(telefono);

            } catch (Exception e) {
                System.out.println("El telefono es invalido.");
            }
        }

        //Crea el cliente
        Cliente cliente = new Cliente(cedula,nombre,direccion,telefonos);

        //Agrega al cliente en la base de datos
        cliente.registrarCliente();
       
    }
        
    /**
     * Metodo que permite consultar un cliente de la base de datos.
     */
    public static void consultarCliente() {
        
        System.out.println("Ingrese la cedula del cliente a consultar.");
        
        int cedula;
        
        //El programa pide la cedula hasta que sea un numero valido
        while (true) {
            try {

                String cedulaStr = Main.scanner.nextLine();
                cedula = Integer.parseInt(cedulaStr);
                break;

            } catch (Exception e) {
                System.out.println("Ingrese la cedula de nuevo");
            }
        }
        
        //Se busca la nformacion en la base de datos
        Cliente cliente = Cliente.consultarCliente(cedula);
                
        //Si el cliente no existe lo notifica.
        if(cliente == null){
            System.out.println("El cliente no existe en el sistema.");
            return;
        }
        
        /* Permite modificar y eliminar el cliente */
        while (true) {
            
            System.out.println(cliente.toString());
            System.out.println("Que desea hacer ahora?");
            System.out.println("1. Modificar el cliente\n2. Eliminar el cliente\n" +
                               "3. Salir");

            String input = Main.scanner.nextLine();

            if (input.equals("1"))
                modificarCliente(cliente);
            else if (input.equals("2")) {
                cliente.eliminarCliente();
                break;
            } else if (input.equals("3"))
                break;
        }
    }
    
    /**
     * Metodo que permite modificar un cliente ya consultado.
     * @param cliente cliente a modificar
     */
    public static void modificarCliente(Cliente cliente) {
        
              
        /*Ciclo que permite modificar varios campos de cliente antes de
           registrarlo en la base de datos */
        while (true) {
            System.out.println("Que desea modificar?");
            System.out.println("1. Nombre\n2. Direccion\n3. Telefonos\n4. Salir");
            String input = Main.scanner.nextLine();

            if (input.equals("1")) {

                System.out.println("Ingrese el nuevo nombre del cliente.");

                String nombre = Main.scanner.nextLine();

                cliente.nombre = nombre;

            } else if (input.equals("2")) {
                
                System.out.println("Ingrese la nueva direccion del cliente.");
                
                String direccion = Main.scanner.nextLine();
                
                cliente.direccion = direccion;
                                
            } else if (input.equals("3")) {
                
                //POR AHORA ES ASI. Falta eliminar y modificar uno que ya existe
                System.out.println("Ingrese un telefono a agregar.");
                
                //Permite agregar varios telefonos.
                while (true) {
                    try {

                        String telefonoStr = Main.scanner.nextLine();
                        long telefono = Long.parseLong(telefonoStr);
                        cliente.telefonos.add(telefono);
                        break;

                    } catch (Exception e) {
                        System.out.println("El telefono es invalido.");
                    }
                }
                
                
            } else if (input.equals("4")) {
                
                /*Luego de que se han modificado todos los campos se modifica 
                  en la base de datos */
                cliente.modificarCliente();
                break;
            }
        }
                
        
        
    }
    
    
    /**
     * Menu principal de gestionar Clientes.
     */
    public static void gestionClientes() {

            OUTER:
            while (true) {
                System.out.println("1. Registrar cliente\n2. Consultar cliente\n" +
                                   "3. Salir");
                String input = Main.scanner.nextLine();
                switch (input) {
                    case "1":
                        registrarCliente();
                        break;
                    case "2":
                        consultarCliente();
                        break;
                    case "3":
                        break OUTER;
                }
            }
        
        
        
    }
    
}
