package pilas;
import excepciones.StackException;

/**
 * IStack.java
 * Interfaz para los métodos de una pila
 * @author Andre
 */
public interface IStack<T> {
    
    /**
     * Agrega un elemento en el tope de la pila
     * 
     * @param o Objeto a agregar
     * 
     * @throws StackException 
     */
    public void push(T o) throws StackException;
    
    /**
     * Extrae el último elemento de la pista
     * 
     * @return el elemento
     * 
     * @throws StackException
     */
    public T pop() throws StackException;
    
    /**
     * Consulta el elemento al tope de la pila
     * 
     * @return el tope de la pila
     * 
     * @throws StackException 
     */
    public T peek() throws StackException;
}