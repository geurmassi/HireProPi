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
public class Universite {
     private int idUniveriste;
    private String libelle;

    public Universite() {
    }

    public Universite( String libelle) {
      
        this.libelle = libelle;
    }
    
    
    public int getIdUniveriste() {
        return idUniveriste;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdUniveriste(int idUniveriste) {
        this.idUniveriste = idUniveriste;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Universite{" + "idUniveriste=" + idUniveriste + ", libelle=" + libelle + '}';
    }

    public void libelleProperty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
