package arboles;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import listas.LinkedListStack;

/**
 * Esta clase representa la implementación de un árbol binario
 * y proporciona los métodos para su manipulación y recorrido
 * 
 * @param <T> indica genéricos
 */
public class BinaryTree<T> extends BinaryTreeComun<T> implements ITreeIterator<T> {
    
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
     * Obtiene un iterador para recorrer el arbol en orden
     *
     * @return Un iterador para recorrer el arbol en orden
     */
    @Override
    public Iterator<T> getInorderIterator() {
        return new InorderIterator();
    }

    /**
     * Obtiene un iterador para recorrer el arbol en preorden
     *
     * @return Un iterador para recorrer el arbol en preorden
     */
    @Override
    public Iterator<T> getPreorderIterator() {
        return new PreorderIterator();
    }

    /**
     * Obtiene un iterador para recorrer el arbol en postorden
     *
     * @return Un iterador para recorrer el arbol en postorden
     */
    @Override
    public Iterator<T> getPostorderIterator() {
        return new PostorderIterator();
    }
    
    /**
     * Imprime el árbol en preorden
     * 
     * @return elementos en preorden
     */
    public String toStringPreOrder() {
        return plantillaImpresion(this::getPreorderIterator);
    }
    
    /**
     * Imprime el árbol en posorden
     * 
     * @return elementos en posorden
     */
    public String toStringPostOrder() {
        return plantillaImpresion(this::getPostorderIterator);
    }
    
    
    /**
     * Regresa una cadena con la información de un arbol general
     *
     * @return Una cadena con la información de un arbol general
     */
    public String toStringInOrder() {
        return plantillaImpresion(this::getInorderIterator);
    }
    
    /**
     * Centraliza internamnete la forma en que se va
     * a imprimir los nodos del árbol. Como solo se
     * debe cambiar el tipo de iterador, este se pone
     * de parámetro. El resto de métodos funcionan
     * como envoltorios
     * 
     * @param iterador a utilizar
     * 
     * @return elementos del árbol según el orden
     */
    private String plantillaImpresion(Supplier<Iterator<T>> iterador) {
        Iterator<T> iter = iterador.get();
        String s = "[";
        if (iter.hasNext()) {
            s += iter.next();
            while (iter.hasNext()) {
                s += ", " + iter.next();
            }
        }
        s += "]";
        return s;
    }
    
    /**
     * Esta clase miembro implementa un iterador para recorrer
     * un arbol binario en preorden
     *
     * @param <T> Parámetro de tipo para los objetos a almacenarse
     * en el arbol
     */
    class PreorderIterator implements Iterator<T> {
        private LinkedListStack<NodoArbolBinario<T>> pilaNodos;
        public PreorderIterator() {
            pilaNodos = new LinkedListStack<>();
            if (raiz != null) {
                pilaNodos.push(raiz);
            }
        }

        /**
         * Determina si aun hay nodos del arbol binario sin recorrer
         *
         * @return true si aun hay nodos del arbol binario sin
         * recorrer, false en caso contrario
         */
        @Override
        public boolean hasNext() {
            return !pilaNodos.empty();
        }

        /**
         * Regresa el siguiente dato en el recorrido en preorden
         *
         * @return El dato del siguiente nodo
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            //Saca el nodo actual de la pila, que sería la raíz
            NodoArbolBinario<T> nodoActual = pilaNodos.pop();

            //Obtiene los nodos
            NodoArbolBinario<T> hijoDerecho = nodoActual.getHijoDer();
            NodoArbolBinario<T> hijoIzquierdo = nodoActual.getHijoIzq();
            
            //Inserta primero el hijo derecho para que quede abajo en la pila
            if (hijoDerecho != null) {
                pilaNodos.push(hijoDerecho);
            }

            //Inserta el hijo izquierdo para que quede arriba y se procese primero
            if (hijoIzquierdo != null) {
                pilaNodos.push(hijoIzquierdo);
            }

            //Regresa el valor
            return nodoActual.getDato();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    /**
     * Esta clase miembro implementa un iterador para recorrer
     * un arbol binario en orden
     *
     * @param <T> Parámetro de tipo para los objetos a almacenarse
     * en el arbol
     */
    class InorderIterator implements Iterator<T> {

        private LinkedListStack<NodoArbolBinario<T>> pilaNodos;
        private NodoArbolBinario<T> nodoActual;

        /**
         * Inicializa los atributos de la clase
         */
        public InorderIterator() {
            // Pila para almacenar los nodos izquierdos
            pilaNodos = new LinkedListStack<>();
            nodoActual = raiz;
            // Inserta en la pila el subarbol izquierdo de la raiz
            pushSubArbolIzquierdo(nodoActual);
        }

        /**
         * Inserta en la pila pilaNodos los nodos del subarbol
         * izquierdo
         *
         * @param nodoActual Raiz del subarbol izquierdo
         */
        private void pushSubArbolIzquierdo(NodoArbolBinario<T> nodoActual) {
            while (nodoActual != null) {
                pilaNodos.push(nodoActual);
                nodoActual = nodoActual.getHijoIzq();
            }
        }

        /**
         * Determina si aun hay nodos del arbol binario sin recorrer
         *
         * @return true si aun hay nodos del arbol binario sin
         * recorrer, false en caso contrario.
         */
        @Override
        public boolean hasNext() {
            // Si aun hay nodos en la pila
            return !pilaNodos.empty();
        }

        @Override
        public T next() {
            NodoArbolBinario<T> nodoSig = null;
            if (!pilaNodos.empty()) {
                nodoSig = pilaNodos.pop();
                pushSubArbolIzquierdo(nodoSig.getHijoDer());
            } else {
                throw new NoSuchElementException();
            }
            return nodoSig.getDato();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    /**
     * Esta clase miembro implementa un iterador para recorrer
     * un arbol binario en postorden
     *
     * @param <T> Parámetro de tipo para los objetos a almacenarse
     * en el arbol
     */
    class PostorderIterator implements Iterator<T> {

        private LinkedListStack<NodoArbolBinario<T>> pilaNodos;
        private NodoArbolBinario<T> nodoActual;
        private NodoArbolBinario<T> ultimoNodoVisitado;

        /**
         * Inicializa los atributos de la clase y prepara la pila
         */
        public PostorderIterator() {
            pilaNodos = new LinkedListStack<>();
            nodoActual = raiz;
            ultimoNodoVisitado = null;
        }

        /**
         * Determina si aun hay nodos del arbol binario sin recorrer
         *
         * @return true si aun hay nodos del arbol binario sin
         * recorrer, false en caso contrario.
         */
        @Override
        public boolean hasNext() {
            return nodoActual != null || !pilaNodos.empty();
        }

        /**
         * Regresa el siguiente dato en el recorrido en postorden
         *
         * @return El dato del siguiente nodo
         * @throws NoSuchElementException si ya no hay elementos por recorrer
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            //Avanzar por el camino izquierdo metiendo los nodos a la pila
            while (nodoActual != null) {
                pilaNodos.push(nodoActual);
                nodoActual = nodoActual.getHijoIzq();
            }

            while (!pilaNodos.empty()) {
                //Mira el nodo que está en el tope de la pila sin sacarlo aún
                NodoArbolBinario<T> nodoTope = pilaNodos.peek();
                NodoArbolBinario<T> hijoDerecho = nodoTope.getHijoDer();

                //Si tiene hijo derecho y no venimos de visitarlo, nos movemos hacia él
                if (hijoDerecho != null && ultimoNodoVisitado != hijoDerecho) {
                    nodoActual = hijoDerecho;
                    
                    //Metemos el hijo izquierdo de este nuevo subárbol a la pila
                    while (nodoActual != null) {
                        pilaNodos.push(nodoActual);
                        nodoActual = nodoActual.getHijoIzq();
                    }
                } 
                //Si no tiene hijo derecho visita o va al raíz
                else {
                    pilaNodos.pop();
                    ultimoNodoVisitado = nodoTope; 
                    return nodoTope.getDato(); 
                }
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}