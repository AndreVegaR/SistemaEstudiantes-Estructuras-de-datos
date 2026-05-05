package listas;
import excepciones.ListException;

/**
 * Lista doblemente enlazada que implementa la interfaz IDoubleList
 */
public class DoubleLinkedList<T> implements IDoubleList<T>{
    
    /**
     * Clase interna de un nodo doble interno
     * 
     * @param <T> indica genéricos
     */
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
        public void setSiguiente(NodoDoble<T> siguiente) {
            this.siguiente = siguiente;
        }
        public NodoDoble<T> getAnterior() {
            return anterior;
        }
        public void setAnterior(NodoDoble<T> anterior) {
            this.anterior = anterior;
        }
    }
    
    //Mensajes centralizados para excepciones
    private final String NULO = "El objeto es nulo";
    private final String INVALIDO = "El índice es inválido";
    
    //Atributo
    private NodoDoble<T> inicio = null;
    private int numElementos = 0;
    
    //Constructor
    public DoubleLinkedList() {}
    
    /**
     * append
     * Recorre la lista hasta agregar el objeto al final
     * @param o Objeto a agregar
     * @throws ListException 
     */
    @Override
    public void append(T o) throws ListException {
        
        //Excepción si es nulo
        datoNulo(o);
        
        //Crea el nodo
        NodoDoble<T> nuevo = new NodoDoble<>(o);

        //Si la lista está vacía
        if (inicio == null) {
            inicio = nuevo;
        } else {
            
            //Nodo auxiliar que recorre la lista
            NodoDoble<T> iterador = iterarFinal();

            //Reacomodos finales
            iterador.setSiguiente(nuevo);
            nuevo.setAnterior(iterador);
        }
        numElementos++;
    }

    
    
    /**
     * insert
     * Recorre la lista hasta llegar al índice e inserta el objeto
     * @param o Dato a insertar
     * @param i Índice donde se va a insertar
     * @throws ListException 
     */
    @Override
    public void insert(T o, int i) throws ListException {
        //Excepciones
        datoNulo(o);
        if (i < 0 || i > numElementos) {
            throw new ListException(INVALIDO);
        }
        
        //Nodo nuevo
        NodoDoble<T> nuevo = new NodoDoble<>(o);
        
        //Si es al inicio
        if (i == 0) {
            nuevo.setSiguiente(inicio);
            if (inicio != null) {
                inicio.setAnterior(nuevo);
            }
            inicio = nuevo;
        } else {
            
            //El auxiliar itera sobre la lista
            NodoDoble<T> iterador = iterarAntesIndice(i);

            //Reacomoda
            nuevo.setSiguiente(iterador.getSiguiente());
            nuevo.setAnterior(iterador);
            if (iterador.getSiguiente() != null) {
                iterador.getSiguiente().setAnterior(nuevo);
            }
            iterador.setSiguiente(nuevo);
        }
        numElementos++;
    }
    
    
    
    /**
     * get
     * Itera en la lista y regresa el dato en el nodo del índice
     * @param i Índice del dato donde se va a obtener
     * @return el dato del nodo
     * @throws ListException 
     */
    @Override
    public T get(int i) throws ListException {
        //Excepeción
        indiceValido(i);
        
        //Nodo auxiliar que itera sobre la lista
        NodoDoble<T> iterador = iterarIndice(i);
        
        //Regresa el dato
        return iterador.getDato();
    }

    
    
    /**
     * set
     * @param o
     * @param i
     * @throws ListException 
     */
    @Override
    public void set(T o, int i) throws ListException {
        //Excepciones
        datoNulo(o);
        indiceValido(i);
        
        //Itera hasta índices coincidentes y reemplaza el dato
        NodoDoble<T> iterador = iterarIndice(i);
        iterador.setDato(o);
    }
    
    
    
    /**
     * Llama a otros métodos para remover un elemento
     * @param o Objeto que se va a remover
     * @return true si se removió, false de lo contrario
     * @throws ListException 
     */
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
        
        //Intenta eliminar donde esté ese objeto
        return removeExtraer(i) != null;
    }

    
    
    /**
     * removeExtreaer
     * Extrae un dato en cierto índice
     * @param i Índice
     * @return el dato extraído
     * @throws ListException
     */
    @Override
    public T removeExtraer(int i) throws ListException {
        //Excepción
        indiceValido(i);

        //Nodo a eliminar
        NodoDoble<T> eliminado;

        //Elimina el primero
        if (i == 0) {
            eliminado = inicio;
            inicio = inicio.getSiguiente();
            if (inicio != null) {
                inicio.setAnterior(null);
            }
        } else {
            
            //Empieza a iterar
            NodoDoble<T> iterador = iterarAntesIndice(i);
            
            //Reacomoda
            eliminado = iterador.getSiguiente();
            iterador.setSiguiente(eliminado.getSiguiente());
            if (eliminado.getSiguiente() != null) {
                eliminado.getSiguiente().setAnterior(iterador);
            }    
        }

        //Regresa el dato del eliminado
        numElementos--;
        return eliminado.getDato();
    }

    
    
    /**
     * Obtiene el índice de la primera ocurrencia del objeto
     * 
     * @param o Dato a buscar su índice
     * 
     * @return índice si se encontró, -1 de lo contrario
     */
    @Override
    public int indexOf(T o) {
        //Regresa -1 si no lo encuentra
        if (o == null) {
            return -1;
        }

        //Nodo auxiliar que itera
        NodoDoble<T> iterador = inicio;
        for (int i = 0; i < numElementos; i++) {
            
            //Si j está en el índice deseado, regresa su dato
            if (iterador.getDato().equals(o)) {
                return i;
            }
            iterador = iterador.getSiguiente();
        }
        return -1;
    }
    
    /**
     * Regresa el contador de la cantidad de elementos agregados
     * 
     * @return cantidad de elementos agregados
     */
    @Override
    public int size() {
        return numElementos;
    }
    
    /** Desconecta la cadena de referencias haciendo que inicio apunte a nulo */
    @Override
    public void clear() {
        inicio = null;
        numElementos = 0;
    }
    
    /**
     * Regresa la comparación de si numElementos es 0
     * 
     * @return true si es igual, false si no
     */
    @Override
    public boolean empty() {
        return numElementos == 0;
    }
    
    /**
     * Regresa el índice de la última instancia de la lista
     * Va actualizando el índice hasta que ya no se repitan
     * 
     * @param o Dato a encontrar índice
     * 
     * @return el índice del dato
     * 
     * @throws ListException 
     */
    @Override
    public int lastIndexOf(T o) throws ListException {
        //Regresa -1 si no lo encuentra
        if (o == null) {
            return -1;
        }

        //Nodo auxiliar que itera
        NodoDoble<T> auxiliar = inicio;
        int indice = -1;
        int iterador = 0;
        while (auxiliar != null) {
            if (auxiliar.getDato().equals(o)) {
                indice = iterador;
            }
            auxiliar = auxiliar.getSiguiente();
            iterador++;
        }
        return indice;
    }
    
    /**
     * Remueve la última instancia
     * 
     * @param o Dato a remover
     * 
     * @return true si se removió, false si no
     * 
     * @throws ListException 
     */
    @Override
    public boolean removeLast(T o) throws ListException {
        //Excepción si es nulo
        datoNulo(o);
        
        //Llama a otro método para el índice
        int i = lastIndexOf(o);
        
        //Si no se encuentra, no se puede eliminar
        if (i == -1) {
            return false;
        }
        
        //Intenta eliminar donde esté ese objeto
        return removeExtraer(i) != null;
    }
    
    /**
     * Método auxiliar que verifica que un índice esté dentro de rango
     * @param i 
     */
    private void indiceValido(int i) throws ListException{
        if (i < 0 || i >= numElementos) {
            throw new ListException(INVALIDO);
        }
    }
    
    /**
     * Método auxiliar que lanza excepción si el dato es nulo
     * @param o Dato a verificar
     */
    private void datoNulo(T o) throws ListException{
        if (o == null) {
            throw new ListException(NULO);
        }
    }
    
    /**
     * Método auxiliar que regresa un nodo en el índice solicitado
     * 
     * @param i Índice
     * 
     * @return el nodo it erado
     */
    private NodoDoble<T> iterarIndice(int i) {
        NodoDoble<T> iterador = inicio;
        for (int j = 0; j < i; j++) {
            iterador = iterador.getSiguiente();
        }
        return iterador;
    }
    
    /**
     * Llama a iterarIndice con el parámetro de numElementos-1
     * 
     * @return el nodo iterado
     */
    private NodoDoble<T> iterarFinal() {
        return iterarIndice(numElementos-1);
    }
    
    /**
     * Llama a iterarIndice pero termina en el índice anterior
     * 
     * @return el nodo iterado
     */
    private NodoDoble<T> iterarAntesIndice(int i) {
        return iterarIndice(i-1);
    }
}