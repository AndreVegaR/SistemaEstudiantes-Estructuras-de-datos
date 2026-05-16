package pilas;
import excepciones.ListException;
import excepciones.StackException;
import listas.ArrayList;

/**
 * ArrayListStack.kava
 * Implementación de una pila sobre un arreglo
 * @author Andre
 * @param <T>
 */
public class ArrayListStack<T> extends ArrayList<T> implements IStack<T> {
    
    //Mensajes de excepciones
    private static final String LLENA = "Pila llena";
    private static final String VACIA = "Pila vacía";
    
    /**
     * Constructor
     * @param tamPila 
     */
    public ArrayListStack(int tamPila) {
        super(tamPila);
    }
    
    /**
     * push
     * Maneja el método append dentro de un try-catch
     * @param o Elemento a agregar
     * @throws StackException 
     */
    @Override
    public void push(T o) throws StackException {
        try {
            append(o);
        } catch (ListException le) {
            throw new StackException(LLENA);
        }
    }

    /**
     * pop
     * Maneja el método removeExtraer dentro de un try-catch
     * Utiliza numElementos-1 pues siempre aplica al último elemento
     * @return el elemento extraído
     * @throws StackException 
     */
    @Override
    public T pop() throws StackException {
        try {
            return removeExtraer(numElementos - 1);
        } catch (ListException le) {
            throw new StackException(VACIA);
        }
    }
    
    /**
     * peek
     * Maneja el método get dentro de un try-catch
     * Utiliza numElementos-1 pues siempre aplica al último elemento
     * @return el elemento consultado
     * @throws StackException 
     */
    @Override
    public T peek() throws StackException {
        try {
            return get(numElementos - 1);
        } catch (ListException le) {
            throw new StackException(VACIA);
        }
    }
}