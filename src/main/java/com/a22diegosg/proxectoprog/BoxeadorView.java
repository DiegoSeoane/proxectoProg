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

    JPanel panelcentral = new JPanel(new BorderLayout());
    JPanel panelSur = new JPanel(new FlowLayout());
    JPanel panelDatos = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));
    JPanel panelDatosBox = new JPanel(new GridLayout(5, 2));
    JPanel panelFoto = new JPanel(new BorderLayout());

    JButton seguinte;
    JButton anterior;

    JLabel nombreImg, nombre, nacImg, nac, peleasImg, peleas, ganadasImg,
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
        //setValores();

        setMenu();

        //PANEL NORTE
        panelDatosBox.add(nombreImg);
        panelDatosBox.add(nombre);
        panelDatosBox.add(nacImg);
        panelDatosBox.add(nac);
        panelDatosBox.add(peleasImg);
        panelDatosBox.add(peleas);
        panelDatosBox.add(ganadasImg);
        panelDatosBox.add(ganadas);
        panelDatosBox.add(perdidasImg);
        panelDatosBox.add(perdidas);

        panelFoto.add(fotoLbl);
        
        //PANEL SUR
        seguinte = new JButton(new ImageIcon(
                BoxeadorView.class.getResource("/images/seguinte.png")));
        seguinte.setBorderPainted(false);
        seguinte.setContentAreaFilled(false);

        anterior = new JButton(new ImageIcon(
                BoxeadorView.class.getResource("/images/anterior.png")));
        anterior.setBorderPainted(false);
        anterior.setContentAreaFilled(false);
        
        

        panelDatos.add(panelFoto, BorderLayout.WEST);
        panelDatos.add(panelDatosBox, BorderLayout.EAST);
        

        seguinte.addActionListener((ActionEvent e) ->
        {
            try
            {
                if (rs.next())
                {
                    System.out.println("seguinte");
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
                }
            } catch (SQLException ex)
            {
                System.out.println("Erro no ActionListener: " + ex.getMessage());
            }
        });
        panelSur.add(anterior);
        panelSur.add(seguinte);

        panelcentral.add(panelDatos);
        
        add(panelSur, BorderLayout.SOUTH);
        add(panelcentral, BorderLayout.CENTER);

    }

    public void setConnection() {
        try
        {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex)
        {
            System.out.println("Erro Drivers: " + ex.getMessage());
        }
        try
        {
            Connection con = DriverManager.getConnection(URL);
            Statement st = con.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT nombre, nacionalidad, peleas, "
                    + "ganadas, perdidas, foto FROM boxeador");
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

                nombreImg = new JLabel("Nombre: ", new ImageIcon(
                        BoxeadorView.class.getResource("/images/nombre.png")),
                        SwingConstants.CENTER);
                nombre = new JLabel(rs.getString("nombre"));

                nacImg = new JLabel("Nacion: ", new ImageIcon(
                        BoxeadorView.class.getResource("/images/mundo.png")),
                        SwingConstants.CENTER);
                nac = new JLabel(rs.getString("nacionalidad"));

                peleasImg = new JLabel("Peleas: ", new ImageIcon(
                        BoxeadorView.class.getResource("/images/peleas.png")),
                        SwingConstants.CENTER);
                peleas = new JLabel(rs.getString("peleas"));

                ganadasImg = new JLabel("Ganadas: ", new ImageIcon(
                        BoxeadorView.class.getResource("/images/win.png")),
                        SwingConstants.CENTER);
                ganadas = new JLabel(rs.getString("ganadas"));

                perdidasImg = new JLabel("Perdidas: ", new ImageIcon(
                        BoxeadorView.class.getResource("/images/lose.png")),
                        SwingConstants.CENTER);
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
            Logger.getLogger(BoxeadorView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setMenu() {
        JMenuBar mnuBar = new JMenuBar();
        JMenu menu = new JMenu();
        JMenuItem guardar = new JMenuItem(new ImageIcon(
                BoxeadorView.class.getResource("/images/guardar.png")));
        ImageIcon icono = new ImageIcon(BoxeadorView.class.getResource(
                "/images/menu.png"));
        menu.setIcon(icono);
        menu.add(guardar);
        mnuBar.add(menu);
        setJMenuBar(mnuBar);
    }

    

}
