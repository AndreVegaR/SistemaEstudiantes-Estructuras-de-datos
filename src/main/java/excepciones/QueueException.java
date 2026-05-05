package excepciones;

/**
 * QueueException.java
 * Excepción para las implementaciones de una cola
 */
public class QueueException extends RuntimeException {
    
    /** Constructor por omisión */
    public QueueException() {
        super();
    }
    
    /**
     * Constructor con mensaje de la excepción
     * 
     * @param msj Mensaje de la excepción
     */
    public QueueException(String msj) {
        super(msj);
    } 
}