

package web;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controlador que permite controlar la pagina
 */
@Controller
public class Controlador {

    protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * Maneja los request 
     */
    @RequestMapping(value="/hello.htm")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning hello view");
        return new ModelAndView("hello");
    }
    
    /**
     * Maneja los request del menu del administrador.
     */
    @RequestMapping(value="/menuAdministrador.htm")
    public ModelAndView handleRequestAdministrador(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning menuAdministrador view");
        return new ModelAndView("menuAdministrador");
    }
    
    /**
     * Maneja los request del menu del cliente.
     */
    @RequestMapping(value="/menuCliente.htm")
    public ModelAndView handleRequestCliente(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning menuCliente view");
        return new ModelAndView("menuCliente");
    }
    
    /**
     * Maneja los request en el caso de exito
     */
    @RequestMapping(value="/success.htm")
    public ModelAndView handleRequestSuccess(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning success view");
        return new ModelAndView("success");
    }    
    
    /**
     * Maneja los request en el caso de un modificar exitoso.
     */
    @RequestMapping(value="/successModificar.htm")
    public ModelAndView handleRequestSuccessModificar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning successModificar view");
        return new ModelAndView("successModificar");
    }   
    
    /**
     * Maneja los requests en caso de un error en las facturas. 
     */
    @RequestMapping(value="/consultarFacturasError.htm")
    public ModelAndView handleRequestErrorFacturas(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning errorFacturas view");
        return new ModelAndView("consultarFacturasError");
    }   
    
     
}
