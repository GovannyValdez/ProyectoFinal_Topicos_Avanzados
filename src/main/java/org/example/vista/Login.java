package org.example.vista;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public Login() {

        setTitle("LOGIN");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // PANEL SUPERIOR
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(0, 150, 255));
        panelTitulo.setBounds(0, 0, 500, 70);
        panelTitulo.setLayout(null);

        JLabel lblTitulo = new JLabel("INICIO DE SESION");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));
        lblTitulo.setBounds(100, 15, 300, 30);

        panelTitulo.add(lblTitulo);

        add(panelTitulo);

        // USUARIO
        JLabel lblUsuario = new JLabel("USUARIO:");
        lblUsuario.setBounds(60, 110, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(170, 110, 200, 25);
        add(txtUsuario);

        // PASSWORD
        JLabel lblPassword = new JLabel("CONTRASEÑA:");
        lblPassword.setBounds(60, 160, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(170, 160, 200, 25);
        add(txtPassword);

        // BOTONES
        JButton btnIngresar = new JButton("INGRESAR");
        btnIngresar.setBounds(90, 230, 120, 30);
        add(btnIngresar);

        JButton btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.setBounds(260, 230, 120, 30);
        add(btnLimpiar);

        // EVENTOS
        btnIngresar.addActionListener(this::iniciarSesion);

        btnLimpiar.addActionListener(e -> limpiarCampos());

        setVisible(true);
    }

    private void iniciarSesion(ActionEvent e) {

        String usuario = txtUsuario.getText();

        String password = new String(txtPassword.getPassword());

        // VALIDACION
        if (usuario.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Completa todos los campos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // USUARIO Y PASSWORD
        if (usuario.equals("admin") && password.equals("1234")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Bienvenido " + usuario
            );

            // ABRIR OTRA VENTANA
            new AltasAlumnos();

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario o contraseña incorrectos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarCampos() {

        txtUsuario.setText("");

        txtPassword.setText("");
    }
}

