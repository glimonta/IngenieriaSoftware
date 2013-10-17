package domain;

/**
 * Clase para representar el formulario del servicio adicional
 */
public class FormularioServicioAdicional {
    int codigo;        //Codigo del servicio adicional
    String nombreProd; //Nombre del producto

    /**
     * Constructor vacio
     */
    public FormularioServicioAdicional() {
    }

    /**
     * Retorna el codigo del servicio adicional
     * @return codigo del servicio adicional
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Asigna el codigo del servicio adicional
     * @param codigo codigo del servicio adicional
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna el nombre del producto
     * @return nombre del producto
     */
    public String getNombreProd() {
        return nombreProd;
    }

    /**
     * Asigna el nombre del producto
     * @param nombreProd nombre del producto
     */
    public void setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
    }
    

       
}
