package arboles;

import excepciones.TreeException;

/**
 * Establece el contrato de la estructura árbol
 * 
 * @author Andre
 * 
 * @param <T> indica genéricos
 */
public interface ITree<T> {
    
    /**
    * Regresa el dato en la raiz del arbol
    * 
    * @return El dato en la raiz del arbol
    */
    T getRootData () throws TreeException;
    
    /**
    * Regresa la altura del arbol
    * 
    * @return La altura del arbol
    */
    int getHeight();
    
    /**
    * Regresa el numero de nodos del arbol
    * 
    * @return El numero de nodos del arbol
    */
    int getNumberNodes();
    
    /**
    * Determina si un arbol esta vacio
    * 
    * @return true si el arbol esta vacio, false en caso contrario
    */
    boolean empty();
    
    /** Limpia un árbol */
    void clear();
}