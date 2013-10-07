/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.DateFormat;

/**
 *
 * @author andreso
 * Metodos a ser utilizados en las diversas pruebas unitarias:
 * 1. Mensajes de Inicio-Fin
 * 2. Metodos de insercion de instancias en la BD
 * 3. Metodos de obtencion de fechas
 */
public class Tests {
    
    /*
     * Constructor de la clase de testeo
     */
    public Tests(){
        
    }
   
    /**********************************************************************
     * 
     *                     1.  MENSAJES DE INICIO- FIN
     * 
     *********************************************************************/
    
    /*
     * Muestra el mensaje respectivo al inicio de las pruebas para
     * cada clase
     */
    public static void mensajeInicioPrueba(String nombre_clase){
        
        System.out.println(" --- INICIANDO PRUEBAS DE "+nombre_clase+".JAVA --- ");
    }
    
    /*
     * Muestra el mensaje respectivo al fin de las pruebas para
     * cada clase
     */
    public static void mensajeFinPrueba(String nombre_clase){
        
        
        System.out.println(" --- FINALIZANDO PRUEBAS DE "+nombre_clase+".JAVA --- \n");
    }
   
    
    
    /**********************************************************************
     * 
     *      2.  METODOS DE INSERCION DE INSTANCIAS EN LA BASE DE DATOS
     * 
     *********************************************************************/
    
    
    /*
     * Definimos un cliente y lo agregamos a la base de datos
     */  
    public static Cliente insertCliente(int CED_CLIENTE,String NOMB_CLIENTE,
                                        String DIR_CLIENTE,ArrayList<Long> telefonos_cliente){
        
        Cliente miCliente = new Cliente(CED_CLIENTE,NOMB_CLIENTE,DIR_CLIENTE,telefonos_cliente);
        miCliente.registrarCliente();
        return miCliente;
    }
    
    /*
     * Definimos un modelo y lo agregamos a la base de datos
     */
    public static Modelo insertModelo(String NOMB_MODELO){
        Modelo miModelo = new Modelo(NOMB_MODELO);
        miModelo.registrarModelo();
        return miModelo;
    }
    
    
    /*
     * Definimos un nuevo plan y lo agregamos a la base de datos
     */
    public static Plan insertPlan(String NOMB_PLAN,String DESCRIPC_PLAN,String TIPO_DEPLAN){
        Plan miPlan = new Plan(NOMB_PLAN, DESCRIPC_PLAN, TIPO_DEPLAN);
        miPlan.registrarPlan();
        return miPlan;
    }
    
    /*
     * Definimos dos productos y los agregamos a la base de datos
     */
    public static Producto insertProducto(int codProd,String nomb_modelo,Cliente cliente){
        Producto prod = new Producto(codProd,nomb_modelo,cliente);
        try {
            prod.registrarProducto();
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
        return prod;
    }
    
    /* 
     * Se define un nuevo tipo de servicio y se agrega a la base de datos
     */
    public static TipoServicio insertTipoServicio(String NOMBRE_TSERVICIO){
        TipoServicio miTipoServicio = new TipoServicio(NOMBRE_TSERVICIO);
        miTipoServicio.registrarTipoServicio();
        
        return miTipoServicio;
    }
    
   /*
    * Se definen valores para una afiliacion y se agrega a la base de datos
    */
    public static Afiliacion insertAfiliacion(Date fecha_inicio, Date fecha_fin,
                                              Plan plan,Producto producto){
        Afiliacion miAfiliacion = new Afiliacion(fecha_inicio,fecha_fin, 
                                      plan, producto);
        
        miAfiliacion.registrarAfiliacion();
        return miAfiliacion;
    }
    
   /*
    * Se definen valores para servicio y se agrega a la base de datos
    */
    public static Servicio insertServicio(String nombre_servicio,
                                          String descripcion_servicio, 
                                          String nombre_tipo_servicio){
        
        /*Se definen valores para servicio y se agrega a la base de datos*/
        Servicio miServicio = new Servicio(nombre_servicio, 
                                           descripcion_servicio,
                                           nombre_tipo_servicio);
        try {
            miServicio.registrarServicio();
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        
        return miServicio;
    }
    
    
   /*
    * Se definen valores para paquete y se agrega a la base de datos
    */
    public static Paquete insertPaquete(String nombre_paquete, 
                                        String descripcion_paquete){
        Paquete miPaquete = new Paquete(nombre_paquete, descripcion_paquete);
        miPaquete.registrarPaquete();
        return miPaquete;
   
    }
   
    /*
    * Se definen valores para paquete y se agrega a la base de datos
    */
    public static Tiene insertTiene(Date fecha_inicio,Date fecha_fin, 
                                        float tiene_costo_plan, Plan plan,
                                        Paquete paquete){
        Tiene miTiene = new Tiene(fecha_inicio,fecha_fin,tiene_costo_plan,
                                     plan,paquete);
        try {
            miTiene.registrarTiene();
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
   
        return miTiene;
    }
    
    
    
    /*Se definen valores para un nuevo servicio adicional consumido
     * por el producto uno
     */
    public static ServicioAdicional insertServicioAdicional(String nombre_servicio,
                                                            String descripcion_servicio,
                                                            String nombre_tipo_servicio,
                                                            float tarifa_serv_adicional,
                                                            int cantidad_serv_adicional,
                                                            String tipo_plan_serv_adicional){
                                         
        ServicioAdicional miServAdicional = new ServicioAdicional(nombre_servicio,
                                                          descripcion_servicio,
                                                          nombre_tipo_servicio,
                                                          tarifa_serv_adicional,
                                                          cantidad_serv_adicional,
                                                          tipo_plan_serv_adicional);
        
        
        try {
            miServAdicional.registrarServicioAd();
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        
        return miServAdicional;
                                                            
    }
    
     
    /*
     * Se definen valores para la clase posee y se incluye dentro de la base de datos
     */ 
     public static Posee insertPosee(Date fecha_inicio,ServicioAdicional serv_adicional,
                                     Producto producto){
          Posee miPosee = new Posee(fecha_inicio,serv_adicional,producto);
          miPosee.registrarPosee();
          
          return miPosee;
     }    
     
     
     
     /*
      * Se definen valores para una instancia de contiene y se agregan a 
      * la base de datos
      */
      public static Contiene insertContiene(int cantidad_contiene,
                                            float costo_adicional_contiene,
                                            Paquete paquete,Servicio servicio){
          
      
        Contiene miContiene = new Contiene(cantidad_contiene, costo_adicional_contiene, 
                                paquete, servicio);     
        
            try {
                miContiene.registrarContiene();
            } catch (Exception e) {
                // Si hay una excepcion se imprime un mensaje
                System.err.println(e.getMessage());
            }
        
        return miContiene;
      }
      
      
      /*
       * Se definen valores para un consumo y se agrega a la base de datos
       */
       public static Consumo insertConsumo(int cantidad_consumo,Date fecha_inicio,
                                           Producto producto,Servicio servicio){
           Consumo miConsumo = new Consumo(cantidad_consumo,fecha_inicio, 
                                           producto, servicio);
           miConsumo.registrarConsumo();
           return miConsumo;
       }
       
       /*
        * Se definen valores para una factura y se agrega a la base de datos
        */
       public static Factura insertFactura(Date fecha_inicio_factura,
                                           float tiene_costo_plan,
                                           float monto_total,
                                           ArrayList<String> comentarios_factura,
                                           Producto producto){
           
            Factura miFactura = new Factura(fecha_inicio_factura,tiene_costo_plan, 
                                        0,comentarios_factura,producto);
       
            //factura.registrarFactura();
       
            return miFactura;
       }
       
       /*
        * Se definen valores para una clase efectivo y se agrega la instancia
        * a la base de datos
        */
       public static Efectivo insertEfectivo(int id_efectivo, int numero_pago){
           
           Efectivo miEfectivo = new Efectivo(id_efectivo,numero_pago);
           miEfectivo.registrarEfectivo();
           return miEfectivo;
       }
       
       /*       
        * Se definen valores para una clase tarjeta y se agrega la instancia
        * a la base de datos
        */
       public static Tarjeta insertTarjeta(int id,String numero,String banco,
                                           Date fencha_vencimiento,
                                           String tipo_tarjeta,int cod_seguridad,
                                           String cedula_titular,String marca){
            Tarjeta miTarjeta = null;
            try {
                miTarjeta = new Tarjeta(id,numero,banco,fencha_vencimiento,
                                               tipo_tarjeta,cod_seguridad,
                                               cedula_titular,marca);
                miTarjeta.RegistrarTarjeta();
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }    
            
            return miTarjeta;
       }
       
       
       
        
        
    /**********************************************************************
     * 
     *       3. METODOS ASOCIADOS A LA OBTENCION  DE FECHAS
     * 
     *********************************************************************/
       
     /*Toma una representacion en string y retorna 
      * un objeto de tipo Date
      */  
     public static Date stringToDate(String string_date){
         Date myDate = null;
         
         try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(string_date);
            myDate = new java.sql.Date(utilDate.getTime());
          } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
         }
           
         return myDate;
     }  
       
     /* Retorna un objeto del tipo Date el cual
      * correspondiente al ultimo dia del mes
      */  
     public static Date lastDayMonth(){
         String rep_fin_de_mes = "";
         try {
            Calendar c = Calendar.getInstance();

            /*Obteniendo el ultimo dia del mes*/
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            /*Almacenamos un string con el formato indicado*/
            rep_fin_de_mes =  sdf.format(c.getTime());
             
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        
        return stringToDate(rep_fin_de_mes);
     }
     
     
     /* Retorna un objeto del tipo Date el cual 
      * correspondiente al primer dia del mes
      */  
     public static Date firstDayMonth(){
         String rep_inicio_de_mes = "";
         try {
            Calendar c = Calendar.getInstance();
            
            /*Obtenemos el primer dia del mes*/
            c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            /*Lo almacenamos como string con el formato adecuado*/
            rep_inicio_de_mes = sdf.format(c.getTime());

             
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        
        return stringToDate(rep_inicio_de_mes);
     }
       
}
