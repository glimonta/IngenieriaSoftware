/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Date;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author fertaku
 */
public class FacturarPostpagoTest extends Tests{
    
    
    //Definicion de constantes
    
        //Constantes del cliente
    public static final int    CEDULA_CLIENTE = 12345678;
    public static final String NOMBRE_CLIENTE = "clientePostpago";
    public static final String DIRECCION_CLIENTE = "direccionPostpago";
    public static final String TELEFONO_CLIENTE = "55555555";
    
        //Constantes del producto
    public static final int CODIGO_PRODUCTO = 456;
    public static final String NOMBRE_MODELO = "modeloPruebaPostpago";
    
        //Constantes para el plan
    public static final String NOMBRE_PLAN = "planPruebaPostpago";
    public static final String NOMBRE_PLAN2 = "planPruebaPostpago2";
    public static final String DESCRIPCION_PLAN = "descripcionPlanPostpago";
    public static final String TIPO_PLAN   = "POSTPAGO"; //No modificar
    
        //Constantes para el paquete
    public static final String NOMBRE_PAQUETE = "nombrePaquetePostpago";
    public static final String DESCRIPCION_PAQUETE = "descripcionPaquete";
    
    public static final String NOMBRE_PAQUETE2 = "nombrePaquetePostpago2";
    public static final String DESCRIPCION_PAQUETE2 = "descripcionPaquete2";
    
        //Constantes para la instancia de tiene
    public static final float COSTO_TIENE     = 7;
    
    public static final float COSTO_TIENE2     = 25;
    
        //Constantes para el tipo de servicio
    public static final String NOMBRE_TIPO_SERVICIO = "nuevoTipoServicio";
    public static final String NOMBRE_TIPO_SERVICIO2 = "nuevoTipoServicio2";
    
        //Constantes para el servicio
    public static final String NOMBRE_SERVICIO = "nombreServPostpago";
    public static final String DESCRIPCION_SERVICIO = "descripcionServ";
    public static final String TIPO_SERVICIO        = "nuevoTipoServicio";
    
    public static final String NOMBRE_SERVICIO2 = "nombreServPostpago2";
    public static final String DESCRIPCION_SERVICIO2 = "descripcionServ2";
    public static final String TIPO_SERVICIO2        = "nuevoTipoServicio2";
    
        //Constantes para la instancia de contiene
    public static final int CANTIDAD_CONTIENE = 10;
    public static final float COSTO_ADICIONAL_CONTIENE = 5;

    public static final int CANTIDAD_CONTIENE2 = 250;
    public static final float COSTO_ADICIONAL_CONTIENE2 = 2;
    
        //Constantes para una instancia de consumo
    public static final Integer CANTIDAD_CONSUMO = 11;
    public static final Integer CANTIDAD_CONSUMO2 = 500;
    public static final Integer CANTIDAD_CONSUMO3 = 10;
    public static final Integer CANTIDAD_CONSUMO4 = 50;
    
    
    //Definicion de variables
    
        //Variables del cliente
    public static ArrayList<Long> telefonos_cliente;
    public static Cliente cliente;
    
        //Variables del modelo
    public static Modelo modelo;
    
        //Variables del producto
    public static Producto producto;
    
        //Variables del plan
    public static Plan plan;
    public static Plan plan2;
    
        //Variables de la afiliacion
    public static Afiliacion afiliacion;
    public static Date fechaInicioAfiliacion;
    public static Date fechaFinAfiliacion;

    public static Afiliacion afiliacion2;
    public static Date fechaInicioAfiliacion2;
    public static Date fechaFinAfiliacion2;

        //Variables del paquete
    
    public static Date fechaInicioTiene;
    public static Date fechaFinTiene;
    public static Paquete paquete;
    
    public static Paquete paquete2;
    
        //Variables de la instancia tiene
    public static Tiene tiene;
    
    public static Tiene tiene2;
    
        //Variables de la instancia tipoServicio
    public static TipoServicio tipoServicio;
    
    public static TipoServicio tipoServicio2;
    
        //Variables del servicio
    public static Servicio servicio;
    
    public static Servicio servicio2;
    
        //Variables del contiene
    public static Contiene contiene;    
    public static Contiene contiene2;
    public static Contiene contiene3;
    
        //Variables del consumo
    public static Date fechaConsumo;
    public static Consumo consumo;
    
    public static Date fechaConsumo2;
    public static Consumo consumo2;
    public static Consumo consumo3;
    public static Consumo consumo4;
    
        //Variables de la factura
    public static Factura factura;
    public static Factura factura2;
    public static Factura factura3;
    
    
    public FacturarPostpagoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        mensajeInicioPrueba("FacturarPostpago");
        
        /*Insercion del cliente a la base de datos*/
        telefonos_cliente = new ArrayList();
        telefonos_cliente.add(Long.valueOf(TELEFONO_CLIENTE));
        cliente = insertCliente(CEDULA_CLIENTE,NOMBRE_CLIENTE,
                                    DIRECCION_CLIENTE,telefonos_cliente);
        
        /*Insercion del modelo a la base de datos*/
        modelo = insertModelo(NOMBRE_MODELO);        
        
        /*Insercion del plan a la base de datos*/
        plan = insertPlan(NOMBRE_PLAN, DESCRIPCION_PLAN,TIPO_PLAN);
        plan2 = insertPlan(NOMBRE_PLAN2, DESCRIPCION_PLAN, TIPO_PLAN);
        
        /*Insercion de dos productos a la base de datos*/
        producto = insertProducto(CODIGO_PRODUCTO,
                                  NOMBRE_MODELO, 
                                  cliente);
        
        /*Invocacion de los metodos auxiliares de la clase padre Tests.java
         * para obtener el primer y ultimo dia del mes
         */
        fechaInicioAfiliacion =  firstDayMonth();
        fechaFinAfiliacion    =   Date.valueOf("2013-11-1");
        
        fechaInicioAfiliacion2 = Date.valueOf("2013-5-1");
        fechaFinAfiliacion2 = Date.valueOf("2013-6-1");

        /*Se asocian los productos a los planes*/
        afiliacion = insertAfiliacion(fechaInicioAfiliacion, 
                                      fechaFinAfiliacion, 
                                      plan, producto);
        
        afiliacion2 = insertAfiliacion(fechaInicioAfiliacion2, 
                                      fechaFinAfiliacion2, 
                                      plan2, producto);
        
        /*Se definen valores para paquete y se agrega a la base de datos*/
        paquete = insertPaquete(NOMBRE_PAQUETE, DESCRIPCION_PAQUETE);
        
        paquete2 = insertPaquete(NOMBRE_PAQUETE2, DESCRIPCION_PAQUETE);
        
        /*Se relaciona dicho paquete con el plan previamente creado*/
        tiene = insertTiene(fechaInicioAfiliacion, 
                            fechaFinAfiliacion, 
                            COSTO_TIENE, 
                            plan, 
                            paquete);
        
        tiene2 = insertTiene(fechaInicioAfiliacion2, 
                            fechaFinAfiliacion2, 
                            COSTO_TIENE2, 
                            plan2, 
                            paquete2);
        
        /*Se inserta un nuevo tipo de servicio*/
        tipoServicio = insertTipoServicio(NOMBRE_TIPO_SERVICIO);
        tipoServicio2 = insertTipoServicio(NOMBRE_TIPO_SERVICIO2);
        
        /*Se inserta un nuevo servicio*/
        servicio = insertServicio(NOMBRE_SERVICIO, 
                                  DESCRIPCION_SERVICIO,
                                  NOMBRE_TIPO_SERVICIO);
        servicio2 = insertServicio(NOMBRE_SERVICIO2, 
                                  DESCRIPCION_SERVICIO,
                                  NOMBRE_TIPO_SERVICIO2);
        
        /*Insercion de una instancia de contiene en la base de datos*/
        contiene = insertContiene(CANTIDAD_CONTIENE, 
                                  COSTO_ADICIONAL_CONTIENE, 
                                  paquete, 
                                  servicio);
        contiene2 = insertContiene(CANTIDAD_CONTIENE, 
                                  COSTO_ADICIONAL_CONTIENE, 
                                  paquete2, 
                                  servicio);
        
        contiene3 = insertContiene(CANTIDAD_CONTIENE2, 
                                  COSTO_ADICIONAL_CONTIENE2, 
                                  paquete2, 
                                  servicio2);
        
        /*Insercion del consumo en la base de datos*/
        consumo = insertConsumo(CANTIDAD_CONSUMO, 
                                fechaInicioAfiliacion, 
                                producto, 
                                servicio);
        consumo2 = insertConsumo(CANTIDAD_CONSUMO2, 
                                fechaInicioAfiliacion2, 
                                producto, 
                                servicio2);

        try {
            
            factura = Facturador.obtenerFacturaActual(producto);
            
            factura.montoTotal = 
                COSTO_ADICIONAL_CONTIENE*(CANTIDAD_CONSUMO - CANTIDAD_CONTIENE);
            
            double montoTotal2 = 
                    COSTO_ADICIONAL_CONTIENE2*(CANTIDAD_CONSUMO2 - CANTIDAD_CONTIENE2);
            
            ArrayList<String> comentarios = new ArrayList();
            
            factura2 = new Factura(fechaInicioAfiliacion2, 
                                   COSTO_TIENE2, 
                                   montoTotal2, 
                                   comentarios, 
                                   producto); 
        
            double montoTotal3 = 
              COSTO_ADICIONAL_CONTIENE2*(CANTIDAD_CONSUMO2 - CANTIDAD_CONTIENE2)
              + COSTO_ADICIONAL_CONTIENE*(CANTIDAD_CONSUMO4 - CANTIDAD_CONTIENE);

            factura3 = new Factura(fechaInicioAfiliacion2, 
                                   COSTO_TIENE2, 
                                   montoTotal3, 
                                   comentarios, 
                                   producto); 
            
        } catch (Exception e) {
            /* Si hay una excepcion se imprime un mensaje*/
            System.err.println(e.getMessage());
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        try {
            factura.eliminarFactura();
            factura2.eliminarFactura();
            factura3.eliminarFactura();
            consumo.eliminarConsumo();
            consumo2.eliminarConsumo();  
            contiene.eliminarContiene();
            contiene2.eliminarContiene();
            contiene3.eliminarContiene();
            servicio.eliminarServicio();
            servicio2.eliminarServicio();
            tipoServicio.eliminarTipoServicio();
            tipoServicio2.eliminarTipoServicio();
            tiene.eliminarTiene();
            tiene2.eliminarTiene();
            paquete.eliminarPaquete();
            paquete2.eliminarPaquete();
            afiliacion.eliminarAfiliacion();
            afiliacion2.eliminarAfiliacion();
            producto.eliminarProducto();
            plan.eliminarPlan();
            plan2.eliminarPlan();
            modelo.eliminarModelo();
            cliente.eliminarCliente();
        } catch (Exception e) {
            /* Si hay una excepcion se imprime un mensaje*/
            System.err.println(e.getMessage());
            
        }
        mensajeFinPrueba("FacturarPostpago");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Prueba 1 del metodo facturar. Prueba un producto que se excedio 
     * en consumir un servicio de su plan postpago.
     */
   @Test
    public void testFacturar() {
        System.out.println("facturar");
        Producto producto = this.producto;
        Date fecha = fechaInicioAfiliacion;
        FacturarPostpago instance = new FacturarPostpago();
        Factura expResult = factura;

        Factura result = instance.facturar(producto, fecha);
        
        assertEquals(expResult, result);
    }
    /**
     * Prueba 2 del metodo facturar. Prueba un producto que posee un plan con
     * dos servicios, se excedio en consumir un servicio y del otro no.
     */
    @Test
    public void testFacturar2() {
        System.out.println("facturar2");
        
        consumo3 = insertConsumo(CANTIDAD_CONSUMO3, 
                                fechaInicioAfiliacion2, 
                                producto, 
                                servicio);        
        Producto producto = this.producto;
        Date fecha = fechaInicioAfiliacion2;
        FacturarPostpago instance = new FacturarPostpago();


        Factura expResult = factura2;
        
        Factura result = instance.facturar(producto, fecha);

        consumo3.eliminarConsumo();
        assertEquals(expResult, result);
    }

    /**
     * Prueba 3 del metodo facturar. Prueba un producto que tiene un plan
     * postpago con dos servicios y se excedio en ambos.
     */
    @Test
    public void testFacturar3() {
        System.out.println("facturar3");
        
        consumo4 = insertConsumo(CANTIDAD_CONSUMO4, 
                                fechaInicioAfiliacion2, 
                                producto, 
                                servicio);
        
        Producto producto = this.producto;
        Date fecha = fechaInicioAfiliacion2;
        FacturarPostpago instance = new FacturarPostpago();

        Factura expResult = factura3;
        
        Factura result = instance.facturar(producto, fecha);

        consumo4.eliminarConsumo();
        assertEquals(expResult, result);
    }
    

    /**
     * Prueba del metodo buscarPaquete. Busca el paquete asociado a
     * un plan en un fecha dada.
     */
    @Test
    public void testBuscarPaquete() {
        System.out.println("buscarPaquete");
        Plan plan = this.plan;
        Date fecha = fechaInicioAfiliacion;
        FacturarPostpago instance = new FacturarPostpago();
        Tiene expResult = tiene;
        Tiene result = instance.buscarPaquete(plan, fecha);        
        assertEquals(expResult, result);
    }

    /**
     * Prueba 1 del metodo calcularCostoTotal. Calcula el costo de la factura 
     * de un producto que se excedio en consumir un servicio de su plan postpago.
     */
    @Test
    public void testCalcularCostoTotal() throws Exception {
        System.out.println("calcularCostoTotal");
        Producto producto = this.producto;
        Date fecha = fechaInicioAfiliacion;
        Date fechaMax = fechaFinAfiliacion;
        ArrayList<Contiene> listaContiene = new ArrayList();
        listaContiene.add(contiene);
        FacturarPostpago instance = new FacturarPostpago();
        float expResult = (float) factura.montoTotal;
        float result = instance.calcularCostoTotal(producto, fecha, fechaMax, listaContiene);
        
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Prueba 2 del metodo calcularCostoTotal. Calcular el costo de la factura
     * de un producto que posee un plan con dos servicios, se excedio en consumir 
     * un servicio y del otro no.
     */
    @Test
    public void testCalcularCostoTotal2() throws Exception {
        System.out.println("calcularCostoTotal2");
        consumo3 = insertConsumo(CANTIDAD_CONSUMO3, 
                                fechaInicioAfiliacion2, 
                                producto, 
                                servicio);  
        Producto producto = this.producto;
        Date fecha = fechaInicioAfiliacion2;
        Date fechaMax = fechaFinAfiliacion2;
        ArrayList<Contiene> listaContiene = new ArrayList();
        listaContiene.add(contiene3);
        listaContiene.add(contiene2);
        FacturarPostpago instance = new FacturarPostpago();
        float expResult = (float) factura2.montoTotal;
        float result = instance.calcularCostoTotal(producto, fecha, fechaMax, listaContiene);
        consumo3.eliminarConsumo();
        assertEquals(expResult, result, 0.0);
    }
 
    /**
     * Prueba 3 del metodo calcularCostoTotal. Calcula el costo de la factura
     * de un producto que tiene un plan postpago con dos servicios y se 
     * excedio en ambos.
     */
    @Test
    public void testCalcularCostoTotal3() throws Exception {
        System.out.println("calcularCostoTotal3");
        consumo4 = insertConsumo(CANTIDAD_CONSUMO4, 
                                fechaInicioAfiliacion2, 
                                producto, 
                                servicio);  
        Producto producto = this.producto;
        Date fecha = fechaInicioAfiliacion2;
        Date fechaMax = fechaFinAfiliacion2;
        ArrayList<Contiene> listaContiene = new ArrayList();
        listaContiene.add(contiene3);
        listaContiene.add(contiene2);
        FacturarPostpago instance = new FacturarPostpago();
        float expResult = (float) factura3.montoTotal;
        float result = instance.calcularCostoTotal(producto, fecha, fechaMax, listaContiene);
        consumo4.eliminarConsumo();
        assertEquals(expResult, result, 0.0);
    }
}