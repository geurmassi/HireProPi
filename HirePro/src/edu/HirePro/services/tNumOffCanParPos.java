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
public class tNumOffCanParPos {
    private String poste;
    private int candidatureCount;
    private int offreCount;

    public tNumOffCanParPos() {
    }
    
    

    public tNumOffCanParPos(String poste, int candidatureCount, int offreCount) {
        this.poste = poste;
        this.candidatureCount = candidatureCount;
        this.offreCount = offreCount;
    }

    public String getPoste() {
        return poste;
    }

    public int getCandidatureCount() {
        return candidatureCount;
    }

    public int getOffreCount() {
        return offreCount;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public void setCandidatureCount(int candidatureCount) {
        this.candidatureCount = candidatureCount;
    }

    public void setOffreCount(int offreCount) {
        this.offreCount = offreCount;
    }

    @Override
    public String toString() {
        return "tNumOffCanParPos{" + "poste=" + poste + ", candidatureCount=" + candidatureCount + ", offreCount=" + offreCount + '}';
    }
    
    
    
    
    
}
