package colas;
import excepciones.ListException;
import excepciones.QueueException;
import listas.ArrayList;

/**
 * ArrayListQueue
 * Implementación de una cola sobre un arreglo
 * @author Andre
 * @param <T>
 */
public class ArrayListQueue<T> extends ArrayList<T> implements IQueue<T> {

    //Mensajes de excepciones
    private static final String LLENA = "Cola llena";
    private static final String VACIA = "Cola vacía";
    
    /**
     * Constructor
     * @param tamLista 
     */
    public ArrayListQueue(int tamLista) {
        super(tamLista);
    }

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
     * Utiliza el índice 0 pues siempre aplica al primer elemento
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
     * Utiliza el índice 0 pues siempre aplica al primer elemento
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