/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class ProductoTest extends Tests{
    
        //Constantes del cliente
    public static final int    CEDULA_CLIENTE = 12345678;
    public static final String NOMBRE_CLIENTE = "cliente";
    public static final String DIRECCION_CLIENTE = "direccion";
    public static final String TELEFONO_CLIENTE = "55555555";
    
        //Constantes del producto
    public static final int CODIGO_PRODUCTO = 456;
    public static final int CODIGO_PRODUCTO_ELIMINAR = 666;
    public static final int CODIGO_PRODUCTO_MODIFICAR = 777;
    public static final String NOMBRE_MODELO_MODIFICAR = "modeloModificar";
    public static final String NOMBRE_MODELO = "modeloPrueba";
    
    
        //Mensajes esperados para el toString
    public static final String MENSAJE_ESPERADO_TOSTRING =
             "Codigo del producto: " + CODIGO_PRODUCTO + ", Modelo: " +
             NOMBRE_MODELO + ", Cliente: [" + "Cedula: " + CEDULA_CLIENTE + 
             ", Nombre: " + NOMBRE_CLIENTE + ", Direccion: " + 
             DIRECCION_CLIENTE + ", Telefonos: " + TELEFONO_CLIENTE + " ]";
    
    
    //Definicion de variables
    
        //Variables del cliente
    public static ArrayList<Long> telefonos_cliente;
    public static Cliente cliente;
    
        //Variables del modelo
    public static Modelo modelo;
    
        //Variables del producto
    public static Producto producto;
    
    static Producto dummyEliminar;
    static Producto dummyModificar;
    static Modelo modelo_modificar;
    
    public ProductoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        mensajeInicioPrueba("PRODUCTO");
        
        telefonos_cliente = new ArrayList();
        telefonos_cliente.add(Long.valueOf(TELEFONO_CLIENTE));
        cliente = insertCliente(CEDULA_CLIENTE, 
                                NOMBRE_CLIENTE, 
                                DIRECCION_CLIENTE, 
                                telefonos_cliente);
        
        modelo = insertModelo(NOMBRE_MODELO);
        
        /*Insercion de dos productos a la base de datos*/
        producto = insertProducto(CODIGO_PRODUCTO,
                                  NOMBRE_MODELO, 
                                  cliente);
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        try {
            producto.eliminarProducto();
            modelo.eliminarModelo();
            cliente.eliminarCliente();
        } catch (Exception e) {
                /* Si hay una excepcion se imprime un mensaje*/
                System.err.println(e.getMessage());
        }
        mensajeFinPrueba("PRODUCTO");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {

    }


    /**
     * Test of consultarProducto method, of class Producto.
     */
    @Test
    public void testConsultarProducto() throws Exception {
        System.out.println("consultarProducto");
        int codigo = CODIGO_PRODUCTO;
        Producto expResult = producto;
        Producto result = Producto.consultarProducto(codigo);
        assertEquals(expResult, result);
    }

     /**
     * Test of eliminarProducto method, of class Producto.
     */
    @Test
    public void testEliminarProducto() throws Exception {
        System.out.println("eliminarProducto");
        
        //Agregamos una instancia para eliminarla
        dummyEliminar = insertProducto(CODIGO_PRODUCTO_ELIMINAR,
                                    NOMBRE_MODELO,
                                    cliente);
        
        //Eliminamos la instancia agregada
        dummyEliminar.eliminarProducto();
        
        Producto result = Producto.consultarProducto(CODIGO_PRODUCTO_ELIMINAR);
        
        //Tratamos de extraer el producto que fue eliminado
        
        assertNull(result);
    }

  /**
     * Test of modificarProducto method, of class Producto.
     */
    @Test
    public void testModificarProducto() throws Exception {
        System.out.println("modificarProducto");
        
        
        //Agregamos un nuevo PRODUCTO a la base para ser modificado
        
        modelo_modificar = insertModelo(NOMBRE_MODELO_MODIFICAR);
        
        dummyModificar = insertProducto(CODIGO_PRODUCTO_MODIFICAR,
                                    NOMBRE_MODELO,
                                    cliente);
     
        
        dummyModificar.modelo = NOMBRE_MODELO_MODIFICAR;
        
        /*Modificamos la instancia*/
        dummyModificar.modificarProducto();
        
        
        //Se extrae el plan modificado de la base de datos
        Producto result = Producto.consultarProducto(dummyModificar.codigoProd);
        
        /*Eliminamos las instancias*/
        dummyModificar.eliminarProducto();
        
        modelo_modificar.eliminarModelo();
        //Se verifica si son iguales o no
        assertTrue(dummyModificar.equals(result));
   
    }

    /**
     * Test of toString method, of class Producto.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Producto instance = producto;
        String expResult = MENSAJE_ESPERADO_TOSTRING;
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerDescripcion method, of class Producto.
     */
    @Test
    public void testObtenerDescripcion() {
        System.out.println("obtenerDescripcion");
        Producto instance = producto;
        String expResult = "";
        String result = instance.obtenerDescripcion();
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerPrecio method, of class Producto.
     */
    @Test
    public void testObtenerPrecio() {
        System.out.println("obtenerPrecio");
        double precioEsperado = 0;
        double result = producto.obtenerPrecio();
        boolean expResult = (precioEsperado == result);
        assertTrue(expResult);
    }

    /**
     * Test of equals method, of class Producto.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        
        int    codProducto   = producto.codigoProd;
        String nombreModelo  = producto.modelo;
        Cliente cliente      = producto.cliente;
        
        
        Producto productoEquals = new Producto(codProducto,nombreModelo,cliente);
        boolean expResult = producto.equals(productoEquals);
        
        assertTrue(expResult);
    }
}