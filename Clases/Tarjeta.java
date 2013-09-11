
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class Tarjeta extends FormaPago {
    String numero;
    String banco;
    Date fechaVenc;
    String tipoTarjeta;
    Integer codSeguridad;
    String cedulaTitular;
    
    public Tarjeta(String tipoDePago, String numero, String banco,
            Date fechaVenc, String tipoTarjeta, Integer codSeguridad,
            String cedulaTitular) {
        super(tipoDePago);
        this.numero = numero;
        this.banco = banco;
        this.fechaVenc = fechaVenc;
        this.tipoTarjeta = tipoTarjeta;
        this.codSeguridad = codSeguridad;
        this.cedulaTitular = cedulaTitular;
    }
    
    @Override
    public String toString() {
        return "Tipo de Pago: " + this.tipoDePago + ", Numero de Tarjeta: "
                + this.numero + ", Banco: " + this.banco
                + ", Fecha de Vencimiento: " + this.fechaVenc.toString()
                + ", Tipo de Tarjeta: " + this.tipoTarjeta
                + ", Codigo de Seguridad: " + this.codSeguridad.toString()
                + ", Cedula del Titular: " + this.cedulaTitular;
    }
    
    void RegistrarTarjeta() {
        
    }
    
    void ConsultarTarjeta() {
        
    }
}
