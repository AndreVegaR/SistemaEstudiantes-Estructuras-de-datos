package listas;
import excepciones.ListException;

/**
 * IList.java
 * Interfaz para los métodos de una lista
 * @author Andre
 * @param <T> indica clase genérica
 */
public interface IList<T> {
    
    /**
     * Agrega un elemento
     * 
     * @param o Objeto a agregar
     * 
     * @throws ListException 
     */
    public void append(T o) throws ListException;
    
    /**
     * Inserta un elemento en medio
     * 
     * @param o Objeto a agregar
     * @param i Índice donde se va a insertar
     * 
     * @throws ListException 
     */
    public void insert(T o, int i) throws ListException;
    
    /**
     * Extrae el elemento de cierto índice
     * 
     * @param i Índice donde está el elemento
     * @return el objeto extraído
     * 
     * @throws ListException 
     */
    public T get(int i) throws ListException;
    
    /**
     * Reemplaza el elemento en cierta posición por otro
     * 
     * @param o Objeto a reemplazar
     * @param i Índice donde se va a reemplazar
     * 
     * @throws ListException
     */
    public void set(T o, int i) throws ListException;
    
    /**
     * Elimina la primera ocurrencia de un elemento
     * 
     * @param o Objeto a eliminar
     * @return si se pudo eliminar o no
     * 
     * @throws ListException
     */
    public boolean remove (T o) throws ListException;
    
    /**
     * Extrae la primera ocurrencia de un elemento y lo elimina
     * 
     * @param i Índice del objeto
     * @return el objeto extraído
     * 
     * @throws ListException
     */
    public T removeExtraer(int i) throws ListException;
    
    /**
     * Regresa el índice de la primera ocurrencia de un elemento
     * 
     * @param o Objeto a buscar
     * 
     * @return el índice. Si no lo encuentra, regresa -1
     */
    int indexOf(T o);
    
    /**
     * Regresa el número de elementos de la lista
     * 
     * @return la cantidad de elementos añadidos
     */
    public int size();
    
    /** Elimina todos los elementos de una lista */
    public void clear();
    
    /**
     * Determina si la lista está vacía
     * 
     * @return true si está vacía, false de lo contrario
     */
    public boolean empty();
}