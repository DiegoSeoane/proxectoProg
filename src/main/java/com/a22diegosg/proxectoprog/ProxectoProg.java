/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.a22diegosg.proxectoprog;

import java.io.File;
import java.sql.Connection;

/**
 *
 * @author A22DiegoSG
 */
public class ProxectoProg {

    public static void main(String[] args) {
        Connection con = ConectionManager.getConnection();
        BoxeadorDAO boxer = new BoxeadorDAO(con);
//        Boxeador big = new Boxeador("George Foreman", "USA", 81, 76, 5);
//        boxer.create(big);
//        boxer.actualizarFoto(big, new File("L:\\Programacion\\3ºTrimestre\\proxectoProg\\img\\foreman.jpg"));
//        
//          Boxeador ali = new Boxeador("Muhammad Ali", "USA", 61, 56, 5);
//          boxer.create(ali);
//          boxer.actualizarFoto(ali, new File("D:\\_Clase\\A22_Programacion\\proxectoProg\\img\\ali.jpg"));


        BoxeadorView bv = new BoxeadorView("Boxeador");
    }
}
