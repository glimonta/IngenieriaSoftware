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
public class ClienteTest extends Tests{
    
    //Creamos varias instancias para probar las distintas funciones
    static Cliente dummy;
    static Cliente dummyAgregar;
    static Cliente dummyEliminar;
    static ArrayList<Long> dummyTelf;
    
    public ClienteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        /*Otorgamos valores especificos a las instancias.*/
        dummyTelf = new ArrayList();
        dummyTelf.add(Long.valueOf("4424246"));
        dummyTelf.add(Long.valueOf("4123622646"));
        
        /*Registramos el cliente que sera utilizado para las demas
         *pruebas que no sean agregar y eliminar.*/
        dummy = insertCliente(19499608, "Fernando", "Montalban", dummyTelf);
        
        /*El dummy para probar la funcion de agregar sera agregado en
         *dicha prueba, igual para el dummy de eliminar.*/
        mensajeInicioPrueba("CLIENTE");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        /*Eliminamos los dummies restantes*/
        dummy.eliminarCliente();
        mensajeFinPrueba("CLIENTE");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registrarCliente method, of class Cliente.
     * Verifica si se registran los clientes efectivamente
     */
    @Test
    public void testRegistrarCliente() throws Exception {
        System.out.println("Probando registrarCliente de Cliente");
        
        //Agregamos una instancia a la base de datos
        dummyAgregar = insertCliente(777, "Twisted", "Fate", dummyTelf);
        
        //Verificamos si se agrego a la base de datos
        Cliente result = Cliente.consultarCliente(dummyAgregar.cedula);
        
        //Eliminamos el cliente de la base de datos
        dummyAgregar.eliminarCliente();
        
        //Verificamos que si se agrego la instancia
        assertNotNull(result);
    }

    /**
     * Test of consultarCliente method, of class Cliente.
     * Verifica si el consultar un cliente se hace de forma correcta
     */
    @Test
    public void testConsultarCliente() {
        
        System.out.println("Probando consultarCliente de Cliente");
        
        Cliente result = Cliente.consultarCliente(dummy.cedula);
        
        boolean success = (result.cedula.equals(dummy.cedula)) & 
                          (result.direccion.equals(dummy.direccion)) & 
                          (result.nombre.equals(dummy.nombre)) & 
                          (result.telefonos.equals(dummy.telefonos));
        
        assertTrue(success);
        
    }

    /**
     * Test of modificarCliente method, of class Cliente.
     * Verifica si la modificacion de un cliente se realiza correctamente
     */
    @Test
    public void testModificarCliente() {
        System.out.println("Probando modificarCliente de Cliente");
        
        //Modificamos ciertos parametros
        dummy.direccion = "Modificado";
        dummy.nombre = "Modificado";
        
        //Ejecutamos la modificacion
        dummy.modificarCliente();
        
        //Verificamos que se hizo la modificacion
        Cliente result = Cliente.consultarCliente(dummy.cedula);
        
        boolean success = (result.cedula.equals(dummy.cedula)) & 
                          (result.direccion.equals(dummy.direccion)) & 
                          (result.nombre.equals(dummy.nombre)) & 
                          (result.telefonos.equals(dummy.telefonos));
        
        assertTrue(success);
        
    }

    /**
     * Test of eliminarCliente method, of class Cliente.
     * Verifica si se elimina correctamente de la base de datos
     */
    @Test
    public void testEliminarCliente() {
        System.out.println("Probando eliminarCliente de Cliente");
        
        //Agregamos en la base de datos un cliente a eliminar
        dummyEliminar = insertCliente(999, "Cassio", "Peia", dummyTelf);
        
        //Eliminamos el cliente que fue agregado
        dummyEliminar.eliminarCliente();
        
        //Tratamos de extraer el cliente eliminado
        Cliente result = Cliente.consultarCliente(dummyEliminar.cedula);
        
        //Verificamos que el cliente no este en la base de datos
        assertNull(result);
                
    }

    /**
     * Test of obtenerProductos method, of class Cliente.
     */
    @Test
    public void testObtenerProductos() {
        
        //Falta implementar
        assertTrue(true);
    }

    /**
     * Test of toString method, of class Cliente.
     * Verifica que el metodo toString funcione correctamente
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de Cliente");
        
        //Extraemos los telefonos para luego especificar el valor esperado
        String tlfs = "";
        // se obtienen todos los telefonos del cliente
        for (int i=0; i < dummy.telefonos.toArray().length; ++i) {
                tlfs = tlfs + dummy.telefonos.get(i).toString() + " ";
                if (dummy.telefonos.toArray().length -1 > i) {
                    tlfs = tlfs + ", ";
                }
            }
        
        //Especificamos el valor esperado
        String expResult = "Cedula: " + dummy.cedula.toString() + ", Nombre: " + 
                dummy.nombre + ", Direccion: " + dummy.direccion + ", Telefonos: "
                + tlfs;
        
        //Ejecutamos la funcion a probar
        String result = dummy.toString();
        
        //Verificamos que los strings coincidan
        assertEquals(expResult, result);
    }
}