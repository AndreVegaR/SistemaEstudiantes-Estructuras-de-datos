package controles;

import arboles.ArbolAVL;
import arboles.BinarySearchTree;
import dominio.Calificacion;
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
    private BinarySearchTree<Estudiante> arbolMatriculas = new BinarySearchTree();
    private ArbolAVL arbolCalificaciones = new ArbolAVL();
    
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
     * Agrega una calificación para un estudiante
     * 
     * @param calificacion a agregar
     * @param matricula del estudiante
     * 
     * @return el estudiante con calificación agregada
     */
    public Estudiante agregarCalificacion(Calificacion calificacion, String matricula) {
        if (calificacion == null) {
            throw new ControlException("Calificación vacía");
        }
        if (matricula == null || matricula.isBlank()) {
            throw new ControlException("Matrícula inválida");
        }
        
        //Consulta el estudiante
        Estudiante estudiante = consultarEstudiante(matricula);
        if (estudiante == null) {
            throw new ControlException("No existe el estudiante con esa matrí");
        }
        
        //Configura las calificaciones PENDIENTEEERHUGFEHUGFEJ
        estudiante.agregarCalificacion(calificacion);
        arbolCalificaciones.insert(calificacion.getValor());
        return estudiante;
    }
    
    
    /**
     * Determina si el estudiante ya existe o no. Busca concretamente
     * en el árbol por eficiencia O(log n)
     * 
     * @param estudiante a verificar
     * 
     * @return si existe o no
     */
    private boolean existeEstudiante(Estudiante estudiante) {
        estudianteVacio(estudiante);
        return arbolMatriculas.get(estudiante) != null;
    }
    
    /**
     * Busca un estudiante por matrícula
     * 
     * @param matricula para consultar
     * 
     * @return el estudiante
     */
    public Estudiante consultarEstudiante(String matricula) {
        if (matricula == null || matricula.isBlank()) {
            throw new ControlException("La matrícula no puede estar vacía");
        }
        
        //Estudiante temporal para recorrer el árbol
        Estudiante auxiliar = new Estudiante(); 
        auxiliar.setMatricula(matricula);

        //Busca al estudiante
        Estudiante encontrado = arbolMatriculas.get(auxiliar);
        if (encontrado == null) {
            throw new ControlException("No existe un estudiante con la matrícula: " + matricula);
        }
        return encontrado;
    }
    
    /**
     * Agrega un estudiante al sistema. Internamente, lo almacena
     * en las estructuras necesarias para todas las necesidades
     * del sistema
     * 
     * @param estudiante a agregar
     */
    public void agregarEstudiante(Estudiante estudiante) {
        estudianteVacio(estudiante);
        if (existeEstudiante(estudiante)) {
            throw new ControlException("Ya existe el estudiante");
        }
        try {
            validarDatosEstudiante(estudiante);
            arbolMatriculas.insert(estudiante);
        } catch (Exception e) {
            throw new ControlException(e);
        } 
    }
    
    /**
     * Elimina un estudiante del sistema removiéndolo de todas
     * las estructuras que lo contengan
     * 
     * @param estudiante a eliminar
     */
    public void eliminarEstudiante(Estudiante estudiante) {
        estudianteVacio(estudiante);
        if (!existeEstudiante(estudiante)) {
            throw new ControlException("No existe el estudiante");
        }
        try {
            arbolMatriculas.remove(estudiante);
        } catch (Exception e) {
            throw new ControlException(e);
        } 
    }
    
    /**
     * Auxiliar que valida si el estudiante está vacío
     * 
     * @param estudiante a validar
     */
    private void estudianteVacio(Estudiante estudiante) {
        if (estudiante == null) {
            throw new ControlException("Estudiante vacío");
        }
    }
    
    /**
     * Valida reflexivamente los campos de un objeto
     * 
     * @param obj a validar
     * 
     * @throws IllegalAccessException 
     */
    private void validarDatosEstudiante(Estudiante obj) throws IllegalAccessException {
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
