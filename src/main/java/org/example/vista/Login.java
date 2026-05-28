package org.example.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public Login() {

        setTitle("LOGIN");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblUsuario = new JLabel("USUARIO:");
        lblUsuario.setBounds(50, 70, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(150, 70, 150, 25);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("CONTRASEÑA:");
        lblPassword.setBounds(50, 120, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 120, 150, 25);
        add(txtPassword);

        JButton btnEntrar = new JButton("ENTRAR");
        btnEntrar.setBounds(130, 180, 120, 30);
        add(btnEntrar);

        btnEntrar.addActionListener(this::iniciarSesion);

        setVisible(true);
    }

    private void iniciarSesion(ActionEvent e) {

        String usuario = txtUsuario.getText();

        String password = new String(txtPassword.getPassword());

        if (usuario.equals("admin") && password.equals("1234")) {

            JOptionPane.showMessageDialog(this, "Bienvenido");

            new MenuPrincipal();

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
}