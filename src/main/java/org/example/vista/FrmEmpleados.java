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

        JLabel lblBuscar = new JLabel("Buscar por Nombre:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblBuscar.setForeground(C_TEXT);

        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JButton btnBuscar = crearBotonEstilizado("Buscar", C_NAVY);
        JButton btnRestablecer = crearBotonEstilizado("Reestablecer", C_TEXT); // Punto 7: Botón Reestablecer

        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnRestablecer);

        JButton btnNuevo = crearBotonEstilizado("+ Agregar Empleado", new Color(0x2A9D8F));

        topPanel.add(panelBusqueda, BorderLayout.WEST);
        topPanel.add(btnNuevo, BorderLayout.EAST);

        btnBuscar.addActionListener(e -> buscarEmpleado());
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
        tablaEmpleados.setRowHeight(32); // Altura cómoda para tus botones integrados
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
            if (emp.getNombre().toLowerCase().contains(query)) {
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
        JTextField ssnIn = new JTextField();
        JTextField nomIn = new JTextField();
        JTextField dirIn = new JTextField();
        JTextField telIn = new JTextField();
        JTextField salIn = new JTextField();
        JTextField uniIn = new JTextField();

        configurarSoloNumerosEnteros(telIn);
        configurarSoloNumerosDecimales(salIn);
        configurarSoloNumerosEnteros(uniIn);

        Object[] formulario = {
                "SSN / Cédula (*Obligatorio):", ssnIn,
                "Nombre Completo (*Obligatorio):", nomIn,
                "Dirección:", dirIn,
                "Teléfono (Solo números):", telIn,
                "Salario ($):", salIn,
                "Número Unión:", uniIn
        };

        int option = JOptionPane.showConfirmDialog(this, formulario, "Registrar Nuevo Empleado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            if (ssnIn.getText().trim().isEmpty() || nomIn.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El SSN y el Nombre son estrictamente obligatorios.", "Campos Incompletos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String telefono = telIn.getText().trim().isEmpty() ? "N/A" : telIn.getText().trim();
            double salario = salIn.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(salIn.getText().trim());
            String numUnion = uniIn.getText().trim().isEmpty() ? "0" : uniIn.getText().trim();

            try {
                Empleado emp = new Empleado(
                        ssnIn.getText().trim(), nomIn.getText().trim(), dirIn.getText().trim(),
                        telefono, salario, numUnion
                );

                if (empleadoDAO.agregarEmpleado(emp)) {
                    JOptionPane.showMessageDialog(this, "Empleado guardado de forma exitosa.");
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error: El SSN ya existe en la base de datos.", "Clave Duplicada", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar los datos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void gestionarModificacion(int fila) {
        String ssn = tablaEmpleados.getValueAt(fila, 0).toString();
        JTextField nomIn = new JTextField(tablaEmpleados.getValueAt(fila, 1).toString());
        JTextField dirIn = new JTextField(tablaEmpleados.getValueAt(fila, 2).toString());
        JTextField telIn = new JTextField(tablaEmpleados.getValueAt(fila, 3).toString());
        JTextField salIn = new JTextField(tablaEmpleados.getValueAt(fila, 4).toString());
        JTextField uniIn = new JTextField(tablaEmpleados.getValueAt(fila, 5).toString());

        configurarSoloNumerosEnteros(telIn);
        configurarSoloNumerosDecimales(salIn);
        configurarSoloNumerosEnteros(uniIn);

        Object[] formulario = {
                "SSN (Llave Primaria - No editable):", new JLabel(ssn),
                "Nombre Completo (*Obligatorio):", nomIn,
                "Dirección:", dirIn,
                "Teléfono:", telIn,
                "Salario ($):", salIn,
                "Número Unión:", uniIn
        };

        int option = JOptionPane.showConfirmDialog(this, formulario, "Modificar Registro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            if (nomIn.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del empleado no puede quedar vacío.", "Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double salario = salIn.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(salIn.getText().trim());

            try {
                Empleado emp = new Empleado(
                        ssn, nomIn.getText().trim(), dirIn.getText().trim(),
                        telIn.getText().trim(), salario, uniIn.getText().trim()
                );

                if (empleadoDAO.actualizarEmpleado(emp)) {
                    JOptionPane.showMessageDialog(this, "Registro actualizado con éxito.");
                    llenarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el registro en la BD.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato numérico incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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