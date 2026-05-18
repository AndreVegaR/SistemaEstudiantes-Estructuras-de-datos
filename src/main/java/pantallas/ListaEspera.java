package pantallas;

import dominio.Curso;
import dominio.Estudiante;
import javax.swing.*;
import java.awt.*;
import listas.DoubleCircularLinkedList;
import utilerias.FachadaUtil;

public class ListaEspera extends JDialog {
    
    private DoubleCircularLinkedList<Estudiante> listaEspera;
    private JLabel lblEstudiante;
    private JButton btnAtras, btnAdelante, btnCerrar;

    public ListaEspera(Curso curso) {
        FachadaUtil.configurarDialogoInicio(this, "Lista de espera del curso " + curso.getNombre());
        this.listaEspera = curso.getListaEspera();
        inicializarComponentes();
        actualizarVista();
        FachadaUtil.configurarDialogoFinal(this);
    }

    private void inicializarComponentes() {
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(Color.WHITE);
        
        lblEstudiante = new JLabel("No hay estudiantes en la lista de espera");
        lblEstudiante.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEstudiante.setHorizontalAlignment(SwingConstants.CENTER);
        panelCentro.add(lblEstudiante);
        this.add(panelCentro, BorderLayout.CENTER);

        // Panel inferior para los botones de navegación y cierre
        JPanel panelControles = new JPanel(new BorderLayout());
        panelControles.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        JPanel panelNavegacion = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnAtras = FachadaUtil.crearBoton("< Atrás");
        btnAdelante = FachadaUtil.crearBoton("Adelante >");
        
        // Eventos de navegación aprovechando la lista doblemente circular
        btnAtras.addActionListener(e -> {
            if (listaEspera != null && !listaEspera.empty()) {
                listaEspera.retroceder();
                actualizarVista();
            }
        });

        btnAdelante.addActionListener(e -> {
            if (listaEspera != null && !listaEspera.empty()) {
                listaEspera.avanzar();
                actualizarVista();
            }
        });

        panelNavegacion.add(btnAtras);
        panelNavegacion.add(btnAdelante);
        panelControles.add(panelNavegacion, BorderLayout.CENTER);
        
        btnCerrar = FachadaUtil.crearBoton("Cerrar");
        btnCerrar.addActionListener(e -> this.dispose());
        panelControles.add(btnCerrar, BorderLayout.EAST);

        this.add(panelControles, BorderLayout.SOUTH);
    }

    private void actualizarVista() {
        if (listaEspera == null || listaEspera.empty()) {
            lblEstudiante.setText("No hay estudiantes en la lista de espera");
            btnAtras.setEnabled(false);
            btnAdelante.setEnabled(false);
        } else {
            btnAtras.setEnabled(true);
            btnAdelante.setEnabled(true);
            Estudiante actual = listaEspera.getCursorDato(); 
            if (actual != null) {
                lblEstudiante.setText(actual.nombreCompleto() + " (" + actual.getMatricula() + ")");
            }
        }
    }
}