package ingsoftware;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
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
public class ConsumoTest {
    
    //Creamos instancias para realizar las pruebas
    static Cliente cliente;
    static Producto producto;
    static Servicio servicio;
    static Plan plan;
    static Afiliacion afiliacion;
    static Paquete paquete;
    static Tiene tiene;
    static Contiene contiene;
    static Consumo dummy;
    static Consumo dummyAgregar;
    static Consumo dummyEliminar;
    
    public ConsumoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        //Otorgamos valores especificos a las instancias
        ArrayList<Long> tlf = new ArrayList();
        tlf.add(Long.valueOf("5555555"));
        cliente = new Cliente(7575, "ClienteFoo", "Meh", tlf);
        producto = new Producto(7575,"modelo1", cliente);
        servicio = new Servicio("FooServicio", "Meh", "TipoServicio1");
        plan     = new Plan("PlanFoo", "Meh", "PREPAGO");
        afiliacion = new Afiliacion(Date.valueOf("2000-1-1"), 
                                    null, plan, producto);
        tiene = new Tiene(Date.valueOf("2013-1-1"), Date.valueOf("2013-1-30"),
                          250, plan, paquete);
        contiene = new Contiene(30, 2, paquete, servicio);
        paquete = new Paquete("PaqueteFoo", "Meh");
        dummy    = new Consumo(5, Date.valueOf("2013-1-1"),producto, servicio);
        dummyAgregar  = new Consumo(1, Date.valueOf("2013-1-15"),producto, servicio);
        dummyEliminar  = new Consumo(10, Date.valueOf("2013-1-29"),producto, servicio);
        
        
        //Creamos un entorno para realizar las pruebas
        try {
            cliente.registrarCliente();
            producto.registrarProducto();
            plan.registrarPlan();
            afiliacion.registrarAfiliacion();
            servicio.registrarServicio();
            paquete.registrarPaquete();
            contiene.registrarContiene();
            tiene.registrarTiene();
            dummy.registrarConsumo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println(" --- INICIANDO PRUEBAS DE CONSUMO.JAVA --- ");
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        
        //Eliminamos los dummies restantes
        dummy.eliminarConsumo();
        tiene.eliminarTiene();
        contiene.eliminarContiene();
        paquete.eliminarPaquete();    
        servicio.eliminarServicio();
        afiliacion.eliminarAfiliacion();
        plan.eliminarPlan();
        producto.eliminarProducto();
        cliente.eliminarCliente();
     
        System.out.println(" --- FINALIZANDO PRUEBAS DE CONSUMO.JAVA --- ");
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Consumo.
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de Consumo");
        
        String expResult = "Cantidad: " + 5 + ", Fecha: " +
                Date.valueOf("2013-1-1") + ", Producto: [" +
                dummy.producto.toString() + "], Servicio: [" +
                dummy.servicio.toString() + "]";
        String result = dummy.toString();
        assertEquals(expResult, result);
    }

    /**
     * Test of registrarConsumo method, of class Consumo.
     */
    @Test
    public void testRegistrarConsumo() throws ParseException {
        System.out.println("Probando registrarConsumo de Consumo");
        
        dummyAgregar.registrarConsumo();
        Consumo result = Consumo.consultarConsumo(7575, "FooServicio", Date.valueOf("2013-1-15"));
        
        assertNotNull(result);
        
        dummyAgregar.eliminarConsumo();
        
    }

    /**
     * Test of consultarConsumo method, of class Consumo.
     */
    @Test
    public void testConsultarConsumo() throws Exception {
        System.out.println("Probando consultarConsumo de Consumo");
        
        Consumo result = Consumo.consultarConsumo(7575, "FooServicio",Date.valueOf("2013-1-1"));
        
        boolean success;
        success = dummy.cantidad == result.cantidad &&
                  dummy.fecha.equals(result.fecha) &&
                  dummy.servicio.nombre.equals(result.servicio.nombre);
        
        assertTrue(success);
    }

    /**
     * Test of eliminarConsumo method, of class Consumo.
     */
    @Test
    public void testEliminarConsumo() throws ParseException {
        System.out.println("Probando eliminarConsumo de Consumo");
        
        dummyEliminar.registrarConsumo();
        dummyEliminar.eliminarConsumo();
        
        Consumo result = Consumo.consultarConsumo(7575, "FooServicio", Date.valueOf("2013-1-29"));
        
        assertNull(result);
        
    }    

    /**
     * Test of modificarConsumo method, of class Consumo.
     */
    @Test
    public void testModificarConsumo() throws ParseException {
        System.out.println("modificarConsumo");
        
        dummy.cantidad = 80;
        dummy.modificarConsumo();
        
        Consumo result = Consumo.consultarConsumo(7575, "FooServicio", Date.valueOf("2013-1-1"));
        
        boolean success;
        success = dummy.cantidad == result.cantidad &&
                  dummy.fecha.equals(result.fecha) &&
                  dummy.servicio.nombre.equals(result.servicio.nombre);
        
        assertTrue(success);
    }
}