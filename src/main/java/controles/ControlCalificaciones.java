package controles;

import colas.IQueue;
import controles.ControlCursos;
import controles.ControlEstudiantes;
import listas.LinkedListQueue;
import dominio.Calificacion;
import dominio.Curso;
import dominio.Estudiante;
import dominio.SolicitudCalificacion;
import excepciones.ControlException;
import excepciones.QueueException;
import pilas.IStack;
import pilas.LinkedListStack;
import dominio.Accion;

public class ControlCalificaciones {
    private static ControlCalificaciones instancia;
    private IQueue<SolicitudCalificacion> colaSolicitudes;
    private IStack<Accion> pilaAcciones;
    
    private ControlCalificaciones() {
        colaSolicitudes = new LinkedListQueue<>();
        pilaAcciones = new LinkedListStack<>();
    }
    
    public static ControlCalificaciones singleton() {
        if (instancia == null) {
            instancia = new ControlCalificaciones();
        }
        return instancia;
    }
    
    public void enviarSolicitud(String matricula, String claveCurso, double valor) {
        if (matricula == null || matricula.isBlank()) {
            throw new ControlException("Matrícula inválida");
        }
        if (claveCurso == null || claveCurso.isBlank()) {
            throw new ControlException("Clave de curso inválida");
        }
        if (valor < 0 || valor > 10) {
            throw new ControlException("Calificación debe estar entre 0 y 10");
        }
        
        SolicitudCalificacion solicitud = new SolicitudCalificacion(matricula, claveCurso, valor);
        try {
            colaSolicitudes.enqueue(solicitud);
        } catch (QueueException e) {
            throw new ControlException("Error al enviar solicitud: " + e.getMessage());
        }
    }
    
    public void procesarSiguienteSolicitud() {
        SolicitudCalificacion solicitud;
        try {
            if (colaSolicitudes.isEmpty()) {
                throw new ControlException("No hay solicitudes pendientes");
            }
            solicitud = colaSolicitudes.dequeue();
        } catch (QueueException e) {
            throw new ControlException("Error al procesar solicitud: " + e.getMessage());
        }
        
        ControlEstudiantes ce = ControlEstudiantes.singleton();
        ControlCursos cc = ControlCursos.singleton();
        
        Estudiante estudiante = ce.consultarEstudiante(solicitud.getMatricula());
        Curso curso = cc.consultarCurso(solicitud.getClaveCurso());
        
        if (estudiante == null) {
            throw new ControlException("Estudiante no encontrado");
        }
        if (curso == null) {
            throw new ControlException("Curso no encontrado");
        }
        
        Calificacion calificacionAnterior = null;
        int indiceAnterior = -1;
        
        for (int i = 0; i < estudiante.getCalificaciones().size(); i++) {
            Calificacion c = estudiante.getCalificaciones().get(i);
            if (c.getCurso().equals(curso)) {
                calificacionAnterior = c;
                indiceAnterior = i;
                break;
            }
        }
        
        if (calificacionAnterior != null) {
            double valorAnterior = calificacionAnterior.getValor();
            calificacionAnterior.setValor((int)solicitud.getCalificacion());
            try {
                pilaAcciones.push(Accion.modificarCalificacion(estudiante, indiceAnterior, valorAnterior));
            } catch (Exception e) {
                throw new ControlException("Error al guardar acción: " + e.getMessage());
            }
        } else {
            Calificacion nuevaCalificacion = new Calificacion(
                (int)solicitud.getCalificacion(), 
                estudiante, 
                curso
            );
            estudiante.agregarCalificacion(nuevaCalificacion);
            try {
                pilaAcciones.push(Accion.agregarCalificacion(estudiante, nuevaCalificacion));
            } catch (Exception e) {
                throw new ControlException("Error al guardar acción: " + e.getMessage());
            }
        }
    }
    
    public void deshacerUltimaCalificacion() {
        if (pilaAcciones.isEmpty()) {
            throw new ControlException("No hay operaciones de calificación para deshacer");
        }
        
        Accion accion = pilaAcciones.pop();
        
        switch (accion.getTipo()) {
            case AGREGAR_CALIFICACION:
                Estudiante e = accion.getEstudiante();
                Calificacion c = accion.getCalificacion();
                e.getCalificaciones().remove(c);
                break;
                
            case MODIFICAR_CALIFICACION:
                Estudiante est = accion.getEstudiante();
                int indice = accion.getIndiceCalificacion();
                double valorAnterior = accion.getCalificacionAnterior();
                est.getCalificaciones().get(indice).setValor((int)valorAnterior);
                break;
        }
    }
    
    public boolean haySolicitudesPendientes() {
        return !colaSolicitudes.isEmpty();
    }
    
    public int totalSolicitudesPendientes() {
        try {
            return colaSolicitudes.size();
        } catch (Exception e) {
            return 0;
        }
    }
}