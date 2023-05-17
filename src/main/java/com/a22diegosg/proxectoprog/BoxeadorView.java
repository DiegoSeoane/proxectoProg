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
    private JPanel panelFoto = new JPanel();

    JPanel panelDatos = new JPanel(new GridLayout(1, 3, 10, 5));

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

//    public void setValores() {
//        try {
//            if (rs != null && !rs.isClosed()
//                    && !rs.isBeforeFirst() && !rs.isAfterLast()) {
//                add(panelDatos, BorderLayout.EAST);
//                panelDatos.add(new JLabel(new ImageIcon(
//                        BoxeadorView.class.getResource("/images/nombre.png"))));
////            panelcentral.add(new JLabel("nombre"));
//                panelDatos.add(new JLabel(rs.getString("nombre")));
//                panelDatos.add(new JLabel(new ImageIcon(
//                        BoxeadorView.class.getResource("/images/mundo.png"))));
////            panelcentral.add(new JLabel("nacionalidad"));
//                panelDatos.add(new JLabel(rs.getString("nacionalidad")));
//                panelDatos.add(new JLabel(new ImageIcon(
//                        BoxeadorView.class.getResource("/images/peleas.png"))));
////            panelcentral.add(new JLabel("peleas"));
//                panelDatos.add(new JLabel(rs.getString("peleas")));
//                panelDatos.add(new JLabel(new ImageIcon(
//                        BoxeadorView.class.getResource("/images/win.png"))));
////            panelcentral.add(new JLabel("ganadas"));
//                panelDatos.add(new JLabel(rs.getString("ganadas")));
//                panelDatos.add(new JLabel(new ImageIcon(
//                        BoxeadorView.class.getResource("/images/lose.png"))));
////            panelcentral.add(new JLabel("perdidas"));
//                panelDatos.add(new JLabel(rs.getString("perdidas")));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BoxeadorView.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
    public void setValores() {

        JPanel panelImg = new JPanel(new GridLayout(5, 1));
        JPanel panelDatosBox = new JPanel(new GridLayout(5, 1));

        try {
            if (rs != null && !rs.isClosed()
                    && !rs.isBeforeFirst() && !rs.isAfterLast()) {
                add(panelDatosBox, BorderLayout.EAST);
                panelImg.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/nombre.png"))));
//            panelcentral.add(new JLabel("nombre"));
                panelDatosBox.add(new JLabel(rs.getString("nombre")));
                panelImg.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/mundo.png"))));
//            panelcentral.add(new JLabel("nacionalidad"));
                panelDatosBox.add(new JLabel(rs.getString("nacionalidad")));
                panelImg.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/peleas.png"))));
//            panelcentral.add(new JLabel("peleas"));
                panelDatosBox.add(new JLabel(rs.getString("peleas")));
                panelImg.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/win.png"))));
//            panelcentral.add(new JLabel("ganadas"));
                panelDatosBox.add(new JLabel(rs.getString("ganadas")));
                panelImg.add(new JLabel(new ImageIcon(
                        BoxeadorView.class.getResource("/images/lose.png"))));
//            panelcentral.add(new JLabel("perdidas"));
                panelDatosBox.add(new JLabel(rs.getString("perdidas")));

//                anhadirImaxe();
                Blob blob = rs.getBlob("foto");
                InputStream inputStream = blob.getBinaryStream();
                Image image = ImageIO.read(inputStream);
                ImageIcon imageIcon = new ImageIcon(image);
                panelFoto.add(new JLabel(imageIcon));

            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(BoxeadorView.class.getName()).log(Level.SEVERE, null, ex);
        }

        panelDatos.add(panelFoto);
        panelDatos.add(panelImg);
        panelDatos.add(panelDatosBox);
        add(panelDatos);
    }

//    public void anhadirImaxe() {
//        // Obtén el flujo de bytes de la base de datos
//        InputStream inputStream;
//        try {
//            inputStream = rs.getBinaryStream(6);
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            byte[] buffer = new byte[4096];
//            int bytesRead;
//
//            try {
//
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//
//                byte[] imageBytes = outputStream.toByteArray();
//                ImageIcon imageIcon = new ImageIcon(imageBytes);
//
//                JLabel imageLabel = new JLabel(imageIcon);
//
//                panelFoto.add(imageLabel);
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(BoxeadorView.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
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
                    + "ganadas, perdidas FROM boxeador");
            if (rs.next()) {
                setValores();
            }
        } catch (SQLException ex) {
            System.out.println("Erro na base de datos: " + ex.getMessage());
        }
    }

}
