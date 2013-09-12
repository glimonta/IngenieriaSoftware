import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.Date;

public class Main {
    
    public  static void main(String[] args) throws Exception {

        
        
        try{
            
            
            Producto.eliminarProducto(21);



            
        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
        }
        
        
        
    } 
}