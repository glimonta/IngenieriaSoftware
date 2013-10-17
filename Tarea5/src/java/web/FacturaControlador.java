/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.text.ParseException;
import java.util.ArrayList;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;


@Controller
public class FacturaControlador {
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping (value = "/consultarFacturas.htm", method = RequestMethod.POST)
    public ModelAndView consultarFacturaForm(Cliente clienteFactura, BindingResult result) throws SQLException {
      logger.info("Se devuelve las facturas del cliente");
 
      
      if (clienteFactura.cedula <= 0) { 
          return new ModelAndView("consultarFacturasError");
      } else {
          
        Cliente cliente = Cliente.consultarCliente(clienteFactura.cedula);
        
        ArrayList<Factura> facturas = Facturador.facturarCliente(cliente);
        return new ModelAndView("consultarFacturas", "facturas", facturas);
      }
    }
    
    
    @RequestMapping (value = "/consultarFacturasActuales.htm", method = RequestMethod.GET)
    public ModelAndView consultarFacturasActuales(Model model) {
        
        ArrayList<Factura> facturas = Facturador.generarFacturasActuales();
        
        model.addAttribute("facturasActuales",facturas);
        
        return new ModelAndView("consultarFacturasActuales", "model", model);       
        
        
    }
    
    
    @RequestMapping (value = "/consultarProductos", method = RequestMethod.GET)
    public ModelAndView consultarProductos(Model model) throws SQLException {
        
        logger.info("Consultar productos");
        
        model.addAttribute("Productos", Producto.listarProductos());
        Producto producto = new Producto();
        model.addAttribute("producto", producto);
        
        return new ModelAndView("consultarProductos", "model", model);       
        
        
    }
    
    @RequestMapping (value = "/consultarFacturaProducto.htm", method = RequestMethod.POST)
    public ModelAndView consultarFacturaProducto(Producto producto, BindingResult result) throws SQLException, ParseException {
        
        logger.info("Consultar Factura Producto");
                
        if (producto.codigoProd < 0) { 
            return new ModelAndView("consultarFacturaProductoError");
        } else {

          Factura factura = Facturador.ultimaFactura(producto);

          return new ModelAndView("consultarFacturaProducto", "factura", factura);
        }
        
    }
}
