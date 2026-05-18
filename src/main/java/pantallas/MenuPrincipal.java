package pantallas;

import controles.Control;
import dominio.Accion;
import excepciones.ControlException;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import utilerias.Constantes;
import utilerias.FachadaUtil;

/**
 * Menú principal del sistema
 * * @author Andre
 */
public class MenuPrincipal extends JFrame {
    private static final Control control = Control.singleton();
    private final Color VERDE_ESCOLAR = Constantes.VERDE_ESCOLAR;
    private final Color FONDO_GRIS = new Color(245, 247, 250);
    
    public MenuPrincipal() {
        FachadaUtil.configurarFrame("Sistema de Gestión Estudiantil", this);
        setLayout(new BorderLayout());

        //Barra superior
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(VERDE_ESCOLAR);
        header.setPreferredSize(new Dimension(getWidth(), 80));
        
        //Título
        JLabel titulo = new JLabel("Sistema Estudiantes", SwingConstants.CENTER);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(titulo, BorderLayout.CENTER);
        
        //Contiene los botones
        JPanel contenedorCentral = new JPanel(new GridBagLayout());
        contenedorCentral.setBackground(FONDO_GRIS);
        
        //Panel que contiene los botones
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; 
        gbc.weighty = 0;
        
        //Crea el botón de estudiantes
        JButton botonEstudiantes = FachadaUtil.crearBotonPrincipal("Estudiantes");
        botonEstudiantes.addActionListener(e -> control.navegarPantallaEstudiantes());
        gbc.gridx = 0; gbc.gridy = 0;
        panelBotones.add(botonEstudiantes, gbc);
        
        //Crea el botón de cursos
        JButton botonCursos = FachadaUtil.crearBotonPrincipal("Cursos");
        botonCursos.addActionListener(e -> control.navegarPantallaCursos());
        gbc.gridx = 1; gbc.gridy = 0;
        panelBotones.add(botonCursos, gbc);
        
        //Crea el botón de calificaciones
        JButton botonCalificaciones = FachadaUtil.crearBotonPrincipal("Calificaciones");
        botonCalificaciones.addActionListener(e -> control.navegarPantallaCalificaciones());
        gbc.gridx = 2; gbc.gridy = 0;
        panelBotones.add(botonCalificaciones, gbc);
        
        //Crea el botón de acciones
        JButton botonAcciones = FachadaUtil.crearBotonPrincipal("Revertir cambio");
        botonAcciones.addActionListener(e -> confimarDeshacer());
        gbc.gridx = 3; gbc.gridy = 0;
        panelBotones.add(botonAcciones, gbc);
        
        //Crea el botón de reportes
        JButton botonReportes = FachadaUtil.crearBotonPrincipal("Reportes");
        botonReportes.addActionListener(e -> control.navegarPantallaReporteEstudiantes());
        gbc.gridx = 4; gbc.gridy = 0;
        panelBotones.add(botonReportes, gbc);
        
        //Crea el botón inferior
        JButton botonSalir = FachadaUtil.crearBotonPrincipal("Salir");
        botonSalir.addActionListener(e -> salir());
        gbc.gridy = 1; gbc.gridx = 0; gbc.gridwidth = 6;
        gbc.insets = new Insets(25, 10, 10, 10);
        panelBotones.add(botonSalir, gbc);
        
        //Arma todo
        contenedorCentral.add(panelBotones);
        JPanel panelNorteCompleto = new JPanel(new BorderLayout());
        panelNorteCompleto.add(header, BorderLayout.NORTH);
        add(panelNorteCompleto, BorderLayout.NORTH);
        add(contenedorCentral, BorderLayout.CENTER);
    }
    
    /** Maneja la confirmación para deshacer una acción */
    public void confimarDeshacer() {
        FachadaUtil.dialogoConfirmacion(MenuPrincipal.this, "¿Deshacer la última acción?", () -> {
            try {
                Accion accion = control.deshacerUltimaAccion();
                FachadaUtil.dialogoAviso(MenuPrincipal.this, "Acción deshecha: " + accion.toString()); 
            } catch (ControlException e) {
                FachadaUtil.dialogoError(MenuPrincipal.this, e.getMessage());
                return;
            }
        });
    }
    
    /** Maneja la salida del sistema */
    public void salir() {
        FachadaUtil.dialogoConfirmacion(MenuPrincipal.this, "¿Salir del sistema?", () -> {
            System.exit(0);
        });
    }
}