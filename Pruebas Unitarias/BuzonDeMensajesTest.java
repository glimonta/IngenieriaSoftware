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
public class BuzonDeMensajesTest extends Tests{
    
    //Declaracion de constantes
    public static final int    CEDULA_CLIENTE        = 19499608;
    public static final String NOMBRE_CLIENTE        = "Fernando";
    public static final String DIRECCION_CLIENTE     = "Montalban";
    public static final String TELEFONO_CLIENTE      = "4424246";
    public static final String NOMBRE_MODELO         = "modeloPrueba";
    public static final int    CODIGO_PRODUCTO       = 777;
    public static final String DESCRIPCION_ESPERADA  = "Buzon de mensajes ";
    public static final double PRECIO_ESPERADO       = 42;
    public static final String DESCRIPCION_ESPERADA_DOS = 
                                "Buzon de mensajes Conferencia tripartita ";
    public static final double PRECIO_ESPERADO_DOS   = 42+126;
    
    //Declaracion de variables
    public static Modelo modelo;
    public static Cliente clienteProd;
    public static ArrayList<Long> telefonos;
    public static Producto prodFacturable;
    public static Facturable buzon;
    public static Facturable buzon_dos;
    
    
    public BuzonDeMensajesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {

        /*Definimos un cliente y lo agregamos a la base de datos*/
        
        telefonos = new ArrayList();
        
        telefonos.add(Long.valueOf(TELEFONO_CLIENTE));
        
        clienteProd = insertCliente(CEDULA_CLIENTE, NOMBRE_CLIENTE, 
                                    DIRECCION_CLIENTE, telefonos);
        
        /*Definimos un modelo y lo agregamos a la base de datos*/
        modelo = insertModelo(NOMBRE_MODELO);
        
        /*Definimos un producto y lo agregamos a la base de datos*/
        prodFacturable = insertProducto(CODIGO_PRODUCTO, NOMBRE_MODELO, 
                                                       clienteProd  );
        /* Creamos una instancia de Buzon de Mensaje */
        buzon     = new BuzonDeMensajes(prodFacturable);
        buzon_dos = new BuzonDeMensajes(new ConferenciaTripartita(prodFacturable));
        mensajeInicioPrueba("BUZON_DE_MENSAJES");
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
        mensajeFinPrueba("BUZON_DE_MENSAJES");
        
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
     * Test of obtenerDescripcion method, of class BuzonDeMensajes.
     */
    @Test
    public void testObtenerDescripcionDos() {
        System.out.println("obtenerDescripcion_Segunda_prueba");
        BuzonDeMensajes instance = (BuzonDeMensajes) buzon_dos;
        String expResult = DESCRIPCION_ESPERADA_DOS;
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
    
    /**
     * Test of obtenerPrecio method, of class BuzonDeMensajes.
     */
    @Test
    public void testObtenerPrecioDos() {
        System.out.println("obtenerPrecio_Segunda_prueba");
        BuzonDeMensajes instance = (BuzonDeMensajes) buzon_dos;
        double expResult = PRECIO_ESPERADO_DOS;
        double result = instance.obtenerPrecio();
        assertEquals(expResult, result, 0.0);
    }
}