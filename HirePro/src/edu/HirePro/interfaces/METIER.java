/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.interfaces;

import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface METIER<T> {
    public  String rechercheNameUserbyId (int id);
    public int conterCondidatureByUser(int idu);    
    public int conterCondidatureByoffre(int idf);
    public int conterCondidatureByPoste(int idp); 
    public T rechercheUserByID (int id);
    public void evaluerCondidat(T t);
    public void trieeCondidatByCv();
    public void planifierEntretient(T t);
    public void trierCandidatByEvaluation();
    
    
}
