/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.services;

/**
 *
 * @author LENOVO
 */
public class tNumCanParPos {

    private String poste;
    private int candidatureCount;

    public tNumCanParPos() {
    }

    public tNumCanParPos(String poste, int candidatureCount) {
        this.poste = poste;
        this.candidatureCount = candidatureCount;
    }

    public String getPoste() {
        return poste;
    }

    public int getCandidatureCount() {
        return candidatureCount;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public void setCandidatureCount(int candidatureCount) {
        this.candidatureCount = candidatureCount;
    }

    @Override
    public String toString() {
        return "tPANCD{" + "poste=" + poste + ", candidatureCount=" + candidatureCount + '}';
    }
    
}
