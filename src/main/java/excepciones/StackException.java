package excepciones;

/**
 * StackException.java
 * Excepción para las implementaciones de una lista
 */
public class StackException extends RuntimeException {
    
    /** Constructor por omisión */
    public StackException() {
        super();
    }
    
    /**
     * Constructor con mensaje de la excepción
     * 
     * @param msj Mensaje de la excepción
     */
    public StackException(String msj) {
        super(msj);
    }
}