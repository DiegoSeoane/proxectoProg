/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a22diegosg.proxectoprog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author A22DiegoSG
 */
public class BoxeadorDAO implements DAO<Boxeador> {

    private Connection con;

    public BoxeadorDAO(Connection con) {
        this.con = con;
    }

    @Override
    public Boxeador get(int id) {

        try
        {
            if (con != null || !con.isClosed())
            {
                try ( Statement st = con.createStatement())
                {
                    ResultSet rs = st.executeQuery("SELECT * FROM BOXEADORES WHERE"
                            + " ID=" + id);
                    if (rs.next())
                    {
                        Boxeador boxeador = new Boxeador(rs.getString("NOMBRE"),
                                rs.getString("NACIONALIDAD"), rs.getInt("PELEAS"),
                                rs.getInt("GANADAS"), rs.getInt("PERDIDAS"),
                                rs.getBytes("FOTO"));
                        return boxeador;
                    }
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Erro ao mostrar: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Boxeador> getAll() {
        List<Boxeador> boxers = new ArrayList<>();
        try
        {
            if (con != null || !con.isClosed())
            {
                try ( Statement st = con.createStatement())
                {
                    ResultSet rs = st.executeQuery("SELECT * FROM BOXEADORES");
                    while (rs.next())
                    {
                        Boxeador boxeador = new Boxeador(rs.getString("NOMBRE"),
                                rs.getString("NACIONALIDAD"), rs.getInt("PELEAS"),
                                rs.getInt("GANADAS"), rs.getInt("PERDIDAS"),
                                rs.getBytes("FOTO"));
                        boxers.add(boxeador);
                    }
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Erro na mostra da lista: " + e.getMessage());
        }
        return boxers;
    }

    @Override
    public void create(Boxeador boxer) {
        try
        {
            if (con != null || !con.isClosed())
            {
                try ( PreparedStatement ps = con.prepareStatement("INSERT INTO "
                        + "BOXEADOR (NOMBRE, NACIONALIDAD, PELEAS, GANADAS,"
                        + " PERDIDAS, FOTO) VALUES (?, ?, ?, ?, ?, ?)"))
                {
                    ps.setString(1, boxer.getNome());
                    ps.setString(2, boxer.getNacionalidad());
                    ps.setInt(3, boxer.getPeleas());
                    ps.setInt(4, boxer.getGanadas());
                    ps.setInt(5, boxer.getPerdidas());
                    ps.setBytes(6, boxer.getFoto());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Erro ao crear: " + e.getMessage());
        }
    }

    @Override
    public void update(Boxeador boxer) {
        try
        {
            if (con != null || !con.isClosed())
            {
                try ( PreparedStatement ps = con.prepareStatement("UPDATE BOXEADOR"
                        + "SET NOMBRE=?, NACIONALIDAD=?, PELEAS=?, "
                        + "GANADAS=?, PERDIDAS=?, FOTO=?"))
                {
                    ps.setString(1, boxer.getNome());
                    ps.setString(2, boxer.getNacionalidad());
                    ps.setInt(3, boxer.getPeleas());
                    ps.setInt(4, boxer.getGanadas());
                    ps.setInt(5, boxer.getPerdidas());
                    ps.setBytes(6, boxer.getFoto());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Erro ao actualizar: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try
        {
            if (con != null || !con.isClosed())
            {
                try ( Statement st = con.createStatement())
                {
                    st.executeQuery("DELETE FROM BOXEADOR WHERE ID = " + id);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Erro ao borrar: " + e.getMessage());
        }
    }

    public void actualizarFoto(Boxeador boxer, File f) {
        try
        {
            if (con != null || !con.isClosed())
            {
                try ( PreparedStatement ps = con.prepareStatement("UPDATE "
                        + "BOXEADOR SET FOTO=? WHERE NOMBRE=?");  BufferedInputStream bis = new BufferedInputStream(
                                new FileInputStream(f)))
                {
                    ps.setBinaryStream(1, bis);
                    ps.setString(2, boxer.getNome());
                    ps.executeUpdate();
                }
            }
        } catch (IOException | SQLException e)
        {
            System.out.println("Erro ao actualizar a imaxe: " + e.getMessage());
        }
    }
}
