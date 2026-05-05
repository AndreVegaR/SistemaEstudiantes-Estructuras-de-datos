package listas;
import excepciones.ListException;
import excepciones.StackException;
import pilas.IStack;

/**
 * Implementación de una pila sobre una lista enlzadada
 * En lugar de recorrer la lista enlazada, se toma inicio como el tope de la misma
 * @param <T> indica genérico
 */
public class LinkedListStack<T> extends LinkedList<T> implements IStack<T> {
    
    //Mensajes de excepciones
    private static final String LLENA = "Pila llena";
    private static final String VACIA = "Pila vacía";
    
    /**
     * Maneja el método insert dentro de un try-catch
     * Se inserta en la posición 0, o sea, se agrega en el inicio
     * 
     * @param o Elemento a agregar
     * 
     * @throws StackException 
     */
    @Override
    public void push(T o) throws StackException {
        try {
            insert(o, 0);
        } catch (ListException le) {
            throw new StackException(LLENA);
        }
    }

    /**
     * Maneja el método removeExtraer dentro de un try-catch
     * Utiliza 0 porque es el tope establecido
     * 
     * @return el elemento extraído
     * 
     * @throws StackException 
     */
    @Override
    public T pop() throws StackException {
        try {
            return removeExtraer(0);
        } catch (ListException le) {
            throw new StackException(VACIA);
        }
    }

    /**
     * Maneja el método get dentro de un try-catch
     * Utiliza 0 porque es el tope establecido
     * 
     * @return el elemento consultado
     * 
     * @throws StackException 
     */
    @Override
    public T peek() throws StackException {
        try {
            return get(0);
        } catch (ListException le) {
            throw new StackException(VACIA);
        }
    }
}