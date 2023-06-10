/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;

/**
 *
 * @author hadil ibenhajfraj
 */
public class Skills {
     private int idS;
    private String libelle;

    public Skills() {
    }

    public Skills(int idS, String libelle) {
        this.idS = idS;
        this.libelle = libelle;
    }
     public Skills( String libelle) {
       
        this.libelle = libelle;
    }

    public int getIdS() {
        return idS;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdS(int idS) {
        this.idS = idS;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "skills{" + "idS=" + idS + ", libelle=" + libelle + '}';
    }
     
    
}
