package com.mycompany.sistemaestudiantes;

import controles.Control;
import controles.ControlEstudiantes;
import controles.ControlPantallas;
import dominio.Estudiante;
import excepciones.ControlException;
import java.util.List;

/**
 *
 * @author Andre
 */
public class SistemaEstudiantes {
    private static Estudiante est1 = null;
    private static Estudiante est2 = null;
    private static Estudiante est3 = null;
    
    
    public static void main(String[] args) {
        Control control = Control.singleton();
        control.pantallaInicial();
        
        //AQUI INICIA LA PRUEBA INSANA
        prueba(control);
        
        String m1 = est1.getMatricula();
        
        try {
            Estudiante consultado1 = control.consultarEstudiante(m1);
            System.out.println("Consultado: " + consultado1.toString());
            control.eliminarEstudiante(est1);
            control.consultarEstudiante(m1);
        } 
        catch (ControlException e) {
            System.out.println("EXCEPCIÓN DETECTADA: " + e.getMessage());
        }
    }
    
    
    private static void prueba(Control control) {
        est1 = new Estudiante(
            "A2026-001", 
            "Ana Beatriz", 
            "García", 
            "López", 
            "5550123456", 
            "ana.garcia@universidad.edu", 
            "Av. Reforma", 
            "120", 
            "Centro", 
            "Ciudad de México"
        );
        control.agregarEstudiante(est1);
        
        est2 = new Estudiante(
            "B2026-045", 
            "Carlos Mario", 
            "Rodríguez", 
            "Pérez", 
            "8119876543", 
            "carlos.rod@gmail.com", 
            "Calle Pino", 
            "45-B", 
            "Vista Hermosa", 
            "Monterrey"
        );
        control.agregarEstudiante(est2);

        est3 = new Estudiante(
            "A2026-002", 
            "Diana Sofía", 
            "Mendoza", 
            "Ruiz", 
            "3334445556", 
            "diana.mza@outlook.com", 
            "Calle 10", 
            "900", 
            "Colonia Juárez", 
            "Guadalajara"
        );
        control.agregarEstudiante(est3);
    }
}