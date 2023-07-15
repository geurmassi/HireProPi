/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;

import java.sql.Timestamp;

/**
 *
 * @author samsung
 */
public class Reclamation {
    private int idR;
    private String contenue;
    private String objet;
    private Timestamp time;
    private int idUser;
    private Etat etat;
    private int idUserReclame;
    
    public Reclamation() {
    }

    public Reclamation(String contenue, String objet, Timestamp time, int idUser, int idUserReclame) {
        this.contenue = contenue;
        this.objet = objet;
        this.time = time;
        this.idUser = idUser;
        this.idUserReclame =idUserReclame;
    }

    public Reclamation(String contenue, String objet, Timestamp time, int idUser, Etat etat, int idUserReclame) {
        this.contenue = contenue;
        this.objet = objet;
        this.time = time;
        this.idUser = idUser;
        this.etat = etat;
        this.idUserReclame =idUserReclame;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public int getIdUserReclame() {
        return idUserReclame;
    }

    public void setIdUserReclame(int idUserReclame) {
        this.idUserReclame = idUserReclame;
    }    
    public int getIdR() {
        return idR;
    }

    public String getContenue() {
        return contenue;
    }

    public String getObjet() {
        return objet;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public void setContenue(String contenue) {
        this.contenue = contenue;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "idR=" + idR + ", contenue=" + contenue + ", objet=" + objet + ", time=" + time + ", idUser=" + idUser + ", etat=" + etat + ", idUserReclame=" + idUserReclame + '}';
    }

 
    

    
    
    
  
    
}
