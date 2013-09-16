/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
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
public class ServicioAdicionalTest {
    
    //Creamos distintas instancias para probar las funciones
    static ServicioAdicional dummy;
    static ServicioAdicional dummyAgregar;
    static ServicioAdicional dummyEliminar;
    static Servicio pDummy;
    static Servicio pDummyAgregar;
    
    public ServicioAdicionalTest() {
    }
    
@BeforeClass
    public static void setUpClass() throws Exception {
        
        //Otorgamos valores a dichas instancias
        dummy = new ServicioAdicional("ServicioAPrueba1","DescripcionPrueba",
                                       "TipoPrueba",1,2,"PREPAGO");
        
        dummyAgregar = new ServicioAdicional("ServicioAPrueba2","DescripcionPrueba",
                                             "TipoPrueba",1,2,"PREPAGO");
        
        dummyEliminar =new ServicioAdicional("ServicioAPrueba3","DescripcionPrueba",
                                              "TipoPrueba",1,2,"PREPAGO");
        
        pDummy = new Servicio("ServicioAPrueba1","DescripcionPrueba",
                                       "TipoPrueba");
        
        pDummyAgregar = new Servicio("ServicioAPrueba2","DescripcionPrueba",
                                             "TipoPrueba");
        
        
        //Agregamos el dummy para modificar, consultar, etc... Se requiere agregar
        //Un dummy de Servicio para satisfacer los constraints
        pDummy.registrarServicio();
        dummy.registrarServicioAd();
      
        
        //El dummy para probar la funcion de agregar sera agregado en
        //dicha prueba
        
        System.out.println(" --- INICIANDO PRUEBAS DE Servicio.JAVA --- ");
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException, Exception {
        
        //Eliminamos los dummies que faltan
        dummy.eliminarServicioAd();
        pDummy.eliminarServicio();
        
        System.out.println(" --- FINALIZANDO PRUEBAS DE Servicio.JAVA --- ");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registrarServicioAd method, of class ServicioAdicional.
     */
    @Test
    public void testRegistrarServicioAd() throws Exception {
        System.out.println("Probando registrarServicioAd de ServicioAdicional");
       
        //Se agrega el servicio adicional a probar y su constraint
        pDummyAgregar.registrarServicio();
        dummyAgregar.registrarServicioAd();
        
        //Se consulta el a ver si esta en la base de datos
        ServicioAdicional result = ServicioAdicional.consultarServicioAd("ServicioAPrueba1");
        
        //Se verifica si de verdad estaba en la base de datos
        assertNotNull(result);
        
        //Se elimina el servicio adicional previamente agregado y su constraint
        dummyAgregar.eliminarServicioAd();
        pDummyAgregar.eliminarServicio();
    }
     

    /**
     * Test of consultarServicioAd method, of class ServicioAdicional.
     */
    @Test
    public void testConsultarServicioAd() throws SQLException {
        System.out.println("Probando consultarServicioAd de ServicioAdicional");
        
        //Obtenemos el Servicio a verificar
        ServicioAdicional result = ServicioAdicional.consultarServicioAd(dummy.nombre);
        
        //Verificamos si lo obtenido se corresponde con lo esperado
        boolean success;
        success = dummy.nombre.equals(result.nombre) && 
                  dummy.tipoServicio.equals(result.tipoServicio) &&
                  dummy.descripcion.equals(result.descripcion) && 
                  dummy.tarifa == result.tarifa &&
                  dummy.cantidadAdicional == result.cantidadAdicional;
                
        assertTrue(success);
    }

    /**
     * Test of eliminarServicioAd method, of class ServicioAdicional.
     */
    @Test
    public void testEliminarServicioAd() throws SQLException {
        System.out.println("Probando eliminarServicioAd de ServicioAdicional");
        
        //Agregamos una instancia para eliminarla
        dummyEliminar.registrarServicio();
        
        //Eliminamos la instancia agregada
        dummyEliminar.eliminarServicio();
        
        //Tratamos de extraer el Servicio que fue eliminado
        ServicioAdicional result = ServicioAdicional.consultarServicioAd("ServicioPrueba3");
        
        //Verificamos si el valor obtenido es null
        assertNull(result);
    }

    /**
     * Test of modificarServicioAd method, of class ServicioAdicional.
     */
    @Test
    public void testModificarServicioAd() throws SQLException {
        
        System.out.println("Probando modificarServicioAd de ServicioAdicional");
        
        //Modificamos el campo de la descripcion
        dummy.cantidadAdicional = 8;
        
        //Se procede a hacer la modificacion
        try {
            dummy.modificarServicioAd();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //Se extrae el Servicio modificado de la base de datos
        ServicioAdicional result = ServicioAdicional.consultarServicioAd("ServicioAPrueba1");
        
        //Se verifica si son iguales o no
        boolean success;
        success = dummy.nombre.equals(result.nombre) && 
                  dummy.tipoServicio.equals(result.tipoServicio) &&
                  dummy.descripcion.equals(result.descripcion) && 
                  dummy.tarifa == result.tarifa &&
                  dummy.cantidadAdicional == result.cantidadAdicional;
                
        assertTrue(success);
        
    }

    /**
     * Test of toString method, of class ServicioAdicional.
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de ServicioAdicional");
        dummy.cantidadAdicional = 2;
        
        //Se especifica el string esperado de la funcion
        String expResult = "Nombre: " + "ServicioAPrueba1" + ", Descripcion: " + 
                "DescripcionPrueba" + "Tipo de Servicio: " + "TipoPrueba" + 
                ", Tarifa: " + 1.0 + ", Cantidad Adicional: " + 2 
                + ", Tipo de plan : " + "PREPAGO";
        
        //Se obtiene el string generado por la funcion
        String result = dummy.toString();
        
        //Se comparan ambos strings
        assertEquals(expResult, result);
    }
}
