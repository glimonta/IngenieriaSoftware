import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;



public class Main {
    
    public static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("Tarea 3");
            
        System.out.println("Elija una opcion.");
            
        System.out.println("1. Gestion de Clientes\n" +
                "2. Gestion de Productos\n" + "3. Gestion de Afiliaciones\n" + 
                "4. Gestion de Consumos\n" + "0. Salir");
                                 
            
        String input = scanner.nextLine();
        OUTER:
        while (!input.equals("0")) {
       
            switch(input) {
                case "0" : 
                    break OUTER;
                case "1" : GestionarClientes.gestionClientes();
                           break;
                case "2" : GestionarProductos.gestionProductos();
                           break;
                case "3" : //GestionarAfiliaciones.gestionAfiliaciones();
                           break;
                case "4" : //GestionarConsumos.gestionConsumos();
                           break;
                default  : break;
            }
            
            System.out.println("Elija una opcion.");
            System.out.println("1. Gestion de Clientes\n" +
                    "2. Gestion de Productos\n" + "3. Gestion de Afiliaciones\n" + 
                    "4. Gestion de Consumos\n" + "0. Salir");
       
            input = scanner.nextLine();
        
        }
            
            
               
    } 
}