/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package web;

import domain.Facturador;
import domain.Producto;
import domain.ServicioDisponiblePrepago;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author johndelgado
 */
@Controller
public class ServicioDisponibleControlador {
    protected final Log logger = LogFactory.getLog(getClass());

    @RequestMapping (value = "/servicioDisponiblePrepago", method = RequestMethod.GET)
    public ModelAndView listarCuposDisponiblesPrepago(Model model) throws SQLException {
        
        logger.info("servicioDisponiblePrepago");
        Producto producto = new Producto();
        model.addAttribute("producto",producto);
        return new ModelAndView("servicioDisponiblePrepago", "model", model);       
        
        
    }
    
    @RequestMapping (value = "/servicioDisponiblePrepago", method = RequestMethod.POST)
    public String listarCuposDisponiblesPrepagoPost(Producto producto, BindingResult bd) throws SQLException {
        
        logger.info("listarCuposDisponiblesPrepago");
        return "listarServicioDisponiblePrepago";   
        
    }
    
    @RequestMapping (value = "/listarServicioDisponiblePrepago", method = RequestMethod.POST)
    public ModelAndView CuposDisponiblesPrepago(Producto producto, BindingResult bd) throws SQLException {
        ArrayList<ServicioDisponiblePrepago> serviciosDisponibles = Facturador.listarDisponibilidadDeCupos(producto);
        logger.info("listarServicioDisponiblePrepago");
        return new ModelAndView("listarServicioDisponiblePrepago","serviciosDisponibles",serviciosDisponibles);   
        
    } 
    
}
