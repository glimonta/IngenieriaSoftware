/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 *
 * @author andreso
 */
public class FacturarPrepagoTest extends Tests {
    
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
    public static final String DESCRIPCION_PLAN = "descripcionPlanPostpago";
    public static final String TIPO_PLAN   = "POSTPAGO"; //No modificar
    
    public static final String NOMBRE_PLAN_SINPAQUETE           = "planPruebaNoPacket";
    public static final String TIPO_PLAN_SINPAQUETE             = "PREPAGO";
    public static final String DESCRIPCION_PLAN_SINPAQUETE      = "DescripPlanNoPacket";
 
    
        //Constantes para el paquete
    public static final String NOMBRE_PAQUETE = "nombrePaquetePostpago";
    public static final String DESCRIPCION_PAQUETE = "descripcionPaquete";
    
        //Constantes para la instancia de tiene
    public static final float COSTO_TIENE     = 7;
    
        //Constantes para el tipo de servicio
    public static final String NOMBRE_TIPO_SERVICIO = "nuevoTipoServicio";
    
        //Constantes para el servicio
    public static final String NOMBRE_SERVICIO = "nombreServPostpago";
    public static final String DESCRIPCION_SERVICIO = "descripcionServ";
    public static final String TIPO_SERVICIO        = "nuevoTipoServicio";
    
        //Constantes para la instancia de contiene
    public static final int CANTIDAD_CONTIENE = 10;
    public static final float COSTO_ADICIONAL_CONTIENE = 5;

    public static final int CANTIDAD_CONTIENE2 = 250;
    
        //Constantes para una instancia de consumo
    public static final Integer CANTIDAD_CONSUMO = 5;
    
    
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
    public static Plan planSinPaquete;
    
        //Variables de la afiliacion
    public static Afiliacion afiliacion;
    public static Date fechaInicioAfiliacion;
    public static Date fechaFinAfiliacion;

        //Variables del paquete
    
    public static Date fechaInicioTiene;
    public static Date fechaFinTiene;
    public static Paquete paquete;
    
        //Variables de la instancia tiene
    public static Tiene tiene;

        //Variables de la instancia tipoServicio
    public static TipoServicio tipoServicio;

        //Variables del servicio
    public static Servicio servicio;
    
        //Variables del contiene
    public static Contiene contiene;    

    
        //Variables del consumo
    public static Date fechaConsumo;
    public static Consumo consumo;
    
        //Variables de la factura
    public static Factura factura;

        //Variables, fechas a utilizar para afiliaciones y paquetes
    public static Date inic;
    public static Date fin;
    
    public FacturarPrepagoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        
        /*Insercion del plan a la base de datos*/
        plan = insertPlan(NOMBRE_PLAN, DESCRIPCION_PLAN,TIPO_PLAN);
        
        planSinPaquete = insertPlan(NOMBRE_PLAN_SINPAQUETE, 
                                    DESCRIPCION_PLAN_SINPAQUETE,
                                    TIPO_PLAN_SINPAQUETE);
        /*Insercion de dos productos a la base de datos*/
        
        /*Invocacion de los metodos auxiliares de la clase padre Tests.java
         * para obtener el primer y ultimo dia del mes
         */
        inic =  firstDayMonth();
        fin =   lastDayMonth();
        /*Se definen valores para paquete y se agrega a la base de datos*/
        paquete = insertPaquete(NOMBRE_PAQUETE, DESCRIPCION_PAQUETE);
        /*Se relaciona dicho paquete con el plan previamente creado*/
        tiene = insertTiene(inic, fin,COSTO_TIENE, plan, paquete);
        
        
        /*Insercion del cliente a la base de datos*/
        telefonos_cliente = new ArrayList();
        telefonos_cliente.add(Long.valueOf(TELEFONO_CLIENTE));
        cliente = insertCliente(CEDULA_CLIENTE,NOMBRE_CLIENTE,
                                    DIRECCION_CLIENTE,telefonos_cliente);
        
        /*Insercion del modelo a la base de datos*/
        modelo = insertModelo(NOMBRE_MODELO); 
        
        /*Insercion de dos productos a la base de datos*/
        producto = insertProducto(CODIGO_PRODUCTO,
                                  NOMBRE_MODELO, 
                                  cliente);
        
        /*Invocacion de los metodos auxiliares de la clase padre Tests.java
         * para obtener el primer y ultimo dia del mes
         */
        fechaInicioAfiliacion =  firstDayMonth();
        fechaFinAfiliacion    =   Date.valueOf("2013-11-1");
        
        /*Se asocian los productos a los planes*/
        afiliacion = insertAfiliacion(fechaInicioAfiliacion, 
                                      fechaFinAfiliacion, 
                                      plan, producto);
        
        /*Se inserta un nuevo tipo de servicio*/
        tipoServicio = insertTipoServicio(NOMBRE_TIPO_SERVICIO);
        
        /*Se inserta un nuevo servicio*/
        servicio = insertServicio(NOMBRE_SERVICIO, 
                                  DESCRIPCION_SERVICIO,
                                  NOMBRE_TIPO_SERVICIO);
        
        /*Insercion de una instancia de contiene en la base de datos*/
        contiene = insertContiene(CANTIDAD_CONTIENE, 
                                  COSTO_ADICIONAL_CONTIENE, 
                                  paquete, 
                                  servicio);
        
        /*Insercion del consumo en la base de datos*/
        consumo = insertConsumo(CANTIDAD_CONSUMO, 
                                fechaInicioAfiliacion, 
                                producto, 
                                servicio);
        
        ArrayList<String> coment = new ArrayList();
        
        factura = insertFactura(fechaInicioAfiliacion,
                                COSTO_TIENE, 0,
                                coment, producto);
        
        mensajeInicioPrueba("FACTURAR PREPAGO");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        /*Se procede a eliminar todo lo agregado a la base de datos
         * durante la prueba.         
         */
        try {
            consumo.eliminarConsumo();
            contiene.eliminarContiene();
            servicio.eliminarServicio();
            tipoServicio.eliminarTipoServicio();
            afiliacion.eliminarAfiliacion();
            producto.eliminarProducto();
            modelo.eliminarModelo();
            cliente.eliminarCliente();
            tiene.eliminarTiene();
            paquete.eliminarPaquete();
            plan.eliminarPlan();
            planSinPaquete.eliminarPlan();
        } catch (Exception e) {
            /* Si hay una excepcion se imprime un mensaje*/
            System.err.println(e.getMessage());
        }
        mensajeFinPrueba("FACTURARPREPAGO");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    
    /**
     * Test of facturar method, of class FacturarPrepago.
     */
    @Test
    public void testFacturar() {
        System.out.println("facturar");
        Producto producto = this.producto;
        Date fecha = fechaInicioAfiliacion;
        FacturarPrepago instance = new FacturarPrepago();
        Factura expResult = factura;
        Factura result = instance.facturar(producto, fecha);
        assertEquals(expResult, result);

    }
    
    /**
     * Test of buscarPaquete method, of class FacturarPrepago.
     */
    @Test
    public void testBuscarPaquete() {
        System.out.println("Probando buscarPaquete de FacturarPrepago");
        
        /*Instanciacion de la clase facturar prepago*/
        FacturarPrepago modoPrepago = new FacturarPrepago();
        
        /*Recuperacion de una instancia de tiene asociado al plan actual*/
        Tiene tienePrueba = modoPrepago.buscarPaquete(plan,inic);
        
        /*La instancia tienePrueba recuperada debe coincidir con la
         *variable de instanciacion tiene
         */
        boolean expResult = tiene.equals(tienePrueba);
        assertTrue(expResult);
    }
    
    
    /**
     * Test of buscarPaquete method, of class FacturarPrepago.
     * Se verifica que, al introducir un plan sin paquete 
     * el metodo retorne buscarPaquete de la clase FacturarPrepago
     * retorne Null
     */
    @Test
    public void testBuscarPaqueteVacio() {
        System.out.println("Probando buscarPaqueteVacio de FacturarPrepago");
         /*Instanciacion de la clase facturar prepago*/
        FacturarPrepago modoPrepago = new FacturarPrepago();
        
        /*Recuperacion de una instancia de tiene asociado al plan actual,
         esta deberia ser nula pues el plan no posee paquete alguno*/
        Tiene tienePrueba = modoPrepago.buscarPaquete(planSinPaquete,inic);
        assertNull(tienePrueba);
    }
    
     /**
     * Test of buscarPaquete method, of class FacturarPrepago.
     * Se verifica que, al introducir un plan con paquete y la fecha
     * de vencimiento del mismo,el metodo retorne buscarPaquete de la
     * clase FacturarPrepago retorne la instancia respectiva
     */
    @Test
    public void testBuscarPaqueteFechaLimite(){
        System.out.println("Probando buscarPaqueteVacio de FacturarPrepago");
         /*Instanciacion de la clase facturar prepago*/
        FacturarPrepago modoPrepago = new FacturarPrepago();
        
        /*Recuperacion de una instancia de tiene asociado al plan actual,
         esta deberia ser nula pues el plan no posee paquete alguno*/
        Tiene tienePrueba = modoPrepago.buscarPaquete(plan,fin);
        
        /*La instancia tienePrueba recuperada debe coincidir con la
         *variable de instanciacion tiene
         */
        boolean expResult = tiene.equals(tienePrueba);
        assertTrue(expResult);
    }
    
}