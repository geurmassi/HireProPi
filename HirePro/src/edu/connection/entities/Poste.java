/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;

/**
 *
 * @author ASUS
 */

 
public class Poste {
    private int idP;
    private String poste;

    public Poste() {
    }

    public Poste(int idP, String poste) {
        this.idP=idP;
        this.poste = poste;
    }
    public Poste(String poste) {
        this.poste = poste;
    }


    public int getIdP() {
        return idP;
    }

    public String getPoste() {
        return poste;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    @Override
    public String toString() {
        return "Poste{" + "idP=" + idP + ", poste=" + poste + '}';
    }
    
}
