package excepciones;

/**
 * Excepción lanzada por los controles
 */
public class ControlException extends RuntimeException {

    /** Constructor por omisión */
    public ControlException() {
        super();
    }
    
    /**
     * Constructor con mensaje de la excepción
     * 
     * @param msj Mensaje de la excepción
     */
    public ControlException(String msj) {
        super(msj);
    }
    
    /**
     * Constructor que contempla la info de la excepción
     * 
     * @param excepcion atrapada
     */
    public ControlException(Exception excepcion) {
        super("Error: " + excepcion.getMessage());
    }
}