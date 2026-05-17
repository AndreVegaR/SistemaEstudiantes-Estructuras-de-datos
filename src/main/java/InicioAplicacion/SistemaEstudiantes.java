package InicioAplicacion;

import controles.Control;

/**
 *
 * @author Andre
 */
public class SistemaEstudiantes {
    public static void main(String[] args) {
        Control control = Control.singleton();
        control.pantallaInicial();
    }
}