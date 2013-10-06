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
public class PaqueteTest extends Tests{
    
    //Creamos instancias para utilizar en las pruebas
    static Paquete dummy;
    static Paquete dummyAgregar;
    static Paquete dummyEliminar;
    
    public PaqueteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        /*Insertamos un paquete a la base de datos*/
        dummy = insertPaquete("paquete1", "descripcion1");
        mensajeInicioPrueba("PAQUETE");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        /*Eliminamos los dummies restantes*/
        dummy.eliminarPaquete();
        mensajeFinPrueba("PAQUETE");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registrarPaquete method, of class Paquete.
     * Verifica si el agregar un paquete funciona correctamente
     */
    @Test
    public void testRegistrarPaquete() throws Exception {
        System.out.println("Probando registrarPaquete de Paquete");
        
        
        /*Se agrega un paquete a la base de datos*/
        dummyAgregar = insertPaquete("paquete2", "descripcion2");
        
        /*Se recupera el paquete de la base de datos*/
        Paquete result = Paquete.consultarPaquete(dummyAgregar.nombre);
        
        /*Se elimina el paquete de la base de datos*/
        dummyAgregar.eliminarPaquete();
        
        //Se verifica que el si se agrego anteriormente
        assertNotNull(result);
        
    }

    /**
     * Test of consultarPaquete method, of class Paquete.
     * Verifica que la consulta de un paquete se ejecute correctamente
     */
    @Test
    public void testConsultarPaquete() throws Exception {
        System.out.println("Probando consultarPaquete de Paquete");
        
        //Se recupera el paquete de la base de datos
        Paquete result = Paquete.consultarPaquete(dummy.nombre);
        
        //Verificamos que sean iguales
        boolean success = result.nombre.equals(dummy.nombre) &
                          result.descripcion.equals(dummy.descripcion);
        
        assertTrue(success);

    }

    /**
     * Test of eliminarPaquete method, of class Paquete.
     * Verifica que eliminar un paquete se haga correctamente
     */
    @Test
    public void testEliminarPaquete() throws Exception {
        System.out.println("Probando eliminarPaquete de Paquete");
        
        
        /*Agregamos un paquete a la base de datos para eliminarlo*/
        dummyEliminar = insertPaquete("paquete3", "descripcion3");
        
        /*Eliminamos el paquete de la base de datos*/
        dummyEliminar.eliminarPaquete();
        
        /*Verificamos que el paquete en verdad fue eliminado*/
        Paquete result = Paquete.consultarPaquete(dummyEliminar.nombre);
        assertNull(result);
        
    }

    /**
     * Test of modificarPaquete method, of class Paquete.
     */
    @Test
    public void testModificarPaquete() throws Exception {
        System.out.println("Probando modificarPaquete de Paquete");
        
        //Modificamos campos del dummy
        dummy.descripcion = "Modificado";
        
        //Ejecutamos la modificacion
        dummy.modificarPaquete();
        
        //Verificamos que la modificacion se realizo correctamente
        Paquete result = Paquete.consultarPaquete(dummy.nombre);
        
        //Verificamos que sean iguales
        boolean success = result.nombre.equals(dummy.nombre) &
                          result.descripcion.equals(dummy.descripcion);
        
        assertTrue(success);

    }

    /**
     * Test of ListarServicios method, of class Paquete.
     */
    @Test
    public void testListarServicios() throws Exception {
        System.out.println("Probando ListarServicios de Paquete");
        
        //Falta por implementar
        assertTrue(true);
    }

    /**
     * Test of toString method, of class Paquete.
     * Verifica si la funcion toString funciona como se espera
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de Paquete");
        
        //Se especifica el valor esperado
        String expResult = "Nombre: " + dummy.nombre + 
                           ", Descrpcion: " + dummy.descripcion;
        
        //Se ejecuta la funcion toString
        String result = dummy.toString();
        
        //Se compara lo obtenido con lo esperado
        assertEquals(expResult, result);
        
    }
}