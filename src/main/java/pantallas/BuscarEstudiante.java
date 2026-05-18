package pantallas;

import controles.Control;
import dominio.Estudiante;
import excepciones.ControlException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import observadores.IObservador;
import observadores.IRemovedor;
import utilerias.FachadaUtil;

/**
 *
 * @author Andre
 */
public class BuscarEstudiante extends JDialog {
    Control control = Control.singleton();
    IObservador observador;
    IRemovedor removedor;
    JTextField txtMatricula;
    
    public BuscarEstudiante(IObservador observador, IRemovedor removedor) {
        FachadaUtil.configurarDialogoInicio(this, "Buscar estudiante por matrícula");
        
        this.observador = observador;
        this.removedor = removedor;
        
        JPanel panel = new JPanel();
        
        txtMatricula = FachadaUtil.crearCampoTexto();
        panel.add(txtMatricula);
        
        JButton botonBuscar = FachadaUtil.crearBoton("Buscar");
        botonBuscar.addActionListener(e -> buscarEstudiante());
        panel.add(botonBuscar);
        
        add(panel);
        
        FachadaUtil.configurarDialogoFinal(this); 
    }
    
    /** Maneja la búsqueda de un estudiante */
    public void buscarEstudiante() {
        String matricula = txtMatricula.getText();
        if (matricula == null || matricula.isBlank()) {
            FachadaUtil.dialogoAviso(BuscarEstudiante.this, "Llene el campo");
        }
        Estudiante estudiante = null;
        try {
            estudiante = control.consultarEstudiante(matricula);
        } catch (ControlException e) {
            FachadaUtil.dialogoError(BuscarEstudiante.this, "No existe un estudiante con la matrícula " + matricula);
        }
        
        //Notifica
        if (observador != null) {
            observador.observar(estudiante);
        }
        if (removedor != null) {
            removedor.remover(estudiante);
        }
    }
}