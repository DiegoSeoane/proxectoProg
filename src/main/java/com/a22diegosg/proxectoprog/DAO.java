/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.a22diegosg.proxectoprog;

import java.util.List;

/**
 *
 * @author A22DiegoSG
 * @param <T>
 */
public interface DAO<T>{
    public T get(String nome);
    public List<T> getAll();
    public void create(T t);
    public void update(T t);
    public void delete(String nombre);
}
