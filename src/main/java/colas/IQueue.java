package colas;
import excepciones.QueueException;

/**
 * IQueue.java
 * Interfaz para los métodos de una cola
 * @param <T> indica clase genérica
 */
public interface IQueue<T> {
    
    /**
     * Agrega un elemento al final de la cola
     * 
     * @throws QueueException 
     */
    public void enqueue(T o) throws QueueException;
    
    /**
     * Extrae el elemento al inicio de la cola
     * 
     * @return el elemento extraído
     * 
     * @throws QueueException 
     */
    public T dequeue() throws QueueException;
    
    /**
     * Consulta el primer elemento de la cola
     * 
     * @return el elemento consultado
     * 
     * @throws QueueException 
     */
    public T peek() throws QueueException;

    public boolean isEmpty();

    public int size();
}