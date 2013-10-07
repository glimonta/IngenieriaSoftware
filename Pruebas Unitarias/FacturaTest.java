/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author federio
 */
public class FacturaTest {
    
    //Se agrega el Producto y Cliente necesarios
    static Cliente C1;
    static Producto P1;
    
    //Se agregan instancias a probar
    static Factura dummy;
    static Factura dummyAgregar;
    static Factura dummyEliminar;
    
    public FacturaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException, ParseException {
        
        //Se le dan valores al Cliente y Producto
        ArrayList<Long> telfC1 = new ArrayList<Long>();
        Long num = new Long(123);
        telfC1.add(num);
        C1 = new Cliente(123,"ClientePrueba","DirPrueba",telfC1);
        C1.registrarCliente();
        
        P1 = new Producto(1,"ModeloPrueba",C1);
        P1.registrarProducto();
        
        //Se le dan valores a las instancias
        String sInic = "1993-03-13";
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sInic);
        Date inic = new java.sql.Date(utilDate.getTime());
        
        sInic = "1994-03-13";
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sInic);
        Date inic2 = new java.sql.Date(utilDate.getTime());
        
        sInic = "1995-03-13";
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sInic);
        Date inic3 = new java.sql.Date(utilDate.getTime());
        
        ArrayList<String> com = new ArrayList<String>();
        com.add("ComentarioPrueba");
        
        dummy = new Factura(inic,1,2,com,P1);
        dummyAgregar = new Factura(inic2,1,2,com,P1);
        dummyEliminar = new Factura(inic3,1,2,com,P1);
        
        dummy.registrarFactura();
        
        System.out.println(" --- INICIANDO PRUEBAS DE FACTURA.JAVA --- ");
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        dummy.eliminarFactura();
        C1.eliminarCliente();
        P1.eliminarProducto();     
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calcularMontoTotal method, of class Factura.
     */
    @Test
    public void testCalcularMontoTotal() {
        
        System.out.println("Probando calcularMontoTotal de Factura");
        double expResult = 3.0;
        double result = dummy.calcularMontoTotal();
        
        assertEquals(expResult, result,0.0);
    }

    /**
     * Test of toString method, of class Factura.
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de Factura");
        
        String expResult = "Fecha: " + "1993-03-13" + ", Monto: " + 
                3.0 + ", Comentarios: [" + "ComentarioPrueba " +
                "] , Producto: [" + dummy.producto.toString() + "]";
        String result = dummy.toString();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of registrarFactura method, of class Factura.
     */
    @Test
    public void testRegistrarFactura() throws ParseException {
        System.out.println("Probando registrarFactura de Factura");
        
        //Se agrega la Factura
        dummyAgregar.registrarFactura();
        
        //Se consulta a ver si fue agregada
        Factura result = Factura.consultarFactura(dummyAgregar.producto, dummyAgregar.fecha);
        
        //Eliminar factura agregada
        dummyAgregar.eliminarFactura();
        
        //Probar si estaba o no
        assertNotNull(result);
    }

    /**
     * Test of consultarFactura method, of class Factura.
     */
    @Test
    public void testConsultarFactura() throws Exception {
        System.out.println("Probando consultarFactura de Factura");
        
        //Se hace la consulta
        Factura result = Factura.consultarFactura(dummy.producto, dummy.fecha);
        
        //Se ve si resulta ser esa
        Boolean success;
        success = dummy.comentarios.equals(result.comentarios) &&
                  dummy.costoPlan == result.costoPlan &&
                  dummy.fecha.equals(result.fecha) &&
                  dummy.montoTotal == result.montoTotal &&
                  dummy.producto.equals(result.producto);
        
        assertTrue(success);
    }

    /**
     * Test of modificarFactura method, of class Factura.
     */
    @Test
    public void testModificarFactura() throws ParseException {
        System.out.println("Probando modificarFactura de Factura");
        
        //Se modifica la factura
        dummy.costoPlan = 28.0;
        dummy.modificarFactura();
        
        //Se consulta a ver si es la misma
        Factura result = Factura.consultarFactura(dummy.producto, dummy.fecha);
        
        Boolean success;
        success = dummy.comentarios.equals(result.comentarios) &&
                  dummy.costoPlan == result.costoPlan &&
                  dummy.fecha.equals(result.fecha) &&
                  dummy.montoTotal == result.montoTotal &&
                  dummy.producto.equals(result.producto);
        
        assertTrue(success);        

    }

    /**
     * Test of eliminarFactura method, of class Factura.
     */
    @Test
    public void testEliminarFactura() throws ParseException {
        System.out.println("Probando eliminarFactura de Factura");
        
        //Agregando y eliminando la instancia
        dummyEliminar.registrarFactura();
        dummyEliminar.eliminarFactura();
        
        //Chequeando si esta
        Factura result = Factura.consultarFactura(dummyEliminar.producto, dummyEliminar.fecha);
        
        assertNull(result);
    }
}