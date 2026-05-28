package org.example.vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {

        setTitle("SGBD AEROPUERTO");

        setSize(900, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);


        JPanel panelSuperior = new JPanel();

        panelSuperior.setBackground(new Color(0, 102, 204));

        panelSuperior.setBounds(0, 0, 900, 80);

        panelSuperior.setLayout(null);

        JLabel lblTitulo = new JLabel("SISTEMA DE GESTION AEROPORTUARIA");

        lblTitulo.setForeground(Color.WHITE);

        lblTitulo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 28));

        lblTitulo.setBounds(160, 20, 600, 30);

        panelSuperior.add(lblTitulo);

        add(panelSuperior);


        JPanel panelMenu = new JPanel();

        panelMenu.setBackground(new Color(230, 230, 230));

        panelMenu.setBounds(0, 80, 220, 520);

        panelMenu.setLayout(null);

        add(panelMenu);


        JButton btnEmpleados = new JButton("EMPLEADOS");

        btnEmpleados.setBounds(20, 30, 180, 40);

        panelMenu.add(btnEmpleados);

        JButton btnVuelos = new JButton("VUELOS");

        btnVuelos.setBounds(20, 90, 180, 40);

        panelMenu.add(btnVuelos);

        JButton btnPasajeros = new JButton("PASAJEROS");

        btnPasajeros.setBounds(20, 150, 180, 40);

        panelMenu.add(btnPasajeros);

        JButton btnEquipaje = new JButton("EQUIPAJE");

        btnEquipaje.setBounds(20, 210, 180, 40);

        panelMenu.add(btnEquipaje);

        JButton btnReportes = new JButton("REPORTES");

        btnReportes.setBounds(20, 270, 180, 40);

        panelMenu.add(btnReportes);

        JButton btnConfiguracion = new JButton("CONFIGURACION");

        btnConfiguracion.setBounds(20, 330, 180, 40);

        panelMenu.add(btnConfiguracion);

        JButton btnAyuda = new JButton("AYUDA");

        btnAyuda.setBounds(20, 390, 180, 40);

        panelMenu.add(btnAyuda);

        JButton btnSalir = new JButton("SALIR");

        btnSalir.setBounds(20, 450, 180, 40);

        panelMenu.add(btnSalir);


        JPanel panelCentral = new JPanel();

        panelCentral.setBackground(Color.WHITE);

        panelCentral.setBounds(220, 80, 680, 520);

        panelCentral.setLayout(null);

        add(panelCentral);

        JLabel lblBienvenida = new JLabel("BIENVENIDO AL SISTEMA");

        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 30));

        lblBienvenida.setBounds(140, 80, 450, 40);

        panelCentral.add(lblBienvenida);

        JLabel lblInfo = new JLabel("Seleccione una opcion del menu lateral");

        lblInfo.setFont(new Font("Arial", Font.PLAIN, 18));

        lblInfo.setBounds(170, 150, 350, 30);

        panelCentral.add(lblInfo);

        JTextArea areaSistema = new JTextArea();

        areaSistema.setEditable(false);

        areaSistema.setFont(new Font("Monospaced", Font.PLAIN, 16));

        areaSistema.setText(
                "SISTEMA GESTOR DE BASES DE DATOS\n\n" +
                        "MODULOS DISPONIBLES:\n\n" +
                        "- EMPLEADOS\n" +
                        "- VUELOS\n" +
                        "- PASAJEROS\n" +
                        "- EQUIPAJE\n" +
                        "- REPORTES\n" +
                        "- CONFIGURACION"
        );

        areaSistema.setBounds(120, 220, 400, 220);

        panelCentral.add(areaSistema);


        btnEmpleados.addActionListener(e -> {

            new FrmEmpleados();

        });

        btnSalir.addActionListener(e -> {

            System.exit(0);

        });

        setVisible(true);
    }
}
