package controles;

/**
 * Control encargado de las operaciones CRUD y la
 * gestión general del sistema. Consume todas las
 * implementaciones de estructuras necesarias
 */
public class ControlEstudiantes {
    private static ControlEstudiantes instancia;
    private ControlEstudiantes(){}
    
    //Estructuras de datos internas para almacenar datos
    
    
    /**
     * Método que regresa el singleton del control
     * para la gestión de estudiantes
     * 
     * @return la instancia única
     */
    public static ControlEstudiantes singleton() {
        if (instancia == null) {
            instancia = new ControlEstudiantes();
        }
        return instancia;
    }
}
