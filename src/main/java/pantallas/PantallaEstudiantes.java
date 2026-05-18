package pantallas;

import controles.Control;
import dominio.Curso;
import dominio.Estudiante;
import excepciones.ControlException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import listas.ArrayList;
import listas.DoubleLinkedList;
import observadores.IObservador;
import utilerias.FachadaUtil;
import utilerias.UtilFormato;

public class PantallaEstudiantes extends JFrame implements IObservador {
    private JTable tabla;
    private JButton btnRegresar, btnEliminar, btnBuscarMatricula;
    private JTextField txtMatricula;
    private JTextField txtNombres, txtApellidoP, txtApellidoM;
    private JTextField txtTelefono, txtCorreo;
    private JTextField txtCalle, txtNumero, txtColonia, txtCiudad;
    private JButton btnGuardar;
    private Control control = Control.singleton();

    public PantallaEstudiantes() {
        FachadaUtil.configurarFrame("Gestión de cursos", this);
        this.setSize(1350, 700); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        JPanel contenedorConMarco = new JPanel(new BorderLayout());
        contenedorConMarco.setBackground(new Color(240, 242, 245)); 
        contenedorConMarco.setBorder(BorderFactory.createEmptyBorder(35, 120, 35, 120));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(900);
        splitPane.setDividerSize(15);      
        splitPane.setEnabled(false);
        splitPane.setBorder(null);         

        splitPane.setLeftComponent(crearPanelIzquierdoTabla());
        splitPane.setRightComponent(crearPanelDerechoInscripcion());

        contenedorConMarco.add(splitPane, BorderLayout.CENTER);
        this.add(contenedorConMarco);
    }
    
    private JPanel crearPanelIzquierdoTabla() {
        JPanel panelIzquierdo = FachadaUtil.crearPanel();
        panelIzquierdo.setLayout(new BorderLayout(10, 10));
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Configurar Tabla
        String[] columnas = {"Matrícula", "Nombre", "Telefono", "Contacto", "Dirección"};
        tabla = FachadaUtil.crearTabla(columnas);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        panelIzquierdo.add(scrollTabla, BorderLayout.CENTER);
        
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tabla.rowAtPoint(e.getPoint());
                    if (fila != -1) {
                        tabla.setRowSelectionInterval(fila, fila);
                        Estudiante seleccionado = obtenerEstudianteSeleccionado();
                        if (seleccionado != null) {
                            FachadaUtil.dialogoAviso(PantallaEstudiantes.this, seleccionado.mostrarCalificaciones());
                        }
                    }
                }
            }
        });

        // Panel Inferior de Botones de acción
        JPanel panelBotonesAccion = FachadaUtil.crearPanel();
        panelBotonesAccion.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        panelBotonesAccion.setBackground(Color.WHITE);

        btnRegresar = FachadaUtil.crearBoton("Regresar");
        btnRegresar.addActionListener(e -> {
            control.navegarMenuPrincial();
        });
        btnRegresar.setPreferredSize(new Dimension(130, 32)); 
        
        btnEliminar = FachadaUtil.crearBoton("Eliminar");
        btnEliminar.addActionListener(e -> {
            Estudiante seleccionado = obtenerEstudianteSeleccionado();
            if (seleccionado != null) {
                FachadaUtil.dialogoConfirmacion(this, "¿Seguro que deseas eliminar el estudiante " + seleccionado.getNombres() + "?", () -> {
                    try {
                        control.eliminarEstudiante(seleccionado); 
                        cargarDatos(control.consultarEstudiantes()); 
                        FachadaUtil.dialogoAviso(this, "Curso eliminado correctamente");
                    } catch (ControlException ex) {
                        FachadaUtil.dialogoError(this, ex.getMessage());
                    }
                });
            }
        });
        btnEliminar.setPreferredSize(new Dimension(130, 32)); 
        
        btnBuscarMatricula = FachadaUtil.crearBoton("Buscar");
        btnBuscarMatricula.setPreferredSize(new Dimension(130, 32)); 
        btnBuscarMatricula.addActionListener(e -> control.abrirBuscarEstudiante(this, null));

        panelBotonesAccion.add(btnRegresar);
        panelBotonesAccion.add(btnEliminar);
        panelBotonesAccion.add(btnBuscarMatricula);
        
        panelIzquierdo.add(panelBotonesAccion, BorderLayout.SOUTH);

        cargarDatos(control.consultarEstudiantes());
        
        return panelIzquierdo;
    }

    private Component crearPanelDerechoInscripcion() {
        JPanel panelDerecho = FachadaUtil.crearPanel();
        panelDerecho.setLayout(new BorderLayout());
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230), 1)); 

        JLabel lblTitulo = new JLabel("        Gestión de cursos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(34, 40, 44));
        lblTitulo.setPreferredSize(new Dimension(200, 50));
        panelDerecho.add(lblTitulo, BorderLayout.NORTH);

        JPanel contenedorCampos = FachadaUtil.crearPanel();
        contenedorCampos.setLayout(new GridBagLayout());
        contenedorCampos.setBackground(Color.WHITE);

        TitledBorder bordeInformacion = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), "Información del Curso");
        bordeInformacion.setTitleFont(new Font("Segoe UI", Font.PLAIN, 11));
        contenedorCampos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 15, 15, 15), bordeInformacion));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;


        txtMatricula = FachadaUtil.crearCampoTexto();
        txtNombres = FachadaUtil.crearCampoTexto();
        txtApellidoP = FachadaUtil.crearCampoTexto();
        txtApellidoM = FachadaUtil.crearCampoTexto();
        txtTelefono = FachadaUtil.crearCampoTexto();
        txtCorreo = FachadaUtil.crearCampoTexto();
        txtCalle = FachadaUtil.crearCampoTexto();
        txtNumero = FachadaUtil.crearCampoTexto();
        txtColonia = FachadaUtil.crearCampoTexto();
        txtCiudad = FachadaUtil.crearCampoTexto();

        añadirFilaFormulario(contenedorCampos, "Matrícula", txtMatricula, gbc, 0);
        añadirFilaFormulario(contenedorCampos, "Nombre", txtNombres, gbc, 1);
        añadirFilaFormulario(contenedorCampos, "ApellidoP", txtApellidoP, gbc, 2);
        añadirFilaFormulario(contenedorCampos, "ApellidoM", txtApellidoM, gbc, 3);
        añadirFilaFormulario(contenedorCampos, "Telefono", txtTelefono, gbc, 4);
        añadirFilaFormulario(contenedorCampos, "Correo", txtCorreo, gbc, 5);
        añadirFilaFormulario(contenedorCampos, "Calle", txtCalle, gbc, 6);
        añadirFilaFormulario(contenedorCampos, "Numero", txtNumero, gbc, 7);
        añadirFilaFormulario(contenedorCampos, "Colonia", txtColonia, gbc, 8);
        añadirFilaFormulario(contenedorCampos, "Ciudad", txtCiudad, gbc, 9);

        GridBagConstraints gbcEmpujeV = new GridBagConstraints();
        gbcEmpujeV.gridy = 10;
        gbcEmpujeV.gridx = 0;
        gbcEmpujeV.gridwidth = 2; 
        contenedorCampos.add(Box.createVerticalStrut(10), gbcEmpujeV);

        panelDerecho.add(contenedorCampos, BorderLayout.CENTER);

        JPanel panelBotonesControl = FachadaUtil.crearPanel();
        panelBotonesControl.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        panelBotonesControl.setBackground(Color.WHITE);

        btnGuardar = FachadaUtil.crearBotonPrincipal("Guardar");
        btnGuardar.setPreferredSize(new Dimension(110, 35)); 
        btnGuardar.addActionListener(e -> {
            FachadaUtil.dialogoConfirmacion(PantallaEstudiantes.this, "¿Agregar este estudiante?", () -> guardarEstudiante());
        });

        panelBotonesControl.add(btnGuardar);
        panelDerecho.add(panelBotonesControl, BorderLayout.SOUTH);

        JScrollPane scrollDerecho = new JScrollPane(panelDerecho);
        scrollDerecho.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDerecho.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDerecho.setBorder(null); 
        scrollDerecho.getViewport().setBackground(Color.WHITE);

        panelDerecho.setPreferredSize(new Dimension(380, 750)); 

        return scrollDerecho; 
    }

    private void añadirFilaFormulario(JPanel panel, String textoLabel, JTextField campo, GridBagConstraints gbc, int fila) {
        JLabel label = new JLabel(textoLabel);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = fila;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        panel.add(label, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; 
        campo.setPreferredSize(new Dimension(150, 30)); // Altura estándar y limpia
        panel.add(campo, gbc);
    }
    
    public void cargarDatos(ArrayList<Estudiante> estudiantes) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante e = estudiantes.get(i);
            Object[] fila = new Object[]{
                e.getMatricula(),
                e.nombreCompleto(),
                e.getTelefono(),
                e.getCorreo(),
                e.direccionCompleta()
            };
            modelo.addRow(fila);
        }
    }
    
    private void guardarEstudiante() {
        Estudiante estudiante = new Estudiante(
            txtMatricula.getText(),
            txtNombres.getText(),
            txtApellidoP.getText(),
            txtApellidoM.getText(),
            txtTelefono.getText(),
            txtCorreo.getText(),
            txtCalle.getText(),
            txtNumero.getText(),
            txtColonia.getText(),
            txtCiudad.getText()
        );
        
        try {
            control.agregarEstudiante(estudiante);
            FachadaUtil.dialogoAviso(PantallaEstudiantes.this, "Estudiante agregado");
        } catch (ControlException e) {
            FachadaUtil.dialogoError(PantallaEstudiantes.this, e.getMessage());
        }
        
        cargarDatos(control.consultarEstudiantes());
    }
    
    private Estudiante obtenerEstudianteSeleccionado() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            FachadaUtil.dialogoAlerta(this, "Por favor, seleccione un estudiante de la tabla.");
            return null;
        }
        int filaModelo = tabla.convertRowIndexToModel(filaSeleccionada);    
        String matricula = (String) tabla.getModel().getValueAt(filaModelo, 0);
        try {
            return control.consultarEstudiante(matricula);
        } catch (ControlException ex) {
            FachadaUtil.dialogoError(this, ex.getMessage());
            return null;
        }
    }

    @Override
    public void observar(Estudiante e) {
        ArrayList<Estudiante> estudiantes = new ArrayList(1);
        estudiantes.append(e);
        cargarDatos(estudiantes);
    }
}