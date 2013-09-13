import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;



public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    
    public static void registrarCliente() {
        
        System.out.println("Por favor ingrese el nombre del cliente.");

        String nombre = Main.scanner.nextLine();

        System.out.println("Ingrese la cedula del cliente.");

        int cedula;

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

        while (true) {
            try {

                String telefonoStr = Main.scanner.nextLine();
                if (telefonoStr.isEmpty())
                    break;
                long telefono = Long.parseLong(telefonoStr);
                telefonos.add(telefono);

            } catch (Exception e) {
                System.out.println("El telefono es invalido.");
            }
        }

        Cliente cliente = new Cliente(cedula,nombre,direccion,telefonos);

        try {
            cliente.registrarCliente();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
       
    }
    
    public static void consultarCliente() {
        
        System.out.println("Ingrese la cedula del cliente a consultar.");
        
        int cedula;
        
        while (true) {
            try {

                String cedulaStr = Main.scanner.nextLine();
                cedula = Integer.parseInt(cedulaStr);
                break;

            } catch (Exception e) {
                System.out.println("Ingrese la cedula de nuevo");
            }
        }
        
        Cliente cliente = Cliente.consultarCliente(cedula);
                
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
    
    public static void modificarCliente(Cliente cliente) {
        
              
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
                
                cliente.modificarCliente();
                break;
            }
        }
                
        
        
    }
    
    
    public static void gestionClientes() {
        
        
        
        while (true) {
        
            System.out.println("1. Registrar cliente\n2. Consultar cliente\n" +
                               "3. Salir");
        
            String input = Main.scanner.nextLine();
            
            if (input.equals("1")) {
                
                registrarCliente();
                
            } else if (input.equals("2")) {
                
                consultarCliente();               
                               
            } else if (input.equals("3"))
                break;
   
        }
        
        
        
    }
    
    public static void main(String[] args) throws Exception {

 
        
       // try{
            
            System.out.println("Tarea 3");
            
            System.out.println("Elija una opcion.");
            
            System.out.println("1. Gestion de Clientes\n" + "2. Gestion de Productos\n" +
                               "3. Gestion de Afiliaciones\n" + "4. Gestion de Consumos");
                                  
            
            String input = scanner.nextLine();
            
            if (input.equals("1")) {
                
                gestionClientes();      
                
                
            }
            
            
            
            System.out.println(input);
                
            
               
            
            
        /*} catch (SQLException e) {
            
            System.out.println(e.getMessage());
        }*/
        
        
        //Calendar calendar = new GregorianCalendar();
        //System.out.println(calendar.get(Calendar.YEAR));
        
        
        
    } 
}