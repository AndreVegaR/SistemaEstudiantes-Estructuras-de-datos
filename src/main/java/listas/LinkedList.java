

package listas;
import excepciones.ListException;

/**
 * LinkedList.java
 * Lista enlazada que implementa la interfaz IList
 * @author Andre
 */
public class LinkedList<T> implements IList<T> {
    
    /**
     * Clase interna de un nodo simple
     * 
     * @param <T> indica genéricos
     */
    public class Nodo<T> {
        private T dato;
        private Nodo<T> siguiente;
        public Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
        public T getDato() {
            return dato;
        }
        public void setDato(T dato) {
            this.dato = dato;
        }
        public Nodo<T> getSiguiente() {
            return siguiente;
        }
        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }  
    }
    
    //Mensajes centralizados para excepciones
    protected final String NULO = "El objeto es nulo";
    protected final String INVALIDO = "El índice es inválido";
    
    
    
    //Atributos
    protected Nodo<T> inicio = null;
    
    
    
    /**
     * Constructor
     */
    public LinkedList() {

    }
    
    
    
    /**
     * append
     * Crea un nodo con el dato del parámetro
     * Con otro nodo auxiliar itera la lista hasta añadir al nuevo
     * @param o Dato a añadir
     * @throws ListException  si el dato es nulo
     */
    @Override
    public void append(T o) throws ListException {
        
        //Excepción si es nulo
        datoNulo(o);
        
        //Crea un nodo con el dato
        Nodo<T> nuevo = new Nodo<T>(o);
        
        //Si es al inicio
        if (inicio == null) {
            inicio = nuevo;
            return;
        }
        
        //Nodo auxiliar que igualada al nodo inicio
        Nodo<T> iterador = inicio;
        
        //El auxiliar itera sobre la lista
        while (iterador.getSiguiente() != null) {
            iterador = iterador.getSiguiente();
        }
        
        //El nodo nuevo queda después del auxiliar
        iterador.setSiguiente(nuevo);
    }

    
    
    /**
     * insert
     * Inserta un nodo en medio de la lista
     * @param o Dato a inserar
     * @param i Índice donde se va insertar
     * @throws ListException 
     */
    @Override
    public void insert(T o, int i) throws ListException {
        
        //Excepción si es nulo
        datoNulo(o);
        
        //Excepción si índice válido
        if (i < 0) {
            throw new ListException(INVALIDO);
        }
        
        //Nodo nuevo
        Nodo<T> nuevo = new Nodo<T>(o); 
        
        //Si es al inicio
        if (i == 0) {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
            return;
        }

        //El auxiliar itera sobre la lista
        Nodo<T> auxiliar = inicio; 
        for (int j = 0; j < i - 1; j++) {
            auxiliar = auxiliar.getSiguiente();
        }
        
        //Reacomoda
        nuevo.setSiguiente(auxiliar.getSiguiente());
        auxiliar.setSiguiente(nuevo);
    }

    
    
    @Override
    public T get(int i) throws ListException {
        
        //Excepeción si el índice es inválido
        indiceValido(i);

        //Nodo auxiliar que igualada al nodo inicio
        Nodo<T> iterador = inicio;
        T regresar = null;
        
        //El auxiliar itera sobre la lista
        for (int j = 0; j < size(); j++) {
            
            //Si j está en el índice deseado, regresa su dato
            if (i == j) {
                regresar = iterador.getDato();
                break;
            }
            iterador = iterador.getSiguiente();
        }
        return regresar;
    }

    
    
    /**
     * Reemplaza el valor del índice por otro
     * @param o Elemento a poner
     * @param i Índice donde se va a reemplazar
     * @throws ListException 
     */
    @Override
    public void set(T o, int i) throws ListException {
        
        //Excepciones
        datoNulo(o);
        indiceValido(i);
        
        //Itera hasta índices coincidentes y reemplaza el dato
        Nodo<T> auxiliar = inicio;
        for (int j = 0; j < i; j++) {
            auxiliar = auxiliar.getSiguiente();
        }
        auxiliar.setDato(o);
    }

    
    
    @Override
    public boolean remove(T o) throws ListException {
        
        //Excepción si es nulo
        datoNulo(o);
        
        //Llama a otro método para el índice
        int i = indexOf(o);
        
        //Si no se encuentra, no se puede eliminar
        if (i == -1) {
            return false;
        }
        
        //Llama a extraer por índice
        removeExtraer(i);
        
        //Si no saltó excepción antes, debe ser siempre verdadero
        return true;

    }
    
    
    
    /**
     * removeExtreaer
     * Extrae un dato en cierto índice
     * @param i Índice
     * @return el dato extraído
     * @throws ListException si el índice es inválido
     */
    @Override
    public T removeExtraer(int i) throws ListException {

        //Excepción si índice inválido
        indiceValido(i);
        
        //Nodo a eliminar
        Nodo<T> eliminado;

        //Elimina el primero
        if (i == 0) {
            eliminado = inicio;
            inicio = inicio.getSiguiente();
        } else {
            
            //Empieza a iterar
            Nodo<T> auxiliar = inicio;
            for (int j = 0; j < i - 1; j++) {
                auxiliar = auxiliar.getSiguiente();
            }
            eliminado = auxiliar.getSiguiente();
            auxiliar.setSiguiente(eliminado.getSiguiente());
        }
        
        //Regresa el dato del eliminado
        return eliminado.getDato();
    }

     
    /**
     * indexOf
     * Obtiene el índice de un dato
     * @param o Dato a buscar
     * @return el índice del dato
     */
    @Override
    public int indexOf(T o) {

        //Regresa -1 si no lo encuentra
        if (o == null) {
            return -1;
        }

        //Nodo auxiliar igualado al nodo inicio
        Nodo<T> iterador = inicio;
        
        //El auxiliar itera sobre la lista
        for (int i = 0; i < size(); i++) {
            
            //Si j está en el índice deseado, regresa su dato
            if (iterador.getDato().equals(o)) {
                return i;
            }
            iterador = iterador.getSiguiente();
        }
        return -1;
    }

    
    
    /**
     * size
     * Itera sobre la lista y da una vuelta por cada nodo
     * @return la cantidad de nodos
     */
    @Override
    public int size() {
        
        //Nodo iterador
        Nodo<T> iterador = inicio;
        
        //Iterador que cuenta cada nodo
        int contador = 0;
        
        //Cuenta cada nodo
        while (iterador != null) {
            contador++;
            iterador = iterador.getSiguiente();
        }
        
        //Regresa la cantidad de vueltas
        return contador;
    }

    
    
    /**
     * clear
     * Hace que inicio apunte a nulo, desconectando la cadena de referencias
     */
    @Override
    public void clear() {
        inicio = null;
    }
    
    
    
    /**
     * clear
     * Llama al método size y compara su valor con 0
     * @return si la lista está vacía o no 
     */
    @Override
    public boolean empty() {
        return size() == 0;
    }
    
    
    
    /**
     * Método auxiliar que verifica que un índice esté dentro de rango
     * @param i 
     */
    protected void indiceValido(int i) throws ListException{
        if (i < 0 || i >= size()) {
            throw new ListException(INVALIDO);
        }
    }
    
    
    
    /**
     * Método auxiliar que lanza excepción si el dato es nulo
     * @param o Dato a verificar
     */
    protected void datoNulo(T o) throws ListException{
        if (o == null) {
            throw new ListException(NULO);
        }
    }
}