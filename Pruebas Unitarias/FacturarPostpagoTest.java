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
    public static final String DESCRIPCION_PLAN = "descripcionPlanPostpago";
    public static final String TIPO_PLAN   = "POSTPAGO"; //No modificar
    
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
    
        //Constantes para una instancia de consumo
    public static final Integer CANTIDAD_CONSUMO = 11;
    
    
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
        
        /*Insercion de dos productos a la base de datos*/
        producto = insertProducto(CODIGO_PRODUCTO,
                                  NOMBRE_MODELO, 
                                  cliente);
        
        /*Invocacion de los metodos auxiliares de la clase padre Tests.java
         * para obtener el primer y ultimo dia del mes
         */
        fechaInicioAfiliacion =  firstDayMonth();
        fechaFinAfiliacion    =   lastDayMonth();

        /*Se asocian los productos a los planes*/
        afiliacion = insertAfiliacion(fechaInicioAfiliacion, 
                                      fechaFinAfiliacion, 
                                      plan, producto);
        
        /*Se definen valores para paquete y se agrega a la base de datos*/
        paquete = insertPaquete(NOMBRE_PAQUETE, DESCRIPCION_PAQUETE);
        
        /*Se relaciona dicho paquete con el plan previamente creado*/
        tiene = insertTiene(fechaInicioAfiliacion, 
                            fechaFinAfiliacion, 
                            COSTO_TIENE, 
                            plan, 
                            paquete);
        
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
        
        try {
            factura = Facturador.obtenerFacturaActual(producto);
        } catch (Exception e) {
            /* Si hay una excepcion se imprime un mensaje*/
            System.err.println(e.getMessage());
        }
        
        factura.montoTotal += 
                COSTO_ADICIONAL_CONTIENE*(CANTIDAD_CONSUMO - CANTIDAD_CONTIENE);
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        try {
            factura.eliminarFactura();
            consumo.eliminarConsumo();
            contiene.eliminarContiene();
            servicio.eliminarServicio();
            tipoServicio.eliminarTipoServicio();
            tiene.eliminarTiene();
            paquete.eliminarPaquete();
            afiliacion.eliminarAfiliacion();
            producto.eliminarProducto();
            plan.eliminarPlan();
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
     * Test of facturar method, of class FacturarPostpago.
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
     * Test of buscarPaquete method, of class FacturarPostpago.
     */
    @Test
    public void testBuscarPaquete() {
        System.out.println("buscarPaquete");
        Plan plan = this.plan;
        Date fecha = fechaInicioAfiliacion;
        FacturarPostpago instance = new FacturarPostpago();
        Tiene expResult = tiene;
        Tiene result = instance.buscarPaquete(plan, fecha);
        
        System.out.println(expResult.toString());
        System.out.println(result.toString());
        
        assertEquals(expResult, result);
    }

    /**
     * Test of calcularCostoTotal method, of class FacturarPostpago.
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
}