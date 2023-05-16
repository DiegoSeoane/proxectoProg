/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a22diegosg.proxectoprog;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 *
 * @author A22DiegoSG
 */
public class Boxeador {    
    private String nome;
    private String nacionalidad;
    private int peleas;
    private int ganadas;
    private int perdidas;
    private byte[] foto;

    public Boxeador() {
    }

    public Boxeador(String nome, int peleas, int ganadas, int perdidas) {
        this.nome = nome;
        this.peleas = peleas;
        this.ganadas = ganadas;
        this.perdidas = perdidas;
    }

    public Boxeador(String nome, int peleas, int ganadas, int perdidas,
            byte[] foto) {
        this.nome = nome;
        this.peleas = peleas;
        this.ganadas = ganadas;
        this.perdidas = perdidas;
        this.foto = foto;
    }

    public Boxeador(String nome, String nacionalidad, int peleas, int ganadas,
            int perdidas, byte[] foto) {
        this.nome = nome;
        this.nacionalidad = nacionalidad;
        this.peleas = peleas;
        this.ganadas = ganadas;
        this.perdidas = perdidas;
        this.foto = foto;
    }
    public Boxeador(String nome, String nacionalidad, int peleas, int ganadas,
            int perdidas) {
        this.nome = nome;
        this.nacionalidad = nacionalidad;
        this.peleas = peleas;
        this.ganadas = ganadas;
        this.perdidas = perdidas;        
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getPeleas() {
        return peleas;
    }

    public void setPeleas(int peleas) {
        this.peleas = peleas;
    }

    public int getGanadas() {
        return ganadas;
    }

    public void setGanadas(int ganadas) {
        this.ganadas = ganadas;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
    public void setFoto(File f) {
        try (BufferedInputStream bi
                = new BufferedInputStream(new FileInputStream(f)); ByteArrayOutputStream outputStream
                = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesLidos;
            while ((bytesLidos = bi.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesLidos);
            }

            foto = outputStream.toByteArray();
        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo non atopado: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro de E/S: " + ex.getMessage());
        }
    }
    
    public void setFoto(String file) {
        try {
            Path ruta = Paths.get(file);
            foto = Files.readAllBytes(ruta);
        } catch (IOException ex) {
            System.out.println("Erro de E/S: " + ex.getMessage());
        }
    }

    public Image getImage() {
        if (foto != null) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(foto)) {
                return ImageIO.read(bis);
            } catch (IOException e) {
            }
        }
        return null;
    }
    
}
