/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 */
public abstract class ServicioAdicionalDecorador extends Facturable{

  public Facturable facturable;
    @Override
    abstract public void obtenerDescripcion();
    @Override
    abstract public void obtenerPrecio();
}
