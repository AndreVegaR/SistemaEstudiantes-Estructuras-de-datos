package controles;

import dominio.Curso;
import dominio.Estudiante;

/**
 * Fachada que centraliza todos los controles
 * 
 * @author Andre
 */
public class Control {
    private static Control instancia;

    private Control() {
        this.cp = ControlPantallas.singleton();
        this.ce = ControlEstudiantes.singleton();
        this.cc = ControlCursos.singleton();
    }
    
    /**
     * Método que regresa el singleton del control
     * para la gestión general del sistema
     * 
     * @return la instancia única
     */
    public static Control singleton() {
        if (instancia == null) {
            instancia = new Control();
        }
        return instancia;
    }
    
    //Controles que posee
    private final ControlPantallas cp;
    private final ControlEstudiantes ce;
    private final ControlCursos cc;
    
    //CONTROL PANTALLAS//
    /** Encapsula qué pantalla debería ser la inicial al arrancar el programa */
    public void pantallaInicial() { cp.pantallaInicial(); }
    
    /** Navega al menú donde se eligen las opciones */
    public void navegarMenuPrincial() {cp.navegarMenuPrincipal(); }
    
    
    //CONTROL ESTUDIANTES
    /**
     * Busca un estudiante por matrícula
     * 
     * @param matricula para consultar
     * 
     * @return el estudiante
     */
    public Estudiante consultarEstudiante(String matricula){ return ce.consultarEstudiante(matricula); }
     
    /**
     * Agrega un estudiante al sistema. Internamente, lo almacena
     * en las estructuras necesarias para todas las necesidades
     * del sistema
     * 
     * @param estudiante a agregar
     */
    public void agregarEstudiante(Estudiante estudiante){ ce.agregarEstudiante(estudiante); }
    
    /**
     * Elimina un estudiante del sistema removiéndolo de todas
     * las estructuras que lo contengan
     * 
     * @param estudiante a eliminar
     */
    public void eliminarEstudiante(Estudiante estudiante){ ce.eliminarEstudiante(estudiante); }
    
    
    //CONTROL CURSOS
    /**
     * Agrega el curso al sistema
     * 
     * @param curso a agregar
     */
    public void agregarCurso(Curso curso) { cc.agregarCurso(curso); }
}