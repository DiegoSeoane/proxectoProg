/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a22diegosg.proxectoprog;

import java.awt.*;
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

    JPanel panelDatos = new JPanel(new FlowLayout(FlowLayout.CENTER, 1, 1));

    public BoxeadorView(String title) throws HeadlessException {
        super(title);
        crearGUI();
        setConnection();        
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void crearGUI() {
        add(new JPanel(), BorderLayout.PAGE_START);
        add(new JPanel(), BorderLayout.PAGE_END);
        setValores();
    }

    public void setValores() {

        
        JPanel panelDatosBox = new JPanel(new GridLayout(5, 2));
        JPanel panelFoto = new JPanel(new BorderLayout());

        try {
            if (rs != null && !rs.isClosed()
                    && !rs.isBeforeFirst() && !rs.isAfterLast()) {
                add(panelDatosBox, BorderLayout.EAST);
                panelDatosBox.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/nombre.png"))));
//            panelcentral.add(new JLabel("nombre"));
                panelDatosBox.add(new JLabel(rs.getString("nombre")));
                panelDatosBox.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/mundo.png"))));
//            panelcentral.add(new JLabel("nacionalidad"));
                panelDatosBox.add(new JLabel(rs.getString("nacionalidad")));
                panelDatosBox.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/peleas.png"))));
//            panelcentral.add(new JLabel("peleas"));
                panelDatosBox.add(new JLabel(rs.getString("peleas")));
                panelDatosBox.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/win.png"))));
//            panelcentral.add(new JLabel("ganadas"));
                panelDatosBox.add(new JLabel(rs.getString("ganadas")));
                panelDatosBox.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/lose.png"))));
//            panelcentral.add(new JLabel("perdidas"));
                panelDatosBox.add(new JLabel(rs.getString("perdidas")));

//                anhadirImaxe();
                byte[] imagen = rs.getBytes("FOTO");
                if (imagen != null) {
                    try {
                        Image image = ImageIO.read(new ByteArrayInputStream(imagen));
                        ImageIcon imageIcon = new ImageIcon(image);
                        panelFoto.add(new JLabel(imageIcon));
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(BoxeadorView.class.getName()).log(Level.SEVERE, null, ex);
        }

        panelDatos.add(panelFoto, BorderLayout.WEST);        
        panelDatos.add(panelDatosBox, BorderLayout.EAST);
        add(panelDatos);
    }

    public void setConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            System.out.println("Erro Drivers: " + ex.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection(URL);
            Statement st = con.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            rs = st.executeQuery("SELECT nombre, nacionalidad, peleas, "
                    + "ganadas, perdidas, foto FROM boxeador");
            if (rs.next()) {
                setValores();
            }
        } catch (SQLException ex) {
            System.out.println("Erro na base de datos: " + ex.getMessage());
        }
    }

}
