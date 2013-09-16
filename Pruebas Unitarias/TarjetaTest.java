/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import java.util.ArrayList;
import java.text.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andreso
 */
public class TarjetaTest {
    
    //Creamos distintas instancias para probar las funciones
    static Cliente cliente1;
    static Cliente cliente2;
    static Cliente cliente3;
    static Tarjeta dummy;
    static Tarjeta dummyAgregar;
    static Tarjeta dummyEliminar;
    
    public TarjetaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        ArrayList<Long> telefonos = new ArrayList<Long>();
        telefonos.add(Long.valueOf("3713947"));
        cliente1     = new Cliente(999599999,"Nombre1","Direccion1",telefonos);
        cliente2     = new Cliente(999699999,"Nombre2","Direccion2",telefonos);
        cliente3     = new Cliente(999799999,"Nombre3","Direccion3",telefonos);
        dummy        = new Tarjeta(1,"1","Banco1",Date.valueOf("2014-2-8"),"DEBITO",1,cliente1.cedula.toString(),"Marca1");
        dummyAgregar = new Tarjeta(2,"2","Banco2",Date.valueOf("2014-3-8"),"CREDITO",2,cliente2.cedula.toString(),"Marca2");
        dummyEliminar= new Tarjeta(3,"3","Banco3",Date.valueOf("2014-4-8"),"DEBITO",3,cliente3.cedula.toString(),"Marca3");
        
        //Se agrega el cliente
        //Se agrega el dummy que se utilizara en las pruebas que
        //no sean agregar y eliminar
        try {
            
            cliente1.registrarCliente();
            cliente2.registrarCliente();
            cliente3.registrarCliente();
            dummy.RegistrarTarjeta();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
         System.out.println(" --- INICIANDO PRUEBAS DE TARJETA.JAVA --- ");
    }
    
    @AfterClass
    public static void tearDownClass() {
        
        //Eliminamos los dummies restantes
        try {
            dummy.eliminarTarjeta();
            cliente1.eliminarCliente();
            cliente2.eliminarCliente();
            cliente3.eliminarCliente();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(" --- FINALIZANDO PRUEBAS DE TARJETA.JAVA --- ");
        
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Tarjeta.
     */
    @Test
    public void testToString() {
        
        System.out.println("Probando toString de Tarjeta");
        
        //Se especifica el string esperado de la funcion
        String expResult =  "ID de Pago: " + dummy.id + ", Numero de Tarjeta: "
                + dummy.numero + ", Banco: " + dummy.banco
                + ", Fecha de Vencimiento: " + dummy.fechaVenc.toString()
                + ", Tipo de Tarjeta: " + dummy.tipoTarjeta
                + ", Codigo de Seguridad: " + dummy.codSeguridad.toString()
                + ", Cedula del Titular: " + dummy.cedulaTitular
                + ", Marca: " + dummy.marca;
        
        //Se obtiene el string generado por la funcion
        String result = dummy.toString();
        
        //Se comparan ambos strings
        assertEquals(expResult, result);
        
    }

    /**
     * Test of RegistrarTarjeta method, of class Tarjeta.
     * Se verifica que el registro de la tarjeta es correcto
     */
    @Test
    public void testRegistrarTarjeta() throws ParseException {
        
        System.out.println("Probando registrarTarjeta de Tarjeta");
        
        //Agregamos a la base de datos una instancia de posee
        dummyAgregar.RegistrarTarjeta();
        
        //Recuperamos esta instancia dhe la base de datos
        Tarjeta result = Tarjeta.consultarTarjeta(dummyAgregar.numero);
                
        //Eliminamos dicha instancia de la base de datos
        dummyAgregar.eliminarTarjeta();
        
        //Verificamos que en efecto fue agregada correctamente
        assertNotNull(result);
    }

    /**
     * Test of consultarTarjeta method, of class Tarjeta.
     */
    @Test
    public void testConsultarTarjeta() throws Exception {
       
       System.out.println("Probando consultarTarjeta de Tarjeta");
        
       String numTarjeta     = dummy.numero;
       String bancoTarjeta   = dummy.banco;
       Date fechaVTarjeta    = dummy.fechaVenc;
       String tipoTarjeta    = dummy.tipoTarjeta; 
       Integer codSegTarjeta = dummy.codSeguridad;
       String cedTitTarjeta  = dummy.cedulaTitular;
       String marcaTarjeta   = dummy.marca; 
       
       
       //Recuperamos la instancia de la base de datos
       Tarjeta result = Tarjeta.consultarTarjeta(dummy.numero);
        
        
       //Realizamos la verificacion
       boolean expResult = ((numTarjeta.equals(result.numero)) &
                            (bancoTarjeta.equals(result.banco))  &
                            (fechaVTarjeta.equals(result.fechaVenc))  &
                             (tipoTarjeta.equals(result.tipoTarjeta)) &
                             (codSegTarjeta.equals(result.codSeguridad)) &
                             (cedTitTarjeta.equals(result.cedulaTitular)) &
                              (marcaTarjeta.equals(result.marca)));
        
       assertTrue(expResult);
    }

    /**
     * Test of modificarTarjeta method, of class Tarjeta.
     * Verfica que la modificacion de la tarjeta sea correcta.
     */
    @Test
    public void testModificarTarjeta() throws ParseException {
        
        System.out.println("Probando modificarTiene de Tarjeta");
        
        //Modificamos unos campos del dummy
        dummy.banco = "NuevoBanco1";
        
        //Ejecutamos la modificacion
        dummy.modificarTarjeta();
        
        //Recuperamos la instancia de la base de datos
        Tarjeta result = Tarjeta.consultarTarjeta(dummy.numero);
        
        String numTarjeta     = dummy.numero;
        String bancoTarjeta   = dummy.banco;
        Date fechaVTarjeta    = dummy.fechaVenc;
        String tipoTarjeta    = dummy.tipoTarjeta; 
        Integer codSegTarjeta = dummy.codSeguridad;
        String cedTitTarjeta  = dummy.cedulaTitular;
        String marcaTarjeta   = dummy.marca; 
        
        //Verificamos que los valores coincidan
        boolean expResult = ((numTarjeta.equals(result.numero)) &
                            (bancoTarjeta.equals(result.banco))  &
                            (fechaVTarjeta.equals(result.fechaVenc))  &
                             (tipoTarjeta.equals(result.tipoTarjeta)) &
                             (codSegTarjeta.equals(result.codSeguridad)) &
                             (cedTitTarjeta.equals(result.cedulaTitular)) &
                              (marcaTarjeta.equals(result.marca)));
        
       assertTrue(expResult);
        
        assertTrue(expResult);
        
    }

    /**
     * Test of eliminarTarjeta method, of class Tarjeta.
     * Verificacion de la correcta eliminacion de una tarjeta
     */
    @Test
    public void testEliminarTarjeta() throws Exception {
        
        System.out.println("Probando eliminarTarjeta de Tarjeta");
        
        //Agregamos una instancia a ser eliminada
        dummyEliminar.RegistrarTarjeta();
        
        //Eliminamos de la base de datos dicha instancia
        dummyEliminar.eliminarTarjeta();
        
        //Intentamos recuperar dicha instancia de la base de datos
        Tarjeta result = Tarjeta.consultarTarjeta(dummyEliminar.numero);
        
        //Verificamos que dicha instancia no existe, es decir
        //la consulta debe retornar null
        assertNull(result);
        
        
        
    }
}