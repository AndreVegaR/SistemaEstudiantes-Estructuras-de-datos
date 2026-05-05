package listas;
import excepciones.ListException;

/**
 * IDoubleList.java
 * Interfaz para los métodos de una lista doblemente enlazada
 * Extiende a IList y agrega nuevos métodos
 * @param <T> indica clase genérica
 */
public interface IDoubleList<T> extends IList<T> {
    
    /**
     * Determina el último índice de un objeto
     * 
     * @param o Objeto a buscar
     * @return el índice del objeto
     * 
     * @throws ListException 
     */
    public int lastIndexOf(T o) throws ListException;
    
    /**
     * Remueve el último objeto coincidente
     * 
     * @param o Objeto a eliminar
     * @return si se eliminó o no
     * 
     * @throws ListException 
     */
    public boolean removeLast(T o) throws ListException;
}