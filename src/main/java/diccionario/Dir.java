/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diccionario;

/**
 *
 * @author aaron
 * 
 * Clase auxiliar generica clave- valor, se utiliza
 * principalmente dentro de diccionario
 * 
 * K tipo de datos de la vlave
 * V tipo de dato del valor
 */
public class Dir<K, V> {
    
    public K clave;
    public V valor;
    
    
    /**
     * Contructor clave valor
     * @param clave
     * @param valor 
     */
    public Dir(K clave, V valor){
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }
    
    
    
    
}
