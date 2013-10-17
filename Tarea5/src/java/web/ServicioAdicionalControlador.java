/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import java.sql.Date;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import domain.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author johndelgado
 */

@Controller
public class ServicioAdicionalControlador {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(value= "/registrarServicioAdicional", method = RequestMethod.GET)
    public String getRegistrationForm(Model model){
        logger.info("Crear objeto de posee");
        FormularioServicioAdicional servicioAdicional = new FormularioServicioAdicional();
        model.addAttribute("servicioAdicional", servicioAdicional);
        return "registrarServicioAdicional";
        
    }
    
    
  @RequestMapping (value = "/registrarServicioAdicional", method = RequestMethod.POST)
    public String getAfiliacionForm(FormularioServicioAdicional servicioAdicional , BindingResult result) throws SQLException {
      logger.info("Se ha resgistrado la posesion");
 
        Calendar calendar = new GregorianCalendar();
        Date ahora = new Date(calendar.get(Calendar.YEAR) - 1900,calendar.get(Calendar.MONTH),1);
        ServicioAdicional servicioAdicionalFinal = ServicioAdicional.consultarServicioAd(servicioAdicional.getNombreProd());
        Producto producto = Producto.consultarProducto(servicioAdicional.getCodigo());
        Posee posee = new Posee(ahora, servicioAdicionalFinal, producto);
        posee.registrarPosee();
        
       return "success";
    }
    
    
    
}
