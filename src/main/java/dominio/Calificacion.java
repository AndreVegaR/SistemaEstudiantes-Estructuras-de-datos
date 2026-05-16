package dominio;

/**
 *
 * @author Andre
 */
public class Calificacion {
    
    double valor;
    Estudiante estudiante;
    Curso curso;

    public Calificacion() {
    }

    public Calificacion(double valor, Estudiante estudiante, Curso curso) {
        this.valor = valor;
        this.estudiante = estudiante;
        this.curso = curso;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    
}
