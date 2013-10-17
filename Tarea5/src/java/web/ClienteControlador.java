
package web;

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
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;

import domain.*;
import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;


@Controller
//@RequestMapping(value="/cliente")
public class ClienteControlador {

    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(value= "/registrarCliente", method = RequestMethod.GET)
    public String getRegistrationForm(Model model){
        logger.info("Crear objeto del cliente");
        ArrayList<Long> list = new ArrayList();
        Cliente cliente = new Cliente();
        cliente.telefonos = list;
        model.addAttribute("cliente", cliente);
        return "registrarCliente";
        
    }
    
    @RequestMapping (value = "/registrarCliente.htm", method = RequestMethod.POST)
    public String postRegistrationForm(@Valid Cliente cliente, BindingResult result) {
      logger.info("Se registra el cliente");
      
      if (result.hasErrors()) {
          return "registrarCliente";
      } else {
          
        cliente.registrarCliente();
        return "success";
      }
    }
    
    @RequestMapping (value = "/consultarCliente.htm", method = RequestMethod.GET) 
    public ModelAndView consultarCliente(Model model) throws SQLException {
        
        logger.info("Consultar clientes");    
        
        model.addAttribute("Clientes", Cliente.listarClientes());       

        ArrayList<Long> list = new ArrayList();
        Cliente cliente = new Cliente();
        Cliente clienteFactura = new Cliente();
        cliente.telefonos = list;
        model.addAttribute("cliente", cliente);
        model.addAttribute("clienteFactura", clienteFactura);
                
        return new ModelAndView("consultarCliente", "model", model);
  
    }
      
    
    @RequestMapping (value = "/consultarCliente.htm", method = RequestMethod.POST)
    public String modificarClienteForm(@Valid Cliente cliente, BindingResult result) {
      logger.info("Se modifica el cliente");
      
      if (result.hasErrors()) {
          return "consultarCliente";
      } else {
          
        cliente.modificarCliente();
        return "successModificar";
      }
    }


    
    
}
