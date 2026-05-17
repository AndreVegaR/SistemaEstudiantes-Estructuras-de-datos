package dominio;

public class Accion {

    public enum TipoAccion {
        //Las acciones de los estudiantes
        REGISTRAR_ESTUDIANTE,
        ELIMINAR_ESTUDIANTE,

        //Las acciones de los cursos
        AGREGAR_CURSO,
        ELIMINAR_CURSO,

        //Las acciones de las inscripciones, donde un estudiante se inscribe a un curso
        INSCRIBIR_ESTUDIANTE,
        DESINSCRIBIR_ESTUDIANTE,

        // Las acciones de las calificaciones
        AGREGAR_CALIFICACION,
        MODIFICAR_CALIFICACION,

        // La rotación del rol
        ROTAR_ROL
        
    }
    
    //Se supone que con estas referencias ya se pueden deshacer todas las acciones
    private final TipoAccion tipo;

    private final Estudiante estudiante;
    private final Curso curso;
    private final Calificacion calificacion;

    private final double calificacionAnterior;
    private final int indiceCalificacion;

    private final Estudiante estudianteAnteriorRol;

    public static Accion registrarEstudiante(Estudiante estudiante) {
        return new Accion(TipoAccion.REGISTRAR_ESTUDIANTE,estudiante,null,null,-1,-1,null);
    }
    public static Accion eliminarEstudiante(Estudiante estudiante) {
        return new Accion(TipoAccion.ELIMINAR_ESTUDIANTE,estudiante,null,null,-1,-1,null);
    }

    public static Accion agregarCurso(Curso curso) {
        return new Accion(TipoAccion.AGREGAR_CURSO,null,curso,null,-1,-1,null);
    }

    public static Accion eliminarCurso(Curso curso) {
        return new Accion(TipoAccion.ELIMINAR_CURSO,null,curso,null,-1,-1,null);
    }

    public static Accion inscribirEstudiante(Estudiante estudiante,Curso curso) {
        return new Accion(TipoAccion.INSCRIBIR_ESTUDIANTE,estudiante,curso,null,-1,-1,null);
    }

    public static Accion desinscribirEstudiante(Estudiante estudiante,Curso curso) {
        return new Accion(TipoAccion.DESINSCRIBIR_ESTUDIANTE,estudiante,curso,null,-1,-1,null);
    }

    public static Accion agregarCalificacion( Estudiante estudiante, Calificacion calificacion) {
        return new Accion(TipoAccion.AGREGAR_CALIFICACION,estudiante,null,calificacion,-1,-1,null);
    }

    public static Accion modificarCalificacion(Estudiante estudiante,int indiceCalificacion,double calificacionAnterior) {
        return new Accion(TipoAccion.MODIFICAR_CALIFICACION,estudiante,null, null,indiceCalificacion,calificacionAnterior,null);
    }

    public static Accion rotarRol(Curso curso, Estudiante estudianteAnteriorRol) {
        return new Accion(TipoAccion.ROTAR_ROL,null,curso,null,-1,-1,estudianteAnteriorRol);
    }

    private Accion( TipoAccion tipo, Estudiante estudiante, Curso curso, Calificacion calificacion, int indiceCalificacion, double calificacionAnterior, Estudiante estudianteAnteriorRol) {
        this.tipo = tipo;
        this.estudiante = estudiante;
        this.curso = curso;
        this.calificacion = calificacion;
        this.indiceCalificacion = indiceCalificacion;
        this.calificacionAnterior = calificacionAnterior;
        this.estudianteAnteriorRol = estudianteAnteriorRol;
    }
    public TipoAccion getTipo() {
        return tipo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public Calificacion getCalificacion() {
        return calificacion;
    }

    public double getCalificacionAnterior() {
        return calificacionAnterior;
    }

    public int getIndiceCalificacion() {
        return indiceCalificacion;
    }

    public Estudiante getEstudianteAnteriorRol() {
        return estudianteAnteriorRol;
    }

    /*
    Método que imprime una cadena con una acción realizada en específico
    */
    @Override
    public String toString() {
        return switch (tipo) {
            case REGISTRAR_ESTUDIANTE ->
                "Accion{REGISTRAR_ESTUDIANTE, matricula='"
                + estudiante.getMatricula() + "'}";
            case ELIMINAR_ESTUDIANTE ->
                "Accion{ELIMINAR_ESTUDIANTE, matricula='"
                + estudiante.getMatricula() + "'}";
            case AGREGAR_CURSO ->
                "Accion{AGREGAR_CURSO, curso='"
                + curso.getClave() + "'}";
            case ELIMINAR_CURSO ->
                "Accion{ELIMINAR_CURSO, curso='"
                + curso.getClave() + "'}";
            case INSCRIBIR_ESTUDIANTE ->
                "Accion{INSCRIBIR_ESTUDIANTE, matricula='"
                + estudiante.getMatricula()
                + "', curso='"
                + curso.getClave() + "'}";
            case DESINSCRIBIR_ESTUDIANTE ->
                "Accion{DESINSCRIBIR_ESTUDIANTE, matricula='"
                + estudiante.getMatricula()
                + "', curso='"
                + curso.getClave() + "'}";
            case AGREGAR_CALIFICACION ->
                "Accion{AGREGAR_CALIFICACION, matricula='"
                + estudiante.getMatricula() + "'}";
            case MODIFICAR_CALIFICACION ->
                "Accion{MODIFICAR_CALIFICACION, matricula='"
                + estudiante.getMatricula()
                + "', indice="
                + indiceCalificacion
                + ", valorAnterior="
                + calificacionAnterior + "}";
            case ROTAR_ROL ->
                "Accion{ROTAR_ROL, curso='"
                + curso.getClave()
                + "', anterior='"
                + estudianteAnteriorRol.getMatricula() + "'}";
        };
    }
}
