package org.example.vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPrincipal extends JFrame {

    private static final Color C_BG          = new Color(0xF0F4F8);
    private static final Color C_NAVY        = new Color(0x1E3A5F);
    private static final Color C_NAVY_DARK   = new Color(0x152B47);
    private static final Color C_ACCENT      = new Color(0x0F52BA);
    private static final Color C_GREEN       = new Color(0x2A9D8F);
    private static final Color C_TEXT        = new Color(0x2D3748);
    private static final Color C_MUTED       = new Color(0xA0AEC0);
    private static final Color C_WHITE       = Color.WHITE;
    private static final Color C_SIDEBAR_HOV = new Color(0x25496E);
    private static final Color C_ERROR       = new Color(0xE63946);

    private static final Font F_HEADER  = new Font("Segoe UI", Font.BOLD,  15);
    private static final Font F_MENU    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font F_WELCOME = new Font("Segoe UI", Font.BOLD,  22);

    private JDesktopPane desktop;
    private JButton btnActivo = null;

    public MenuPrincipal() {
        setTitle("SGBD Aeropuerto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setSize(1100, 680);
        setLocationRelativeTo(null);

       

        setJMenuBar(buildMenuBar());

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(C_BG);
        setContentPane(root);

        root.add(buildToolBar(), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(C_BG);
        body.add(buildSidebar(), BorderLayout.WEST);
        body.add(buildDesktop(), BorderLayout.CENTER);
        root.add(body, BorderLayout.CENTER);

        setVisible(true);
    }

    private JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar();
        bar.setBackground(C_NAVY);
        bar.setBorder(BorderFactory.createEmptyBorder());

        JMenu mArchivo = menuItem("Archivo");
        mArchivo.add(menuOption("Nuevo"));
        mArchivo.add(menuOption("Exportar"));
        mArchivo.addSeparator();
        JMenuItem salir = menuOption("Salir");
        salir.addActionListener(e -> cerrarSesion());
        mArchivo.add(salir);

        JMenu mModulos = menuItem("Modulos");
        JMenuItem miEmpleados = menuOption("Empleados");
        miEmpleados.addActionListener(e -> abrirEmpleados());
        mModulos.add(miEmpleados);
        mModulos.add(menuOption("Vuelos"));
        mModulos.add(menuOption("Pasajeros"));
        mModulos.add(menuOption("Equipaje"));

        JMenu mReportes = menuItem("Reportes");
        mReportes.add(menuOption("Generar reporte"));
        mReportes.add(menuOption("Ver historial"));

        JMenu mAyuda = menuItem("Ayuda");
        mAyuda.add(menuOption("Manual de usuario"));
        mAyuda.add(menuOption("Acerca de"));

        bar.add(mArchivo);
        bar.add(mModulos);
        bar.add(mReportes);
        bar.add(mAyuda);

        return bar;
    }

    private JMenu menuItem(String texto) {
        JMenu m = new JMenu(texto);
        m.setForeground(new Color(255, 255, 255, 200));
        m.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        m.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        return m;
    }

    private JMenuItem menuOption(String texto) {
        JMenuItem mi = new JMenuItem(texto);
        mi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        mi.setBackground(C_WHITE);
        mi.setForeground(C_TEXT);
        return mi;
    }

    private JToolBar buildToolBar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        tb.setBackground(C_NAVY);
        tb.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        JLabel title = new JLabel("  Sistema de Gestion Aeroportuaria");
        title.setFont(F_HEADER);
        title.setForeground(C_WHITE);

        JPanel sep = new JPanel();
        sep.setOpaque(false);
        sep.setPreferredSize(new Dimension(16, 1));

        tb.add(title);
        tb.add(Box.createHorizontalGlue());

        tb.add(toolBtn("Empleados", e -> abrirEmpleados()));
        tb.add(toolSep());
        tb.add(toolBtn("Vuelos", e -> {}));
        tb.add(toolSep());
        tb.add(toolBtn("Pasajeros", e -> {}));
        tb.add(toolSep());
        tb.add(toolBtn("Equipaje", e -> {}));
        tb.add(toolSep());
        tb.add(toolBtn("Reportes", e -> {}));

        tb.add(Box.createHorizontalGlue());

        JButton btnCerrarSesion = toolBtn("Salir", e -> cerrarSesion());
        btnCerrarSesion.setForeground(new Color(255, 200, 200, 190));
        tb.add(btnCerrarSesion);
        tb.add(toolSep());

        JPanel avatar = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_ACCENT);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avatar.setOpaque(false);
        avatar.setPreferredSize(new Dimension(30, 30));
        avatar.setMaximumSize(new Dimension(30, 30));
        JLabel av = new JLabel("A");
        av.setFont(new Font("Segoe UI", Font.BOLD, 13));
        av.setForeground(C_WHITE);
        avatar.add(av);
        tb.add(avatar);

        return tb;
    }

    private JButton toolBtn(String label, ActionListener al) {
        JButton btn = new JButton(label) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(new Color(255,255,255,40));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(255,255,255,20));
                } else {
                    g2.setColor(new Color(0,0,0,0));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(new Color(255, 255, 255, 190));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(al);
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.repaint(); }
            @Override public void mouseExited(MouseEvent e)  { btn.repaint(); }
        });
        return btn;
    }

    private JSeparator toolSep() {
        JSeparator s = new JSeparator(JSeparator.VERTICAL);
        s.setMaximumSize(new Dimension(1, 20));
        s.setForeground(new Color(255, 255, 255, 40));
        return s;
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(C_NAVY_DARK);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        sidebar.setPreferredSize(new Dimension(200, 600));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setOpaque(false);

        sidebar.add(Box.createVerticalStrut(16));

        JLabel seccion = new JLabel("  NAVEGACION");
        seccion.setFont(new Font("Segoe UI", Font.BOLD, 9));
        seccion.setForeground(new Color(255, 255, 255, 70));
        seccion.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(seccion);
        sidebar.add(Box.createVerticalStrut(6));

        sidebar.add(sideBtn("Empleados",     "Gestion de personal",    e -> abrirEmpleados()));
        sidebar.add(sideBtn("Vuelos",         "Control de vuelos",      e -> {}));
        sidebar.add(sideBtn("Pasajeros",      "Registro de pasajeros",  e -> {}));
        sidebar.add(sideBtn("Equipaje",       "Control de equipaje",    e -> {}));
        sidebar.add(sideBtn("Reportes",       "Estadisticas",           e -> {}));
        sidebar.add(sideBtn("Configuracion",  "Ajustes del sistema",    e -> {}));
        sidebar.add(sideBtn("Ayuda",          "Centro de ayuda",        e -> {}));

        sidebar.add(Box.createVerticalGlue());

        JButton btnSalir = new JButton("Cerrar sesion") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? C_ERROR : new Color(255,255,255,12));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 7, 7);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnSalir.setForeground(new Color(255,255,255,150));
        btnSalir.setContentAreaFilled(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSalir.setPreferredSize(new Dimension(176, 36));
        btnSalir.setMaximumSize(new Dimension(176, 36));
        btnSalir.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSalir.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
        btnSalir.addActionListener(e -> cerrarSesion());
        btnSalir.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btnSalir.repaint(); }
            @Override public void mouseExited(MouseEvent e)  { btnSalir.repaint(); }
        });

        sidebar.add(btnSalir);
        sidebar.add(Box.createVerticalStrut(14));

        return sidebar;
    }

    private JButton sideBtn(String nombre, String desc, ActionListener al) {
        JButton btn = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                if (this == btnActivo) {
                    g2.setColor(C_ACCENT);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setColor(C_WHITE);
                    g2.fillRect(0, (getHeight()-22)/2, 3, 22);
                } else if (getModel().isRollover()) {
                    g2.setColor(C_SIDEBAR_HOV);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setLayout(new BorderLayout());
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setMaximumSize(new Dimension(200, 50));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel inner = new JPanel(new GridBagLayout());
        inner.setOpaque(false);
        inner.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 8));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.weightx = 1.0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.WEST;

        JLabel n = new JLabel(nombre);
        n.setFont(F_MENU);
        n.setForeground(new Color(255,255,255,210));
        gc.gridy = 0; gc.insets = new Insets(0,0,1,0);
        inner.add(n, gc);

        JLabel d = new JLabel(desc);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        d.setForeground(new Color(255,255,255,80));
        gc.gridy = 1; gc.insets = new Insets(0,0,0,0);
        inner.add(d, gc);

        btn.add(inner, BorderLayout.CENTER);

        btn.addActionListener(e -> {
            btnActivo = btn;
            btn.repaint();
            al.actionPerformed(e);
        });
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.repaint(); }
            @Override public void mouseExited(MouseEvent e)  { btn.repaint(); }
        });
        return btn;
    }

    private JDesktopPane buildDesktop() {
        desktop = new JDesktopPane();
        desktop.setBackground(C_BG);
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

        JPanel welcome = new JPanel(new GridBagLayout());
        welcome.setOpaque(false);
        welcome.setBounds(0, 0, 900, 600);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.anchor = GridBagConstraints.CENTER;

        JLabel lblW = new JLabel("Bienvenido al sistema");
        lblW.setFont(F_WELCOME);
        lblW.setForeground(C_TEXT);
        g.gridy = 0; g.insets = new Insets(0,0,6,0);
        welcome.add(lblW, g);

        JLabel lblS = new JLabel("Seleccione una opcion del menu lateral para continuar");
        lblS.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblS.setForeground(C_MUTED);
        g.gridy = 1; g.insets = new Insets(0,0,36,0);
        welcome.add(lblS, g);

        JPanel cards = new JPanel(new GridLayout(2, 3, 14, 14));
        cards.setOpaque(false);
        cards.setPreferredSize(new Dimension(560, 220));

        String[][] mods = {
                {"Empleados","Personal activo"},
                {"Vuelos","Vuelos programados"},
                {"Pasajeros","Pasajeros registrados"},
                {"Equipaje","Bultos en sistema"},
                {"Reportes","Informes disponibles"},
                {"Configuracion","Ajustes del sistema"}
        };
        for (String[] m : mods) cards.add(buildCard(m[0], m[1]));

        g.gridy = 2; g.insets = new Insets(0,0,0,0);
        welcome.add(cards, g);

        desktop.add(welcome);
        desktop.setLayer(welcome, JLayeredPane.DEFAULT_LAYER);

        return desktop;
    }

    private JPanel buildCard(String titulo, String desc) {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(C_WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(new Color(0xC8D6E5));
                g2.setStroke(new BasicStroke(0.8f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };
        card.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.anchor = GridBagConstraints.CENTER;

        JPanel dot = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(15, 82, 186, 25));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        dot.setOpaque(false);
        dot.setPreferredSize(new Dimension(36, 36));
        gc.gridy = 0; gc.insets = new Insets(14,0,8,0);
        card.add(dot, gc);

        JLabel t = new JLabel(titulo);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setForeground(C_TEXT);
        gc.gridy = 1; gc.insets = new Insets(0,0,3,0);
        card.add(t, gc);

        JLabel d = new JLabel(desc);
        d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        d.setForeground(C_MUTED);
        gc.gridy = 2; gc.insets = new Insets(0,0,14,0);
        card.add(d, gc);

        return card;
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            // Cierra la ventana actual (MenuPrincipal)
            dispose();
            // Abre una nueva instancia de Login
            new Login();
        }
    }

    private void abrirEmpleados() {
        for (JInternalFrame f : desktop.getAllFrames()) {
            if (f instanceof FrmEmpleados) {
                try { f.setSelected(true); } catch (Exception ex) {}
                return;
            }
        }
        FrmEmpleados frm = new FrmEmpleados();
        desktop.add(frm);
        frm.setVisible(true);
        try { frm.setSelected(true); } catch (Exception ex) {}
    }
}