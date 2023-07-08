/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.interfaces;

import java.util.List;

/**
 *
 * @author ASUS
 */
public interface ICRUD<T>{
    public void addEntity(T t);
    public List<T> displayEntities();
    public void supprimer(int id);
    public void modifier(T t);
    
}