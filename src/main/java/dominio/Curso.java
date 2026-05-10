package dominio;

import listas.ArrayList;
import listas.LinkedList;

/**
 *
 * @author Andre
 */
public class Curso {
    private String clave;
    private String nombre;
    private LinkedList<Estudiante> estudiantes;

    public Curso(String clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
        this.estudiantes = new LinkedList();
    }

    
    
}
