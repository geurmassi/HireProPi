/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.interfaces;

import static java.util.Collections.list;
import java.util.List;

/**
 *
 * @author samsung
 */
public interface ICRUDAmina<T> {
    public void addEntity(T t);
    public void update(T t);
    public void delete(int id);
    
    public List<T> displayEntities();
    
}

