import java.sql.SQLException;
import java.text.ParseException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class GestionarConsumos {
    
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
  /**
   * Metodo que interactua con el usuario para registrar un consumo.
   */
  public static void registrarConsumo() {

    System.out.println("Ingrese el codigo del producto asociado al consumo.");

    int codigo_producto;

    //El programa pide el codigo del producto hasta que sea valido
    while (true) {
      try {

        String codigo_productoStr = Main.scanner.nextLine();
        codigo_producto = Integer.parseInt(codigo_productoStr);
        break;

      } catch (Exception e) {
        System.out.println("Ingrese el codigo del producto de nuevo");
      }
    }

    // Se solicita el nombre del servicio asociado al consumo
    System.out.println("Por favor ingrese el nombre del servicio consumido.");

    String nombre_servicio = Main.scanner.nextLine();

    //Se solicita la cantidad consumida
    System.out.println("Ingrese la cantidad del consumo.");

    int monto;

    //El programa pide el monto hasta que sea valido
    while (true) {
      try {

        String montoStr = Main.scanner.nextLine();
        monto = Integer.parseInt(montoStr);
        break;

      } catch (Exception e) {
        System.out.println("Ingrese la cantidad del consumo de nuevo");
      }
    }

    //Se solicita la fecha asociada al consumo y se parsea al tipo de dato requerido
    System.out.println("Ingrese la fecha del consumo en formato: yyyy-MM-dd");

    String fechaStr = Main.scanner.nextLine();

    Date fecha = Date.valueOf(fechaStr);
    try {
        //Se obtiene el producto
        Producto producto = Producto.consultarProducto(codigo_producto);

        if (null == producto){
          System.out.println("El producto no existe en el sistema");
          return;
        }

        //Se obtiene el servicio
        Servicio servicio = Servicio.consultarServicio(nombre_servicio);
        if (null == servicio){
          System.out.println("El servicio no existe en el sistema");
          return;
        }
        //Crea el consumo
        Consumo consumo = new Consumo(monto, fecha, producto, servicio);

        //Agrega al consumo en la base de datos

          consumo.registrarConsumo();

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }


    /**
     * Metodo que permite consultar un consumo de la base de datos.
     */
    public static void consultarConsumo() {

      //Se solicita el codigo de identificacion del producto asociado al consumo
      System.out.println("Ingrese el codigo del producto asociado al consumo.");
      int codigo;

      //El programa pide la codigo hasta que sea un numero valido
      while (true) {
        try {

          String codigoStr = Main.scanner.nextLine();
          codigo = Integer.parseInt(codigoStr);
          break;

        } catch (Exception e) {
          System.out.println("Ingrese el codigo de nuevo");
        }
      }

      //Se solicita el nombre del servicio asociado al consumo
      System.out.println("Por favor ingrese el nombre del servicio asociado al consumo.");

      String nombre_servicio = Main.scanner.nextLine();

      //Se solicita la fecha en donde se incurrio al consumo y se parsea al tipo de dato requerido
      System.out.println("Ingrese la fecha del consumo en formato: yyyy-MM");

      String fechaStr = Main.scanner.nextLine() + "-1";

      Date fecha = Date.valueOf(fechaStr);
      
      Consumo consumo;
      
      try {
        //Se busca el consumo requerido
        consumo = Consumo.consultarConsumo(codigo, nombre_servicio, fecha);
        
        if (consumo == null){
            System.out.println("Este consumo no existe.");
            return;
            
        }
        
        /* Permite modificar y eliminar el consumo */
        while (true) {

          System.out.println(consumo.toString());
          System.out.println("Que desea hacer ahora?");
          System.out.println("1. Modificar el consumo\n2. Eliminar el consumo\n" +
              "3. Salir");

          String input = Main.scanner.nextLine();

          if (input.equals("1"))
            modificarConsumo(consumo);
          else if (input.equals("2")) {
            consumo.eliminarConsumo();
            break;
          } else if (input.equals("3"))
            break;
        }
      
      } catch (ParseException e){
          System.out.println(e.getMessage());
      }
      
      
    }


    /**
     * Metodo que permite modificar un consumo ya consultado.
     * @param consumo consumo a modificar
     */
    public static void modificarConsumo(Consumo consumo) {

      /*Ciclo que permite modificar varios campos de consumo antes de
        registrarlo en la base de datos */
      while (true) {
        System.out.println("Que desea modificar?");
        System.out.println("1. Cantidad consumida\n3. Salir");
        String input = Main.scanner.nextLine();

        if (input.equals("2")) {

          System.out.println("Ingrese la nueva cantidad consumida.");

          int monto;

          //El programa pide el monto hasta que sea valido
          while (true) {
            try {

              String montoStr = Main.scanner.nextLine();
              monto = Integer.parseInt(montoStr);
              break;

            } catch (Exception e) {
              System.out.println("Ingrese la cantidad del consumo de nuevo");
            }
          }
          consumo.cantidad = monto;

        } else if (input.equals("3")) {

          /*Luego de que se han modificado todos los campos se modifica 
            en la base de datos */
          consumo.modificarConsumo();
          break;
        }
      }
    }

    public static void listarConsumos(){
        
        System.out.println("Ingrese el codigo del producto");
        int codigo = leerEnteroEntrada("Codigo invalido, ingrese de nuevo.");
        
        try{
            Producto product = Producto.consultarProducto(codigo);
            
            if (product == null){
                System.out.println("Este producto no existe");
                return; 
            }
            
            System.out.println("Desde que fecha a que fecha quiere consultar " +
                            "los consumos?");
            
            System.out.println("Ingrese el mes y el año en formato yyyy-MM-dd");
            Date fechaI = Date.valueOf(Main.scanner.nextLine());
            
            System.out.println("Ingrese otra fecha en formato yyyy-MM-dd");
            Date fechaF = Date.valueOf(Main.scanner.nextLine());
            
            ArrayList<Consumo> consumos = product.listarConsumos(fechaI, fechaF);
            
            if (consumos.isEmpty()){
                System.out.println("Este producto no tiene consumos"+
                        " comprendidos entre estas fechas");
                
            } else {
            
                Iterator iter = consumos.iterator();
                
                while (iter.hasNext())
                    System.out.println(iter.next().toString());
           
            }
            
        } catch (SQLException e){
            System.out.println(e.getMessage());
            
        }
    }
    /**
     * Menu principal de gestionar Consumo.
     */
    public static void gestionConsumos() {

      OUTER:
      while (true) {
        System.out.println("1. Registrar consumo\n2. Consultar consumo\n" +
            "3.Listar los consumos de un producto\n4. Salir");
        String input = Main.scanner.nextLine();
        switch (input) {
          case "1":
            registrarConsumo();
            break;
          case "2":
            consultarConsumo();
            break;
          case "3":
              break;
          case "4":
            break OUTER;
        }
      }
    }
}
