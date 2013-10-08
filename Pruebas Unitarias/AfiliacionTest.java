/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mjose
 */
public class AfiliacionTest {
    
    static Modelo M1;
    
    //Se agregan las instancias necesarias para poder probar
    static Afiliacion dummy;
    static Afiliacion dummyNull; //dummy con fechafin = null
    static Afiliacion dummyAgregar;
    static Afiliacion dummyEliminar;
    
    //Tambien es necesario agregar instancias de Producto y Servicio
    static Producto P1;
    static Producto P2;
    static Producto P3;
    static Plan L1;
    
    //Constraint de cliente necesario para Producto
    static Cliente C1;
    
    
    public AfiliacionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException, ParseException {
        
        M1 = new Modelo("ModeloPrueba");
        M1.registrarModelo();
        
        //Constraint de Cliente para Producto
        ArrayList<Long> telfC1 = new ArrayList<Long>();
        Long num = new Long(123);
        telfC1.add(num);
        C1 = new Cliente(123,"ClientePrueba","DirPrueba",telfC1);
        C1.registrarCliente();
        
        //Se le dan valores a las instancias de Producto y Plan. Se agregan
        P1 = new Producto(1,"ModeloPrueba",C1);
        P2 = new Producto(2,"ModeloPrueba",C1);
        P3 = new Producto(3,"ModeloPrueba",C1);
        P1.registrarProducto();
        P2.registrarProducto();        
        P3.registrarProducto();
        
        L1 = new Plan("PlanPrueba","DescripcionPrueba","POSTPAGO");
        L1.registrarPlan();
        
        //Se le dan valores a las instancias de Afiliacion. Se agregan
        String sInic = "1993-03-13";
        String sFin = "2013-03-13";
        String sInic2 = "1990-03-13";
        String sFin2 = "1992-03-13";
        String sInic3 = "1980-03-13";
        String sFin3 = "1984-03-13";
        String sInic4 = "1880-03-13";
        String sFin4 = "1884-03-13";
        
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sInic);
        Date inic = new java.sql.Date(utilDate.getTime());
        
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sFin);
        Date fin = new java.sql.Date(utilDate.getTime());
        
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sInic2);
        Date inic2 = new java.sql.Date(utilDate.getTime());
        
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sFin2);
        Date fin2 = new java.sql.Date(utilDate.getTime());
        
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sInic3);
        Date inic3 = new java.sql.Date(utilDate.getTime());
        
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sFin3);
        Date fin3 = new java.sql.Date(utilDate.getTime());
        
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sInic4);
        Date inic4 = new java.sql.Date(utilDate.getTime());
        
        utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(sFin4);
        Date fin4 = new java.sql.Date(utilDate.getTime());
        
        dummy = new Afiliacion(inic,fin,L1,P1);
        dummyNull = new Afiliacion(inic2,null,L1,P2);
        dummyAgregar = new Afiliacion(inic3,fin3,L1,P3);
        dummyEliminar = new Afiliacion(inic4,fin4,L1,P3);
        
        dummy.registrarAfiliacion();
        dummyNull.registrarAfiliacion();
        
        //Las otras afiliaciones se agregan en sus pruebas
        
        System.out.println(" --- INICIANDO PRUEBAS DE AFILIACION.JAVA --- ");
    }
    
    @AfterClass
    public static void tearDownClass() throws SQLException {
        //Se eliminan todas las instancias agregadas 
        dummy.eliminarAfiliacion();
        dummyNull.eliminarAfiliacion();
        C1.eliminarCliente();
        P1.eliminarProducto();
        P2.eliminarProducto();
        P3.eliminarProducto();
        L1.eliminarPlan();
        M1.eliminarModelo();
        
        System.out.println(" --- FINALIZANDO PRUEBAS DE AFILIACION.JAVA --- ");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Afiliacion.
     */
    @Test
    public void testToString() {
        System.out.println("Probando toString de Afiliacion");
        
        //Test para afiliacion con fechafin definida
        String expResult1 = "Fecha de Inicio: " + dummy.fechaInicio.toString() +
                           ", Fecha de Fin: " + dummy.fechaFin.toString() +
                           ", Producto: [ " + dummy.producto.toString() +
                           "], Plan: [" + dummy.plan.toString() + "]";
        String result1 = dummy.toString();
        
        //Test para afiliacion con fechafin indefinida
        String expResult2 = "Fecha de Inicio: " + dummyNull.fechaInicio.toString() +
                           ", Producto: [ " + dummyNull.producto.toString() +
                           "], Plan: [" + dummyNull.plan.toString() + "]";
        String result2 = dummyNull.toString();
        
        boolean success = expResult1.equals(result1) && expResult2.equals(result2);
        
        assertTrue(success);
    }

    /**
     * Test of registrarAfiliacion method, of class Afiliacion.
     */
    @Test
    public void testRegistrarAfiliacion() throws ParseException {
        System.out.println("Probando registrarAfiliacion de Afiliacion");
        
        //Se agrega la afiliacion
        dummyAgregar.registrarAfiliacion();
        
        //Se consulta a ver si fue agregada
        Afiliacion result = Afiliacion.consultarAfiliacion(3,"PlanPrueba",
                                                           "POSTPAGO",
                                                            dummyAgregar.fechaInicio);
        
        //Probar si estaba o no
        assertNotNull(result);
        
        //Eliminar afiliacion agregada
        dummyAgregar.eliminarAfiliacion();
    }

    /**
     * Test of consultarAfiliacion method, of class Afiliacion.
     */
    @Test
    public void testConsultarAfiliacion() throws Exception {
        System.out.println("Probando consultarAfiliacion de Afiliacion");
        
        //Consultamos la afiliacion a verificar
        Afiliacion result = Afiliacion.consultarAfiliacion(1,
                                                           dummy.plan.nombre, 
                                                           dummy.plan.tipoPlan, 
                                                           dummy.fechaInicio);
       
        
        //Verificamos si son iguales
        boolean success;
        success = dummy.producto.codigoProd == result.producto.codigoProd && 
                  dummy.plan.nombre.equals(result.plan.nombre) &&
                  dummy.fechaInicio.equals(result.fechaInicio) && 
                  dummy.plan.tipoPlan.equals(result.plan.tipoPlan);
        
        assertTrue(success);
    }
      

    /**
     * Test of eliminarAfiliacion method, of class Afiliacion.
     */
    @Test
    public void testEliminarAfiliacion() throws ParseException {
        System.out.println("Probando eliminarAfiliacion de Afiliacion");
        
        //Se agrega una instancia para luego eliminarla
        dummyEliminar.registrarAfiliacion();
        dummyEliminar.eliminarAfiliacion();
        
        //Se verifica que no este en la base de datos con una consulta
        Afiliacion result = Afiliacion.consultarAfiliacion(dummyEliminar.producto.codigoProd, 
                                                           dummyEliminar.plan.nombre, 
                                                           dummyEliminar.plan.tipoPlan, 
                                                           dummyEliminar.fechaInicio);
       assertNull(result);
       
    }   

    /**
     * Test of modificarAfiliacion method, of class Afiliacion.
     */
    @Test
    public void testModificarAfiliacion() throws ParseException {
        try {
            System.out.println("Probando modificarAfiliacion de Afiliacion");
        
        
        //Se modifica dummy antes de modificarlo en la base de datos
        dummy.fechaFin = null;
        dummy.modificarAfiliacion();
        
        //Se extrae la Afiliacion modificada
        Afiliacion result = Afiliacion.consultarAfiliacion(1, 
                                                           dummy.plan.nombre,
                                                           dummy.plan.tipoPlan, 
                                                           dummy.fechaInicio);
        
        //Se verifica si son iguales
        boolean success;
        success = dummy.producto.codigoProd == result.producto.codigoProd && 
                  dummy.plan.nombre.equals(result.plan.nombre) &&
                  dummy.fechaInicio.equals(result.fechaInicio) && 
                  dummy.plan.tipoPlan.equals(result.plan.tipoPlan) && 
                  dummy.fechaFin == result.fechaFin;
        
        assertTrue(success);      
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}