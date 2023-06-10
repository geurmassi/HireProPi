/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;

import java.sql.Date;

/**
 *
 * @author hadil ibenhajfraj
 */
public class Offre {
     private int idOffre;
    private String lieuT;
    private String descriptif;
    private Date dateDebutOffre;
    private Date dateFinOffre;
    private TypeTravail tyepTravail;
    private TypeLieuTravail typeLieuTravail;
    private int idUser;
    private int poste;
    private int question;

    public Offre() {
    }

    public Offre( String lieuT, String descriptif, Date dateDebutOffre, Date dateFinOffre, TypeTravail tyepTravail, TypeLieuTravail typeLieuTravail, int idUser, int poste, int question) {
      
        this.lieuT = lieuT;
        this.descriptif = descriptif;
        this.dateDebutOffre = dateDebutOffre;
        this.dateFinOffre = dateFinOffre;
        this.tyepTravail = tyepTravail;
        this.typeLieuTravail = typeLieuTravail;
        this.idUser = idUser;
        this.poste = poste;
        this.question = question;
    }

    public String getLieuT() {
        return lieuT;
    }

    public Date getDateDebutOffre() {
        return dateDebutOffre;
    }

    public Date getDateFinOffre() {
        return dateFinOffre;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getPoste() {
        return poste;
    }

    public int getQuestion() {
        return question;
    }

    public TypeTravail getTyepTravail() {
        return tyepTravail;
    }

    public TypeLieuTravail getTypeLieuTravail() {
        return typeLieuTravail;
    }

    public void setDateDebutOffre(Date dateDebutOffre) {
        this.dateDebutOffre = dateDebutOffre;
    }

    public void setDateFinOffre(Date dateFinOffre) {
        this.dateFinOffre = dateFinOffre;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setLieuT(String lieuT) {
        this.lieuT = lieuT;
    }

    public void setPoste(int poste) {
        this.poste = poste;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public void setTyepTravail(TypeTravail tyepTravail) {
        this.tyepTravail = tyepTravail;
    }

    public void setTypeLieuTravail(TypeLieuTravail typeLieuTravail) {
        this.typeLieuTravail = typeLieuTravail;
    }

    @Override
    public String toString() {
        return "Offre{" + "idOffre=" + idOffre + ", lieuT=" + lieuT + ", descriptif=" + descriptif + ", dateDebutOffre=" + dateDebutOffre + ", dateFinOffre=" + dateFinOffre + ", tyepTravail=" + tyepTravail + ", typeLieuTravail=" + typeLieuTravail + ", idUser=" + idUser + ", poste=" + poste + ", question=" + question + '}';
    }
    
    
}
