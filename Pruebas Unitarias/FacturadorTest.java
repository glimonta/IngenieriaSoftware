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
import java.sql.SQLException;
import java.util.Arrays;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.DateFormat;
/**
 *
 * @author fertaku
 */
public class FacturadorTest extends Tests{
    
    /*
     * Declaracion de constantes
     */
        //Constantes de cliente
    public static final int    CEDULA_CLIENTE        = 19499608;
    public static final String NOMBRE_CLIENTE        = "Fernando";
    public static final String DIRECCION_CLIENTE     = "Montalban";
    public static final String TELEFONO_CLIENTE      = "4424246";
    
        //Constantes de modelo
    public static final String NOMBRE_MODELO         = "modeloPrueba";
    
        //Constantes de productos
    public static final int    CODIGO_PRODUCTO_UNO     = 777;
    public static final int    CODIGO_PRODUCTO_DOS     = 888;
    public static final int    CODIGO_PRODUCTO_SINPLAN = 666;
    
        //Constantes de Plan
    public static final String NOMBRE_PLAN           = "planPrueba";
    public static final String TIPO_PLAN             = "PREPAGO";
    public static final String DESCRIPCION_PLAN      = "DescripPlanPrueba";
    
        //Constantes para una instancia de tiene
    public static final float TIENE_COSTO_PLAN       = 100;
    
        //Constantes de paquetes
    public static final String NOMBRE_PAQUETE        = "paquetePrueba";
    public static final String DESCRIPCION_PAQUETE   = "descrPaquetePrueba";
    
        //Constantes de contiene
    public static final int CANTIDAD_CONTIENE        = 50;
    public static final float COSTO_ADICIONAL_CONTIENE = 10;
    
        //Constantes de tipo servicio
    public static final String NOMBRE_TIPO_SERVICIO  = "tipoServicioPrueba";
    
        //Constantes de servicio
    public static final String NOMBRE_SERVICIO       = "servicioPrueba";
    public static final String DESCRIPCION_SERVICIO  = "descServicioPrueba";
    
        //Constantes de consumo
    public static final int CANTIDAD_CONSUMO         = 5;
    
        //Constantes de factura
    public static final double FACTURA_COSTO_PLAN    = 100;
    public static final double FACTURA_MONTO_TOTAL   = 200;
    
        //Constantes de servicio adicional
    public static final float  TARIFA_SERV_ADICIONAL    = 7;
    public static final int    CANTIDAD_SERV_ADICIONAL  = 10;
    public static final String TIPO_PLAN_SERV_ADICIONAL = "PREPAGO";
    
    /*
     * Declaracion de variables
     */
    
        //Arreglo para probar obtenerProductos
    public static ArrayList<Producto> arrayProductos;
    
        //Afiliaciones de los productos para los planos
    public static Afiliacion afiliacionProdUno;
    public static Afiliacion afiliacionProdDos;
    
        //Arreglo para almacener los telefonos del cliente
    public static ArrayList<Long> telefonos;
    
        //Variables para almacenar los productos del cliente
    public static Producto producto_uno;
    public static Producto producto_dos;
    public static Producto producto_sinplan;
    
        //Variable que almacena los datos del cliente
    public static Cliente clienteProd;
    
        //Variable que almacena los datos del modelo
    public static Modelo modelo;
    
        //Variable que almacena los datos del plan
    public static Plan plan;
    
        //Variable que almacena los datos de un paquete
    public static Paquete paquete;
    
        //Variable que almacena los datos de un servicio
    public static Servicio servicio;
    
        //Variable que almacena los datos de un tipo_servicio
    public static TipoServicio tipoServicio;
    
        //Variable que almacena los datos de un consumo
    public static Consumo consumo;
    
        //Variable que almacena los datos de una instancia de tiene
    public static Tiene tiene;
    
        //Variable que almacena los datos de una instancia de contiene
    public static Contiene contiene;
    
        //Variable que almacena los comentarios de la factura
    public static ArrayList<String> comentariosFactura;
    
        //Variable que almacena los datos de la factura
    public static Factura factura;
    
        //Variable que almacena los datos de un servicio adicional
    public static ServicioAdicional servAdicional;
    
        //Variable que almacena datos de una instancia de posee
    public static Posee posee;
    
        //Variables, fechas a utilizar para afiliaciones y paquetes
    public static Date inic;
    public static Date fin;
    public static Date inicFact;
    
    public FacturadorTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
        
        /*Insercion del cliente a la base de datos*/
        telefonos = new ArrayList();
        telefonos.add(Long.valueOf(TELEFONO_CLIENTE));
        clienteProd = insertCliente(CEDULA_CLIENTE,NOMBRE_CLIENTE,
                                    DIRECCION_CLIENTE,telefonos);
        
        /*Insercion del modelo a la base de datos*/
        modelo = insertModelo(NOMBRE_MODELO);        
        /*Insercion del plan a la base de datos*/
        plan = insertPlan(NOMBRE_PLAN, DESCRIPCION_PLAN,TIPO_PLAN);
        /*Insercion de dos productos a la base de datos*/
        producto_uno = insertProducto(CODIGO_PRODUCTO_UNO,
                                    NOMBRE_MODELO, 
                                    clienteProd);
        
        producto_dos = insertProducto(CODIGO_PRODUCTO_DOS,
                                    NOMBRE_MODELO, 
                                    clienteProd);
        
        producto_sinplan = insertProducto(CODIGO_PRODUCTO_SINPLAN,
                                         NOMBRE_MODELO,
                                         clienteProd);
        
        /*Invocacion de los metodos auxiliares de la clase padre Tests.java
         * para obtener el primer y ultimo dia del mes
         */
        inic =  firstDayMonth();
        fin =   lastDayMonth();
        inicFact = inic;
        
        /*Se asocian los productos a los planes*/
        afiliacionProdUno = insertAfiliacion(inic, null, plan, producto_uno);
        afiliacionProdDos = insertAfiliacion(inic, fin, plan, producto_dos);
        
        /*Se definen valores para paquete y se agrega a la base de datos*/
        paquete = insertPaquete(NOMBRE_PAQUETE, DESCRIPCION_PAQUETE);
        /*Se relaciona dicho paquete con el plan previamente creado*/
        tiene = insertTiene(inic, fin, TIENE_COSTO_PLAN, plan, paquete);
        /*Se inserta un nuevo tipo de servicio*/
        tipoServicio = insertTipoServicio(NOMBRE_TIPO_SERVICIO);
        /*Se inserta un nuevo servicio*/
        servicio = insertServicio(NOMBRE_SERVICIO, 
                                DESCRIPCION_SERVICIO,
                                NOMBRE_TIPO_SERVICIO);
        
        /*Insercion de un servicio adicional en la base de datos*/
        servAdicional = insertServicioAdicional(NOMBRE_SERVICIO, 
                                              DESCRIPCION_SERVICIO, 
                                              NOMBRE_TIPO_SERVICIO, 
                                              TARIFA_SERV_ADICIONAL,
                                              CANTIDAD_SERV_ADICIONAL,
                                              TIPO_PLAN_SERV_ADICIONAL);
        
        posee = insertPosee(inic, servAdicional, producto_uno);
        /*Insercion de una instancia de contiene en la base de datos*/
        contiene = insertContiene(CANTIDAD_CONTIENE, 
                                COSTO_ADICIONAL_CONTIENE, 
                                paquete, servicio);
        
        /*Insercion del consumo en la base de datos*/
        consumo = insertConsumo(CANTIDAD_CONSUMO, inic, producto_uno, servicio);
        comentariosFactura = new ArrayList();
        factura = insertFactura(inicFact, TIENE_COSTO_PLAN, 
                                        0, //Aqui falta revisar condicion de factura
                                        comentariosFactura,
                                        producto_uno);
        
        //factura.registrarFactura();
        mensajeInicioPrueba("FACTURADOR");

    }
    
    @AfterClass
    public static void tearDownClass() {
        
        /*Se procede a eliminar todo lo agregado a la base de datos
         * durante la prueba.         
         */
        try {
            factura.eliminarFactura();
            consumo.eliminarConsumo();
            posee.eliminarPosee();
            servAdicional.eliminarServicioAd();
            contiene.eliminarContiene();
            servicio.eliminarServicio();
            tipoServicio.eliminarTipoServicio();
            tiene.eliminarTiene();
            paquete.eliminarPaquete();
            afiliacionProdUno.eliminarAfiliacion();
            afiliacionProdDos.eliminarAfiliacion();
            plan.eliminarPlan();
            producto_uno.eliminarProducto();
            producto_dos.eliminarProducto();
            producto_sinplan.eliminarProducto();
            modelo.eliminarModelo();
            clienteProd.eliminarCliente();
            
        } catch (Exception e) {
            /* Si hay una excepcion se imprime un mensaje*/
            System.err.println(e.getMessage());
        }
        
        mensajeFinPrueba("FACTURADOR");
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of obtenerProductos method, of class Facturador.
     */
    @Test
    public void testObtenerProductos() {
        
        System.out.println("obtenerProductos");
        Cliente cliente = clienteProd;
        
        arrayProductos = new ArrayList();
        arrayProductos.add(producto_uno);
        arrayProductos.add(producto_dos);
        arrayProductos.add(producto_sinplan);

        ArrayList result = Facturador.obtenerProductos(cliente);
        ArrayList expResult = arrayProductos;
        
       
        assertEquals(expResult, result);

    }

    /**
     * Test of obtenerPlanActual method, of class Facturador.
     */
    @Test
    public void testObtenerPlanActual() throws Exception {
        System.out.println("obtenerPlanActual");
        Producto producto = producto_uno;
        Plan expResult = plan;
        Plan result = Facturador.obtenerPlanActual(producto);
        assertEquals(expResult, result);
    }

    
    /**
     * Test of obtenerPlanActual method, of class Facturador.
     * Revisamos que para un producto que no esta afiliado
     * a ningun plan , el metodo obtenerplanActual retorna
     * el valor nulo
     */
    @Test
    public void testObtenerPlanActualSinAfiliacion() throws Exception {
        System.out.println("Probando obtenerPlanActual de Facturador");
        Producto producto = producto_sinplan;
        
        /*expResult debe ser nulo pues el producto no esta afiliado
         * a ningun plan
         */
        Plan expResult = Facturador.obtenerPlanActual(producto);
        assertNull(expResult);
    }
    
    /**
     * Test of obtenerFacturaActual method, of class Facturador.
     */
    @Test
    public void testObtenerFacturaActual() throws Exception {
        System.out.println("obtenerFacturaActual");
        Producto producto = producto_uno;
        Factura expResult = factura;
        Factura result = Facturador.obtenerFacturaActual(producto); 
        assertEquals(expResult, result);
    }

    
    
    /**
     * Test of obtenerFacturaActual method, of class Facturador.
     * Revisamos que para un producto que no esta afiliado
     * a ningun plan , el metodo obtenerFacturaActual retorna
     * el valor nulo
     */
    @Test
    public void testObtenerFacturaActualSinAfiliacion() throws Exception {
        System.out.println("obtenerFacturaActual");
        Producto producto = producto_sinplan;
        Factura expResult = factura;
        Factura result = Facturador.obtenerFacturaActual(producto); 
        assertNull(result);
    }

    /**
     * Test of listarPlanesAfiliados method, of class Facturador.
     */
    @Test
    public void testListarPlanesAfiliados() throws Exception {
        System.out.println("listarPlanesAfiliados");
        Producto producto = producto_dos;
        Facturador instance = new Facturador();
        ArrayList expResult = new ArrayList();
        expResult.add(afiliacionProdDos);
        ArrayList result = instance.listarPlanesAfiliados(producto);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of listarPlanesAfiliados method, of class Facturador.
     * Revisamos que para un producto que no esta afiliado
     * a ningun plan , el metodo listarPlanesAfiliados retorna
     * una lista vacia
     */
    @Test
    public void testListarPlanesAfiliadosSinAfiliacion() throws Exception {
        System.out.println("listarPlanesAfiliados");
        Producto producto = producto_sinplan;
        Facturador instance = new Facturador();
        ArrayList result = instance.listarPlanesAfiliados(producto);
        boolean expResult = result.isEmpty();
        assertTrue(expResult);
    }

    /**
     * Test of listarFacturas method, of class Facturador.
     */
    @Test
    public void testListarFacturas() throws Exception {
        System.out.println("listarFacturas");
        Producto producto = producto_uno;
        Facturador instance = new Facturador();
        ArrayList expResult = new ArrayList();
        
        expResult.add(factura);
        ArrayList result = instance.listarFacturas(producto);

        assertEquals(expResult, result);
    }
  
    /**
     * Test of listarConsumos method, of class Facturador.
     */
    @Test
    public void testListarConsumos() throws Exception {
        System.out.println("listarConsumos");
        Producto producto = producto_uno;
        Date inicio = inic;
        Date fin = this.fin;
        Facturador instance = new Facturador();
        ArrayList expResult = new ArrayList();
        expResult.add(consumo);
        ArrayList result = instance.listarConsumos(producto, inicio, fin);   
        assertEquals(expResult, result);
    }
    
    /**
     * Test of listarConsumos method, of class Facturador.
     * Revisamos que para un producto que no esta afiliado
     * a ningun plan , el metodo listarConsumos retorna
     * una lista vacia
     */
    @Test
    public void testListarConsumosSinAfiliacion() throws Exception {
        System.out.println("listarConsumos");
        Producto producto = producto_sinplan;
        Date inicio = inic;
        Date fin = this.fin;
        Facturador instance = new Facturador();
        ArrayList result = instance.listarConsumos(producto, inicio, fin); 
        boolean expResult = result.isEmpty();
        assertTrue(expResult);
    }

    /**
     * Test of listarServiciosAdicionalesContratados method, of class Facturador.
     */
    @Test
    public void testListarServiciosAdicionalesContratados() throws Exception {
        System.out.println("listarServiciosAdicionalesContratados");
        Producto producto = producto_uno;
        Facturador instance = new Facturador();
        ArrayList expResult = new ArrayList();
        expResult.add(posee);
        ArrayList result = instance.listarServiciosAdicionalesContratados(producto);
        
        assertEquals(expResult, result);
    }
    
     /**
     * Test of listarServiciosAdicionalesContratados method, of class Facturador.
     * Revisamos que para un producto que no esta afiliado
     * a ningun plan , el metodo listarServiciosAdicionalesContratados retorna
     * una lista vacia
     */
    @Test
    public void testListarServiciosAdicionalesContratadosSinAfiliacion() throws Exception {
        System.out.println("listarServiciosAdicionalesContratados");
        Producto producto = producto_sinplan;
        Facturador instance = new Facturador();
        ArrayList result = instance.listarServiciosAdicionalesContratados(producto);
        boolean expResult = result.isEmpty();
        assertTrue(expResult);
    }
}