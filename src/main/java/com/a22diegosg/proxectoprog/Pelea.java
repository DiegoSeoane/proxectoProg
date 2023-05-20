/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a22diegosg.proxectoprog;

/**
 *
 * @author diego
 */
public class Pelea {    
    private String boxeador1;
    private String boxeador2;
    private String resultado;

    public Pelea( String boxeador1, String boxeador2, String resultado) {        
        this.boxeador1 = boxeador1;
        this.boxeador2 = boxeador2;
        this.resultado = resultado;
    }
    
    public String getBoxeador1() {
        return boxeador1;
    }

    public void setBoxeador1(String boxeador1) {
        this.boxeador1 = boxeador1;
    }

    public String getBoxeador2() {
        return boxeador2;
    }

    public void setBoxeador2(String boxeador2) {
        this.boxeador2 = boxeador2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    
    
}
