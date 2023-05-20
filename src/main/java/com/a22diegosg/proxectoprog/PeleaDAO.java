/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a22diegosg.proxectoprog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class PeleaDAO implements DAO<Pelea> {

    private Connection con;

    public PeleaDAO(Connection con) {
        this.con = con;
    }

    @Override
    public Pelea get(int id) {
        try
        {
            if (con != null || !con.isClosed())
            {
                try ( Statement st = con.createStatement())
                {
                    ResultSet rs = st.executeQuery("SELECT * FROM PELEA WHERE"
                            + " ID=" + id);
                    if (rs.next())
                    {
                        Pelea pelea = new Pelea(rs.getString("boxeador1_nombre"),
                                rs.getString("boxeador2_nombre"),
                                rs.getString("resultado"));
                        return pelea;
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
    public List<Pelea> getAll() {
        List<Pelea> peleas = new ArrayList<>();
        try
        {
            if (con != null || !con.isClosed())
            {
                try ( Statement st = con.createStatement())
                {
                    ResultSet rs = st.executeQuery("SELECT * FROM PELEA WHERE");
                    if (rs.next())
                    {
                        Pelea pelea = new Pelea(rs.getString("boxeador1_nombre"),
                                rs.getString("boxeador2_nombre"),
                                rs.getString("resultado"));
                        peleas.add(pelea);
                    }
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Erro na mostra da lista: " + e.getMessage());
        }
        return peleas;
    }

    @Override
    public void create(Pelea pelea) {
        try
        {
            if (con != null || !con.isClosed())
            {
                try ( PreparedStatement ps = con.prepareStatement("INSERT INTO "
                        + "PELEA (boxeador1_nombre, boxeador2_nombre, resultado)"
                        + " VALUES (?, ?, ?)"))
                {
                    ps.setString(1, pelea.getBoxeador1());
                    ps.setString(2, pelea.getBoxeador2());
                    ps.setString(3, pelea.getResultado());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Erro ao crear: " + e.getMessage());
        }
    }

    @Override
    public void update(Pelea t) {

    }

    @Override
    public void delete(int id) {
        try
        {
            if (con != null || !con.isClosed())
            {
                try ( Statement st = con.createStatement())
                {
                    st.executeQuery("DELETE FROM PELEA WHERE ID = " + id);
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Erro ao borrar: " + e.getMessage());
        }
    }

}
