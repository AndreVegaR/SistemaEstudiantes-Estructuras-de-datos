/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diccionario;

import excepciones.ListException;
import java.util.ArrayList;
import java.util.List;
import listas.LinkedList;

/**
 *
 * @author aaron
 */

public class Diccionario<K, V> {
    
    
    private LinkedList<Dir<K, V>>[] tabla;
    private int capacidad;
    
    public Diccionario(int capacidad){
        this.capacidad = capacidad;
        tabla = new LinkedList[capacidad];
        
        for(int i = 0; i < capacidad; i++){
            tabla[i] = new LinkedList<>();
        }
    }
    
    private int hash(K clave){
        return Math.abs(clave.hashCode()% capacidad);
    }
    
    public void agregar(K clave, V valor){
        int indice = hash(clave);
        LinkedList<Dir<K, V>> balde = tabla[indice];
        
        try {
            int tamanoBalde = balde.size(); 
            
            for (int i = 0; i < tamanoBalde; i++) {
                Dir<K, V> par = balde.get(i);
                
                if (par != null && par.getClave().equals(clave)) {
                    par.valor = valor; // Actualiza el valor del par existente
                    return; // termina
                }
            }
            
            // Si salimos del ciclo, el elemento es nuevo. Lo añadimos al final del balde.
            Dir<K, V> nuevoPar = new Dir<>(clave, valor);
            balde.append(nuevoPar);
            
        } catch (ListException e) {
            throw new ListException("erorr al agregar en diccionario");
        }
    }
    
    /**
     * Recupera el valor asociado a una clave
     * @param clave el identificar
     * @return la calve de ese identificador
     */
    public V recuperar(K clave){
        int indice = hash(clave);
        LinkedList<Dir<K, V>> balde = tabla[indice];
        
        int tamanioBalde = balde.size();
        
        for(int i = 0; i < tamanioBalde; i++){
            Dir<K,V> conjunto = balde.get(i);
            if(conjunto.clave.equals(clave)){
                return conjunto.valor;
            }
        }
        return null;
    }
    
    /**
     * Elimina el conjunto clave valor mediante la clave proporcionada
     * @param clave
     * @return true si se eliminó, false en caso contrario
     */
    public boolean eliminar(K clave) {
        int indice = hash(clave);
        LinkedList<Dir<K, V>> balde = tabla[indice];
        
        try {
            int tamanioBalde = balde.size();
            
            // Buscamos iterativamente en qué índice de tu LinkedList está la clave
            for (int i = 0; i < tamanioBalde; i++) {
                Dir<K, V> conjunto = balde.get(i);
                
                if (conjunto != null && conjunto.getClave().equals(clave)) {
                    // Si existe, lo eliminamos
                    balde.removeExtraer(i);
                    return true;
                }
            }
        } catch (ListException e) {
            throw new ListException("Error interno al eliminar del diccionario: " + e.getMessage());
        }
        return false;
    }
    
    /**
     *  Devuelve una lista nativa de Java con todos los valores (V) almacenados en la tabla.
     *  Utilizado para rellenar componentes 
     */
    public List<V> obtenerValores() {
        List<V> listaValores = new ArrayList<>();
        
        try {
            // Recorremos cada "balde" o casilla de la tabla hash
            for (int i = 0; i < capacidad; i++) {
                LinkedList<Dir<K, V>> balde = tabla[i];
                int tamanoBalde = balde.size();
                
                // Recorremos el balde de forma indexada adaptándonos a tu LinkedList
                for (int j = 0; j < tamanoBalde; j++) {
                    Dir<K, V> conjunto = balde.get(j);
                    if (conjunto != null) {
                        listaValores.add(conjunto.valor); // Extraemos solo el objeto de valor útil
                    }
                }
            }
        } catch (ListException e) {
            System.err.println("Error interno al obtener valores del diccionario: " + e.getMessage());
        }
        
        return listaValores;
    }
    
    /**
     * Devuelve una lista de Java con todos los pares clave-valor (Dir) completos.
     * mostrando explícitamente tanto los identificadores (claves) como el objeto completo.
     */
    public List<Dir<K, V>> obtenerRegistros() {
        List<Dir<K, V>> listaRegistros = new ArrayList<>();
        
        try {
            for (int i = 0; i < capacidad; i++) {
                LinkedList<Dir<K, V>> balde = tabla[i];
                int tamanoBalde = balde.size();
                
                for (int j = 0; j < tamanoBalde; j++) {
                    Dir<K, V> conjunto = balde.get(j);
                    if (conjunto != null) {
                        listaRegistros.add(conjunto); // Guardamos el contenedor par completo
                    }
                }
            }
        } catch (ListException e) {
            System.err.println("Error interno al obtener registros del diccionario: " + e.getMessage());
        }
        
        return listaRegistros;
    }
}
