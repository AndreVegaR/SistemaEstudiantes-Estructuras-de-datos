package dominio;

/**
 *
 * @author Andre
 */
public class Calificacion {
    
    int valor;
    Estudiante estudiante;
    Curso curso;

    public Calificacion() {
    }

    public Calificacion(int valor, Estudiante estudiante, Curso curso) {
        this.valor = valor;
        this.estudiante = estudiante;
        this.curso = curso;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
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
