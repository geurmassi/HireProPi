/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.entities;

/**
 *
 * @author LENOVO
 */
public class Poste {

    private int idP;
    private String poste;

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public Poste() {
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

    public void setPoste(String poste) {
        this.poste = poste;
    }
    
    
}
