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

/**
 *
 * @author fertaku
 */
public class FacturadorTest {
    
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
    public static final int    CODIGO_PRODUCTO_UNO   = 777;
    public static final int    CODIGO_PRODUCTO_DOS   = 888;
    
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
    public static final int COSTO_ADICIONAL_CONTIENE = 10;
    
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
        
        /*Definimos un cliente y lo agregamos a la base de datos*/
        
        telefonos = new ArrayList();
        
        telefonos.add(Long.valueOf(TELEFONO_CLIENTE));
        
        clienteProd     = new Cliente(CEDULA_CLIENTE, 
                                      NOMBRE_CLIENTE, 
                                      DIRECCION_CLIENTE, 
                                      telefonos);
        
        clienteProd.registrarCliente();
        
        /*Definimos un modelo y lo agregamos a la base de datos*/
        
        modelo = new Modelo(NOMBRE_MODELO);
        
        modelo.registrarModelo();
        
        /*Definimos un nuevo plan y lo agregamos a la base de datos*/
        plan = new Plan(NOMBRE_PLAN, DESCRIPCION_PLAN, TIPO_PLAN);
        plan.registrarPlan();
        
        /*Definimos dos productos y los agregamos a la base de datos*/
        producto_uno = new Producto(CODIGO_PRODUCTO_UNO,
                                    NOMBRE_MODELO, 
                                    clienteProd);
        
        producto_dos = new Producto(CODIGO_PRODUCTO_DOS,
                                    NOMBRE_MODELO, 
                                    clienteProd);
        
        try {
            producto_uno.registrarProducto();
            producto_dos.registrarProducto();
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
        
        /*Se asocian los productos a los planes*/
        inic = null;
        fin = null;
        inicFact = null;
        
        try {
        String sInic = "2013-03-13";
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sInic);
        inic = new java.sql.Date(utilDate.getTime());
        
        String sFin = "2014-01-31";
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sFin);
        fin = new java.sql.Date(utilDate.getTime());
        
        String sFact = "2013-10-01";
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sFact);
        inicFact = new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        afiliacionProdUno = new Afiliacion(inic, null, plan, producto_uno);
        afiliacionProdDos = new Afiliacion(inic, fin, plan, producto_dos);
        
        afiliacionProdUno.registrarAfiliacion();
        afiliacionProdDos.registrarAfiliacion();
        
        /*Se definen valores para paquete y se agrega a la base de datos*/
        paquete = new Paquete(NOMBRE_PAQUETE, DESCRIPCION_PAQUETE);
        paquete.registrarPaquete();
        
        /*Se relaciona dicho paquete con el plan previamente creado*/
        tiene = new Tiene(inic, fin, TIENE_COSTO_PLAN, plan, paquete);
        try {
            tiene.registrarTiene();
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        
        /* Se define un nuevo tipo de servicio y se agrega a la base de datos*/
        tipoServicio = new TipoServicio(NOMBRE_TIPO_SERVICIO);
        tipoServicio.registrarTipoServicio();
        
        /*Se definen valores para servicio y se agrega a la base de datos*/
        servicio = new Servicio(NOMBRE_SERVICIO, 
                                DESCRIPCION_SERVICIO,
                                NOMBRE_TIPO_SERVICIO);
        try {
            servicio.registrarServicio();
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        
          
        /*Se definen valores para una instancia de contiene*/
        contiene = new Contiene(CANTIDAD_CONTIENE, 
                                COSTO_ADICIONAL_CONTIENE, 
                                paquete, servicio);
        
        /*Se definen valores para un nuevo servicio adicional consumido
         * por el producto uno
         */
        servAdicional = new ServicioAdicional(NOMBRE_SERVICIO, 
                                              DESCRIPCION_SERVICIO, 
                                              NOMBRE_TIPO_SERVICIO, 
                                              TARIFA_SERV_ADICIONAL,
                                              CANTIDAD_SERV_ADICIONAL,
                                              TIPO_PLAN_SERV_ADICIONAL);
        try {
            servAdicional.registrarServicioAd();
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        posee = new Posee(inicFact, servAdicional, producto_uno);
        posee.registrarPosee();
        
        try {
            contiene.registrarContiene();
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        
        /*Se definen valores para un consumo y se agrega a la base de datos*/
        consumo = new Consumo(CANTIDAD_CONSUMO, inic, producto_uno, servicio);
        consumo.registrarConsumo();
        
        comentariosFactura = new ArrayList();
        comentariosFactura.add("Comentario1");
        comentariosFactura.add("Comentario2");
        comentariosFactura.add("Comentario3");
        
        factura = new Factura(inicFact, FACTURA_COSTO_PLAN+TARIFA_SERV_ADICIONAL, 
                                        FACTURA_MONTO_TOTAL, 
                                        comentariosFactura,
                                        producto_uno);
        
        //factura.registrarFactura();
        
        System.out.println("\n**INICIO DE PRUEBAS DE FACTURADOR**");
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        /*Se procede a eliminar todo lo agregado a la base de datos
         * durante la prueba.         
         */
        factura.eliminarFactura();
        consumo.eliminarConsumo();
        posee.eliminarPosee();
        try {
            servAdicional.eliminarServicioAd();
            contiene.eliminarContiene();
            servicio.eliminarServicio();
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        tipoServicio.eliminarTipoServicio();
        try {
            tiene.eliminarTiene();
        } catch (Exception e) {
            // Si hay una excepcion se imprime un mensaje
            System.err.println(e.getMessage());
        }
        paquete.eliminarPaquete();
        afiliacionProdUno.eliminarAfiliacion();
        afiliacionProdDos.eliminarAfiliacion();
        plan.eliminarPlan();
        
        try {
            producto_uno.eliminarProducto();
            producto_dos.eliminarProducto();
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
        
        modelo.eliminarModelo();
        
        clienteProd.eliminarCliente();
        System.out.println("**FIN DE PRUEBAS DE FACTURADOR**\n");
        
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
        System.out.println(factura.toString());
        System.out.println(result.toString());
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
        
        System.out.println("expResult: "+ expResult.toString());
        System.out.println("result: "+result.toString());
        
        assertEquals(expResult, result);
    }
}