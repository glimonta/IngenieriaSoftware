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
public class FormaPagoTest {
    
    //Creamos una instancia para probar las funciones
    static FormaPago dummy;
    
    public FormaPagoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        dummy = new FormaPago(7);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class FormaPago.
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de FormaPago");
        
        //Verificamos que se obtiene el resultado esperado
        assertEquals(dummy.toString(), dummy.id.toString());
        
    }
}