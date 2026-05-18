package arboles;

import listas.ArrayList;

/**
 * Clase que representa un arbol binario de busqueda
 * @author Andre
 * @param <T> Tipo de datos a almacenar
 */
public class BinarySearchTree<T extends Comparable<T>> extends BinaryTreeComun<T> {
    
    /**
     * Crea un arbol binario de busqueda vacio
     */
    public BinarySearchTree() {
        raiz = null;
    }
    
    /**
     * Regresa una cadena con los datos de los nodos del arbol en orden
     * @return Una cadena con los datos del arbol
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
            sb.append(nodo.getDato()).append(", ");//agrega dato actual
            toStringInOrder(nodo.getHijoDer(), sb);//recorre hijo derecho
        }
    }
    
    /**
     * Devuelve una lista con todos los elementos del árbol ordenados en orden
     * @return Una lista de tipo ArrayList con los elementos del árbol
     */
    public ArrayList<T> toList() {
        ArrayList<T> lista = new ArrayList<>(100000);
        toList(raiz, lista);
        return lista;
    }

    private void toList(NodoArbolBinario<T> nodo, ArrayList<T> lista) {
        if (nodo != null) {
            toList(nodo.getHijoIzq(), lista);//recorre hijo izquierdo
            lista.append(nodo.getDato());//agrega dato a la lista
            toList(nodo.getHijoDer(), lista);//recorre hijo derecho
        }
    }
    
    /**
     * Driver para el metodo recursivo para agregar un nodo al arbol
     * @param dato Dato del nodo a agregar al arbol
     */
    public void insert(T dato) {
        raiz = insert(raiz, dato);
    }
    
    private NodoArbolBinario<T> insert(NodoArbolBinario<T> nodo, T dato) {
        if(nodo == null) {
            return new NodoArbolBinario(dato);//crea nodo si encuentra posicion vacia
        }
        if(dato.compareTo(nodo.getDato()) < 0) {
            nodo.setHijoIzq(insert(nodo.getHijoIzq(), dato));//inserta en subarbol izquierdo
        }
        else {
            nodo.setHijoDer(insert(nodo.getHijoDer(), dato));//inserta en subarbol derecho
        }
        return nodo;
    }
    
    /**
     * Driver para el metodo recursivo para eliminar un nodo del arbol
     * @param dato Dato a eliminar del arbol
     */
    public void remove(T dato) {
        raiz = remove(raiz, dato);
    }
    
    private NodoArbolBinario<T> remove(NodoArbolBinario<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }
        int comparacion = ((Comparable<T>) dato).compareTo(nodo.getDato());
        if (comparacion == 0) {
            if (nodo.getHijoIzq() == null && nodo.getHijoDer() == null) {
                return null;//elimina nodo hoja
            }
            if (nodo.getHijoIzq() == null) {
                return nodo.getHijoDer();//reemplaza por hijo derecho
            }
            if (nodo.getHijoDer() == null) {
                return nodo.getHijoIzq();//reemplaza por hijo izquierdo
            }
            NodoArbolBinario<T> nodoMenor = findSmallestNode(nodo.getHijoDer());
            nodo.setDato(nodoMenor.getDato());//reemplaza dato por el menor del subarbol derecho
            nodo.setHijoDer(remove(nodo.getHijoDer(), nodoMenor.getDato()));//elimina nodo duplicado
        } else if (comparacion < 0) {
            nodo.setHijoIzq(remove(nodo.getHijoIzq(), dato));//busca en subarbol izquierdo
        } else {
            nodo.setHijoDer(remove(nodo.getHijoDer(), dato));//busca en subarbol derecho
        }
        return nodo;
    }

    /**
     * Obtiene el nodo con el dato mas pequeño
     * @param nodo Nodo desde el que se busca
     * @return El nodo con el dato mas pequeño
     */
    protected NodoArbolBinario<T> findSmallestNode(NodoArbolBinario<T> nodo) {
        return nodo.getHijoIzq() == null? nodo:findSmallestNode(nodo.getHijoIzq());
    }
    
    /**
     * Obtiene el nodo con el dato mas grande
     * @param nodo Nodo desde el que se busca
     * @return El nodo con el dato mas grande
     */
    protected NodoArbolBinario<T> findBiggestNode(NodoArbolBinario<T> nodo) {
        return nodo.getHijoDer() == null ? nodo : findBiggestNode(nodo.getHijoDer());
    }
    
    /**
     * Obtiene el primer elemento del arbol
     * @return El primer elemento del arbol
     */
    public T firstEntry() {
        if (raiz == null) {
            return null;
        }
        return findSmallestNode(raiz).getDato();
    }

    /**
     * Obtiene el ultimo elemento del arbol
     * @return El ultimo elemento del arbol
     */
    public T lastEntry() {
        if (raiz == null) {
            return null;
        }
        return findBiggestNode(raiz).getDato();
    }

    /**
     * Busca un dato especifico en el arbol
     * @param dato Dato a buscar
     * @return El dato encontrado o null
     */
    public T get(T dato) {
        return get(raiz, dato);
    }

    private T get(NodoArbolBinario<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }
        int comparacion = ((Comparable<T>) dato).compareTo(nodo.getDato());
        if (comparacion == 0) {
            return nodo.getDato();//devuelve dato si lo encuentra
        } else if (comparacion < 0) {
            return get(nodo.getHijoIzq(), dato);//busca en subarbol izquierdo
        } else {
            return get(nodo.getHijoDer(), dato);//busca en subarbol derecho
        }
    }
}