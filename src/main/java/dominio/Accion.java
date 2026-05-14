package dominio;

public class Accion {

    public enum TipoAccion {
        REGISTRAR_ESTUDIANTE,
        INSCRIBIR_ESTUDIANTE,
        BAJA_ESTUDIANTE,
        AGREGAR_CALIFICACION,
        MODIFICAR_CALIFICACION
    }

    private final TipoAccion tipo;

    private final Estudiante estudiante;

    private final Curso curso;

    private final double calificacionAnterior;

    private final int indiceCalificacion;

    public static Accion registrarEstudiante(Estudiante estudiante) {
        return new Accion(TipoAccion.REGISTRAR_ESTUDIANTE, estudiante, null, -1, -1);
    }

    public static Accion inscribirEstudiante(Estudiante estudiante, Curso curso) {
        return new Accion(TipoAccion.INSCRIBIR_ESTUDIANTE, estudiante, curso, -1, -1);
    }

    public static Accion bajaEstudiante(Estudiante estudiante, Curso curso) {
        return new Accion(TipoAccion.BAJA_ESTUDIANTE, estudiante, curso, -1, -1);
    }

    public static Accion agregarCalificacion(Estudiante estudiante) {
        return new Accion(TipoAccion.AGREGAR_CALIFICACION, estudiante, null, -1, -1);
    }

    public static Accion modificarCalificacion(Estudiante estudiante,
            int indiceCalificacion,
            double calificacionAnterior) {
        return new Accion(TipoAccion.MODIFICAR_CALIFICACION, estudiante,
                null, indiceCalificacion, calificacionAnterior);
    }

    private Accion(TipoAccion tipo, Estudiante estudiante, Curso curso,
            int indiceCalificacion, double calificacionAnterior) {
        this.tipo = tipo;
        this.estudiante = estudiante;
        this.curso = curso;
        this.indiceCalificacion = indiceCalificacion;
        this.calificacionAnterior = calificacionAnterior;
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

    public double getCalificacionAnterior() {
        return calificacionAnterior;
    }

    public int getIndiceCalificacion() {
        return indiceCalificacion;
    }

    @Override
    public String toString() {
        return switch (tipo) {
            case REGISTRAR_ESTUDIANTE ->
                "Accion{REGISTRAR_ESTUDIANTE, matricula='" + estudiante.getMatricula() + "'}";
            case INSCRIBIR_ESTUDIANTE ->
                "Accion{INSCRIBIR_ESTUDIANTE, matricula='" + estudiante.getMatricula()
                + "', curso='" + curso.getClave() + "'}";
            case BAJA_ESTUDIANTE ->
                "Accion{BAJA_ESTUDIANTE, matricula='" + estudiante.getMatricula()
                + "', curso='" + curso.getClave() + "'}";
            case AGREGAR_CALIFICACION ->
                "Accion{AGREGAR_CALIFICACION, matricula='" + estudiante.getMatricula() + "'}";
            case MODIFICAR_CALIFICACION ->
                "Accion{MODIFICAR_CALIFICACION, matricula='" + estudiante.getMatricula()
                + "', indice=" + indiceCalificacion
                + ", valorAnterior=" + calificacionAnterior + '}';
        };
    }
}
