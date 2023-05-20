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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author A22DiegoSG
 */
public class BoxeadorView extends JFrame {

    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:D:\\_Clase\\A22_Programacion\\proxectoProg\\src\\main\\resources\\bd\\boxeador";

    private ResultSet rs;

    private JPanel panelcentral = new JPanel(new BorderLayout());
    private JPanel panelSur = new JPanel(new FlowLayout());
    private JPanel panelDatos = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
    private JPanel panelDatosBox = new JPanel(new GridLayout(5, 2));
    private JPanel panelFoto = new JPanel(new BorderLayout());

    private JButton seguinte;
    private JButton anterior;

    private JLabel nombreImg, nombre, nacImg, nac, peleasImg, peleas, ganadasImg,
            ganadas, perdidasImg, perdidas, fotoLbl;

    public BoxeadorView(String title) throws HeadlessException {
        super(title);
        setConnection();
        crearGUI();
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
        panelDatosBox.add(nombreImg = new JLabel("Nombre: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/nombre.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(nombre);
        panelDatosBox.add(nacImg = new JLabel("Nacion: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/mundo.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(nac);
        panelDatosBox.add(peleasImg = new JLabel("Peleas: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/peleas.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(peleas);
        panelDatosBox.add(ganadasImg = new JLabel("Ganadas: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/win.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(ganadas);
        panelDatosBox.add(perdidasImg = new JLabel("Perdidas: ", new ImageIcon(
                BoxeadorView.class.getResource("/images/lose.png")),
                SwingConstants.CENTER));
        panelDatosBox.add(perdidas);

        panelFoto.add(fotoLbl);
        panelDatos.add(panelFoto, BorderLayout.WEST);
        panelDatos.add(panelDatosBox, BorderLayout.EAST);
        panelcentral.add(panelDatos);
        add(panelcentral, BorderLayout.CENTER);
    }

    public void setPanelSur() {
        seguinte = new JButton(new ImageIcon(
                BoxeadorView.class.getResource("/images/seguinte.png")));
        seguinte.setBorderPainted(false);
        seguinte.setContentAreaFilled(false);

        anterior = new JButton(new ImageIcon(
                BoxeadorView.class.getResource("/images/anterior.png")));
        anterior.setBorderPainted(false);
        anterior.setContentAreaFilled(false);
        seguinte.addActionListener((ActionEvent e) ->
        {
            try
            {
                if (rs.next())
                {
                    System.out.println("seguinte");
                    setValores();
                } else
                {
                    rs.first();
                    setValores();
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
                } else
                {
                    rs.last();
                    setValores();
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
                nombre = new JLabel(rs.getString("nombre"));
                nac = new JLabel(rs.getString("nacionalidad"));
                peleas = new JLabel(rs.getString("peleas"));
                ganadas = new JLabel(rs.getString("ganadas"));
                perdidas = new JLabel(rs.getString("perdidas"));
                byte[] imagen = rs.getBytes("FOTO");
                if (imagen != null)
                {
                    try
                    {
                        Image image = ImageIO.read(new ByteArrayInputStream(imagen));
                        ImageIcon imageIcon = new ImageIcon(image);
                        fotoLbl = new JLabel(imageIcon);
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
        JMenuItem guardar = new JMenuItem(new ImageIcon(
                BoxeadorView.class.getResource("/images/guardar.png")));
        ImageIcon icono = new ImageIcon(BoxeadorView.class.getResource(
                "/images/menu.png"));
        guardar.addActionListener((ActionEvent e) ->
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
        JMenuItem mnuPeleas = new JMenuItem(new ImageIcon(
                BoxeadorView.class.getResource("/images/peleas.png")));
        JMenuItem mnuPerfil = new JMenuItem(new ImageIcon(
                BoxeadorView.class.getResource("/images/nombre.png")));

        mnuPeleas.addActionListener((ActionEvent e) ->
        {
            try
            {
                panelDatos.removeAll();
                panelDatos.repaint();
                panelDatos.revalidate();
                panelSur.removeAll();
                panelSur.repaint();
                panelSur.revalidate();
                Connection con = DriverManager.getConnection(URL);
                Statement st = con.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                rs = st.executeQuery("SELECT boxeador1_nombre, boxeador2_nombre"
                        + " resultado FROM pelea");

                while (rs.next())
                {
                    panelDatos.add(new JLabel("\n" + rs.getString("boxeador1_nombre") + "   |   "
                            + rs.getString("boxeador2_nombre") + "   |   "
                            + rs.getString("resultado") + "\n"));

                }
                panelSur.add(new JLabel("Boxeador 1    | ", new ImageIcon(
                        BoxeadorView.class.getResource("/images/peleas.png")),
                        SwingConstants.CENTER));
                panelSur.add(new JLabel("Boxeador 2    | ", new ImageIcon(
                        BoxeadorView.class.getResource("/images/peleas.png")),
                        SwingConstants.CENTER));
                panelSur.add(new JLabel("Ganador", new ImageIcon(
                        BoxeadorView.class.getResource("/images/win.png")),
                        SwingConstants.CENTER));

                panelcentral.add(panelDatos);
                panelDatos.repaint();
                panelDatos.revalidate();
            } catch (SQLException ex)
            {
                System.out.println("Erro SQL: " + ex.getMessage());
            }
        });

        mnuPerfil.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    panelDatos.removeAll();
                    panelDatos.repaint();
                    panelDatos.revalidate();
                    panelSur.removeAll();
                    panelSur.repaint();
                    rs.first();
                    setValores();

                    setPanelNorte();
                    setPanelSur();
                } catch (SQLException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }));

        menu.setIcon(icono);
        menu.add(guardar);
        menu.add(mnuPeleas);
        menu.add(mnuPerfil);
        mnuBar.add(menu);
        setJMenuBar(mnuBar);
    }

}
