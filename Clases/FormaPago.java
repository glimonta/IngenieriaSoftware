/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabriela
 */
public class FormaPago {
    Integer id;
    
    public FormaPago(Integer id) {
        this.id = id;
    }   
    
    @Override
    public String toString() {
        return this.id.toString();
    }
}
