package excepciones;

/**
 * Excepción lanzada por las clases que implementan ITree
 */
public class TreeException extends RuntimeException {

    /** Constructor por omisión */
    public TreeException() {
        super();
    }
    
    /**
     * Constructor con mensaje de la excepción
     * 
     * @param msj Mensaje de la excepción
     */
    public TreeException(String msj) {
        super(msj);
    }
}