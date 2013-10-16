

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


@Controller
public class Controlador {

    protected final Log logger = LogFactory.getLog(getClass());
    
    
    @RequestMapping(value="/hello.htm")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning hello view");
        return new ModelAndView("hello");
    }
    
    @RequestMapping(value="/success.htm")
    public ModelAndView handleRequestSuccess(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning success view");
        return new ModelAndView("success");
    }    
    
    @RequestMapping(value="/successModificar.htm")
    public ModelAndView handleRequestSuccessModificar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning successModificar view");
        return new ModelAndView("successModificar");
    }   
    
    
    @RequestMapping(value="/consultarFacturasError.htm")
    public ModelAndView handleRequestErrorFacturas(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        logger.info("Returning errorFacturas view");
        return new ModelAndView("consultarFacturasError");
    }   
    
     
}
