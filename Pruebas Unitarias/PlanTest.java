/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andreso
 */
public class PlanTest extends Tests{
    
     
    /*
     * Declaracion de constantes
     */
        //Constantes de paquetes
    public static final String NOMBRE_PAQUETE_UNO        = "paquetePrueba1";
    public static final String NOMBRE_PAQUETE_DOS        = "paquetePrueba2";
    public static final String DESCRIPCION_PAQUETE   = "descrPaquetePrueba";
    
        //Constantes de Plan
    public static final String NOMBRE_PLAN           = "planPrueba";
    public static final String TIPO_PLAN             = "PREPAGO";
    public static final String DESCRIPCION_PLAN      = "DescripPlanPrueba";
    
    public static final String NOMBRE_PLAN_SINPAQUETE           = "planPruebaNoPacket";
    public static final String TIPO_PLAN_SINPAQUETE             = "PREPAGO";
    public static final String DESCRIPCION_PLAN_SINPAQUETE      = "DescripPlanNoPacket";
    
        //Constantes para una instancia de tiene
    public static final float TIENE_COSTO_PLAN       = 100;
   
        //Variable que almacena los datos del plan
    public static Plan plan;
    public static Plan planSinPaquete;
    
        //Variable que almacena los datos de un paquete
    public static Paquete paquete_uno;
    public static Paquete paquete_dos;
    
        //Variable que almacena la lista de paquetes de un plan
    public static ArrayList<Paquete> lista_paquetes_plan;
    
        //Variable que almacena los datos de una instancia de tiene
    public static Tiene tiene_uno;
    public static Tiene tiene_dos;
    
        //Variable que almacena la lista de instancias de 'tiene'
        //asociadas a un plan un plan
    public static ArrayList<Tiene> lista_tiene_plan;
    
        //Variables, fechas a utilizar para los paquetes
    public static Date fechaInicio;
    public static Date fechaFin;
    
   //Creamos distintas instancias para probar las funciones
    static Plan dummy;
    static Plan dummyToString;
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
    
        /*Insercion del plan a la base de datos*/
        plan = insertPlan(NOMBRE_PLAN, DESCRIPCION_PLAN,TIPO_PLAN);
        
        planSinPaquete = insertPlan(NOMBRE_PLAN_SINPAQUETE, DESCRIPCION_PLAN_SINPAQUETE
                                     ,TIPO_PLAN_SINPAQUETE);
        
        fechaInicio =  firstDayMonth();
        fechaFin    =  lastDayMonth();
        
        
        /*Se definen valores para paquete y se agrega a la base de datos*/
        paquete_uno = insertPaquete(NOMBRE_PAQUETE_UNO, DESCRIPCION_PAQUETE);
        
        /*Se definen valores para paquete y se agrega a la base de datos*/
        paquete_dos = insertPaquete(NOMBRE_PAQUETE_DOS, DESCRIPCION_PAQUETE);
        
        /*Se relaciona dicho paquete con el plan previamente creado*/
        tiene_uno = insertTiene(fechaInicio,fechaFin, TIENE_COSTO_PLAN, plan, paquete_uno);
       
        
        //El dummy para probar la funcion de agregar sera agregado en
        //dicha prueba, igual para el dummy de eliminar.
        
        mensajeInicioPrueba("PLAN");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
       
        /*Se procede a eliminar todo lo agregado a la base de datos
         * durante la prueba.         
         */
        try {
            dummy.eliminarPlan();
            tiene_uno.eliminarTiene();
            paquete_uno.eliminarPaquete();
            paquete_dos.eliminarPaquete();
            plan.eliminarPlan();
            planSinPaquete.eliminarPlan();
        } catch (Exception e) {
            /* Si hay una excepcion se imprime un mensaje*/
            System.err.println(e.getMessage());
        }
        mensajeFinPrueba("PLAN");
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
        
        dummyToString = new Plan("PlanPrueba1","DescripcionPrueba","PREPAGO");  
        //Se especifica el string esperado de la funcion
        String expResult = "Nombre: PlanPrueba1, Descripcion: "+
                           "DescripcionPrueba, Tipo de Plan: PREPAGO";
        
        //Se obtiene el string generado por la funcion
        String result = dummyToString.toString();
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
     * Se verifica que la lista de paquetes retornada
     * por este metodo retorna en efecto los paquetes
     * esperados
     */
    @Test
    public void testListarPaquetes() {
        System.out.println("Probando ListarPaquetes de Plan");
        ArrayList<Paquete> listaPaquetes = plan.ListarPaquetes();
        
        /*se agregan los paquetes esperados a la lista esperada*/
        lista_paquetes_plan = new ArrayList();
        lista_paquetes_plan.add(paquete_uno);
        
        
        int tamListaEsperada = lista_paquetes_plan.size();
        int tamListaActual   = listaPaquetes.size();
        int i=0;
        boolean expResult = (tamListaEsperada == tamListaActual);
        
        /*Si las listas no poseen el mismo tamanio, se retorna false
         * directamente
         */
        if (expResult) {

            /*Mientras ambas los paquetes actuales sigan siendo
             * iguales y la lista no se haya recorrido en su totalidad
             * se continua el chequeo de paquetes
             */
            while (i<tamListaEsperada && expResult){
                expResult = (expResult && 
                            (lista_paquetes_plan.get(i).equals(listaPaquetes.get(i))));
                i++;
            }
           
        }
        
        assertTrue(expResult);
    }
    
    /**
     * Test of ListarPaquetes method, of class Plan.
     * Se verifica que la lista de paquetes retorne null
     * para un plan que no tiene ningun paquete asociado
     */
    @Test
    public void testListarPaquetesVacia() {
        System.out.println("Probando ListarPaquetes de Plan");
        ArrayList<Paquete> listaPaquetes = planSinPaquete.ListarPaquetes();
        
        boolean expResult = listaPaquetes.isEmpty();
        assertTrue(expResult);
    }

    /**
     * Test of listarTiene method, of class Plan.
     */
    @Test
    public void testListarTiene() {
        System.out.println("Probando ListarTiene de Plan");
        ArrayList<Tiene> listaTiene = plan.listarTiene();
        
        /*se agregan los paquetes esperados a la lista esperada*/
        lista_tiene_plan = new ArrayList();
        lista_tiene_plan.add(tiene_uno);
        
        
        int tamListaEsperada = lista_tiene_plan.size();
        int tamListaActual   = listaTiene.size();
        int i=0;
        boolean expResult = (tamListaEsperada == tamListaActual);
        
        /*Si las listas no poseen el mismo tamanio, se retorna false
         * directamente
         */
        if (expResult) {

            /*Mientras ambas los paquetes actuales sigan siendo
             * iguales y la lista no se haya recorrido en su totalidad
             * se continua el chequeo de paquetes
             */
            while (i<tamListaEsperada && expResult){
                expResult = (expResult && 
                            (lista_tiene_plan.get(i).equals(listaTiene.get(i))));
                i++;
            }
           
        }
        
        assertTrue(expResult);
    }

    
    /**
     * Test of listarTiene method, of class Plan.
     */
    @Test
    public void testListarTieneVacia() {
        
        System.out.println("Probando ListarTiene de Plan");
        ArrayList<Tiene> listaTiene = planSinPaquete.listarTiene();
        boolean expResult = listaTiene.isEmpty();
        assertTrue(expResult);
    }
    
    
    /**
     * Test of obtenerCosto method, of class Plan.
     */
    @Test
    public void testObtenerCosto() {
        System.out.println("Probando obtenerCosto de Plan");
        Double costo = plan.obtenerCosto(fechaInicio);
        boolean expResult = (costo == tiene_uno.costo);
        assertTrue(expResult);
    }

    
    /**
     * Test of obtenerCosto method, of class Plan.
     */
    @Test
    public void testObtenerCostoNull() {
        System.out.println("Probando obtenerCostoNull de Plan");
        
        Double costo = planSinPaquete.obtenerCosto(fechaInicio);
        assertNull(costo);
    }
            
            
            
    
    
    /**
     * Test of equals method, of class Plan.
     * Se verifica que dos planes identicos
     * devuelvan true como resultado de haber 
     * aplicado la funcion equals
     */
    @Test
    public void testEquals() {
        System.out.println("Probando equals de Plan");
        
        /*Obtenemos los datos del plan de referencia de la clase
         * para crear una clase cuya informacion sea identica
         * al primer plan
         */
        String nombrePlan = plan.nombre;
        String descripcionPlan = plan.descripcion;
        String tipoPlan = plan.tipoPlan;
        
        Plan planEquals = new Plan(nombrePlan,descripcionPlan,tipoPlan);
        boolean expResult = plan.equals(planEquals);
        
        assertTrue(expResult);
    }
}