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
    private int user;

    public Skills() {
    }

    public Skills(int idS, String libelle) {
        this.idS = idS;
        this.libelle = libelle;
    }

    public Skills(String libelle, int user) {
        this.libelle = libelle;
        this.user = user;
    }
     public Skills( String libelle) {
       
        this.libelle = libelle;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getUser() {
        return user;
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
        return "Skills{" + "idS=" + idS + ", libelle=" + libelle + ", user=" + user + '}';
    }

   
     
    
}
