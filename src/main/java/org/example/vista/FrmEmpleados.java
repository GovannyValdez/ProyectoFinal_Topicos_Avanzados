package org.example.vista;

import javax.swing.*;
import java.awt.*;

public class FrmEmpleados extends JInternalFrame {

    public FrmEmpleados() {
        setTitle("Gestión de Empleados");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(800, 500);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        // Contenido temporal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Módulo de Empleados - En construcción", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(label, BorderLayout.CENTER);

        setContentPane(panel);
    }
}