package controles;

import dominio.Accion;
import dominio.Calificacion;
import dominio.Curso;
import dominio.Estudiante;
import dominio.SolicitudCalificacion;
import excepciones.ControlException;
import excepciones.ListException;
import excepciones.StackException;
import listas.ArrayList;
import listas.DoubleLinkedList;
import observadores.*;
import pantallas.PantallaReporteEstudiantes;
import pilas.IStack;
import pilas.LinkedListStack;

/**
 * Fachada que centraliza todos los controles
 * 
 * @author Andre
 */
public class Control {
    private static Control instancia;

    private final IStack<Accion> pilaAcciones;

    private final ControlPantallas cp;
    private final ControlEstudiantes ce;
    private final ControlCursos cc;
    private final ControlInscripciones ci;
    private final ControlCalificaciones ccal;

    /**
     * Constructor privado singleton
     */
    private Control() {
        this.cp = ControlPantallas.singleton();
        this.ce = ControlEstudiantes.singleton();
        this.cc = ControlCursos.singleton();
        this.ci = ControlInscripciones.singleton();
        this.pilaAcciones = new LinkedListStack<>();
        this.ccal = ControlCalificaciones.singleton();
    }
    /**
     * Singleton del control principal
     *
     * @return instancia única
     */
    public static Control singleton() {
        if (instancia == null) {
            instancia = new Control();
        }
        return instancia;
    }

    public void pantallaInicial() {
        cp.pantallaInicial();
    }

    /** Navega al menú principal */
    public void navegarMenuPrincial() {
        cp.navegarMenuPrincipal();
    }
    
    /** Navega al menú donde se administran los cursos */
    public void navegarPantallaCursos() {
        cp.navegarPantallaCursos();
    }
    
    /** Navega al menú donde se administran los estudiantes */
    public void navegarPantallaEstudiantes() {
        cp.navegarPantallaEstudiantes();
    }
    
    /** Navega a la pantalla de calificaciones */
    public void navegarPantallaCalificaciones(){
        cp.navegarPantallaCalificaciones();
    }
    
    /** Navega a los estudiantes por promedio */
    public void navegarPantallaReporteEstudiantes() {
        cp.navegarPantallaReporteEstudiantes();
    }
    
    /** Navega a la pantalla de inscripciones */
    public void navegarPantallaInscripciones() {
        cp.navegarPantallaInscripciones();
    }
    
    /** Diálogo para buscar por matrícula */
    public void abrirBuscarEstudiante(IObservador observador, IRemovedor removedor) {
        cp.abrirBuscarEstudiante(observador, removedor);
    }
    
    /** Diálogo para consultar la lista de espera de un curso */
    public void abrirListaEspera(Curso curso) {
        cp.abrirListaEspera(curso);
    }
    
  
    /**
     * Consulta todos los estudiantes
     * 
     * @return estudiantes
     */
    public ArrayList<Estudiante> consultarEstudiantes() {
        return ce.obtenerEstudiantes();
    }

    /**
     * Consulta a un estudiante por su matrícula
     * @param matricula la matrícula del estudiante
     * @return el estudiante
     */
    public Estudiante consultarEstudiante(String matricula) {
        return ce.consultarEstudiante(matricula);
    }

    /**
     * Registra estudiante
     *
     * @param estudiante estudiante a agregar
     */
    public void agregarEstudiante(Estudiante estudiante) {
        ce.agregarEstudiante(estudiante);
        pilaAcciones.push(Accion.registrarEstudiante(estudiante));
    }

    /**
     * Elimina estudiante
     *
     * @param estudiante estudiante a eliminar
     */
    public void eliminarEstudiante(Estudiante estudiante) {
        pilaAcciones.push(Accion.eliminarEstudiante(estudiante));
        ce.eliminarEstudiante(estudiante);
    }
    
    /**
     * Obtiene los estudiantes por promedio
     * 
     * @return estudiantes
     */
    public ArrayList<Estudiante> obtenerEstudiantesPromedio() {
        return ce.obtenerEstudiantesPromedio();
    }
    
    /**
     * Agrega curso
     *
     * @param curso curso a agregar
     */
    public void agregarCurso(Curso curso) {
        cc.agregarCurso(curso);
        pilaAcciones.push(Accion.agregarCurso(curso));
    }
    /**
     * Elimina curso
     *
     * @param curso curso a eliminar
     */
    public void eliminarCurso(Curso curso) {
        pilaAcciones.push(Accion.eliminarCurso(curso));
        cc.eliminarCurso(curso);
        
    }

    /**
     * Consulta curso
     *
     * @param clave clave curso
     * @return curso encontrado
     */
    public Curso consultarCurso(String clave) {
        return cc.consultarCurso(clave);
    }

    
    public DoubleLinkedList<Curso> obtenerCursos() {
        return cc.obtenerCursos();
    }
    
    /**
     * Inscribe estudiante en curso
     *
     * @param matricula matrícula estudiante
     * @param claveCurso clave curso
     */
    public void inscribirEstudiante(String matricula, String claveCurso) {
        ci.inscribirEstudiante(matricula, claveCurso);
        Estudiante estudiante = ce.consultarEstudiante(matricula);
        Curso curso = cc.consultarCurso(claveCurso);
        pilaAcciones.push(Accion.inscribirEstudiante(estudiante,curso));
    }

    /**
     * Da de baja estudiante de curso
     *
     * @param matricula matrícula estudiante
     * @param claveCurso clave curso
     */
    public void bajaEstudianteCurso(String matricula, String claveCurso) {
        Estudiante estudiante = ce.consultarEstudiante(matricula);
        Curso curso = cc.consultarCurso(claveCurso);
        ci.bajaEstudiante(matricula, claveCurso);
        pilaAcciones.push(Accion.desinscribirEstudiante(estudiante,curso));
    }
    public ArrayList<SolicitudCalificacion> consultarSolicitudes(){
        return ccal.obtenerSolicitudesPendientes();

    }

    public void enviarSolicitudCalificacion(String matricula, String claveCurso, double calificacion) {
        ccal.enviarSolicitud(matricula, claveCurso, calificacion);
    }

    /**
     * Procesa la siguiente solicitud de calificación pendiente
     */
    public void procesarSiguienteSolicitudCalificacion() {
        ccal.procesarSiguienteSolicitud();
    }

    /**
     * Rota tutor/líder de curso
     *
     * @param claveCurso clave curso
     * @return estudiante seleccionado
     */
    public Estudiante rotarRol(String claveCurso) {
        return ci.rotarRol(claveCurso);
    }
    /**
     * Lo que pidió el vega
     */
    public Accion deshacerUltimaAccion() {
        
        try {
        Accion accion = pilaAcciones.pop();    
            switch (accion.getTipo()) {
                case REGISTRAR_ESTUDIANTE ->
                    ce.eliminarEstudiante(accion.getEstudiante());

                case ELIMINAR_ESTUDIANTE ->
                    ce.agregarEstudiante(accion.getEstudiante());

                case AGREGAR_CURSO ->
                    cc.eliminarCurso(accion.getCurso());

                case ELIMINAR_CURSO ->
                    cc.agregarCurso(accion.getCurso());

                case INSCRIBIR_ESTUDIANTE ->
                    ci.bajaEstudiante(
                            accion.getEstudiante().getMatricula(),
                            accion.getCurso().getClave()
                    );

                case DESINSCRIBIR_ESTUDIANTE ->
                    ci.inscribirEstudiante(
                            accion.getEstudiante().getMatricula(),
                            accion.getCurso().getClave()
                    );

            }

            return accion;
        } catch (StackException e) {
            throw new ControlException("No hay acciones para deshacer");
        }
    }
}