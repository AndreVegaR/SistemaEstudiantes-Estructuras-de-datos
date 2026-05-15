/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arboles;

/**
 * Esta clase representa un árbol avl el cual se balancea automáticamente al sufrir algún cambio
 * en su estructura.
 * El parámetro genérico de la clase main utiliza comparable para poder realizar comparaciones
 * e identificar si la clave de un nodo es mayor, menor o igual.
 * @author aaron
 */
public class ArbolAVL<T extends Comparable<T>> {
    
    /**
     * Clase nodo que se encarga de la recursividad 
     */
    private class Nodo{

        private T clave;
        private Nodo hijoIzquierdo;
        private Nodo hijoDerecho;
        
        /**
         * Constructor del Nodo
         * @param clave 
         */
        public Nodo(T clave) {
            this.clave = clave;
            this.hijoIzquierdo = null;
            this.hijoDerecho = null;
        }

        public T getClave() {
            return clave;
        }

        public Nodo getHijoIzquierdo() {
            return hijoIzquierdo;
        }

        public Nodo getHijoDerecho() {
            return hijoDerecho;
        }

        public void setHijoIzquierdo(Nodo hijoIzquierdo) {
            this.hijoIzquierdo = hijoIzquierdo;
        }

        public void setHijoDerecho(Nodo hijoDerecho) {
            this.hijoDerecho = hijoDerecho;
        }
    }
    
    // --- ATRIBUTO PRINCIPAL (RAÍZ) --- 
    private Nodo raiz;
    
    
    // ---auxiliar para no tener 0's harcodeados ---
    private final int COMPARACION_COMPARE = 0;
    
    //  --- CONSTRUCTOR  ---
    public ArbolAVL() {
        this.raiz = null;
    }

    // --- MÉTODOS PÚBLICOS ---
    
    /**
     * @param args the command line arguments
     */
    
    /**
     * Driver para el método recursivo que elimina un nodo.
     *
     * @param dato El valor entero que se desea eliminar.
     */
    public void remove(T dato) {
        raiz = remove(raiz, dato);
    }

    /**
     * Método recursivo para eliminar un nodo y aplicar balanceo AVL.
     *
     * @param nodo Raíz del subárbol actual.
     * @param dato Valor a eliminar.
     * @return El nodo (o su reemplazo) ya balanceado.
     */
    private Nodo remove(Nodo nodo, T clave) {
        // CASO BASE: Si el dato no existe en el árbol
        if (nodo == null) {
            return null;
        }
        // --- Es el resultado de la compracacion de la clave a eliminar con laa del nodo ---
        int resultado = clave.compareTo(nodo.clave);
        
        // ---  Empieza el flujo de recorrido --- 
        if (resultado < COMPARACION_COMPARE) {
            nodo.hijoIzquierdo = remove(nodo.hijoIzquierdo, clave);
        } else if (resultado > COMPARACION_COMPARE) {
            nodo.hijoDerecho = remove(nodo.hijoDerecho, clave);
        } // 2. Se encuentra el nodo a eliminar
        else {
            // Caso A: El nodo es una hoja o solo tiene un hijo (el derecho)
            if (nodo.hijoIzquierdo == null) {
                return nodo.hijoDerecho;
            } // Caso B: El nodo solo tiene el hijo izquierdo
            else if (nodo.hijoDerecho == null) {
                return nodo.hijoIzquierdo;
            }

            // Caso C: EL NODO TIENE DOS HIJOS
            // Se busca el dato más grande
            Nodo reemplazo = findBiggestNode(nodo.hijoIzquierdo);

            // se reemplaza el nodo actual con el que se encuentra
            nodo.clave = reemplazo.clave;

            // Eliminamos el nodo que usamos como reemplazo (su copia original)
            nodo.hijoIzquierdo = remove(nodo.hijoIzquierdo, reemplazo.clave);
        }

        // 3. Rebalanceamos el arbol
        return rebalance(nodo);
    }

    /**
     * Rotación Simple a la Derecha.
     */
    private Nodo rotateRight(Nodo nodo) {
        Nodo nodoT = nodo.hijoIzquierdo;
        nodo.hijoIzquierdo = nodoT.hijoDerecho;
        nodoT.hijoDerecho = nodo;
        return nodoT;
    }

    /**
     * Rotación Simple a la Izquierda.
     */
    private Nodo rotateLeft(Nodo nodo) {
        Nodo nodoT = nodo.hijoDerecho;
        nodo.hijoDerecho = nodoT.hijoIzquierdo;
        nodoT.hijoIzquierdo = nodo;
        return nodoT;
    }

    /**
     * Rotación Doble Izquierda-Derecha (LR).
     */
    private Nodo rotateLeftRight(Nodo nodo) {
        nodo.hijoIzquierdo = rotateLeft(nodo.hijoIzquierdo);
        return rotateRight(nodo);
    }

    /**
     * Rotación Doble Derecha-Izquierda (RL).
     */
    private Nodo rotateRightLeft(Nodo nodo) {
        nodo.hijoDerecho = rotateRight(nodo.hijoDerecho);
        return rotateLeft(nodo);
    }

    /**
     * Calcula la altura de un nodo de forma recursiva.
     */
    private int getHeight(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }

        int alturaIzq = getHeight(nodo.hijoIzquierdo);
        int alturaDer = getHeight(nodo.hijoDerecho);

        return 1 + Math.max(alturaIzq, alturaDer);
    }

    /**
     * Obtiene el factor de balance: AlturaDerecha - AlturaIzquierda.
     */
    private int balanceFactor(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return getHeight(nodo.hijoDerecho) - getHeight(nodo.hijoIzquierdo);
    }

    public void insert(T clave) {
        raiz = insert(raiz, clave);
    }

    private Nodo insert(Nodo nodo, T clave) {
        if (nodo == null) {
            return new Nodo(clave);
        }

        // --- Comparar la clave con otro ---
        int resultado = clave.compareTo(nodo.clave);
        
        if (resultado < COMPARACION_COMPARE) {
            nodo.hijoIzquierdo = insert(nodo.hijoIzquierdo, clave);
        } else if (resultado > COMPARACION_COMPARE) {
            nodo.hijoDerecho = insert(nodo.hijoDerecho, clave);
        } else {
            return nodo; // Clave duplicada, no se hace nada
        }

        return rebalance(nodo);
    }

    /**
     * Revisa el factor de equilibrio del nodo y aplica las rotaciones
     * necesarias (Simple o Doble) para mantener el árbol balanceado.
     *
     * * @param nodo El nodo que se va a revisar.
     * @return El nodo ya balanceado (puede ser el mismo o uno nuevo tras
     * rotar).
     */
    private Nodo rebalance(Nodo nodo) {
        // Calculamos el factor de balance (Altura Derecha - Altura Izquierda)
        int factorBalance = balanceFactor(nodo);

        // CASO 1: Cargado a la IZQUIERDA (factor < -1)
        if (factorBalance < -1) {
            // Si el hijo izquierdo está cargado a la izquierda -> Rotación Simple Derecha
            if (balanceFactor(nodo.hijoIzquierdo) <= 0) {
                nodo = rotateRight(nodo);
            } // Si el hijo izquierdo está cargado a la derecha -> Rotación Doble (Izquierda-Derecha)
            else {
                nodo = rotateLeftRight(nodo);
            }
        } // CASO 2: Cargado a la DERECHA (factor > 1)
        else if (factorBalance > 1) {
            // Si el hijo derecho está cargado a la derecha -> Rotación Simple Izquierda
            if (balanceFactor(nodo.hijoDerecho) >= 0) {
                nodo = rotateLeft(nodo);
            } // Si el hijo derecho está cargado a la izquierda -> Rotación Doble (Derecha-Izquierda)
            else {
                nodo = rotateRightLeft(nodo);
            }
        }

        // Si el factor es -1, 0 o 1, el nodo está en paz y no hacemos nada
        return nodo;
    }

    /**
     * Busca el nodo que contiene el valor más grande a partir de un punto.
     *
     * @param nodo El nodo desde donde empieza la búsqueda.
     * @return El nodo que está más a la derecha.
     */
    private Nodo findBiggestNode(Nodo nodo) {
        if (nodo == null) {
            return null;
        }

        // Mientras haya alguien a la derecha, ese es más grande
        if (nodo.hijoDerecho != null) {
            return findBiggestNode(nodo.hijoDerecho);
        }

        // Si ya no hay hijo derecho, este es el más grande
        return nodo;
    }
}
