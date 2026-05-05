package listas;
import excepciones.ListException;

/**
 * ArrayList.java
 * Varios métodos para los elementos de un arreglo
 * @author Andre
 * @param <T> 
 */
public class ArrayList<T> implements IList<T>{

    //Mensajes centralizados para excepciones
    private final String NULO = "El objeto es nulo";
    private final String INVALIDO = "El índice es inválido";
    private final String VACIA = "La lista está vacía";
    private final String LLENA = "La lista está llena";
    
    
    
    //Atributos
    protected int numElementos = 0;
    protected final int tamLista;
    protected T[] lista;
    
    
    
    /**
     * Constructor
     * @param tamLista 
     */
    public ArrayList(int tamLista) {
        //Excepción si la capacidad es menor a 0
        if(tamLista < 0) {
            throw new ListException("La capacidad no puede ser menor a 0");
        }
        this.tamLista = tamLista;
        this.lista = (T[]) new Object[tamLista]; 
    }
    
    
    
     /**
     * append
     * Reemplaza el último índice lógicamente accesible por el valor del parámetro
     * @param o Objeto a agregar
     * @throws ListException si el objeto es nulo o la lista está llena
     */
    public void append(T o) throws ListException {
        
        //Excepción si el objeto es nulo
        if (o == null) {
            throw new ListException(NULO);
        }
        
        //Excepción si la lista está llena
        if (numElementos == tamLista) {
            throw new ListException(LLENA);
        }
        
        //
        lista[numElementos] = o;
        numElementos++;
    }
    
    
    
    /**
     * insert
     * Copia los elementos del arreglo a otro nuevo, y en el índice del parámetro pone el objeto
     * @param o Objeto a reemplazar
     * @param i Índice donde se va a reemplazar
     * @throws ListException si el objeto es nulo, índice es inválido o la lista está llena
     */
    public void insert(T o, int i) throws ListException {
        
        //Excepción si el objeto es nulo
        if (o == null) {
            throw new ListException(NULO);
        }
        
        //Excpeción si el índice es inválido
        if (i < 0 || i > numElementos) {
            throw new ListException(INVALIDO);
        }
        
        //Excepción si la lista está llena
        if (numElementos == tamLista) {
            throw new ListException(LLENA);
        }
        
        //Nuevo arreglo
        T[] nuevo = (T[]) new Object[tamLista];
        
        //Copia los elementos antes del índice
        for (int j = 0; j < i; j++) {
            nuevo[j] = lista[j];
        }
        
        //Inserta en el índice deseado
        nuevo[i] = o;
        
        //Copia los elementos después del índice
        for (int j = i; j < numElementos; j++) {
            nuevo[j+1] = lista[j];
        }
        
        //Configuracione finales
        lista = nuevo;
        numElementos++;
    }
    
    
    /**
     * Extrae directamente el elemento de cierto índice
     * @param i Índice donde está el elemento
     * @return el objeto extraído
     * @throws ListException si la lista está vacía o el índice es inválido
     */
    public T get(int i) throws ListException {

        //Excepeción si el índice es inválido
        if (i < 0 || i >= numElementos) {
            throw new ListException(INVALIDO);
        }
        
        //Regresa el objeto en el índice del parámetro
        return lista[i];
    }
    
    
    
    /**
     * set
     * Accede directamente a la posición del arreglo para reemplazar el objeto
     * @param o Objeto a reemplazar
     * @param i Índice donde se va a reemplazar
     * @throws ListException si la lista está vacía, el objeto es nulo o el índice es inválido
     */
    @Override
    public void set(T o, int i) throws ListException {
        
        //Excepción si el objeto es nulo
        if (o == null) {
            throw new ListException(NULO);
        }
        
        //Excepción por índice inválido
        if (i < 0 || i >= numElementos) {
            throw new ListException(INVALIDO);
        }
        
        //Reemplaza el elemento
        lista[i] = o;
    }

    
    
    /**
     * remove
     * Copia todos los elementos a un nuevo arreglo, menos el que se quiere eliminar
     * @param o Objeto a eliminar
     * @return si se pudo eliminar o no
     * @throws ListException 
     */
    @Override
    public boolean remove(T o) throws ListException {
        
        //Excepción si el objeto es nulo
        if (o == null) {
            throw new ListException(NULO);
        }
        
        //Excepción si la lista está vacía
        if (empty()) {
            throw new ListException(VACIA);
        }
        
        //Nuevo arreglo y variables auxiliares
        T[] nuevo = (T[]) new Object[tamLista];
        int contador = 0;
        boolean eliminado = false;
        
        //Recorre el arreglo
        for (int i = 0; i < numElementos; i++) {
            
            //Pone eliminado en verdadero solo una vez para eliminar la primera instancia
            if (!eliminado && o.equals(lista[i])) {
                eliminado = true;
                continue;
            }
            
            //Aumenta el contador
            nuevo[contador++] = lista[i];  
        }
        
        //Si no se eliminó nada, regres falso
        if (!eliminado) {
            return false;
        }
        
        //Configuraciones finales
        lista = nuevo;
        numElementos--;
        return true;
    }
    
    
    
    /**
     * removeExtraer
     * Copia todos los elementos a un nuevo arreglo, menos el que se quiere extraer
     * @param i Índice del objeto
     * @return objeto extraído
     * @throws ListException si la lista está vacía
     */
    @Override
    public T removeExtraer(int i) throws ListException {
        
        //Excepción si la lista está vacía
        if (empty()) {
            throw new ListException(VACIA);
        }
        
        //Extrae el índice del objeto
        T o = get(i);
        
        //Elimina el objeto
        for (int j = i; j < numElementos-1; j++) {
            lista[j] = lista[j+1];
        }

        //Configuraciones finales
        lista[numElementos-1] = null;
        numElementos--;
        return o;
    }
    
    
    
    /**
     * indexOf
     * Itera sobre el arreglo hasta que el elemento del índice coincida con el parámetro
     * @param o Objeto a buscar
     * @return el índice. Si no lo encuentra, regresa -1
     */
    @Override
    public int indexOf(T o) {
        
        //Regresa -1 si es nulo
        if (o == null) {
            return -1;
        }
        
        //Itera sobre el arreglo, si coincide el parámetro regresa su índice
        for (int i = 0; i < numElementos; i++) {
            if(o.equals(lista[i])) {
                return i;
            }
        }
        
        //Si no encuentra nada, regresa -1
        return -1;
    }

    
    
    /**
     * Regresa el tamaño de numElementos
     * @return la variable numElementos
     */
    @Override
    public int size() {
        return numElementos;
    }
    
    
    
    /**
     * Establece todos los elementos como null y reinicia numElementos
     */
    @Override
    public void clear() {
        for (int i = 0; i < numElementos; i++) {
            lista[i] = null;    
        }
        numElementos = 0;
    }
    
    
    
    /**
     * Regresa el resultado de validar si numElementos == 0
     * @return si está vacía o no
     */
    public boolean empty() {
        return numElementos == 0;
    }
}