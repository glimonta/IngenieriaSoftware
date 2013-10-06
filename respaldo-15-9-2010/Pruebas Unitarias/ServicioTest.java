/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mjose
 */
public class ServicioTest {
    
    //Creamos distintas instancias para probar las funciones
    static Servicio dummy;
    static Servicio dummyAgregar;
    static Servicio dummyEliminar;
    
    public ServicioTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {
        
        //Otorgamos valores a dichas instancias
        dummy = new Servicio("ServicioPrueba1","DescripcionPrueba","TipoServicio1");
        
        dummyAgregar = new Servicio("ServicioPrueba2","DescripcionPrueba","TipoServicio2");
        
        dummyEliminar =new Servicio("ServicioPrueba3","DescripcionPrueba","TipoServicio3");
        
        //Agregamos el dummy para modificar, consultar, etc...
        dummy.registrarServicio();
        
        //El dummy para probar la funcion de agregar sera agregado en
        //dicha prueba
        
        System.out.println(" --- INICIANDO PRUEBAS DE SERVICIO.JAVA --- ");
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        
        //Eliminamos los dummies que faltan
        dummy.eliminarServicio();
        
        System.out.println(" --- FINALIZANDO PRUEBAS DE SERVICIO.JAVA --- ");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registrarServicio method, of class Servicio.
     * Verifica si un servicio se agrega correctamente a la base de datos
     */
    @Test   
    public void testRegistrarServicio() throws SQLException {
        
        System.out.println("Probando registrarServicio de Servicio");
        
        //Se registra el Servicio en la base de datos
        dummyAgregar.registrarServicio();
        
        //Se extrae el Servicio que se acaba de registrar
        Servicio result = Servicio.consultarServicio(dummyAgregar.nombre);
        
        
        //Se elimina el Servicio, volviendo al estado inicial de la base de datos
        dummyAgregar.eliminarServicio();
        
        //Se verifica que el Servicio de verdad exista en la base de datos
        assertNotNull(result);
        
    }

    /**
     * Test of consultarServicio method, of class Servicio.
     * Se verifica si la consulta de un Servicio funciona correctamente
     * Se hace comparando los valores obtenidos con los esperados
     */
    @Test
    public void testConsultarServicio() throws SQLException {
        System.out.println("Probando consultarServicio de Servicio");
        
        //Obtenemos el Servicio a verificar
        Servicio result = Servicio.consultarServicio(dummy.nombre);
        
        //Verificamos si lo obtenido se corresponde con lo esperado
        boolean success = dummy.nombre.equals(result.nombre) &
                          dummy.descripcion.equals(result.descripcion) &
                          dummy.tipoServicio.equals(result.tipoServicio);
        
        assertTrue(success);
    }

    /**
     * Test of eliminarServicio method, of class Servicio.
     * Se verifica si un Servicio se elimina correctamente de la base de datos
     */

    @Test
    public void testEliminarServicio() throws SQLException {
        System.out.println("Probando eliminarServicio de Servicio");
        
        //Agregamos una instancia para eliminarla
        dummyEliminar.registrarServicio();

        //Eliminamos la instancia agregada
        dummyEliminar.eliminarServicio();

        //Tratamos de extraer el Servicio que fue eliminado
        Servicio result = Servicio.consultarServicio("ServicioPrueba3");

        //Verificamos si el valor obtenido es null
        assertNull(result);
    }

    /**
     * Test of modificarServicio method, of class Servicio.
     * Verifica si un Servicio se modifica de la base de datos correctamente
     */
    @Test
    public void testModificarServicio() throws SQLException {
        
        System.out.println("Probando modificarServicio de Servicio");
        
        //Agregamos un nuevo Servicio a la base para ser modificado
        
        //Modificamos el campo de la descripcion
        dummy.descripcion = "Modificado";
        
        //Se procede a hacer la modificacion
        dummy.modificarServicio();
        
        //Se extrae el Servicio modificado de la base de datos
        Servicio result = Servicio.consultarServicio("ServicioPrueba1");
        
        //Se verifica si son iguales o no
        assertEquals(dummy.descripcion, result.descripcion);
        assertEquals(dummy.tipoServicio, result.tipoServicio);
        assertEquals(dummy.nombre, result.nombre);
        
    }

    @Test
    public void testToString() {
        System.out.println("Probando toString de Servicio");
        dummy.descripcion = "DescripcionPrueba";
        
        //Se especifica el string esperado de la funcion
        String expResult = "Nombre: " + dummy.nombre + ", Descripcion: " + 
                            dummy.descripcion + ", Tipo de servicio: " + 
                            dummy.tipoServicio;
        
        //Se obtiene el string generado por la funcion
        String result = dummy.toString();
        
        //Se comparan ambos strings
        assertEquals(expResult, result);
    }

}
