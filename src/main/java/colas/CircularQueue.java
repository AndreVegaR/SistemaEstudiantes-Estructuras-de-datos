package colas;
import excepciones.QueueException;
import listas.ArrayList;

/**
 * CircularQueue.java
 * Implementación de una cola circular
 * @author Andre
 * @param <T>
 */
public class CircularQueue<T> extends ArrayList<T> implements IQueue<T> {

    //Strings
    private static final String NULO = "El objeto es nulo";
    private static final String LLENA = "La cola está llena";
    private static final String VACIA = "La cola está vacía";
    
    //Atributos
    private int inicio = -1;
    private int fin = -1;
    
    /**
     * Constructor
     * @param tamLista 
     */
    public CircularQueue(int tamLista) {
        super(tamLista);
    }

    
    
    /**
     * enqueue
     * Agrega un elemento en la cola
     * @param o Elemento a agregar
     * @throws QueueException
     */
    @Override
    public void enqueue(T o) throws QueueException {
        //Excepción
        if (o == null) {
            throw new QueueException(NULO);
        }
        if (full()) {
            throw new QueueException(LLENA);
        }
        
        //Si es el primer elemento a agregar, reconfigura los índices
        if (inicio == -1) {
            inicio = 0;
            fin = 0;
        } 
        
        //Si ya tiene elementos
        else {
            fin = (fin + 1)%tamLista;
        }
        
        //Se guarda el elemento en fin
        lista[fin] = o;
        numElementos++;
    }

    
    
    /**
     * dequeue
     * Elimina un elemento de la cola circular
     * @return el elemento removido
     * @throws QueueException 
     */
    @Override
    public T dequeue() throws QueueException {
        //Excepción
        vacia();
        
        T o = get(inicio);
        
        //Si es el único elemento
        if (inicio == fin) {
            inicio = -1;
            fin = -1;
        }
        
        //Si había varios
        else {
            inicio = (inicio + 1) % tamLista;
        }
        numElementos--;
        return o;
    }

    
    
    
    /**
     * peek
     * Consulta el elemento en el tope
     * @return el elemento en el tope
     * @throws QueueException 
     */
    @Override
    public T peek() throws QueueException {
        vacia();
        return get(inicio);
    }
    
    
    
    /**
     * imprimir
     * Imprime los elementos de la lista
     */
    public void imprimir() {
        for (int k = 0; k < numElementos; k++) {
            int i = (inicio + k) % tamLista;
            System.out.println(get(i));
        }
    }
    
    
    
    /**
     * full
     * Determina si la cola está llena
     * @return true si está llena, false de lo contrario
     */
    private boolean full() {
        return (fin + 1)%tamLista == inicio;
    }
    
    
    
    /**
     * vacia
     * Auxiliar que determina si la cola está vacía
     */
    private void vacia() {
        if (inicio == -1) {
            throw new QueueException(VACIA);
        }
    }

    @Override
    public boolean isEmpty() {
        return size() ==0;
    }
    
}
