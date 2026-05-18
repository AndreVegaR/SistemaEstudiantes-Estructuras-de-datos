package pantallas;

import controles.Control;
import dominio.Calificacion;
import dominio.Curso;
import dominio.Estudiante;
import excepciones.ControlException;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import listas.DoubleLinkedList;
import observadores.IObservador;
import utilerias.FachadaUtil;
import utilerias.UtilFormato;

public class PantallaCursos extends JFrame implements IObservador {
    private JTable tablaCursos;
    private JButton btnRegresar, btnEliminar, btnVerDetalles, btnAgregarEstudiante;
    private JTextField txtClave, txtNombre, txtCapacidad;
    private JButton btnGuardar;
    private Control control = Control.singleton();

    public PantallaCursos() {
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
        String[] columnas = {"Clave del curso", "Nombre del curso", "Capacidad", "Cantidad de alumnos", "Líder"};
        tablaCursos = FachadaUtil.crearTabla(columnas);
        JScrollPane scrollTabla = new JScrollPane(tablaCursos);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        panelIzquierdo.add(scrollTabla, BorderLayout.CENTER);

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
            Curso seleccionado = obtenerCursoSeleccionado();
            if (seleccionado != null) {
                FachadaUtil.dialogoConfirmacion(this, "¿Seguro que deseas eliminar el curso " + seleccionado.getNombre() + "?", () -> {
                    try {
                        control.eliminarCurso(seleccionado); 
                        cargarDatos(); 
                        FachadaUtil.dialogoAviso(this, "Curso eliminado correctamente");
                    } catch (ControlException ex) {
                        FachadaUtil.dialogoError(this, ex.getMessage());
                    }
                });
            }
        });
        btnEliminar.setPreferredSize(new Dimension(130, 32)); 
        
        btnVerDetalles = FachadaUtil.crearBoton("Detalles");
        btnVerDetalles.setPreferredSize(new Dimension(130, 32)); 
        
        btnAgregarEstudiante = FachadaUtil.crearBoton("Agregar estudiante");
        btnAgregarEstudiante.addActionListener(e -> {
            if (obtenerCursoSeleccionado() == null) {
                FachadaUtil.dialogoAlerta(PantallaCursos.this, "Seleccione un curso primero");
                return;
            }
            control.abrirBuscarEstudiante(this);
        });
        
        panelBotonesAccion.add(btnRegresar);
        panelBotonesAccion.add(btnEliminar);
        panelBotonesAccion.add(btnVerDetalles);
        panelBotonesAccion.add(btnAgregarEstudiante);
        
        panelIzquierdo.add(panelBotonesAccion, BorderLayout.SOUTH);

        cargarDatos();
        
        return panelIzquierdo;
    }

    private JPanel crearPanelDerechoInscripcion() {
        JPanel panelDerecho = FachadaUtil.crearPanel();
        panelDerecho.setLayout(new BorderLayout());
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230), 1)); 

        //Encabezado
        JLabel lblTitulo = new JLabel("       Gestión de cursos");
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

        //Crea los campos de texto
        txtClave = FachadaUtil.crearCampoTexto();
        txtNombre = FachadaUtil.crearCampoTexto();
        txtCapacidad = FachadaUtil.crearCampoTexto();
        añadirFilaFormulario(contenedorCampos, "Clave", txtClave, gbc, 0);
        añadirFilaFormulario(contenedorCampos, "Nombre", txtNombre, gbc, 1);
        añadirFilaFormulario(contenedorCampos, "Capacidad", txtCapacidad, gbc, 2);
        
        GridBagConstraints gbcEmpujeV = new GridBagConstraints();
        gbcEmpujeV.gridy = 5;
        gbcEmpujeV.weighty = 1.0; 
        contenedorCampos.add(new Box.Filler(new Dimension(0,0), new Dimension(0,0), new Dimension(0, Short.MAX_VALUE)), gbcEmpujeV);

        panelDerecho.add(contenedorCampos, BorderLayout.CENTER);
        JPanel panelBotonesControl = FachadaUtil.crearPanel();
        panelBotonesControl.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15)); 
        panelBotonesControl.setBackground(Color.WHITE);
        
        //Botón para guardar el curso
        btnGuardar = FachadaUtil.crearBotonPrincipal("Guardar");
        btnGuardar.setPreferredSize(new Dimension(110, 35)); 
        btnGuardar.addActionListener(e -> {
            FachadaUtil.dialogoConfirmacion(PantallaCursos.this, "¿Agregar este curso?", () -> guardarCurso());
        });
        
        panelBotonesControl.add(btnGuardar);

        panelDerecho.add(panelBotonesControl, BorderLayout.SOUTH);

        return panelDerecho;
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
    
    public void cargarDatos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaCursos.getModel();
        modelo.setRowCount(0);
        DoubleLinkedList<Curso> cursos = control.obtenerCursos();
        for (int i = 0; i < cursos.size(); i++) {
            Curso curso = cursos.get(i);
            Object[] fila = new Object[]{
                curso.getClave(),
                curso.getNombre(),
                curso.getCapacidad(),
                curso.getEstudiantes().size(),
                curso.getLider() != null ? curso.getLider().nombreCompleto() : "Sin líder"
            };
            modelo.addRow(fila);
        }
    }
    
    private void guardarCurso() {
        String capacidad = txtCapacidad.getText();
        if (!UtilFormato.numeroEnteroPositivo(capacidad)) {
            FachadaUtil.dialogoError(PantallaCursos.this, "Capacidad inválida");
            return;
        }
        
        Curso curso = new Curso (
            txtClave.getText(), 
            txtNombre.getText(),
            Integer.valueOf(capacidad)       
        );
        
        try {
            control.agregarCurso(curso);
            txtClave.setText("");
            txtNombre.setText("");
            txtCapacidad.setText("");
            FachadaUtil.dialogoAviso(PantallaCursos.this, "Curso agregado");
        } catch (ControlException e) {
            FachadaUtil.dialogoError(PantallaCursos.this, e.getMessage());
        }
        
        cargarDatos();
    }
    
    private Curso obtenerCursoSeleccionado() {
        int filaSeleccionada = tablaCursos.getSelectedRow();
        if (filaSeleccionada == -1) {
            FachadaUtil.dialogoAlerta(this, "Por favor, seleccione un curso de la tabla.");
            return null;
        }
        DoubleLinkedList<Curso> cursos = control.obtenerCursos();
        return cursos.get(filaSeleccionada);
    }

    @Override
    public void observar(Estudiante e) {
        Curso curso = obtenerCursoSeleccionado();
        if (curso != null) {
            curso.agregarEstudiante(e);
            e.agregarCalificacion(new Calificacion(0, e, curso));
            cargarDatos();
        }
    }
}