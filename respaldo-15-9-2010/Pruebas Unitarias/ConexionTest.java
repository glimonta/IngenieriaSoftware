/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
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
public class ConexionTest {
    
    public ConexionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
     * Test of obtenerConn method, of class Conexion.
     * Verifica que la conexion a la base de datos es posible.
     * Si el resultado es distinto de null es que la conexion fue efectiva.
     */
    @Test
    public void testObtenerConn() {
        System.out.println("Probando conexion a la base de datos ");
        Connection result = Conexion.obtenerConn();
        assertNotNull(result);
    }
}