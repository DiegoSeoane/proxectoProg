/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a22diegosg.proxectoprog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author A22DiegoSG
 */
public class ConectionManager {
    private static final String DRIVER="org.h2.Driver";
//    private static final String URL="jdbc:h2:L:\\Programacion\\3ºTrimestre\\proxectoProg\\boxeador";
    private static final String URL = "jdbc:h2:L:\\Programacion\\3ºTrimestre\\proxectoProg\\src\\main\\resources\\bd\\boxeador";
    
    private static Connection con;
    
    public static Connection getConnection(){
        try {
            if (con == null || con.isClosed()) {
                Class.forName(DRIVER);
                con = DriverManager.getConnection(URL);   
                System.out.println("Conectado.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Non se pudo establecer conexion: " + e.getMessage());
        }
        return con;
    }
}
