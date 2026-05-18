package arboles;

import java.util.Iterator;
import java.util.NoSuchElementException;
import listas.ArrayList;
import listas.LinkedListStack;

/**
 *
 * @author Andre
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> extends BinaryTreeComun<T> {
    
    /**
    * Esta clase miembro implementa un iterador para recorrer
    * un arbol binario en orden
    *
    * @param <T> Parámetro de tipo para los objetos a almacenarse
    * en el arbol
    */
    class InorderIterator implements Iterator<T>{
        private LinkedListStack<NodoArbolBinario<T>> pilaNodos;
        private NodoArbolBinario<T> nodoActual;
        
        /** Inicializa los atributos de la clase */
        public InorderIterator() {
            pilaNodos = new LinkedListStack<>();
            nodoActual = raiz;
        }

        @Override
        public boolean hasNext() {
            return !pilaNodos.empty() || (nodoActual != null);
        }

        @Override
        public T next() {
            NodoArbolBinario<T> nodoSig = null;
            while(nodoActual != null) {
                pilaNodos.push(nodoActual);
                nodoActual = nodoActual.getHijoIzq();
            }
            if(!pilaNodos.empty()) {
                nodoSig = pilaNodos.pop();
                nodoActual = nodoSig.getHijoDer();
            }
            else throw new NoSuchElementException();
            return nodoSig.getDato();
        }
    }
    
    /**
    * Obtiene un iterador para recorrer el arbol en orden
    * @return Un iterador para recorrer el arbol en orden
    */
    public Iterator<T> getInorderIterator() {
        return new BinarySearchTree.InorderIterator();
    }
    
    /**
    * Crea un arbol binario de busqueda vacio.
    */
    public BinarySearchTree() {
        raiz = null;
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
            return new NodoArbolBinario(dato);
        }
        if(dato.compareTo(nodo.getDato()) < 0) {
            nodo.setHijoIzq(insert(nodo.getHijoIzq(), dato));
        }
        else {
            nodo.setHijoDer(insert(nodo.getHijoDer(), dato));
        }
        return nodo;
    }
    
    /**
    * Driver para el metodo recursivo para eliminar un nodo
    * del arbol
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
            return null;
        }
        if (nodo.getHijoIzq() == null) {
            return nodo.getHijoDer();
        }
        if (nodo.getHijoDer() == null) {
            return nodo.getHijoIzq();
        }
        NodoArbolBinario<T> nodoMenor = findSmallestNode(nodo.getHijoDer());
        nodo.setDato(nodoMenor.getDato());
        nodo.setHijoDer(remove(nodo.getHijoDer(), nodoMenor.getDato()));
    } else if (comparacion < 0) {
        nodo.setHijoIzq(remove(nodo.getHijoIzq(), dato));
    } else {
        nodo.setHijoDer(remove(nodo.getHijoDer(), dato));
    }
        return nodo;
    }

    
    /**
    * Obtiene el nodo descendiente del nodo del parametro con el
    * dato mas pequeño. Es el nodo mas a la izquierda del nodo del
    * parametro
    * @param nodo Nodo del que se va buscar el nodo con el dato
    * mas pequeño
    * @return El nodo con el dato mas pequeño que el dato del
    * nodo del parametro.
    */
    protected NodoArbolBinario<T> findSmallestNode(NodoArbolBinario<T> nodo) {
        return nodo.getHijoIzq() == null? nodo:findSmallestNode(nodo.getHijoIzq());
    }
    
   /**
    * Regresa una cadena con los datos de los nodos del arbol
    * recorriendolo en orden
    *
    * @return Una cadena con los datos de los nodos del arbol
    * recorriendolo en orden
    */
    public String toStringInOrder() {
        Iterator<T> iter = getInorderIterator();
        String s = "[";
        if(iter.hasNext()) {
        s += iter.next();
        while(iter.hasNext())
        s += ", " +iter.next();
        }
        s += "]";
        return s;
    }
    
    /**
    * Devuelve una lista con todos los elementos del árbol ordenados en orden
    * @return Una lista de tipo List<T> con los elementos del árbol
    */
   public ArrayList<T> toList() {
        ArrayList<T> lista = new ArrayList<>(100000);
        Iterator<T> iter = getInorderIterator();
        while (iter.hasNext()) {
            lista.append(iter.next());
        }
        return lista;
   }

    
    
    
    //-----MÉTODOS PROPIOS DE LA ASIGNACION 12----//
    
    
    protected NodoArbolBinario<T> findBiggestNode(NodoArbolBinario<T> nodo) {
        return nodo.getHijoDer() == null ? nodo : findBiggestNode(nodo.getHijoDer());
    }
    
    public T firstEntry() {
    if (raiz == null) {
        return null;
    }
        return findSmallestNode(raiz).getDato();
    }

    public T lastEntry() {
        if (raiz == null) {
            return null;
        }
        return findBiggestNode(raiz).getDato();
    }

    public T get(T dato) {
        return get(raiz, dato);
    }

    private T get(NodoArbolBinario<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }

        int comparacion = ((Comparable<T>) dato).compareTo(nodo.getDato());

        if (comparacion == 0) {
            return nodo.getDato();
        } else if (comparacion < 0) {
            return get(nodo.getHijoIzq(), dato);
        } else {
            return get(nodo.getHijoDer(), dato);
        }
    }
}