/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.entities;

import java.sql.Date;

/**
 *
 * @author LENOVO
 */
public class Offre {
    
  private int idOffre;
  private String lieuT ;
  private String descriptif ;
  private Date dateDebutOffre; 
  private Date dateFinOffre;
  private TypeTravail typeEmploi;
  private TypeLieuTravail TypeLieuTravail;
  private int idUser;
  private int idposte;
  private int question;  

    public Offre() {
    }

    public Offre(String lieuT, String descriptif, Date dateDebutOffre, Date dateFinOffre, TypeTravail typeEmploi, TypeLieuTravail TypeLieuTravail, int idUser, int idposte, int question) {
        this.lieuT = lieuT;
        this.descriptif = descriptif;
        this.dateDebutOffre = dateDebutOffre;
        this.dateFinOffre = dateFinOffre;
        this.typeEmploi = typeEmploi;
        this.TypeLieuTravail = TypeLieuTravail;
        this.idUser = idUser;
        this.idposte = idposte;
        this.question = question;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public String getLieuT() {
        return lieuT;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public Date getDateDebutOffre() {
        return dateDebutOffre;
    }

    public Date getDateFinOffre() {
        return dateFinOffre;
    }

    public TypeTravail getTypeEmploi() {
        return typeEmploi;
    }

    public TypeLieuTravail getTypeLieuTravail() {
        return TypeLieuTravail;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdposte() {
        return idposte;
    }

    public int getQuestion() {
        return question;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public void setLieuT(String lieuT) {
        this.lieuT = lieuT;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    public void setDateDebutOffre(Date dateDebutOffre) {
        this.dateDebutOffre = dateDebutOffre;
    }

    public void setDateFinOffre(Date dateFinOffre) {
        this.dateFinOffre = dateFinOffre;
    }

    public void setTypeEmploi(TypeTravail typeEmploi) {
        this.typeEmploi = typeEmploi;
    }

    public void setTypeLieuTravail(TypeLieuTravail TypeLieuTravail) {
        this.TypeLieuTravail = TypeLieuTravail;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdposte(int idposte) {
        this.idposte = idposte;
    }


    public void setQuestion(int question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Offre{" + "idOffre=" + idOffre + ", lieuT=" + lieuT + ", descriptif=" + descriptif + ", dateDebutOffre=" + dateDebutOffre + ", dateFinOffre=" + dateFinOffre + ", typeEmploi=" + typeEmploi + ", TypeLieuTravail=" + TypeLieuTravail + ", idUser=" + idUser + ", idposte=" + idposte + ", question=" + question + '}';
    }


    

 
  
}
