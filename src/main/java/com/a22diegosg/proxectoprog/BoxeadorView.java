/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a22diegosg.proxectoprog;

import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author A22DiegoSG
 */
public class BoxeadorView extends JFrame {

    private static final String DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:\\bd\\boxeador";

    private ResultSet rs;

    JPanel panelcentral = new JPanel(new GridLayout(5, 2, 10, 5));

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
        add(new JPanel(), BorderLayout.EAST);
        add(new JPanel(), BorderLayout.WEST);
    }

    public void setValores() {
//        try {
            add(panelcentral, BorderLayout.CENTER);
            panelcentral.add(new JLabel(new ImageIcon(
                    BoxeadorView.class.getResource("/images/nombre.png"))));
            panelcentral.add(new JLabel("nombre"));
            //panelcentral.add(new JLabel(rs.getString("nombre")));
            panelcentral.add(new JLabel(new ImageIcon(
                    BoxeadorView.class.getResource("/images/mundo.png"))));
            panelcentral.add(new JLabel("nacionalidad"));
            //panelcentral.add(new JLabel(rs.getString("nacionalidad")));
            panelcentral.add(new JLabel(new ImageIcon(
                    BoxeadorView.class.getResource("/images/peleas.png"))));
            panelcentral.add(new JLabel("peleas"));
            //panelcentral.add(new JLabel(rs.getString("peleas")));
            panelcentral.add(new JLabel(new ImageIcon(
                    BoxeadorView.class.getResource("/images/win.png"))));
            panelcentral.add(new JLabel("ganadas"));
            //panelcentral.add(new JLabel(rs.getString("ganadas")));
            panelcentral.add(new JLabel(new ImageIcon(
                    BoxeadorView.class.getResource("/images/lose.png"))));
            panelcentral.add(new JLabel("perdidas"));
            //panelcentral.add(new JLabel(rs.getString("perdidas")));
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
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
                    + "ganadas, perdidas FROM boxeador");
            if (rs.next()) {
                setValores();
            }
        } catch (SQLException ex) {
            System.out.println("Erro na base de datos: " + ex.getMessage());
        }
    }

}
