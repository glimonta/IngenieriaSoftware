import java.util.Scanner;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

            
            System.out.println("Tarea 3");
            
            while (true) {
                
                System.out.println("Elija una opcion.");

                System.out.println("1. Gestion de Clientes\n" + 
                                   "2. Gestion de Productos\n" +
                                   "3. Gestion de Afiliaciones\n" + 
                                   "4. Gestion de Consumos\n" + 
                                   "5. Gestion de facturas\n" +
                                   "6. Salir");


                String input = scanner.nextLine();

                if (input.equals("1"))               
                    GestionarCliente.gestionClientes();  
                
                else if (input.equals("6"))
                     break;
            }         
    } 
}