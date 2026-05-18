package arboles;

/**
 * Esta clase representa la implementación de un árbol binario
 * y proporciona los métodos para su manipulación y recorrido
 * 
 * @param <T> indica genéricos
 */
public class BinaryTree<T> extends BinaryTreeComun<T> {
    
    /**
     * Crea un arbol binario vacio
     */
    public BinaryTree() {
        raiz = null;
    }

    /**
     * Crea un arbo binario con un solo nodo con el dato
     * del nodo raiz dado por el parametro datoRaiz
     *
     * @param datoRaiz Dato a guardar en el nodo raiz
     */
    public BinaryTree(T datoRaiz) {
        raiz = new NodoArbolBinario<>(datoRaiz);
    }

    /**
     * Crea un arbol binario con el dato del nodo raiz y
     * los subarboles izquierdo y derecho dados por los
     * parametros
     *
     * @param datoRaiz Dato a guardar en el nodo raiz
     * @param arbolIzq Subarbol izquierdo
     * @param arbolDer Subarbol derecho
     */
    public BinaryTree(T datoRaiz, BinaryTree<T> arbolIzq, BinaryTree<T> arbolDer) {
        setTree(datoRaiz, arbolIzq, arbolDer);
    }

    /**
     * Establece un arbol binario con el dato del nodo raiz y
     * los subarboles izquierdo y derecho dados por los
     * parametros
     *
     * @param datoRaiz Dato a guardar en el nodo raiz
     * @param arbolIzq Subarbol izquierdo
     * @param arbolDer Subarbol derecho
     */
    private void setTree(T datoRaiz, BinaryTree<T> arbolIzq, BinaryTree<T> arbolDer) {
        raiz = new NodoArbolBinario<>(datoRaiz);
        if ((arbolIzq != null) && !arbolIzq.empty()) {
            raiz.setHijoIzq(copy(arbolIzq.raiz)); 
        }
        if ((arbolDer != null) && !arbolDer.empty()) {
            raiz.setHijoDer(copy(arbolDer.raiz)); 
        }
    }

    /**
     * Regresa un subarbol del nodo del parametro
     *
     * @param nodo Nodo del que se obtendra el subarbol
     * @return Un subarbol del nodo del parametro
     */
    private NodoArbolBinario<T> copy(NodoArbolBinario<T> nodo) {
        if (nodo == null) {
            return null;
        }
        NodoArbolBinario<T> nuevoNodo = new NodoArbolBinario<>(nodo.getDato());
        if (nodo.getHijoIzq() != null) {
            nuevoNodo.setHijoIzq(copy(nodo.getHijoIzq())); 
        }
        if (nodo.getHijoDer() != null) {
            nuevoNodo.setHijoDer(copy(nodo.getHijoDer())); 
        }
        return nuevoNodo;
    }
    
    /**
     * Imprime el árbol en preorden
     * 
     * @return elementos en preorden
     */
    public String toStringPreOrder() {
        StringBuilder sb = new StringBuilder("[");
        toStringPreOrder(raiz, sb);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    private void toStringPreOrder(NodoArbolBinario<T> nodo, StringBuilder sb) {
        if (nodo != null) {
            sb.append(nodo.getDato()).append(", ");//agrega dato actual de la raiz
            toStringPreOrder(nodo.getHijoIzq(), sb);//recorre hijo izquierdo
            toStringPreOrder(nodo.getHijoDer(), sb);//recorre hijo derecho
        }
    }
    
    /**
     * Imprime el árbol en posorden
     * 
     * @return elementos en posorden
     */
    public String toStringPostOrder() {
        StringBuilder sb = new StringBuilder("[");
        toStringPostOrder(raiz, sb);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    private void toStringPostOrder(NodoArbolBinario<T> nodo, StringBuilder sb) {
        if (nodo != null) {
            toStringPostOrder(nodo.getHijoIzq(), sb);//recorre hijo izquierdo
            toStringPostOrder(nodo.getHijoDer(), sb);//recorre hijo derecho
            sb.append(nodo.getDato()).append(", ");//agrega dato actual de la raiz
        }
    }
    
    /**
     * Regresa una cadena con la información de un arbol general
     *
     * @return Una cadena con la información de un arbol general
     */
    public String toStringInOrder() {
        StringBuilder sb = new StringBuilder("[");
        toStringInOrder(raiz, sb);
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    private void toStringInOrder(NodoArbolBinario<T> nodo, StringBuilder sb) {
        if (nodo != null) {
            toStringInOrder(nodo.getHijoIzq(), sb);//recorre hijo izquierdo
            sb.append(nodo.getDato()).append(", ");//agrega dato actual de la raiz
            toStringInOrder(nodo.getHijoDer(), sb);//recorre hijo derecho
        }
    }
}