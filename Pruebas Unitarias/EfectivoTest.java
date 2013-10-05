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

/**
 *
 * @author fertaku
 */
public class EfectivoTest {
    
    //Creamos instancias para probar las funciones
    static Efectivo dummy;
    static Efectivo dummyAgregar;
    static Efectivo dummyEliminar;
    
    public EfectivoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {

        //Otorgamos valores especificos a las instancias
        dummy = new Efectivo(999, 999);
        dummyAgregar = new Efectivo(888, 888);
        dummyEliminar = new Efectivo(777, 777);

        dummy.registrarEfectivo();
        
        System.out.println(" --- INICIANDO PRUEBAS DE EFECTIVO.JAVA --- ");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        dummy.eliminarEfectivo();
        
        System.out.println(" --- FINALIZANDO PRUEBAS DE EFECTIVO.JAVA --- ");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registrarEfectivo method, of class Efectivo.
     */
    @Test
    public void testRegistrarEfectivo() {
        System.out.println("Probando registrarEfectivo de Efectivo");
        
        //Agregamos un efectivo a la base de datos
        dummyAgregar.registrarEfectivo();
        
        //Recuperamos dicha instancia de la base de datos
        Efectivo result = Efectivo.consultarEfectivo(dummyAgregar.numeroPago);
        
        //Eliminamos dicha instancia de la base de datos
        dummyAgregar.eliminarEfectivo();
        
        //Verificamos que el efectivo se agrego correctamente.
        assertNotNull(result);
        
    }

    /**
     * Test of consultarEfectivo method, of class Efectivo.
     */
    @Test
    public void testConsultarEfectivo() {
        System.out.println("Probando consultarEfectivo de Efectivo");
        
        Integer numPago = dummy.numeroPago;
        
        Efectivo result = Efectivo.consultarEfectivo(numPago);
        
        //Realizamos la verificacion
        
        System.out.println(dummy.id);
        System.out.println(result.id);
        System.out.println(dummy.numeroPago);
        System.out.println(result.numeroPago);
        
        boolean success = (result.id.equals(dummy.id)) & 
                          (result.numeroPago.equals(dummy.numeroPago)); 
        
        assertTrue(success);
    }

    /**
     * Test of eliminarEfectivo method, of class Efectivo.
     */
    @Test
    public void testEliminarEfectivo() {
        System.out.println("Probando eliminarEfectivo de Efectivo");
        
        //Agregamos una instancia para eliminarla posteriormente
        dummyEliminar.registrarEfectivo();
        
        //Eliminamos dicha instancia de la base de datos
        dummyEliminar.eliminarEfectivo();
        
        //Intentamos recuperar dicha informacion de la base de datos
        Efectivo result = Efectivo.consultarEfectivo(dummyEliminar.numeroPago);
        
        //Verificamos que en verdad fue eliminada, result debe ser null
        assertNull(result);
    }

    /**
     * Test of toString method, of class Efectivo.
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de Efectivo");
        
        //Se especifica el string esperado
        String expResult = "ID de Pago: " + dummy.id.toString() + 
                           ", Numero de Pago: " + dummy.numeroPago.toString();
        
        //Verificamos que los valores coinciden
        assertEquals(dummy.toString(), expResult);
    }
}