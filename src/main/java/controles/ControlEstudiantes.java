package controles;

import arboles.ArbolAVL;
import arboles.BinarySearchTree;
import dominio.Calificacion;
import dominio.Curso;
import dominio.Estudiante;
import excepciones.ControlException;
import java.lang.reflect.Field;
import listas.ArrayList;
import listas.CircularLinkedList;

/**
 * Control encargado de las operaciones CRUD y la
 * gestión general del sistema. Consume todas las
 * implementaciones de estructuras necesarias
 */
public class ControlEstudiantes {
    private BinarySearchTree<Estudiante> arbolMatriculas;
    private ArbolAVL<String, Double> arbolPromedios;
    
    private static ControlEstudiantes instancia;
    private ControlEstudiantes(){
        arbolMatriculas = new BinarySearchTree();
        arbolPromedios = new ArbolAVL();
    }
    
    
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
     * Obtiene todos los estudiantes del sistema
     * 
     * @return 
     */
    public ArrayList<Estudiante> obtenerEstudiantes() {
        return arbolMatriculas.toList();
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
        try {
            validarDatosEstudiante(estudiante);
        } catch (IllegalAccessException e) {
            throw new ControlException("Error de permisos al validar atributos: " + e.getMessage());
        }
        
        if (existeEstudiante(estudiante)) {
            throw new ControlException("Ya existe un estudiante registrado con esta matrícula");
        }
        
        try {
            arbolMatriculas.insert(estudiante);
            arbolPromedios.insert(estudiante.getMatricula(), estudiante.promediar());
        } catch (Exception e) {
            throw new ControlException("Error al insertar en el árbol: " + e.getMessage());
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
            arbolPromedios.remove(estudiante.getMatricula());
        } catch (Exception e) {
            throw new ControlException(e);
        } 
    }
    
    /**
     * Elimina una calificación
     * 
     * @param estudiante
     * @param curso 
     */
    public void eliminarCalificacion(Estudiante estudiante, Curso curso) {
        if (curso == null || estudiante == null) {
            throw new ControlException("Datos inválidos");
        }
        if (!existeEstudiante(estudiante)) {
            throw new ControlException("No existe el estudiante");
        }
        estudiante.eliminarCalificacion(curso);
    }
    
    /**
     * Obtiene los estudiantes por promedio
     * 
     * @return estudiantes
     */
    public ArrayList<Estudiante> obtenerEstudiantesPromedio() {
        return arbolMatriculas.toList();
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
