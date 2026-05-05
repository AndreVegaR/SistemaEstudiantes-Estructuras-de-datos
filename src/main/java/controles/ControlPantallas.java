package controles;

import java.util.function.Supplier;
import javax.swing.JFrame;

/**
 * Control encargado del flujo de navegación del
 * sistema, abriendo y cerrando frames y diálogos
 */
public class ControlPantallas {
    private static ControlPantallas instancia;
    private ControlPantallas(){}
    private JFrame ventanaActual;
    
    /**
     * Método que regresa el singleton del control
     * para las pantallas
     * 
     * @return la instancia única
     */
    public static ControlPantallas singleton() {
        if (instancia == null) {
            instancia = new ControlPantallas();
        }
        return instancia;
    }
    
    /**
     * Método privado que centraliza la navegación
     * 
     * @param ventanaSiguiente a navegar
     */
    private void navegar(Supplier<JFrame> ventanaSiguiente) {
        //Guarda el frame anterior
        JFrame ventanaAnterior = ventanaActual;

        //Crea la nueva ventana
        ventanaActual = ventanaSiguiente.get();

        //La nueva ventana copia propiedades de la anterior
        if (ventanaAnterior != null) {
            ventanaActual.setBounds(ventanaAnterior.getBounds());
            ventanaActual.setExtendedState(ventanaAnterior.getExtendedState());
        } else {
            ventanaActual.setLocationRelativeTo(null);
        }

        //Muestra la ventana actual
        ventanaActual.setVisible(true);
        ventanaActual.toFront();

        //Fuerza a sincronicar gráficos
        java.awt.Toolkit.getDefaultToolkit().sync();

        //Cierra la ventana anterior
        if (ventanaAnterior != null) {
            ventanaAnterior.dispose();
        }
    }
}