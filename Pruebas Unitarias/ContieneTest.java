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
public class ContieneTest {
    
    //Creamos instancias a utilizar durante las pruebas
    static Paquete paquete;
    static Servicio servicio;
    static TipoServicio tiposervicio;
    static Servicio servicioAgregar;
    static Servicio servicioEliminar;
    static Contiene dummy;
    static Contiene dummyAgregar;
    static Contiene dummyEliminar;
            
    
    public ContieneTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        //Se le otorgan valores especificos a las instancias
        paquete = new Paquete("paquetePrueba", "Descripcion");
        tiposervicio = new TipoServicio("TipoServicio1");
        servicio = new Servicio("servPrueba1", "Descripcion1", "TipoServicio1");
        servicioAgregar = new Servicio("servPrueba2", "Descripcion2", "TipoServicio1");
        servicioEliminar = new Servicio("servPrueba3", "Descripcion3", "TipoServicio1");
        dummy = new Contiene(10, 6 ,paquete, servicio);
        dummyAgregar = new Contiene(2, 3 ,paquete, servicioAgregar);
        dummyEliminar = new Contiene(27, 15 ,paquete, servicioEliminar);
        
        //Agregamos uno de los dummies a la base de datos
        //para probar las funciones que no sean agregar ni eliminar
        try {
            paquete.registrarPaquete();
            tiposervicio.registrarTipoServicio();
            servicio.registrarServicio();
            servicioAgregar.registrarServicio();
            dummy.registrarContiene();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println(" --- INICIANDO PRUEBAS DE CONTIENE.JAVA --- ");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        //Eliminando los dummies restantes
        try {
            dummy.eliminarContiene();
            paquete.eliminarPaquete();
            servicio.eliminarServicio();
            servicioAgregar.eliminarServicio();
            tiposervicio.eliminarTipoServicio();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println(" --- FINALIZANDO PRUEBAS DE CONTIENE.JAVA --- ");
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registrarContiene method, of class Contiene.
     * Verifica que se registren correctamente instancias de la entidad
     * contiene
     */
    @Test
    public void testRegistrarContiene() throws Exception {
        System.out.println("Probando registrarContiene de Contiene");
        
        //Agregamos una relacion de tipo contiene a la base de datos
        dummyAgregar.registrarContiene();
        
        //Recuperamos dicha instancia de la base de datos
        Contiene result = Contiene.consultarContiene(paquete.nombre, 
                                                     servicio.nombre);
        
        //Eliminamos la instancia de la base de datos
        dummyAgregar.eliminarContiene();
        
        //Verificamos que si se agrego anteriormente.
        assertNotNull(result);
        
    }

    /**
     * Test of consultarContiene method, of class Contiene.
     * Verifica que se consulten correctamente instancias
     * de la entidad Contiene
     */
    @Test
    public void testConsultarContiene() throws Exception {
        System.out.println("Probando consultarContiene de Contiene");
        
        //Recuperamos la instancia de la base de datos
        String nomPack = paquete.nombre;
        String nomServ = servicio.nombre;
        Contiene result = Contiene.consultarContiene(nomPack, nomServ);

        //Verificamos que los datos concuerden con lo esperado
        boolean success = (result.cantidad == dummy.cantidad) &
                          (result.costoAdicional == dummy.costoAdicional) &
                          (result.paquete.nombre.equals(paquete.nombre)) &
                          (result.servicio.nombre.equals(servicio.nombre));
        
        assertTrue(success);
        
    }

    /**
     * Test of eliminarContiene method, of class Contiene.
     * Comprueba que se eliminan correctamente de la base de datos
     * una instancia de la entidad Contiene
     */
    @Test
    public void testEliminarContiene() throws Exception {
        System.out.println("Probando eliminarContiene en Contiene");
        
        //Agregamos una instancia para ser eliminada
        servicioEliminar.registrarServicio();
        dummyEliminar.registrarContiene();
        
        //Eliminamos la instancia recien agregada
        dummyEliminar.eliminarContiene();
        
        //Tratamos de recuperar dicha instancia de la base de datos
        Contiene result = Contiene.consultarContiene(paquete.nombre, 
                                                     servicioEliminar.nombre);
        
        servicioEliminar.eliminarServicio();
        //Si el resultado de dicha consulta es null, quiere
        //decir que la instancia se elimino correctamente
        assertNull(result);
    }

    /**
     * Test of modificarContiene method, of class Contiene.
     * Comprueba que se realicen modificaciones de forma correcta
     */
    @Test
    public void testModificarContiene() throws Exception {
        System.out.println("Probando modificarContiene de Contiene");
        
        //Modificamos un campo del dummy
        dummy.cantidad = 777;
        dummy.costoAdicional = 777;
        
        //Ejecutamos la modificacion
        dummy.modificarContiene();
        
        //Verificamos que la modificacion fue efectiva
        //Recuperamos la instancia de la base de datos
        String nomPack = paquete.nombre;
        String nomServ = servicio.nombre;
        Contiene result = Contiene.consultarContiene(nomPack, nomServ);

        //Verificamos que los datos concuerden con lo esperado
        boolean success = (result.cantidad == dummy.cantidad) &
                          (result.costoAdicional == dummy.costoAdicional) &
                          (result.paquete.nombre.equals(paquete.nombre)) &
                          (result.servicio.nombre.equals(servicio.nombre));
        
        assertTrue(success);
    }

    /**
     * Test of toString method, of class Contiene.
     * Comprueba que la funcion toString devuelva el valor esperado
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de Contiene");
        
        String expResult = "Cantidad: " + dummy.cantidad + 
                           ", Costo Adicional: " + dummy.costoAdicional
                           + ", Paquete: [" + paquete.toString() + "], Servicio: [" 
                           + servicio.toString() + "]";
        
        assertEquals(expResult, dummy.toString());
    }
}