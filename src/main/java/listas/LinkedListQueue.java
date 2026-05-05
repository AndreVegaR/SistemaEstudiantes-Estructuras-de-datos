package listas;
import excepciones.ListException;
import excepciones.QueueException;
import colas.IQueue;

/**
 * LinkedListQueue
 * Implementación de una cola sobre una lista enlazada
 * @author Andre
 * @param <T>
 */
public class LinkedListQueue<T> extends LinkedList<T> implements IQueue<T> {

    //Mensajes de excepciones
    private static final String LLENA = "Cola llena";
    private static final String VACIA = "Cola vacía";
    
    /**
     * enqueue
     * Maneja el método append dentro de un try-catch
     * @param o Elemento a agregar
     * @throws QueueException 
     */
    @Override
    public void enqueue(T o) throws QueueException {
        try {
            append(o);
        } catch (ListException le) {
            throw new QueueException(LLENA);
        }
    }

    /**
     * dequeue
     * Maneja el método removeExtraer dentro de un try-catch
     * Utiliza 0 porque es el frente de la cola
     * @return el elemento extraído
     * @throws QueueException 
     */
    @Override
    public T dequeue() throws QueueException {
        try {
            return removeExtraer(0);
        } catch (ListException le) {
            throw new QueueException(VACIA);
        }
    }

    /**
     * peek
     * Maneja el método get dentro de un try-catch
     * Utiliza 0 porque es el frente de la cola
     * @return el elemento extraído
     * @throws QueueException 
     */
    @Override
    public T peek() throws QueueException {
        try {
            return get(0);
        } catch (ListException le) {
            throw new QueueException(VACIA);
        }
    }
}