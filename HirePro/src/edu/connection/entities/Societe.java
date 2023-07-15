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
public class Societe {
     private int idS;
    private String nom;
    private String adresse;
    private String description;
    private String logo;
    private String tel;
    private String email;

    public Societe() {
    }

    public Societe(int idS, String nom, String adresse, String description, String logo, String tel, String email) {
        this.idS = idS;
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.logo = logo;
        this.tel = tel;
        this.email = email;
    }
     public Societe( String nom, String adresse, String description, String logo, String tel, String email) {
        
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.logo = logo;
        this.tel = tel;
        this.email = email;
    }

    public int getIdS() {
        return idS;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getLogo() {
        return logo;
    }

    public String getNom() {
        return nom;
    }

    public String getTel() {
        return tel;
    }

    public void setIdS(int idS) {
        this.idS = idS;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Societe{" + "idS=" + idS + ", nom=" + nom + ", adresse=" + adresse + ", description=" + description + ", logo=" + logo + ", tel=" + tel + ", email=" + email + '}';
    }
     
    
}
