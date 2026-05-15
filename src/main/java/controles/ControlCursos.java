package controles;

import dominio.Curso;
import excepciones.ControlException;
import listas.DoubleLinkedList;

/**
 *
 * @author Andre
 */
public class ControlCursos {
    private static ControlCursos instancia;
    
    private static DoubleLinkedList<Curso> cursos;
    
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
    
    /**
     * Agrega el curso al sistema
     * 
     * @param curso a agregar
     */
    public void agregarCurso(Curso curso) {
        if (curso.getClave() == null || curso.getClave().isBlank()) {
            throw new ControlException("Clave vacía");
        }
        if (curso.getNombre() == null || curso.getNombre().isBlank()) {
            throw new ControlException("Nombre vacío");
        }
        if (curso.getCapacidad() < 1) {
            throw new ControlException("El curso debe poder tener al menos un estudiante");
        }
        if (cursos.indexOf(curso) != -1) {
            throw new ControlException("El curso ya existe");
        }
        cursos.append(curso);
    }
}