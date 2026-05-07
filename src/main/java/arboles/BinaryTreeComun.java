package arboles;

import excepciones.TreeException;

/**
 *
 */
public class BinaryTreeComun<T> implements ITree {
    NodoArbolBinario<T> raiz;
    
    /** Esta clase miembro representa un nodo de un arbol binario */
    class NodoArbolBinario<T> {
        private T dato;
        private NodoArbolBinario<T> hijoIzq;
        private NodoArbolBinario<T> hijoDer;
        public NodoArbolBinario(T dato) {
            this.dato = dato;
        }
        public T getDato() {
            return dato;
        }
        public void setDato(T dato) {
            this.dato = dato;
        }
        public NodoArbolBinario<T> getHijoIzq() {
            return hijoIzq;
        }
        public void setHijoIzq(NodoArbolBinario<T> hijoIzq) {
            this.hijoIzq = hijoIzq;
        }
        public NodoArbolBinario<T> getHijoDer() {
            return hijoDer;
        }
        public void setHijoDer(NodoArbolBinario<T> hijoDer) {
            this.hijoDer = hijoDer;
        }
        public boolean esHoja() {
            return hijoDer == null && hijoIzq == null;
        }
    }
    
    /**
     * Regresa el dato en la raíz del árbol
     * 
     * @return el dato de la raíz
     */
    @Override
    public T getRootData() throws TreeException {
        if (raiz == null) {
            return null;
        }
        return raiz.getDato();
    }

    /**
     * Regresa la altura del árbol
     * 
     * @return la altura del árbol
     */
    @Override
    public int getHeight() {
        return getHeight(raiz);
    }
    
    /**
    * Metodo recursivo que obtiene la altura del arbol
    *
    * @return La altura del arbol
    */
    protected int getHeight(NodoArbolBinario<T> nodo) {
        int height = 0;
        if (nodo != null) {
            height = 1 + Math.max(getHeight(nodo.getHijoIzq()),
            getHeight(nodo.getHijoDer()));
        }
        return height;
    }
    
    /**
    * Driver para el metodo recursivo que obtiene el numero de
    * nodos del arbol
    *
    * @return El numero de nodos del arbol
    */
    @Override
    public int getNumberNodes() {
        return getNumberNodes(raiz);
    }
    
    /**
    * Regresa el numero de nodos del arbol
    * Metodo recursivo que obtiene el numero de nodos del arbol
    *
    * @return El numero de nodos del arbol
    */
    private int getNumberNodes(NodoArbolBinario<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        int numNodosIzq = getNumberNodes(nodo.getHijoIzq());
        int numNodosDer = getNumberNodes(nodo.getHijoDer());
        return 1 + numNodosIzq + numNodosDer;
    }
    
    /**
     * Determina si el árbol está vacío
     * 
     * @return si la raíz es null, false de lo contrario
     */
    @Override
    public boolean empty() {
        return raiz == null;
    }

    /** Vacía el árbol declarando la raíz como null */
    @Override
    public void clear() {
        raiz = null;
    }
}