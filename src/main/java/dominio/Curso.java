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
        this.listaEspera = new DoubleCircularLinkedList<>();
    }
    
     public String mostrarEstudiantes() {
        String resultado = "Estudiantes del curso " + this.nombre + " (" + this.clave + "):\n";
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante e = estudiantes.get(i);
            String estudiante = e.nombreCompleto() + " (" + e.getMatricula() + "): " + e.calificacionCurso(this);
            resultado += "- " + estudiante + "\n"; 
        }
        return resultado;
    }
    
    public void rotarLider() {
        listaRoles.rotar();
    }
    
    public Estudiante getLider() {
        return listaRoles.actual();
    }
    
    
    public void agregarListaEspera(Estudiante estudiante) {
        listaEspera.append(estudiante);
    }
    
    public void eliminarListaEspera(Estudiante estudiante) {
        listaEspera.remove(estudiante);
    }
    
    /**
     * Maneja la inserción de estudiantes
     * 
     * @param estudiante a agregar
     */
    public void agregarEstudiante(Estudiante estudiante) {
        if (capacidad == estudiantes.size()) {
            throw new ListException("Curso lleno");
        }
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
    
    public boolean lleno() {
        return capacidad == estudiantes.size();
    }
    
    public boolean existeEstudiante(Estudiante e) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).equals(e)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean existeEsperando(Estudiante e) {
        return listaEspera.indexOf(e) != -1;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.clave);
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
