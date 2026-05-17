/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controles;

import dominio.Curso;
import dominio.Estudiante;
import excepciones.ControlException;
import listas.LinkedList;

/**
 *
 * @author Tungs
 */
public class ControlInscripciones {
    private static ControlInscripciones instancia;

    private final ControlEstudiantes ce;
    private final ControlCursos cc;

    private ControlInscripciones() {
        ce = ControlEstudiantes.singleton();
        cc = ControlCursos.singleton();
    }
    
    /**
     * Método que regresa el singleton del control
     * para la gestión de inscripciones
     *
     * @return la instancia única
     */
    public static ControlInscripciones singleton() {

        if (instancia == null) {
            instancia = new ControlInscripciones();
        }

        return instancia;
    }
    
    /**
     * Método que inscribe un estudiante a un curso, si el curso está lleno, lo mete a una lista de espera
     * @param matricula la matrícula del estudiante a inscribir
     * @param claveCurso la clave del curso a la que se inscribirá
     */
    public void inscribirEstudiante(String matricula, String claveCurso) {
        if (matricula == null || matricula.isBlank()) {
            throw new ControlException("Matrícula inválida");
        }
        if (claveCurso == null || claveCurso.isBlank()) {
            throw new ControlException("Clave inválida");
        }
        Estudiante estudiante =ce.consultarEstudiante(matricula);
        Curso curso =cc.consultarCurso(claveCurso);

        if (curso.getEstudiantes().indexOf(estudiante) != -1) {
            throw new ControlException("El estudiante ya está inscrito en el curso"
            );
        }
        if (curso.getListaEspera().indexOf(estudiante) != -1) {
            throw new ControlException("El estudiante ya está en lista de espera");
        }
        try {
            if (curso.getEstudiantes().size() < curso.getCapacidad()) {
                curso.agregarEstudiante(estudiante);
            } else {
                curso.getListaEspera().append(estudiante);
            }
        } catch (Exception e) {
            throw new ControlException(e);
        }
    }
    
    
    /**
     * Elimina un estudiante de un curso.
     *
     * Si existe lista de espera inserta al primer estudiante en la cola
     * 
     *
     * @param matricula del estudiante
     * @param claveCurso del curso
     */
    public void bajaEstudiante(String matricula,String claveCurso) {
        if (matricula == null || matricula.isBlank()) {
            throw new ControlException("Matrícula inválida");
        }
        if (claveCurso == null || claveCurso.isBlank()) {
            throw new ControlException("Clave inválida");
        }
        Estudiante estudiante =ce.consultarEstudiante(matricula);
        Curso curso = cc.consultarCurso(claveCurso);
        if (curso.getEstudiantes().indexOf(estudiante) == -1) {
            throw new ControlException("El estudiante no está inscrito en el curso"
            );
        }
        try {
            curso.eliminarEstudiante(estudiante);
            estudiante.eliminarCalificacion(curso);
            if (!curso.getListaEspera().empty()) {
                Estudiante siguiente =curso.getListaEspera().removeExtraer(0);
                curso.agregarEstudiante(siguiente);
            }
        } catch (Exception e) {
            throw new ControlException(e);
        }
    }
    
    /**
     * Método que obtiene a los estudiantes inscritos en un curso
     * @param claveCurso la clave del curso en el que están inscritos
     * @return  una lista ligada con los estudiantes
     */
    public LinkedList<Estudiante>consultarInscritos(String claveCurso) {
        if (claveCurso == null || claveCurso.isBlank()) {
            throw new ControlException("Clave inválida");
        }
        Curso curso =cc.consultarCurso(claveCurso);
        return curso.getEstudiantes();
    }
    
    /**
     * Método que rota el rol actual del curso
     * @param claveCurso la clave del curso a rotar
     * @return 
     */
    public Estudiante rotarRol(String claveCurso) {
        if (claveCurso == null || claveCurso.isBlank()) {
            throw new ControlException("Clave inválida");
        }
        Curso curso =cc.consultarCurso(claveCurso);
        if (curso.getListaRoles().empty()) {
            throw new ControlException( "No hay estudiantes en lista de roles"
            );
        }
        try {
            return curso.getListaRoles().rotar();
        } catch (Exception e) {
            throw new ControlException(e);
        }
    }
}
