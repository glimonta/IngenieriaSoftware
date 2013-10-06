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
public class PlanTest {
    
    //Creamos distintas instancias para probar las funciones
    static Plan dummy;
    static Plan dummyAgregar;
    static Plan dummyEliminar;
    
    public PlanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        //Otorgamos valores a dichas instancias
        dummy = new Plan("PlanPrueba1","DescripcionPrueba","PREPAGO");
        
        dummyAgregar = new Plan("PlanPrueba2","DescripcionPrueba","PREPAGO");
        
        dummyEliminar = new Plan("PlanPrueba2","DescripcionPrueba","PREPAGO");
        
        //Agregamos el dummy para modificar, consultar, etc...
        dummy.registrarPlan();
        
        //El dummy para probar la funcion de agregar sera agregado en
        //dicha prueba, igual para el dummy de eliminar.
        
        System.out.println(" --- INICIANDO PRUEBAS DE PLAN.JAVA --- ");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        //Eliminamos los dummies que faltan
        dummy.eliminarPlan();
        
        System.out.println(" --- FINALIZANDO PRUEBAS DE PLAN.JAVA --- ");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Plan.
     * Verifica si el toString funciona de la manera esperada,
     * es decir, verifica si imprime como deberia hacerlo.
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de Plan");
        
        //Se especifica el string esperado de la funcion
        String expResult = "Nombre: PlanPrueba1, Descripcion: "+
                           "DescripcionPrueba, Tipo de Plan: PREPAGO";
        
        //Se obtiene el string generado por la funcion
        String result = dummy.toString();
        
        //Se comparan ambos strings
        assertEquals(expResult, result);
    }

    /**
     * Test of modificarPlan method, of class Plan.
     * Verifica si un plan se modifica de la base de datos correctamente
     */
    @Test
    public void testModificarPlan() {
        
        System.out.println("Probando modificarPlan de Plan");
        
        //Agregamos un nuevo plan a la base para ser modificado
        
        //Modificamos el campo de la descripcion
        dummy.descripcion = "Modificado";
        
        //Se procede a hacer la modificacion
        dummy.modificarPlan();
        
        //Se extrae el plan modificado de la base de datos
        Plan result = Plan.consultarPlan("PlanPrueba1", "PREPAGO");
        
        //Se verifica si son iguales o no
        assertEquals(dummy.descripcion, result.descripcion);
        assertEquals(dummy.tipoPlan, result.tipoPlan);
        assertEquals(dummy.nombre, result.nombre);
        
    }

    /**
     * Test of registrarPlan method, of class Plan.
     * Verifica si un plan se agrega correctamente a la base de datos
     */
    @Test   
    public void testRegistrarPlan() {
        
        System.out.println("Probando registrarPlan de Plan");
        
        //Se registra el plan en la base de datos
        dummyAgregar.registrarPlan();
        
        //Se extrae el plan que se acaba de registrar
        Plan result = Plan.consultarPlan("PlanPrueba2","PREPAGO");
        
        //Se verifica que el plan de verdad exista en la base de datos
        assertNotNull(result);
        
        //Se elimina el plan, volviendo al estado inicial de la base de datos
        dummyAgregar.eliminarPlan();
    }

    /**
     * Test of eliminarPlan method, of class Plan.
     * Se verifica si un plan se elimina correctamente de la base de datos
     */
    @Test
    public void testEliminarPlan() {
        System.out.println("Probando eliminarPlan de Plan");
        
        //Agregamos una instancia para eliminarla
        dummyEliminar.registrarPlan();
        
        //Eliminamos la instancia agregada
        dummyEliminar.eliminarPlan();
        
        //Tratamos de extraer el plan que fue eliminado
        Plan result = Plan.consultarPlan("PlanPrueba3", "PREPAGO");
        
        //Verificamos si el valor obtenido es null
        //Si es null, significa que si fue eliminado.
        assertNull(result);
    }

    /**
     * Test of consultarPlan method, of class Plan.
     * Se verifica si la consulta de un plan funciona correctamente
     * Se hace comparando los valores obtenidos con los esperados
     */
    @Test
    public void testConsultarPlan() {
        System.out.println("Probando consultarPlan de Plan");
        
        //Obtenemos el plan a verificar
        Plan result = Plan.consultarPlan(dummy.nombre, dummy.tipoPlan);
        
        //Verificamos si lo obtenido se corresponde con lo esperado
        assertEquals(dummy.nombre, result.nombre);
        assertEquals(dummy.tipoPlan, result.tipoPlan);
        assertEquals(dummy.descripcion, result.descripcion);
    }

    /**
     * Test of ListarPaquetes method, of class Plan.
     */
    @Test
    public void testListarPaquetes() {
        //Falta por implementar
        assertTrue(true);
    }
}