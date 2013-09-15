
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

public class GestionarAfiliaciones {
    
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
    
    public static void afiliarPlan() throws SQLException {
        
        System.out.println("Por favor ingrese el ID del producto.");
        
        int id = leerEnteroEntrada("Ingrese el ID del producto nuevamente.");
                
        Producto producto = Producto.consultarProducto(id);   
        
        System.out.println("Por favor ingrese el nombre del plan.");
        
        String nomPlan = Main.scanner.nextLine();
        
        System.out.println("Por favor ingrese el tipo de plan. (PREPAGO o POSTPAGO)");
        
        String tipoPlan = Main.scanner.nextLine().toUpperCase();
        
        Plan plan = Plan.consultarPlan(nomPlan,tipoPlan);
        
        System.out.println("Por favor ingrese la fecha de inicio del plan." +
                           "Formato: AAAA-MM-DD");
        
        String fechaInicStr = Main.scanner.nextLine();
        
        Date fechaInic = Date.valueOf(fechaInicStr);
        
        System.out.println("Por favor ingrese la fecha fin del plan." +
                           "Formato: AAAA-MM-DD o null");
        
        String fechaFinStr = Main.scanner.nextLine();
        
        Date fechaFin = Date.valueOf(fechaFinStr);
        
        Afiliacion afiliacion = new Afiliacion(fechaInic,fechaFin,plan,producto);
        
        afiliacion.registrarAfiliacion();           

        
    }
    
    public static void afiliarServicio() throws SQLException {
        
        System.out.println("Por favor ingrese el ID del producto.");
        
        Integer id = leerEnteroEntrada("Ingrese el ID del producto nuevamente.");
        
        Producto producto = Producto.consultarProducto(id);        
        
        System.out.println("Por favor ingrese el nombre del servicio adicional.");
        
        String nomServicio = Main.scanner.nextLine();
        
        ServicioAdicional servicio = ServicioAdicional.consultarServicioAd(nomServicio);
        
        System.out.println("Por favor ingrese la fecha de inicio del servicio." +
                           "Formato: AAAA-MM-DD");
        
        String fechaInicStr = Main.scanner.nextLine();
        
        Date fechaInic = Date.valueOf(fechaInicStr);
        
        Posee posee = new Posee(fechaInic,servicio,producto);
        
        posee.registrarPosee();
        
    }
    
    public static void desafiliarPlan(Integer id) throws SQLException, ParseException {
                
        System.out.println("Por favor ingrese el nombre del plan.");
        
        String nomPlan = Main.scanner.nextLine();
        
        System.out.println("Por favor ingrese el tipo de plan. (PREPAGO o POSTPAGO)");
        
        String tipoPlan = Main.scanner.nextLine();
                
        System.out.println("Por favor ingrese la fecha de inicio del plan." +
                           "Formato: AAAA-MM-DD");
        
        String fechaInicStr = Main.scanner.nextLine();
        
        Date fechaInic = Date.valueOf(fechaInicStr);
        
        Afiliacion afiliacion = Afiliacion.consultarAfiliacion(id, nomPlan, tipoPlan, fechaInic);
        
        if (afiliacion != null)
            afiliacion.eliminarAfiliacion();
        else
            System.out.println("Este producto no posee este plan");
        
    }
    
    public static void desafiliarServicio(Integer id) throws SQLException, ParseException {
        
        System.out.println("Por favor ingrese el nombre del servicio adicional.");
        
        String nomServicio = Main.scanner.nextLine();
        
        System.out.println("Por favor ingrese la fecha de inicio del servicio." +
                           "Formato: AAAA-MM-DD");
        
        String fechaInicStr = Main.scanner.nextLine();
        
        Date fechaInic = Date.valueOf(fechaInicStr);
        
        Posee posee = Posee.consultarPosee(id, nomServicio, fechaInic);
        
        if (posee != null)
            posee.eliminarPosee();
        else
            System.out.println("Este producto no posee este servicio");
        
    }
    
    public static void consultarPlanesAfiliados() throws SQLException, ParseException {
        
        System.out.println("Por favor ingrese el ID del producto.");
        
        Integer id = leerEnteroEntrada("Ingrese el ID del producto nuevamente.");
        
        Producto producto = Producto.consultarProducto(id);
        
        if (producto == null){
            System.out.println("Este producto no existe.");
            return;
            
        }
        
        ArrayList<Afiliacion> afiliaciones = producto.listarPlanesAfiliados();
        
        if (afiliaciones.isEmpty()){
          System.out.println("Este producto no tiene planes asociados.");
          return;
        }  
        Iterator afil = afiliaciones.iterator();
        
        while (afil.hasNext()) 
            System.out.println(afil.next().toString());
        
        System.out.println("Desea eliminar un plan de este producto?\n"+
                           "1.Eliminar un plan\n2.Salir");
        
        String input = Main.scanner.nextLine();
        
        switch (input) {
            case "1":
                desafiliarPlan(id);
            case "2":
                break;
        }
        
        
    }
    
    
    public static void consultarServiciosAfiliados() throws SQLException, ParseException {
        
        System.out.println("Por favor ingrese el ID del producto.");
        
        Integer id = leerEnteroEntrada("Ingrese el ID del producto nuevamente.");
        
        Producto producto = Producto.consultarProducto(id);
        
        if (producto == null){
            System.out.println("Este producto no existe.");
            return;
            
        }
        
        ArrayList<Posee> poseeList = producto.listarServiciosAdicionalesContratados();
        
        if (poseeList.isEmpty()){
          System.out.println("Este producto no tiene servicios asociados.");
          return;
        }  
        
        Iterator afil = poseeList.iterator();
        
        while (afil.hasNext())
            System.out.println(afil.next().toString());
        
        System.out.println("Desea eliminar un servicio de este producto?\n"+
                           "1.Eliminar un servicio\n 2.Salir");
        
        String input = Main.scanner.nextLine();
        
        switch (input) {
            case "1":
                desafiliarServicio(id);
            case "2":
                break;
        }
    }
    
    public static void gestionAfiliaciones() throws SQLException, ParseException {
        
        OUTER:
        while (true) {
            System.out.println("1. Afiliar un plan a un producto.\n"+
                               "2. Afiliar un servicio a un producto.\n"+
                               "3. Consultar los planes afiliados a un producto.\n"+
                               "4. Consultar los servicios asociados a un producto.\n"+
                               "5. Salir");
            
            String input = Main.scanner.nextLine();
            
            switch (input) {
                case "1":
                    afiliarPlan();
                    break;
                case "2":
                    afiliarServicio();
                    break;
                case "3":
                    consultarPlanesAfiliados();
                    break;
                case "4":
                    consultarServiciosAfiliados();
                    break;
                case "5":
                    break OUTER;
            }
        }   
    }
}
