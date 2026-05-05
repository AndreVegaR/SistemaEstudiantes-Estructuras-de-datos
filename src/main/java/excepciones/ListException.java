package excepciones;

/**
 * ListException.java
 * Excepción lanzada por las clases que implementan IList
 */
public class ListException extends RuntimeException {

    /** Constructor por omisión */
    public ListException() {
        super();
    }
    
    /**
     * Constructor con mensaje de la excepción
     * 
     * @param msj Mensaje de la excepción
     */
    public ListException(String msj) {
        super(msj);
    }
}