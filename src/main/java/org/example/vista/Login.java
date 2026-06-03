package org.example.vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.net.URL;

public class Login extends JFrame {

    private static final Color C_BG       = new Color(0xF0F4F8);
    private static final Color C_NAVY     = new Color(0x1E3A5F);
    private static final Color C_TEXT     = new Color(0x2D3748);
    private static final Color C_MUTED    = new Color(0x7A93B0);
    private static final Color C_ACCENT   = new Color(0x0F52BA);
    private static final Color C_GREEN    = new Color(0x2A9D8F);
    private static final Color C_ERROR    = new Color(0xE63946);
    private static final Color C_INPUT_BG = new Color(0xF8FAFC);
    private static final Color C_BORDER   = new Color(0xC8D6E5);
    private static final Color C_WHITE    = Color.WHITE;


    private static final String LOGO_PATH = "org/example/imagenes/logo.png";

    private static final Font F_TITLE  = new Font("Segoe UI", Font.BOLD,  17);
    private static final Font F_SUB    = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font F_LABEL  = new Font("Segoe UI", Font.BOLD,  11);
    private static final Font F_INPUT  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font F_BUTTON = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font F_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);


    private JTextField     txtUsuario;
    private JPasswordField txtPassword;
    private JLabel         lblError;
    private ImageIcon      originalLogo;
    private JButton btnEntrar;


    private JDialog        dialogCarga;
    private JProgressBar   progressBar;
    private JLabel         lblEstadoCarga;

    public Login() {
        setTitle("Sistema Aeroportuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 540);
        setLocationRelativeTo(null);

        originalLogo = loadLogo();

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(C_BG);
        setContentPane(root);
        root.add(buildCard());
        setVisible(true);
    }

    private JPanel buildCard() {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(340, 490));
        card.add(buildHeader(), BorderLayout.NORTH);
        card.add(buildBody(),   BorderLayout.CENTER);
        card.add(buildFooter(), BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_NAVY);
                g2.fillRoundRect(0, 0, getWidth(), getHeight() + 14, 14, 14);
                g2.dispose();
            }
        };
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(340, 165));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.anchor = GridBagConstraints.CENTER;

        JPanel logoCircle = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics gr) {
                Graphics2D g2 = (Graphics2D) gr.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255, 255, 255, 50));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
                g2.dispose();
                super.paintComponent(gr);
            }
        };
        logoCircle.setOpaque(false);
        logoCircle.setPreferredSize(new Dimension(64, 64));

        if (originalLogo != null) {
            JLabel logoLabel = new JLabel();
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logoLabel.setVerticalAlignment(SwingConstants.CENTER);

            int logoSize = (int)(64 * 0.75);
            Image scaledImg = originalLogo.getImage().getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImg));

            logoCircle.add(logoLabel);
        } else {
            JLabel txt = new JLabel("✈");
            txt.setFont(new Font("Segoe UI", Font.PLAIN, 28));
            txt.setForeground(new Color(255, 255, 255, 140));
            txt.setHorizontalAlignment(SwingConstants.CENTER);
            logoCircle.add(txt);
        }

        g.gridy = 0; g.insets = new Insets(18, 0, 10, 0);
        header.add(logoCircle, g);

        JLabel lblTitle = new JLabel("Sistema Aeroportuario");
        lblTitle.setFont(F_TITLE);
        lblTitle.setForeground(C_WHITE);
        g.gridy = 1; g.insets = new Insets(0, 0, 3, 0);
        header.add(lblTitle, g);

        JLabel lblSub = new JLabel("Control de Acceso");
        lblSub.setFont(F_SUB);
        lblSub.setForeground(new Color(255, 255, 255, 130));
        g.gridy = 2; g.insets = new Insets(0, 0, 10, 0);
        header.add(lblSub, g);

        JPanel line = new JPanel();
        line.setOpaque(true);
        line.setBackground(C_ACCENT);
        line.setPreferredSize(new Dimension(34, 2));
        g.gridy = 3; g.insets = new Insets(0, 0, 14, 0);
        header.add(line, g);

        return header;
    }

    private JPanel buildBody() {
        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(C_WHITE);
        body.setBorder(BorderFactory.createEmptyBorder(22, 28, 18, 28));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;

        JLabel lblUsuario = new JLabel("USUARIO");
        lblUsuario.setFont(F_LABEL);
        lblUsuario.setForeground(C_TEXT);
        g.gridy = 0; g.insets = new Insets(0, 0, 5, 0);
        g.anchor = GridBagConstraints.WEST;
        body.add(lblUsuario, g);

        txtUsuario = new JTextField();
        styleInput(txtUsuario);
        g.gridy = 1; g.insets = new Insets(0, 0, 14, 0);
        body.add(txtUsuario, g);

        JLabel lblPass = new JLabel("CONTRASEÑA");
        lblPass.setFont(F_LABEL);
        lblPass.setForeground(C_TEXT);
        g.gridy = 2; g.insets = new Insets(0, 0, 5, 0);
        body.add(lblPass, g);

        txtPassword = new JPasswordField();
        styleInput(txtPassword);
        g.gridy = 3; g.insets = new Insets(0, 0, 5, 0);
        body.add(txtPassword, g);

        JLabel lblForgot = new JLabel("Olvide mi contraseña");
        lblForgot.setFont(F_SMALL);
        lblForgot.setForeground(C_ACCENT);
        lblForgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        g.gridy = 4; g.insets = new Insets(0, 0, 14, 0);
        g.anchor = GridBagConstraints.EAST;
        g.fill = GridBagConstraints.NONE;
        body.add(lblForgot, g);

        lblError = new JLabel("Usuario o contraseña incorrectos");
        lblError.setFont(F_SMALL);
        lblError.setForeground(C_ERROR);
        lblError.setOpaque(true);
        lblError.setBackground(new Color(0xFFF0F0));
        lblError.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 4, 1, 1, C_ERROR),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        lblError.setVisible(false);
        g.gridy = 5; g.insets = new Insets(0, 0, 12, 0);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        body.add(lblError, g);

        btnEntrar = buildButton("INICIAR SESION");
        g.gridy = 6; g.insets = new Insets(0, 0, 0, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        body.add(btnEntrar, g);

        btnEntrar.addActionListener(this::iniciarSesion);
        txtPassword.addActionListener(this::iniciarSesion);
        return body;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 10));
        footer.setBackground(C_WHITE);
        footer.setBorder(new MatteBorder(1, 0, 0, 0, C_BORDER));

        JLabel shield = new JLabel("+");
        shield.setFont(new Font("Segoe UI", Font.BOLD, 10));
        shield.setForeground(C_GREEN);

        JLabel txt = new JLabel("Acceso seguro y cifrado");
        txt.setFont(F_SMALL);
        txt.setForeground(C_MUTED);

        footer.add(shield);
        footer.add(txt);
        return footer;
    }

    private void styleInput(JTextField field) {
        field.setFont(F_INPUT);
        field.setForeground(C_TEXT);
        field.setBackground(C_INPUT_BG);
        field.setCaretColor(C_ACCENT);
        field.setPreferredSize(new Dimension(284, 38));
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(7, C_BORDER, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(7, C_ACCENT, 2),
                        BorderFactory.createEmptyBorder(0, 10, 0, 10)
                ));
            }
            @Override public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(7, C_BORDER, 1),
                        BorderFactory.createEmptyBorder(0, 10, 0, 10)
                ));
            }
        });
    }


    private JButton buildButton(String label) {
        JButton btn = new JButton(label) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? C_NAVY
                        : getModel().isRollover() ? new Color(0x0D47A1)
                        : C_ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(F_BUTTON);
        btn.setForeground(C_WHITE);
        btn.setPreferredSize(new Dimension(284, 42));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }



    private void iniciarSesion(ActionEvent e) {

        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.equals("admin") && password.equals("1234")) {

            lblError.setVisible(false);

            btnEntrar.setEnabled(false);

            mostrarDialogoCarga();

            new HiloCarga().start();

        } else {

            lblError.setVisible(true);

        }
    }


    private void mostrarDialogoCarga() {
        dialogCarga = new JDialog(this, "Cargando", false);        dialogCarga.setSize(300, 130);
        dialogCarga.setLocationRelativeTo(this);
        dialogCarga.setUndecorated(true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(C_WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(12, C_ACCENT, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        lblEstadoCarga = new JLabel("Iniciando sesión...", SwingConstants.CENTER);
        lblEstadoCarga.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstadoCarga.setForeground(C_ACCENT);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(C_ACCENT);
        progressBar.setBackground(new Color(0xE2E8F0));
        progressBar.setBorder(BorderFactory.createEmptyBorder());
        progressBar.setPreferredSize(new Dimension(250, 25));

        JLabel lblIcono = new JLabel("", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblIcono.setForeground(C_ACCENT);

        panel.add(lblIcono, BorderLayout.NORTH);
        panel.add(lblEstadoCarga, BorderLayout.CENTER);
        panel.add(progressBar, BorderLayout.SOUTH);

        dialogCarga.add(panel);
        dialogCarga.setVisible(true);
    }




    //hilo
    class HiloCarga extends Thread {

        private final String[] mensajes = {
                "Verificando credenciales...",
                "Conectando con la base de datos...",
                "Cargando módulos del sistema...",
                "Inicializando interfaz...",
                "Abriendo menú principal..."
        };

        @Override
        public void run() {

            try {

                for (int i = 0; i < mensajes.length; i++) {

                    final int progreso = (i + 1) * 20;
                    final String mensaje = mensajes[i];

                    Thread.sleep(400);

                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(progreso);
                        lblEstadoCarga.setText(mensaje);
                    });
                }

                Thread.sleep(300);

                SwingUtilities.invokeLater(() -> {

                    if (dialogCarga != null) {
                        dialogCarga.dispose();
                    }

                    new MenuPrincipal();

                    Login.this.dispose();

                });

            } catch (InterruptedException ex) {

                ex.printStackTrace();

                SwingUtilities.invokeLater(() -> {
                    if (dialogCarga != null) {
                        dialogCarga.dispose();
                    }
                    btnEntrar.setEnabled(true);
                });
            }
        }
    }




    private ImageIcon loadLogo() {
        try {
            File logoFile = new File("src/main/java/org/example/imagenes/logo.png");
            if (logoFile.exists()) {
                System.out.println("Logo encontrado en: " + logoFile.getAbsolutePath());
                return new ImageIcon(logoFile.getAbsolutePath());
            }
        } catch (Exception e) {}

        try {
            URL url = getClass().getClassLoader().getResource("org/example/imagenes/logo.png");
            if (url != null) {
                System.out.println("Logo encontrado en classpath: " + url);
                return new ImageIcon(url);
            }
        } catch (Exception e) {}

        try {
            File logoFile = new File("logo.png");
            if (logoFile.exists()) {
                System.out.println("Logo encontrado en raíz: " + logoFile.getAbsolutePath());
                return new ImageIcon(logoFile.getAbsolutePath());
            }
        } catch (Exception e) {}

        System.err.println("No se encontró el logo en ninguna ubicación");
        return null;
    }

    static class RoundedBorder extends AbstractBorder {
        private final int arc, thickness;
        private final Color color;


        RoundedBorder(int arc, Color color, int thickness) {
            this.arc = arc; this.color = color; this.thickness = thickness;
        }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c) {
            return new Insets(thickness + 3, thickness + 3, thickness + 3, thickness + 3);
        }
    }
}