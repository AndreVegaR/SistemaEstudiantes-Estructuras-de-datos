package dominio;

import excepciones.ListException;
import java.util.Objects;
import listas.CircularLinkedList;
import listas.DoubleCircularLinkedList;
import listas.LinkedList;

/**
 *
 * @author Andre
 */
public class Curso {
    private String clave;
    private String nombre;
    private int capacidad;
    private LinkedList<Estudiante> estudiantes;
    private CircularLinkedList<Estudiante> listaRoles;
    private DoubleCircularLinkedList<Estudiante> listaEspera;
    
    public Curso(String clave, String nombre, int capacidad) {
        this.clave = clave;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estudiantes = new LinkedList();
        this.listaRoles = new CircularLinkedList();
        estudiantes = new LinkedList<>();
        listaRoles = new CircularLinkedList<>();
        listaEspera = new DoubleCircularLinkedList<>();
    }
    
    /**
     * Maneja la inserción de estudiantes
     * 
     * @param estudiante a agregar
     */
    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.append(estudiante);
        listaRoles.append(estudiante);
    }
    /**
     * Método que maneja la eliminación de estudiantes
     * @param estudiante el estudiante a eliminar
     */
     public void eliminarEstudiante(Estudiante estudiante) {
        estudiantes.remove(estudiante);
        listaRoles.remove(estudiante);
    }

    public CircularLinkedList<Estudiante> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(CircularLinkedList<Estudiante> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public DoubleCircularLinkedList<Estudiante> getListaEspera() {
        return listaEspera;
    }

    public void setListaEspera(DoubleCircularLinkedList<Estudiante> listaEspera) {
        this.listaEspera = listaEspera;
    }
     
    @Override
    public String toString() {
        return nombre + " [ " + clave + "]: " + estudiantes.size() + " de " + capacidad;
    }
    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(LinkedList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Curso other = (Curso) obj;
        return Objects.equals(this.clave, other.clave);
    }
    
    
}
