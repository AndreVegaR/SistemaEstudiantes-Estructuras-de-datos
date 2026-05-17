package controles;

import dominio.Curso;
import dominio.Estudiante;
import excepciones.ControlException;
import java.util.List;
import listas.DoubleLinkedList;
import listas.LinkedList;

/**
 *
 * @author Andre
 */
public class ControlCursos {
    private static ControlCursos instancia;
    
    private static DoubleLinkedList<Curso> cursos;
    
    private ControlCursos() {
        cursos = new DoubleLinkedList<>();
    }
    /**
     * Método que regresa el singleton del control
     * para la gestión de cursos
     * 
     * @return la instancia única
     */
    public static ControlCursos singleton() {
        if (instancia == null) {
            instancia = new ControlCursos();
        }
        return instancia;
    }
    
    
    public DoubleLinkedList<Curso> obtenerCursos() {
        return cursos;
    }
    
    
    /**
     * Agrega el curso al sistema
     * 
     * @param curso a agregar
     */
    public void agregarCurso(Curso curso) {
        if (curso == null) {
            throw new ControlException("Curso vacío");
        }
        if (curso.getClave() == null|| curso.getClave().isBlank()) {
            throw new ControlException("Clave vacía");
        }

        if (curso.getNombre() == null || curso.getNombre().isBlank()) {
            throw new ControlException("Nombre vacío");
        }
        
        if (curso.getCapacidad() == 0) {
            throw new ControlException("Capacidad inválida");
        }

        if (cursos.indexOf(curso) != -1) {
            throw new ControlException("El curso ya existe");
        }
        cursos.append(curso);
    }
    /**
     * Método que elimina un curso
     * @param curso el curso a eliminar
     */
    public void eliminarCurso(Curso curso) {
        if (curso == null) {
            throw new ControlException("Curso vacío");
        }
        if (cursos.indexOf(curso) == -1) {
            throw new ControlException("El curso no existe");
        }
        cursos.remove(curso);
        
        //Elimina el curso de todos los estudiantes inscritos
        LinkedList<Estudiante> estudiantes = curso.getEstudiantes();
        for (int i = 0; i < estudiantes.size(); i++) {
            estudiantes.get(i).eliminarCalificacion(curso);
        }
    }
    /**
     * Método que obtiene un curso
     * @param clave la clave del curso a buscar
     * @return el curso si lo encuentra
     */
    public Curso consultarCurso(String clave) {
        if (clave == null || clave.isBlank()) {
            throw new ControlException("Clave vacía");
        }
        for (int i = 0; i < cursos.size(); i++) {
            Curso curso = cursos.get(i);
            if (curso.getClave().equals(clave)) {
                return curso;
            }
        }
        return null;
    }
    /**
     * Método que devuelve todos los cursos para mostrarlos en alguna pantalla
     * @return una lista doblemente enlazada de cursos
     */
    public DoubleLinkedList<Curso> listarCursos() {
       return cursos;
    }
}