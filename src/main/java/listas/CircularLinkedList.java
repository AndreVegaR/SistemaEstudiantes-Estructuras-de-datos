package listas;

import excepciones.ListException;

/**
 * Lista enlazada simple circular que extiende de la simple, en
 * esta estructura el último dato apunta al primero. Sobreescribe
 * métodos de la superclase para lograr la circularidad
 */
public class CircularLinkedList<T> extends LinkedList<T> {
    
    /**
     * append
     * Crea un nodo con el dato del parámetro
     * Con otro nodo auxiliar itera la lista hasta añadir al nuevo
     * 
     * @param o Dato a añadir
     */
    @Override
    public void append(T o) {
        
        //Excepción si es nulo
        datoNulo(o);
        
        //Crea un nodo con el dato
        Nodo<T> nuevo = new Nodo<T>(o);
        
        //Si es al inicio
        if (inicio == null) {
            inicio = nuevo;
            nuevo.setSiguiente(inicio);
            actual = inicio;
            return;
        }
        
        //Nodo auxiliar que igualada al nodo inicio
        Nodo<T> iterador = inicio;
        
        //El auxiliar itera sobre la lista
        while (iterador.getSiguiente() != inicio) {
            iterador = iterador.getSiguiente();
        }
        
        //El nodo nuevo queda después del auxiliar
        iterador.setSiguiente(nuevo);
        
        //Cierra el círculo
        nuevo.setSiguiente(inicio);
    }
    
    
    @Override
    public void insert(T o, int i) throws ListException {
        datoNulo(o);

        if (i < 0) {
            throw new ListException(INVALIDO);
        }

        Nodo<T> nuevo = new Nodo<T>(o); 

        if (i == 0) {
            if (inicio == null) {
                inicio = nuevo;
                nuevo.setSiguiente(inicio);
                actual = inicio;
            } else {

                Nodo<T> ultimo = inicio;
                while (ultimo.getSiguiente() != inicio) {
                    ultimo = ultimo.getSiguiente();
                }

                nuevo.setSiguiente(inicio);
                inicio = nuevo;
                ultimo.setSiguiente(inicio);
            }
            return;
        }

        Nodo<T> auxiliar = inicio; 
        for (int j = 0; j < i - 1; j++) {
            auxiliar = auxiliar.getSiguiente();

            if (auxiliar == inicio) {
                throw new ListException(INVALIDO);
            }
        }

        nuevo.setSiguiente(auxiliar.getSiguiente());
        auxiliar.setSiguiente(nuevo);
    }
    
    @Override
    public int size() {
        if (inicio == null) return 0;

        int contador = 0;
        Nodo<T> iterador = inicio;
        do {
            contador++;
            iterador = iterador.getSiguiente();
        } while (iterador != inicio);

        return contador;
    }
    
    @Override
    public T removeExtraer(int i) throws ListException {
        indiceValido(i);

        Nodo<T> eliminado;

        if (i == 0) {
            eliminado = inicio;
            if (actual == inicio) {
                actual = inicio.getSiguiente();
            }

            if (inicio.getSiguiente() == inicio) { 
                inicio = null;
                actual = null;
            } else {
                
                Nodo<T> ultimo = inicio;
                while (ultimo.getSiguiente() != inicio) {
                    ultimo = ultimo.getSiguiente();
                }

                inicio = inicio.getSiguiente(); 
                ultimo.setSiguiente(inicio);   
            }
        } 

        else {
            Nodo<T> auxiliar = inicio;

            for (int j = 0; j < i - 1; j++) {
                auxiliar = auxiliar.getSiguiente();
            }
            eliminado = auxiliar.getSiguiente();
            if (actual == eliminado) {
                actual = eliminado.getSiguiente();
            }
            auxiliar.setSiguiente(eliminado.getSiguiente());
        }

        return eliminado.getDato();
    }
    
    public T rotar() {
        if (inicio == null) {
            throw new ListException("Lista vacía");
        }
        if (actual == null) {
            actual = inicio;
        }
        actual = actual.getSiguiente();

        return actual.getDato();
    }
    
    public T actual() {
        if (actual == null) {
            return null;
        }
        return actual.getDato();
    }
    

}
