/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author fertaku
 */
public class BuzonDeMensajesTest {
    
    //Declaracion de constantes
    public static final int    CEDULA_CLIENTE        = 19499608;
    public static final String NOMBRE_CLIENTE        = "Fernando";
    public static final String DIRECCION_CLIENTE     = "Montalban";
    public static final String TELEFONO_CLIENTE      = "4424246";
    public static final String NOMBRE_MODELO         = "modeloPrueba";
    public static final int    CODIGO_PRODUCTO       = 777;
    public static final String DESCRIPCION_ESPERADA  = "Buzon de mensajes ";
    public static final double PRECIO_ESPERADO       = 42;
    
    //Declaracion de variables
    public static Modelo modelo;
    public static Cliente clienteProd;
    public static ArrayList<Long> telefonos;
    public static Producto prodFacturable;
    public static Facturable buzon;
    
    
    public BuzonDeMensajesTest() {
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
        
        /*Definimos un producto y lo agregamos a la base de datos*/
        prodFacturable = new Producto(CODIGO_PRODUCTO, NOMBRE_MODELO, 
                                                       clienteProd  );
        try {
            prodFacturable.registrarProducto();
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
        
        /* Creamos una instancia de Buzon de Mensaje */
        buzon = new BuzonDeMensajes(prodFacturable);
        System.out.println("\n**INICIO DE PRUEBAS DE BUZON_DE_MENSAJES**");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        /*Se procede a eliminar todo lo agregado a la base de datos
         * durante la prueba.         
         */
        
        try {
            prodFacturable.eliminarProducto();
        } catch (SQLException ex) {
            // En caso de haber una excepcion se imprime el mensaje
            System.err.println(ex.getMessage());
        }
        
        modelo.eliminarModelo();
        
        clienteProd.eliminarCliente();
        System.out.println("**FIN DE PRUEBAS DE BUZON_DE_MENSAJES**\n");
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of obtenerDescripcion method, of class BuzonDeMensajes.
     */
    @Test
    public void testObtenerDescripcion() {
        System.out.println("obtenerDescripcion");
        BuzonDeMensajes instance = (BuzonDeMensajes) buzon;
        String expResult = DESCRIPCION_ESPERADA;
        String result = instance.obtenerDescripcion();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerPrecio method, of class BuzonDeMensajes.
     */
    @Test
    public void testObtenerPrecio() {
        System.out.println("obtenerPrecio");
        BuzonDeMensajes instance = (BuzonDeMensajes) buzon;
        double expResult = PRECIO_ESPERADO;
        double result = instance.obtenerPrecio();
        assertEquals(expResult, result, 0.0);
    }
}