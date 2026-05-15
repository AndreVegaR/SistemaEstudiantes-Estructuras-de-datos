package listas;

import excepciones.ListException;

public class DoubleCircularLinkedList<T> implements IList<T> {

    public class NodoDoble<T> {

        private T dato;
        private NodoDoble<T> siguiente;
        private NodoDoble<T> anterior;

        public NodoDoble(T dato) {
            this.dato = dato;
            this.siguiente = null;
            this.anterior = null;
        }

        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public NodoDoble<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(NodoDoble<T> s) {
            this.siguiente = s;
        }

        public NodoDoble<T> getAnterior() {
            return anterior;
        }

        public void setAnterior(NodoDoble<T> a) {
            this.anterior = a;
        }
    }

    private final String NULO = "El objeto es nulo";
    private final String INVALIDO = "El índice es inválido";
    private final String VACIA = "La lista está vacía";

    private NodoDoble<T> inicio;

    private int numElementos;

    private NodoDoble<T> cursor;

    public DoubleCircularLinkedList() {
        inicio = null;
        numElementos = 0;
        cursor = null;
    }

    @Override
    public void append(T o) throws ListException {
        datoNulo(o);
        NodoDoble<T> nuevo = new NodoDoble<>(o);

        if (inicio == null) {
            inicio = nuevo;
            nuevo.setSiguiente(inicio);
            nuevo.setAnterior(inicio);
            cursor = inicio;
        } else {
            NodoDoble<T> ultimo = inicio.getAnterior();
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
            nuevo.setSiguiente(inicio);
            inicio.setAnterior(nuevo);
        }
        numElementos++;
    }

    @Override
    public void insert(T o, int i) throws ListException {
        datoNulo(o);
        if (i < 0 || i > numElementos) {
            throw new ListException(INVALIDO);
        }

        if (i == numElementos) {
            append(o);
            return;
        }

        NodoDoble<T> nuevo = new NodoDoble<>(o);

        if (i == 0) {
            if (inicio == null) {
                inicio = nuevo;
                nuevo.setSiguiente(inicio);
                nuevo.setAnterior(inicio);
                cursor = inicio;
            } else {
                NodoDoble<T> ultimo = inicio.getAnterior();
                nuevo.setSiguiente(inicio);
                nuevo.setAnterior(ultimo);
                inicio.setAnterior(nuevo);
                ultimo.setSiguiente(nuevo);
                inicio = nuevo;
            }
        } else {
            NodoDoble<T> iterador = iterarIndice(i - 1);
            NodoDoble<T> siguiente = iterador.getSiguiente();
            iterador.setSiguiente(nuevo);
            nuevo.setAnterior(iterador);
            nuevo.setSiguiente(siguiente);
            siguiente.setAnterior(nuevo);
        }
        numElementos++;
    }

    @Override
    public T get(int i) throws ListException {
        indiceValido(i);
        return iterarIndice(i).getDato();
    }

    @Override
    public void set(T o, int i) throws ListException {
        datoNulo(o);
        indiceValido(i);
        iterarIndice(i).setDato(o);
    }

    @Override
    public boolean remove(T o) throws ListException {
        datoNulo(o);
        int i = indexOf(o);
        if (i == -1) {
            return false;
        }
        removeExtraer(i);
        return true;
    }

    @Override
    public T removeExtraer(int i) throws ListException {
        indiceValido(i);
        NodoDoble<T> eliminado;

        if (numElementos == 1) {
            eliminado = inicio;
            inicio = null;
            cursor = null;
        } else if (i == 0) {
            eliminado = inicio;
            NodoDoble<T> ultimo = inicio.getAnterior();
            NodoDoble<T> segundo = inicio.getSiguiente();
            ultimo.setSiguiente(segundo);
            segundo.setAnterior(ultimo);
            inicio = segundo;
            if (cursor == eliminado) {
                cursor = inicio;
            }
        } else {
            eliminado = iterarIndice(i);
            NodoDoble<T> ant = eliminado.getAnterior();
            NodoDoble<T> sig = eliminado.getSiguiente();
            ant.setSiguiente(sig);
            sig.setAnterior(ant);
            if (cursor == eliminado) {
                cursor = sig;
            }
        }

        numElementos--;
        return eliminado.getDato();
    }

    @Override
    public int indexOf(T o) {
        if (o == null || inicio == null) {
            return -1;
        }
        NodoDoble<T> iterador = inicio;
        for (int i = 0; i < numElementos; i++) {
            if (iterador.getDato().equals(o)) {
                return i;
            }
            iterador = iterador.getSiguiente();
        }
        return -1;
    }

    @Override
    public int size() {
        return numElementos;
    }

    @Override
    public void clear() {
        inicio = null;
        cursor = null;
        numElementos = 0;
    }

    @Override
    public boolean empty() {
        return numElementos == 0;
    }

    public T avanzar() throws ListException {
        if (empty()) {
            throw new ListException(VACIA);
        }
        cursor = cursor.getSiguiente();
        return cursor.getDato();
    }

    public T retroceder() throws ListException {
        if (empty()) {
            throw new ListException(VACIA);
        }
        cursor = cursor.getAnterior();
        return cursor.getDato();
    }

    public T getCursorDato() throws ListException {
        if (empty()) {
            throw new ListException(VACIA);
        }
        return cursor.getDato();
    }

    public void resetCursor() throws ListException {
        if (empty()) {
            throw new ListException(VACIA);
        }
        cursor = inicio;
    }

    public String getNPrimeros(int n) throws ListException {
        if (empty()) {
            throw new ListException(VACIA);
        }
        int mostrar = Math.min(n, numElementos);
        StringBuilder sb = new StringBuilder("Lista de espera (")
                .append(mostrar).append(" primero")
                .append(mostrar == 1 ? "" : "s").append("):\n");

        NodoDoble<T> iterador = inicio;
        for (int i = 0; i < mostrar; i++) {
            sb.append("  [").append(i + 1).append("] ")
                    .append(iterador.getDato())
                    .append("\n");
            iterador = iterador.getSiguiente();
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        if (empty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        NodoDoble<T> iterador = inicio;
        for (int i = 0; i < numElementos; i++) {
            sb.append(iterador.getDato());
            if (i < numElementos - 1) {
                sb.append(" ↔ ");
            }
            iterador = iterador.getSiguiente();
        }
        sb.append(" ↔ (circular)]");
        return sb.toString();
    }

    private NodoDoble<T> iterarIndice(int i) {
        NodoDoble<T> iterador = inicio;
        for (int j = 0; j < i; j++) {
            iterador = iterador.getSiguiente();
        }
        return iterador;
    }

    private void indiceValido(int i) throws ListException {
        if (i < 0 || i >= numElementos) {
            throw new ListException(INVALIDO);
        }
    }

    private void datoNulo(T o) throws ListException {
        if (o == null) {
            throw new ListException(NULO);
        }
    }
}
