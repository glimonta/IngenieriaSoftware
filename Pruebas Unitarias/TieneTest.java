/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;
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
public class TieneTest extends Tests{
    
    //Creamos instancias para probar las funciones
    static Plan plan;
    static Paquete paquete;
    static Tiene dummy;
    static Tiene dummyAgregar;
    static Tiene dummyEliminar;
    
    public TieneTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        //Otorgamos valores especificos a las instancias.
        plan    = insertPlan("NuevoPlan", "DescripcionPlan", "PREPAGO");
        paquete = insertPaquete("NuevoPaquete", "DescripcionPaquete");
        dummy = insertTiene(Date.valueOf("2013-1-1"), 
                Date.valueOf("2013-1-15"), 50, plan, paquete);
        
        mensajeInicioPrueba("TIENE");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        //Eliminamos los dummies restantes
        try {
            dummy.eliminarTiene();
            plan.eliminarPlan();
            paquete.eliminarPaquete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        mensajeFinPrueba("TIENE");

    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registrarTiene method, of class Tiene.
     * Verifica que el relacionar un plan a un paquete se
     * realice de forma correcta
     */
    @Test
    public void testRegistrarTiene() throws Exception {
        System.out.println("Probando registrarTiene de Tiene");
        
        //Agregamos a la base de datos una instancia de tiene
        dummyAgregar = insertTiene(Date.valueOf("2013-2-1"), 
                Date.valueOf("2013-2-15"), 150, plan, paquete);
        
        //Recuperamos esta instancia de la base de datos
        Tiene result = Tiene.consultarTiene(plan.nombre, paquete.nombre, 
                        plan.tipoPlan, dummy.fechaInicio);
        
        //Eliminamos dicha instancia de la base de datos
        dummyAgregar.eliminarTiene();
        
        //Verificamos que si fue agregada correctamente
        assertNotNull(result);
    }

    /**
     * Test of consultarTiene method, of class Tiene.
     * Verifica que la funcion consultar funcione correctamente
     */
    @Test
    public void testConsultarTiene() throws Exception {
        System.out.println("Probando consultarTiene de Tiene");
        
        String nomPlan = plan.nombre;
        String nomPack = paquete.nombre;
        String tipoPlan = plan.tipoPlan;
        Date fechaI = dummy.fechaInicio;
        
        //Recuperamos la instancia de la base de datos
        Tiene result = Tiene.consultarTiene(nomPlan, nomPack, tipoPlan, fechaI);
        
        //Realizamos la verificacion
        boolean expResult = (nomPlan.equals(result.plan.nombre)) &
                            (nomPack.equals(result.paquete.nombre)) &
                            (result.fechaInicio.equals(dummy.fechaInicio)) &
                            (result.fechaFin.equals(dummy.fechaFin)) &
                            (result.costo == dummy.costo);
        
        assertTrue(expResult);

    }

    /**
     * Test of eliminarTiene method, of class Tiene.
     * Verifica que el eliminar funcione correctamente
     */
    @Test
    public void testEliminarTiene() throws Exception {
        System.out.println("Probando eliminarTiene de Tiene");
        
        //Agregamos una instancia a ser eliminada
        dummyEliminar = insertTiene(Date.valueOf("2013-3-1"), 
                Date.valueOf("2013-3-15"), 250, plan, paquete);
        
        //Eliminamos de la base de datos dicha instancia
        dummyEliminar.eliminarTiene();
        
        //Intentamos recuperar dicha instancia de la base de datos
        String nomPlan = plan.nombre;
        String nomPack = paquete.nombre;
        String tipoPlan = plan.tipoPlan;
        Date fechaI = dummyEliminar.fechaInicio;
        Tiene result = Tiene.consultarTiene(nomPlan, nomPack, tipoPlan, fechaI);
        
        //Verificamos que dicha instancia no existe, es decir
        //la consulta debe retornar null
        assertNull(result);
    }

    /**
     * Test of modificarTiene method, of class Tiene.
     */
    @Test
    public void testModificarTiene() throws Exception {
        System.out.println("Probando modificarTiene de Tiene");
        
        //Modificamos unos campos del dummy
        dummy.costo = 350;
        
        //Ejecutamos la modificacion
        dummy.modificarTiene();
        
        //Recuperamos dicha instancia de la base de datos
        String nomPlan = plan.nombre;
        String nomPack = paquete.nombre;
        String tipoPlan = plan.tipoPlan;
        Date fechaI = dummy.fechaInicio;
        Tiene result = Tiene.consultarTiene(nomPlan, nomPack, tipoPlan, fechaI);
        
        //Verificamos que los valores coincidan
        boolean expResult = (nomPlan.equals(result.plan.nombre)) &
                            (nomPack.equals(result.paquete.nombre)) &
                            (result.fechaInicio.equals(dummy.fechaInicio)) &
                            (result.fechaFin.equals(dummy.fechaFin)) &
                            (result.costo == dummy.costo);
        
        assertTrue(expResult);
    }

    /**
     * Test of toString method, of class Tiene.
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de Tiene");
        String res = "Nombre del Plan: " + plan.nombre + ", Tipo del plan: " +
                plan.tipoPlan + ", Nombre del paquete: " + paquete.nombre + 
                ", Costo: " + dummy.costo + ", Fecha de inicio: " + 
                dummy.fechaInicio.toString();
        
        //Si fecha fin no es null se concatena su informacion
        if (dummy.fechaFin != null)
            res = res + ", Fecha fin: " + dummy.fechaFin.toString();
        
        assertEquals(res, dummy.toString());
    }
}