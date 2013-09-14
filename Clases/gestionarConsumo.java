import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GestionarConsumo {

  /**
   * Metodo que interactua con el usuario para registrar un consumo.
   */
  public static void registrarConsumo() {

    System.out.println("Ingrese la cedula del cliente asociada al consumo.");

    int cedula;

    //El programa pide la cedula hasta que sea valido
    while (true) {
      try {

        String cedulaStr = Main.scanner.nextLine();
        cedula = Integer.parseInt(cedulaStr);
        break;

      } catch (Exception e) {
        System.out.println("Ingrese la cedula del cliente de nuevo");
      }
    }

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

    java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
    Date fecha = new java.sql.Date(utilDate.getTime());

    //Se obtiene el cliente
    Cliente cliente = consultarCliente(cedula);
    //Se obtiene el producto
    Producto producto = consultarProducto(codigo_producto);
    //Se obtiene el servicio
    Servicio servicio = consultarServicio(nombre_servicio);
    //Crea el consumo
    Consumo consumo = new Consumo(monto, fecha, producto, servicio);

    //Agrega al consumo en la base de datos
    try {
      consumo.registrarConsumo();

    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
    }
  }


    /**
     * Metodo que permite consultar un consumo de la base de datos.
     */
    public static void consultarConsumo() {

      //Se solicita el nombre del servicio asociado al consumo
      System.out.println("Por favor ingrese el nombre del servicio asociado al consumo.");

      String nombre_servicio = Main.scanner.nextLine();

      //Se solicita el codigo de identificacion del consumo
      System.out.println("Ingrese el codigo del consumo a consultar.");
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

      //Se solicita la fecha en donde se incurrio al consumo y se parsea al tipo de dato requerido
      System.out.println("Ingrese la fecha del consumo en formato: yyyy-MM-dd");

      String fechaStr = Main.scanner.nextLine();

      java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
      Date fecha = new java.sql.Date(utilDate.getTime());

      //Se busca el consumo requerido
      Consumo consumo = consultarConsumo(codigo, nombre_servicio, fecha);

      /* Permite modificar y eliminar el consumo */
      while (true) {

        System.out.println(consumo.toString());
        System.out.println("Que desea hacer ahora?");
        System.out.println("1. Modificar el consumo\n2. Eliminar el cliente\n" +
            "3. Salir");

        String input = Main.scanner.nextLine();

        if (input.equals("1"))
          modificarConsumo();
        else if (input.equals("2")) {
          consumo.eliminarConsumo();
          break;
        } else if (input.equals("3"))
          break;
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
        System.out.println("1. Fecha\n2. Nombre del servicio consumido\n3. Cantidad consumida\n4. Salir");
        String input = Main.scanner.nextLine();

        if (input.equals("1")) {

          System.out.println("Ingrese la nueva fecha del consumo en formato: yyyy-MM-dd");

          String fechaStr = Main.scanner.nextLine();

          java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
          Date fecha = new java.sql.Date(utilDate.getTime());

          consumo.fecha = fecha;

        } else if (input.equals("2")) {

          System.out.println("Ingrese el nuevo nombre del servicio consumido.");

          String nombre_servicio = Main.scanner.nextLine();

          consumo.servicio = new consultarServicio(nombre_servicio);

        } else if (input.equals("3")) {

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

        } else if (input.equals("4")) {

          /*Luego de que se han modificado todos los campos se modifica 
            en la base de datos */
          consumo.modificarConsumo();
          break;
        }
      }
    }


    /**
     * Menu principal de gestionar Consumo.
     */
    public static void gestionConsumo() {

OUTER:
      while (true) {
        System.out.println("1. Registrar consumo\n2. Consultar consumo\n" +
            "3. Salir");
        String input = Main.scanner.nextLine();
        switch (input) {
          case "1":
            registrarConsumo();
            break;
          case "2":
            consultarConsumo();
            break;
          case "3":
            break OUTER;
        }
      }
    }
}
