package pantallas;

import controles.Control;
import dominio.SolicitudCalificacion;
import excepciones.ControlException;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import listas.ArrayList;
import utilerias.FachadaUtil;

public class PantallaCalificaciones extends JFrame {

    private JTable tablaSolicitudes;
    private JButton btnProcesar, btnEnviar, btnRegresar;
    private JTextField txtMatricula, txtClaveCurso, txtCalificacion;
    private Control control = Control.singleton();

    public PantallaCalificaciones() {
        FachadaUtil.configurarFrame("Gestión de Calificaciones", this);
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Split pane para dividir en dos áreas
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(450);
        splitPane.setDividerSize(10);

        splitPane.setLeftComponent(crearPanelEnviarSolicitud());
        splitPane.setRightComponent(crearPanelProcesarSolicitudes());

        panelPrincipal.add(splitPane, BorderLayout.CENTER);
        
        // Botón regresar abajo
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRegresar = FachadaUtil.crearBoton("Regresar al Menú");
        btnRegresar.addActionListener(e -> {
            control.navegarMenuPrincial();
            this.dispose();
        });
        panelInferior.add(btnRegresar);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        this.add(panelPrincipal);
        cargarSolicitudesPendientes();
    }

    /**
     * Panel izquierdo: Formulario para ENVIAR nueva solicitud
     */
    private JPanel crearPanelEnviarSolicitud() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel lblTitulo = new JLabel("Enviar Solicitud de Calificación");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Formulario
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(Color.WHITE);
        formulario.setBorder(new TitledBorder("Datos de la solicitud"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 0.3;

        // Campo Matrícula
        gbc.gridy = 0;
        formulario.add(new JLabel("Matrícula del Estudiante:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtMatricula = FachadaUtil.crearCampoTexto();
        formulario.add(txtMatricula, gbc);

        // Campo Clave del Curso
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        formulario.add(new JLabel("Clave del Curso:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtClaveCurso = FachadaUtil.crearCampoTexto();
        formulario.add(txtClaveCurso, gbc);

        // Campo Calificación
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        formulario.add(new JLabel("Calificación (0-10):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtCalificacion = FachadaUtil.crearCampoTexto();
        formulario.add(txtCalificacion, gbc);

        panel.add(formulario, BorderLayout.CENTER);

        // Botón Enviar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE);
        btnEnviar = FachadaUtil.crearBotonPrincipal("Enviar Solicitud");
        btnEnviar.addActionListener(e -> enviarSolicitud());
        panelBoton.add(btnEnviar);
        panel.add(panelBoton, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Panel derecho: Tabla de solicitudes pendientes y botón procesar
     */
    private JPanel crearPanelProcesarSolicitudes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel lblTitulo = new JLabel("Solicitudes Pendientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"Matrícula", "Curso", "Calificación", "Tipo"};
        tablaSolicitudes = FachadaUtil.crearTabla(columnas);
        JScrollPane scroll = new JScrollPane(tablaSolicitudes);
        panel.add(scroll, BorderLayout.CENTER);

        // Botón Procesar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBackground(Color.WHITE);
        btnProcesar = FachadaUtil.crearBoton("Procesar Siguiente");
        btnProcesar.addActionListener(e -> procesarSiguienteSolicitud());
        panelBoton.add(btnProcesar);
        panel.add(panelBoton, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Envía una nueva solicitud de calificación a la cola
     */
    private void enviarSolicitud() {
        String matricula = txtMatricula.getText().trim();
        String claveCurso = txtClaveCurso.getText().trim();
        String calificacionStr = txtCalificacion.getText().trim();

        // Validaciones
        if (matricula.isEmpty() || claveCurso.isEmpty() || calificacionStr.isEmpty()) {
            FachadaUtil.dialogoAlerta(this, "Todos los campos son obligatorios");
            return;
        }

        double calificacion;
        try {
            calificacion = Double.parseDouble(calificacionStr);
        } catch (NumberFormatException e) {
            FachadaUtil.dialogoAlerta(this, "La calificación debe ser un número válido");
            return;
        }

        if (calificacion < 0 || calificacion > 10) {
            FachadaUtil.dialogoAlerta(this, "La calificación debe estar entre 0 y 10");
            return;
        }

        try {
            // Enviar la solicitud al control
            // Necesitarás agregar este método a tu clase Control
            control.enviarSolicitudCalificacion(matricula, claveCurso, calificacion);
            
            FachadaUtil.dialogoAviso(this, "Solicitud enviada correctamente");
            
            // Limpiar campos
            txtMatricula.setText("");
            txtClaveCurso.setText("");
            txtCalificacion.setText("");
            
            // Recargar la tabla de solicitudes pendientes
            cargarSolicitudesPendientes();
            
        } catch (ControlException ex) {
            FachadaUtil.dialogoError(this, ex.getMessage());
        }
    }

    /**
     * Procesa la siguiente solicitud en la cola
     */
    private void procesarSiguienteSolicitud() {
        try {
            control.procesarSiguienteSolicitudCalificacion();
            cargarSolicitudesPendientes();
            FachadaUtil.dialogoAviso(this, "Solicitud procesada exitosamente");
        } catch (ControlException ex) {
            FachadaUtil.dialogoError(this, ex.getMessage());
        }
    }

    /**
     * Carga las solicitudes pendientes desde el control
     */
    private void cargarSolicitudesPendientes() {
        DefaultTableModel modelo = (DefaultTableModel) tablaSolicitudes.getModel();
        modelo.setRowCount(0);

        ArrayList<SolicitudCalificacion> solicitudes = control.consultarSolicitudes();

        for (int i = 0; i < solicitudes.size(); i++) {
            SolicitudCalificacion sol = solicitudes.get(i);
            Object[] fila = new Object[]{
                sol.getMatricula(),
                sol.getClaveCurso(),
                sol.getCalificacion(),
                sol.getTipo().toString()
            };
            modelo.addRow(fila);
        }

        if (solicitudes.size() == 0) {
            modelo.addRow(new Object[]{"No hay solicitudes pendientes", "", "", ""});
        }
    }
}