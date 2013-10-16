
package web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author acs
 */
public class ControladorTest {
    
    public ControladorTest() {
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
     * Test of handleRequest method, of class Controlador.
     */
    @Test
    public void testHandleRequest() throws Exception {
        System.out.println("handleRequest");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        Controlador instance = new Controlador();
        ModelAndView expResult = null;
        ModelAndView result = instance.handleRequest(request, response);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}