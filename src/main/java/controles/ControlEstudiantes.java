package controles;

import dominio.Estudiante;
import excepciones.ControlException;
import java.lang.reflect.Field;
import listas.CircularLinkedList;

/**
 * Control encargado de las operaciones CRUD y la
 * gestión general del sistema. Consume todas las
 * implementaciones de estructuras necesarias
 */
public class ControlEstudiantes {
    private static ControlEstudiantes instancia;
    private ControlEstudiantes(){}
    
    //En una lista enlazada simple circular guarda los estudiantes para ir rotando su rol
    private CircularLinkedList<Estudiante> listaRolesEstudiantes = new CircularLinkedList();
    
    
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
    
    /**
     * Agrega un estudiante al sistema. Internamente, lo almacena
     * en las estructuras necesarias para todas las necesidades
     * del sistema
     * 
     * @param estudiante a agregar
     */
    public void agregarEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            throw new ControlException("Estudiante vacío");
        }
        try {
            validarDatosEstudiante(estudiante);
            listaRolesEstudiantes.append(estudiante);
        } catch (Exception e) {
            throw new ControlException(e);
        } 
    }
    
    /**
     * Valida reflexivamente los campos de un objeto
     * 
     * @param obj a validar
     * 
     * @throws IllegalAccessException 
     */
    public static void validarDatosEstudiante(Object obj) throws IllegalAccessException {
        if (obj == null) return;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                String value = (String) field.get(obj);
                if (value == null || value.isBlank()) {
                    throw new ControlException ("No se admiten campos vacíos");
                }
            }
        }
    }
}
