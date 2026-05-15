package dominio;

public class SolicitudCalificacion {

    /**
     * Tipo de solicitud de calificación.
     */
    public enum TipoSolicitud {
        NUEVA,
        MODIFICACION
    }

    /**
     * Matrícula del estudiante al que pertenece la solicitud.
     */
    private final String matricula;

    /**
     * Clave del curso al que corresponde la calificación.
     */
    private final String claveCurso;

    /**
     * Nueva calificación a aplicar (0.0 – 10.0).
     */
    private final double calificacion;

    /**
     * Tipo de solicitud: NUEVA o MODIFICACION.
     */
    private final TipoSolicitud tipo;

    private final int indice;

    public SolicitudCalificacion(String matricula, String claveCurso, double calificacion) {
        this(matricula, claveCurso, calificacion, TipoSolicitud.NUEVA, -1);
    }

    public SolicitudCalificacion(String matricula, String claveCurso,
            double calificacion, int indice) {
        this(matricula, claveCurso, calificacion, TipoSolicitud.MODIFICACION, indice);
    }

    private SolicitudCalificacion(String matricula, String claveCurso,
            double calificacion, TipoSolicitud tipo, int indice) {
        if (calificacion < 0.0 || calificacion > 10.0) {
            throw new IllegalArgumentException(
                    "La calificación debe estar entre 0.0 y 10.0. Valor recibido: " + calificacion);
        }
        if (tipo == TipoSolicitud.MODIFICACION && indice < 0) {
            throw new IllegalArgumentException(
                    "El índice de modificación no puede ser negativo.");
        }
        this.matricula = matricula;
        this.claveCurso = claveCurso;
        this.calificacion = calificacion;
        this.tipo = tipo;
        this.indice = indice;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getClaveCurso() {
        return claveCurso;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public TipoSolicitud getTipo() {
        return tipo;
    }

    public int getIndice() {
        return indice;
    }

    public boolean esNueva() {
        return tipo == TipoSolicitud.NUEVA;
    }

    public boolean esModificacion() {
        return tipo == TipoSolicitud.MODIFICACION;
    }

    @Override
    public String toString() {
        if (tipo == TipoSolicitud.NUEVA) {
            return "SolicitudCalificacion{tipo=NUEVA"
                    + ", matricula='" + matricula + '\''
                    + ", curso='" + claveCurso + '\''
                    + ", calificacion=" + calificacion + '}';
        }
        return "SolicitudCalificacion{tipo=MODIFICACION"
                + ", matricula='" + matricula + '\''
                + ", curso='" + claveCurso + '\''
                + ", indice=" + indice
                + ", calificacion=" + calificacion + '}';
    }
}
