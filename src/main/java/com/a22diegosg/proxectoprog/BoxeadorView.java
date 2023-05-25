/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a22diegosg.proxectoprog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author A22DiegoSG
 */
public class BoxeadorView extends JFrame {

    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:D:\\_Clase\\A22_Programacion\\proxectoProg\\src\\main\\resources\\bd\\boxeador";

    static
    {
        System.out.println(BoxeadorView.class.getResource("bd/boxeador"));
    }

    private ResultSet rs;

    private JPanel panelSur = new JPanel(new FlowLayout());
    private JPanel panelDatos = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
    private JPanel panelDatosBox = new JPanel(new GridLayout(5, 2));
    private JPanel panelFoto = new JPanel(new BorderLayout());

    private JButton seguinte;
    private JButton anterior;

    private JPanel panelbox1 = new JPanel();
    private JPanel panelbox2 = new JPanel();
    private JPanel panelboxwin = new JPanel();
    private JLabel nombreImg, nombre, nacImg, nac, peleasImg, peleas, ganadasImg,
            ganadas, perdidasImg, perdidas, fotoLbl;

    public BoxeadorView(String title) throws HeadlessException {
        super(title);
        crearGUI();
        setConnection();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void crearGUI() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass()
                .getResource("/images/icono.png")));
        setMenu();
        setPanelNorte();
        setPanelSur();

    }

    public void setPanelNorte() {
        panelDatos.setBackground(Color.white);
        panelFoto.setBackground(Color.white);
        panelDatosBox.add(nombreImg = new JLabel("Nombre: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/nombre.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(nombre = new JLabel());
        panelDatosBox.add(nacImg = new JLabel("Nacion: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/mundo.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(nac = new JLabel());
        panelDatosBox.add(peleasImg = new JLabel("Peleas: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/peleas.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(peleas = new JLabel());
        panelDatosBox.add(ganadasImg = new JLabel("Ganadas: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/win.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(ganadas = new JLabel());
        panelDatosBox.add(perdidasImg = new JLabel("Perdidas: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/lose.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(perdidas = new JLabel());

        panelFoto.add(fotoLbl = new JLabel());
        panelDatos.add(panelFoto, BorderLayout.WEST);
        panelDatos.add(panelDatosBox, BorderLayout.EAST);
        add(panelDatos);
    }

    public void setPanelSur() {
        panelSur.setBackground(Color.white);
        seguinte = new JButton(new ImageIcon(
                BoxeadorView.class.getResource("/images/seguinte.png")));
        seguinte.setBorderPainted(false);
        seguinte.setContentAreaFilled(false);

        anterior = new JButton(new ImageIcon(
                BoxeadorView.class.getResource("/images/anterior.png")));
        anterior.setBorderPainted(false);
        anterior.setContentAreaFilled(false);
        anterior.setEnabled(false);
        seguinte.addActionListener((ActionEvent e) ->
        {
            try
            {
                if (rs.next())
                {
                    System.out.println("seguinte");
                    setValores();
                    anterior.setEnabled(true);
                    if (rs.isLast())
                    {
                        seguinte.setEnabled(false);
                    }
                }
            } catch (SQLException ex)
            {
                System.out.println("Erro no ActionListener: " + ex.getMessage());
            }
        });

        anterior.addActionListener((ActionEvent e) ->
        {
            try
            {
                if (rs.previous())
                {
                    System.out.println("anterior");
                    setValores();
                    seguinte.setEnabled(true);
                    if (rs.isFirst())
                    {
                        anterior.setEnabled(false);
                    }
                }
            } catch (SQLException ex)
            {
                System.out.println("Erro no ActionListener: " + ex.getMessage());
            }
        });
        panelSur.add(anterior);
        panelSur.add(seguinte);
        add(panelSur, BorderLayout.SOUTH);
    }

    public void setConnection() {
        try
        {
            Class.forName(DRIVER);
            System.out.println("Drivers correctos");
        } catch (ClassNotFoundException ex)
        {
            System.out.println("Erro Drivers: " + ex.getMessage());
        }
        try
        {
            System.out.println("Statement creado");
            Connection con = DriverManager.getConnection(URL);
            PreparedStatement ps = con.prepareStatement("SELECT nombre, nacionalidad,"
                    + " peleas, ganadas, perdidas, foto FROM boxeador",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            rs = ps.executeQuery();
            if (rs.next())
            {
                setValores();
            }

        } catch (SQLException ex)
        {
            System.out.println("Erro na base de datos: " + ex.getMessage());
        }
    }

    public void setValores() {

        try
        {
            if (rs != null && !rs.isClosed()
                    && !rs.isBeforeFirst() && !rs.isAfterLast())
            {
                nombre.setText(rs.getString("nombre"));
                nac.setText(rs.getString("nacionalidad"));
                peleas.setText(rs.getString("peleas"));
                ganadas.setText(rs.getString("ganadas"));
                perdidas.setText(rs.getString("perdidas"));
                byte[] imagen = rs.getBytes("FOTO");
                if (imagen != null)
                {
                    try
                    {
                        Image image = ImageIO.read(new ByteArrayInputStream(imagen));
                        ImageIcon imageIcon = new ImageIcon(image);
                        fotoLbl.setIcon(imageIcon);
                    } catch (IOException e)
                    {
                        System.out.println(e.getMessage());
                    }
                } else
                {
                    fotoLbl.setIcon(null); //NECESARIO COMPROBAR CON NULOS
                }

            }
        } catch (SQLException ex)
        {
            System.out.println("Erro SQL: " + ex.getMessage());
        }

    }

    public void setMenu() {
        JMenuBar mnuBar = new JMenuBar();
        JMenu menu = new JMenu();
        JMenuItem guardar = new JMenuItem("Guardar boxeadores", new ImageIcon(
                BoxeadorView.class.getResource("/images/guardar.png")));
        ImageIcon icono = new ImageIcon(BoxeadorView.class.getResource(
                "/images/menu.png"));
        guardar.addActionListener((ActionEvent e)
                ->
        {
            JFileChooser fc = new JFileChooser("c:\\");
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                File f = fc.getSelectedFile();
                try ( BufferedWriter br = new BufferedWriter(new FileWriter(f)))
                {
                    rs.beforeFirst();
                    while (rs.next())
                    {
                        br.write(new String("Nombre: " + rs.getString("nombre")));
                        br.write(new String(" Nacionalidad: " + rs.getString("nacionalidad")));
                        br.write(new String(" Peleas: " + String.valueOf(rs.getInt("peleas"))));
                        br.write(new String(" Ganadas: " + String.valueOf(rs.getInt("ganadas"))));
                        br.write(new String(" Perdidas: " + String.valueOf(rs.getInt("perdidas"))));
                        br.write("\n");
                    }
                } catch (Exception ex)
                {
                    System.out.println("Erro:" + ex.getMessage());
                }
            }
        });
        JMenuItem mnuPeleas = new JMenuItem("Historial peleas", new ImageIcon(
                BoxeadorView.class.getResource("/images/peleas.png")));
        JMenuItem mnuPerfil = new JMenuItem("Boxeadores", new ImageIcon(
                BoxeadorView.class.getResource("/images/nombre.png")));

        mnuPeleas.addActionListener((ActionEvent e)
                ->
        {
            try
            {
                panelSur.setBackground(Color.lightGray);
                panelDatos.setBackground(Color.lightGray);
                panelDatos.removeAll();
                panelDatos.repaint();
                panelDatos.revalidate();
                panelSur.removeAll();
                panelSur.repaint();
                panelSur.revalidate();
                add(panelSur, BorderLayout.PAGE_START);
                panelDatos.setLayout(new GridLayout(1, 3, 10, 10));
                panelSur.setLayout(new GridLayout(1, 3, 10, 10));

                panelDatos.add(panelbox1);
                panelDatos.add(panelbox2);
                panelDatos.add(panelboxwin);
                panelbox1.setBackground(Color.CYAN);

                panelbox2.setBackground(Color.orange);
                panelboxwin.setBackground(Color.GREEN);
                Connection con = DriverManager.getConnection(URL);
                Statement st = con.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                rs = st.executeQuery("SELECT boxeador1_nombre, boxeador2_nombre"
                        + " resultado FROM pelea");

                panelbox1.removeAll();
                panelbox2.removeAll();
                panelboxwin.removeAll();
                while (rs.next())
                {
                    panelbox1.add(new JLabel("\n" + rs.getString("boxeador1_nombre") + "\n"));
                    panelbox2.add(new JLabel("\n" + rs.getString("boxeador2_nombre") + "\n"));
                    panelboxwin.add(new JLabel("\n" + rs.getString("resultado") + "\n"));

                }
                panelSur.add(new JLabel("Boxeador 1", new ImageIcon(
                        BoxeadorView.class.getResource("/images/peleas.png")),
                        SwingConstants.CENTER));
                panelSur.add(new JLabel("Boxeador 2", new ImageIcon(
                        BoxeadorView.class.getResource("/images/peleas.png")),
                        SwingConstants.CENTER));
                panelSur.add(new JLabel("Ganador", new ImageIcon(
                        BoxeadorView.class.getResource("/images/win.png")),
                        SwingConstants.CENTER));

                panelDatos.repaint();
                panelDatos.revalidate();
            } catch (SQLException ex)
            {
                System.out.println("Erro SQL: " + ex.getMessage());
            }
        });

        mnuPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seguinte.setEnabled(true);
                anterior.setEnabled(false);
                panelDatos.remove(panelbox1);
                panelDatos.remove(panelbox2);
                panelDatos.remove(panelboxwin);
                panelSur.removeAll();
                panelDatosBox.removeAll();
                panelFoto.removeAll();
                panelSur.setBackground(Color.white);
                panelDatos.setBackground(Color.white);
                panelFoto.setBackground(Color.white);
                setPanelNorte();
                setPanelSur();
                panelDatos.repaint();
                panelDatos.revalidate();
                panelSur.repaint();
                panelSur.revalidate();
                try
                {
                    Connection con = DriverManager.getConnection(URL);
                    PreparedStatement ps = con.prepareStatement("SELECT nombre, nacionalidad,"
                            + " peleas, ganadas, perdidas, foto FROM boxeador",
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    rs = ps.executeQuery();
                    rs.beforeFirst();
                    panelDatosBox.repaint();
                    panelDatosBox.revalidate();
                } catch (SQLException ex)
                {
                    System.out.println("Error SQL: " + ex.getMessage());
                }
            }
        });
        JButton sair = new JButton(new ImageIcon(
                BoxeadorView.class.getResource("/images/salir.png")));
        sair.setBorderPainted(false);
        sair.setContentAreaFilled(false);
        sair.setFocusable(false);
        sair.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (JOptionPane.showConfirmDialog(null, new JLabel("Quieres salir?"),
                        "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, new ImageIcon(
                                BoxeadorView.class.getResource("/images/salida.png")))
                        == JOptionPane.YES_NO_OPTION)
                {
                    JOptionPane.showMessageDialog(null, new JLabel("Hasta la vista!"),
                            "Día triste", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(
                                    BoxeadorView.class.getResource("/images/salida.png")));
                    System.exit(0);
                }
            }
        }));

        menu.setIcon(icono);
        menu.add(guardar);
        menu.add(mnuPeleas);
        menu.add(mnuPerfil);
        mnuBar.add(menu);
        mnuBar.add(Box.createGlue());
        mnuBar.add(sair);
        setJMenuBar(mnuBar);
    }

}
