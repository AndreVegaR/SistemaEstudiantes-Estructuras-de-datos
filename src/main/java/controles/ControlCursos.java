package controles;

/**
 *
 * @author Andre
 */
public class ControlCursos {
    private static ControlCursos instancia;
    
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
}
