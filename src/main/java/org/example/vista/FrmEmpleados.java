package org.example.vista;

import org.example.controlador.EmpleadoDAO;
import org.example.modelo.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class FrmEmpleados extends JInternalFrame {

    private static final Color C_BG          = new Color(0xF0F4F8);
    private static final Color C_NAVY        = new Color(0x1E3A5F);
    private static final Color C_ACCENT      = new Color(0x0F52BA);
    private static final Color C_TEXT        = new Color(0x2D3748);
    private static final Color C_WHITE       = Color.WHITE;
    private static final Color C_ERROR       = new Color(0xE63946);
    private static final Color C_GREEN       = new Color(0x2A9D8F);
    private static final Color C_MUTED       = new Color(0xA0AEC0);


    private final String[] OPCIONES_UNION = {"0 - Ninguno", "101 - Sindicato General", "102 - Sindicato Técnico", "103 - Directiva"};
    private EmpleadoDAO empleadoDAO;
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;

    public FrmEmpleados() {
        setTitle("Gestión de Empleados - ABCC");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(850, 560);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        empleadoDAO = new EmpleadoDAO();

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBackground(C_BG);
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        root.add(buildTopPanel(), BorderLayout.NORTH);
        root.add(buildTablePanel(), BorderLayout.CENTER);

        setContentPane(root);
        llenarTabla();
    }



    private JPanel buildTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setOpaque(false);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelBusqueda.setOpaque(false);

        JLabel lblBuscar = new JLabel("Búsqueda Inteligente (Nombre o SSN):");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBuscar.setForeground(C_TEXT);

        txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xC8D6E5), 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarEmpleado();
            }
        });

        JButton btnRestablecer = crearBotonEstilizado("Reestablecer", C_TEXT);

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnRestablecer);

        JButton btnNuevo = crearBotonEstilizado("+ Agregar Empleado", C_GREEN);

        topPanel.add(panelBusqueda, BorderLayout.WEST);
        topPanel.add(btnNuevo, BorderLayout.EAST);

        btnRestablecer.addActionListener(e -> {
            txtBuscar.setText("");
            llenarTabla();
        });
        btnNuevo.addActionListener(e -> abrirFormularioAgregar());

        return topPanel;
    }



    private JPanel buildTablePanel() {
        JPanel tableCard = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(new Color(0xC8D6E5));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.dispose();
            }
        };
        tableCard.setOpaque(false);
        tableCard.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        String[] columnas = {"SSN", "Nombre", "Dirección", "Teléfono", "Salario", "Nº Unión", "Modificar", "Eliminar"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7;
            }
        };

        tablaEmpleados = new JTable(modeloTabla);
        tablaEmpleados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaEmpleados.setForeground(C_TEXT);
        tablaEmpleados.setRowHeight(32);
        tablaEmpleados.setShowVerticalLines(false);

        JTableHeader header = tablaEmpleados.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(C_NAVY);
        header.setForeground(C_WHITE);
        header.setReorderingAllowed(false);

        tablaEmpleados.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Modificar", C_ACCENT));
        tablaEmpleados.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), "Modificar"));

        tablaEmpleados.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Eliminar", C_ERROR));
        tablaEmpleados.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox(), "Eliminar"));

        JScrollPane scroll = new JScrollPane(tablaEmpleados);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(C_WHITE);

        tableCard.add(scroll, BorderLayout.CENTER);

        return tableCard;
    }

    private void llenarTabla() {
        modeloTabla.setRowCount(0);
        List<Empleado> lista = empleadoDAO.obtenerTodos();
        for (Empleado emp : lista) {
            Object[] fila = {
                    emp.getSsn(), emp.getNombre(), emp.getDireccion(),
                    emp.getTelefono(), emp.getSalario(), emp.getNumeroUnion(),
                    "Modificar", "Eliminar"
            };
            modeloTabla.addRow(fila);
        }
    }

    private void buscarEmpleado() {
        String query = txtBuscar.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            llenarTabla();
            return;
        }
        modeloTabla.setRowCount(0);
        List<Empleado> lista = empleadoDAO.obtenerTodos();
        for (Empleado emp : lista) {
            if (emp.getNombre().toLowerCase().contains(query) || emp.getSsn().toLowerCase().contains(query)) {
                Object[] fila = {
                        emp.getSsn(), emp.getNombre(), emp.getDireccion(),
                        emp.getTelefono(), emp.getSalario(), emp.getNumeroUnion(),
                        "Modificar", "Eliminar"
                };
                modeloTabla.addRow(fila);
            }
        }
    }


    private void abrirFormularioAgregar() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(topFrame, "Registrar Nuevo Empleado", true);
        dialog.setSize(460, 480);
        dialog.setLocationRelativeTo(topFrame);
        dialog.setResizable(false);

        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBackground(C_BG);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Alta de Empleado");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(C_NAVY);
        pnlMain.add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlCampos = new JPanel(new GridLayout(6, 2, 10, 15));
        pnlCampos.setOpaque(false);

        JTextField txtSsn = crearTextFieldFormulario();
        JTextField txtNombre = crearTextFieldFormulario();
        JTextField txtDireccion = crearTextFieldFormulario();
        JTextField txtTelefono = crearTextFieldFormulario();
        JTextField txtSalario = crearTextFieldFormulario();
        JTextField txtUnion = crearTextFieldFormulario();

        configurarSoloNumerosEnteros(txtTelefono);
        configurarSoloNumerosDecimales(txtSalario);
        configurarSoloNumerosEnteros(txtUnion);

        pnlCampos.add(crearLabelFormulario("SSN / Cédula (*):")); pnlCampos.add(txtSsn);
        pnlCampos.add(crearLabelFormulario("Nombre Completo (*):")); pnlCampos.add(txtNombre);
        pnlCampos.add(crearLabelFormulario("Dirección:")); pnlCampos.add(txtDireccion);
        pnlCampos.add(crearLabelFormulario("Teléfono:")); pnlCampos.add(txtTelefono);
        pnlCampos.add(crearLabelFormulario("Salario ($):")); pnlCampos.add(txtSalario);
        pnlCampos.add(crearLabelFormulario("Número Unión:")); pnlCampos.add(txtUnion);

        pnlMain.add(pnlCampos, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlBotones.setOpaque(false);
        JButton btnGuardar = crearBotonEstilizado("Guardar", C_GREEN);
        JButton btnCancelar = crearBotonEstilizado("Cancelar", C_TEXT);

        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnGuardar);
        pnlMain.add(pnlBotones, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> dialog.dispose());
        btnGuardar.addActionListener(e -> {
            if (txtSsn.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "El SSN y el Nombre son estrictamente obligatorios.", "Campos Incompletos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String telefono = txtTelefono.getText().trim().isEmpty() ? "N/A" : txtTelefono.getText().trim();
            double salario = txtSalario.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtSalario.getText().trim());
            String numUnion = txtUnion.getText().trim().isEmpty() ? "0" : txtUnion.getText().trim();

            Empleado emp = new Empleado(txtSsn.getText().trim(), txtNombre.getText().trim(), txtDireccion.getText().trim(), telefono, salario, numUnion);
            if (empleadoDAO.agregarEmpleado(emp)) {
                JOptionPane.showMessageDialog(dialog, "Empleado guardado de forma exitosa.");
                dialog.dispose();
                llenarTabla();
            } else {
                JOptionPane.showMessageDialog(dialog, "Error: El SSN ya existe en el sistema.", "Clave Duplicada", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setContentPane(pnlMain);
        dialog.setVisible(true);
    }


    private void gestionarModificacion(int fila) {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(topFrame, "Modificar Empleado", true);
        dialog.setSize(460, 480);
        dialog.setLocationRelativeTo(topFrame);
        dialog.setResizable(false);

        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBackground(C_BG);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Modificar Registro");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(C_NAVY);
        pnlMain.add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlCampos = new JPanel(new GridLayout(6, 2, 10, 15));
        pnlCampos.setOpaque(false);

        String ssn = tablaEmpleados.getValueAt(fila, 0).toString();
        JLabel lblSsnValor = new JLabel(ssn);
        lblSsnValor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSsnValor.setForeground(C_NAVY);

        JTextField txtNombre = crearTextFieldFormulario();
        txtNombre.setText(tablaEmpleados.getValueAt(fila, 1).toString());
        JTextField txtDireccion = crearTextFieldFormulario();
        txtDireccion.setText(tablaEmpleados.getValueAt(fila, 2).toString());
        JTextField txtTelefono = crearTextFieldFormulario();
        txtTelefono.setText(tablaEmpleados.getValueAt(fila, 3).toString());
        JTextField txtSalario = crearTextFieldFormulario();
        txtSalario.setText(tablaEmpleados.getValueAt(fila, 4).toString());
        JTextField txtUnion = crearTextFieldFormulario();
        txtUnion.setText(tablaEmpleados.getValueAt(fila, 5).toString());

        configurarSoloNumerosEnteros(txtTelefono);
        configurarSoloNumerosDecimales(txtSalario);
        configurarSoloNumerosEnteros(txtUnion);

        pnlCampos.add(crearLabelFormulario("SSN (No Editable):")); pnlCampos.add(lblSsnValor);
        pnlCampos.add(crearLabelFormulario("Nombre Completo (*):")); pnlCampos.add(txtNombre);
        pnlCampos.add(crearLabelFormulario("Dirección:")); pnlCampos.add(txtDireccion);
        pnlCampos.add(crearLabelFormulario("Teléfono:")); pnlCampos.add(txtTelefono);
        pnlCampos.add(crearLabelFormulario("Salario ($):")); pnlCampos.add(txtSalario);
        pnlCampos.add(crearLabelFormulario("Número Unión:")); pnlCampos.add(txtUnion);

        pnlMain.add(pnlCampos, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlBotones.setOpaque(false);
        JButton btnActualizar = crearBotonEstilizado("Actualizar", C_ACCENT);
        JButton btnCancelar = crearBotonEstilizado("Cancelar", C_TEXT);

        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnActualizar);
        pnlMain.add(pnlBotones, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> dialog.dispose());
        btnActualizar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "El nombre del empleado no puede quedar vacío.", "Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double salario = txtSalario.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtSalario.getText().trim());

            Empleado emp = new Empleado(ssn, txtNombre.getText().trim(), txtDireccion.getText().trim(), txtTelefono.getText().trim(), salario, txtUnion.getText().trim());
            if (empleadoDAO.actualizarEmpleado(emp)) {
                JOptionPane.showMessageDialog(dialog, "Registro actualizado con éxito.");
                dialog.dispose();
                llenarTabla();
            } else {
                JOptionPane.showMessageDialog(dialog, "No se pudo actualizar el registro en la BD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setContentPane(pnlMain);
        dialog.setVisible(true);
    }

    private void gestionarEliminacion(int fila) {
        String ssn = tablaEmpleados.getValueAt(fila, 0).toString();
        String nombre = tablaEmpleados.getValueAt(fila, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar a " + nombre + "?\nEsta acción no se puede deshacer.", "Confirmar baja", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (empleadoDAO.eliminarEmpleado(ssn)) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado de la base de datos.");
                llenarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private JLabel crearLabelFormulario(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(C_TEXT);
        return label;
    }

    private JTextField crearTextFieldFormulario() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tf.setForeground(C_TEXT);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xC8D6E5), 1),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)
        ));
        return tf;
    }

    private void configurarSoloNumerosEnteros(JTextField tf) {
        tf.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
    }

    private void configurarSoloNumerosDecimales(JTextField tf) {
        tf.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
                if (c == '.' && tf.getText().contains(".")) {
                    e.consume();
                }
            }
        });
    }

    private JButton crearBotonEstilizado(String texto, Color bg) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(C_WHITE);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        return btn;
    }



    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String label, Color background) {
            setText(label);
            setFont(new Font("Segoe UI", Font.BOLD, 11));
            setForeground(Color.WHITE);
            setBackground(background);
            setOpaque(true);
            setBorderPainted(false);
        }
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private String accion;
        private JTable tableTarget;

        public ButtonEditor(JCheckBox checkBox, String tipoAccion) {
            super(checkBox);
            this.accion = tipoAccion;

            button = new JButton();
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFont(new Font("Segoe UI", Font.BOLD, 11));
            button.setForeground(Color.WHITE);
            button.setBackground(tipoAccion.equals("Modificar") ? C_ACCENT : C_ERROR);

            button.addActionListener(new ActionListener() {
                @Override public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();

                    int filaReal = tableTarget.getSelectedRow();
                    if (filaReal >= 0) {
                        if (accion.equals("Modificar")) {
                            gestionarModificacion(filaReal);
                        } else if (accion.equals("Eliminar")) {
                            gestionarEliminacion(filaReal);
                        }
                    }
                }
            });
        }

        @Override public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.tableTarget = table;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override public Object getCellEditorValue() {
            return label;
        }

        @Override public boolean stopCellEditing() {
            return super.stopCellEditing();
        }
    }
}