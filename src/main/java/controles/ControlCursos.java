package controles;

import dominio.Curso;
import dominio.Estudiante;
import excepciones.ControlException;
import java.util.List;
import listas.DoubleLinkedList;
import listas.LinkedList;
import diccionario.Diccionario;

/**
 *
 * @author Andre
 */
public class ControlCursos {
    private static ControlCursos instancia;
    
    private Diccionario<String, Curso> catalogoCursos;
    
    private ControlCursos() {
        catalogoCursos = new Diccionario<>(20);
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
        DoubleLinkedList<Curso> listaCursos = new DoubleLinkedList<>();
        List<Curso> cursos = catalogoCursos.obtenerValores();
        for (Curso curso : cursos) {
            listaCursos.append(curso);
        }
        return listaCursos;
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

        if (catalogoCursos.recuperar(curso.getClave()) != null) {
            throw new ControlException("El curso ya existe");
        }
        catalogoCursos.agregar(curso.getClave(), curso);
    }
    /**
     * Método que elimina un curso
     * @param curso el curso a eliminar
     */
    public void eliminarCurso(Curso curso) {
        if (curso == null) {
            throw new ControlException("Curso vacío");
        }
        if (catalogoCursos.recuperar(curso.getClave()) == null) {
            throw new ControlException("El curso no existe");
        }
        catalogoCursos.eliminar(curso.getClave());
        
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
        return catalogoCursos.recuperar(clave);
    }
    /**
     * Método que devuelve todos los cursos para mostrarlos en alguna pantalla
     * @return una lista doblemente enlazada de cursos
     */
    public DoubleLinkedList<Curso> listarCursos() {
        DoubleLinkedList<Curso> listaCursos = new DoubleLinkedList<>();
        List<Curso> cursos = catalogoCursos.obtenerValores();
        for (Curso curso : cursos) {
            listaCursos.append(curso);
        }
        return listaCursos;
    }
}