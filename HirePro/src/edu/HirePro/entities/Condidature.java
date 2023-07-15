/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 *
 * @author LENOVO
 */
public class Condidature {

    private int idC;
    private String cv;
    private String lettreMotivation;
    private Timestamp dateCandidature;
    private User user;
    private Offre offre;
    private String portfolio;
    private int idUtilisateur;
    private int idOffre;
    private Etat etat;
    private int matchCount;

    public Condidature() {
        this.matchCount = 0;
    }

    public Condidature(String cv, String lettreMotivation, Timestamp dateCandidature, int idUtilisateur, int idOffre, String portfilio) {
        this.cv = cv;
        this.lettreMotivation = lettreMotivation;
        this.dateCandidature = dateCandidature;
        this.idUtilisateur = idUtilisateur;
        this.idOffre = idOffre;
        this.portfolio = portfilio;
    }

    public Condidature(String cv, String lettreMotivation, Timestamp dateCandidature, int idUtilisateur, int idOffre) {
        this.cv = cv;
        this.lettreMotivation = lettreMotivation;
        this.dateCandidature = dateCandidature;
        this.idUtilisateur = idUtilisateur;
        this.idOffre = idOffre;
    }

    public Condidature(int idC, String cv, Timestamp dateCandidature, String portfolio, int idUtilisateur, int idOffre) {
        this.idC = idC;
        this.cv = cv;
        this.dateCandidature = dateCandidature;
        this.portfolio = portfolio;
        this.idUtilisateur = idUtilisateur;
        this.idOffre = idOffre;
    }

    public Condidature(int idC, String cv, String lettreMotivation, Timestamp dateCandidature, int idUtilisateur, int idOffre, String portfolio, Etat etat) {
        this.idC = idC;
        this.cv = cv;
        this.lettreMotivation = lettreMotivation;
        this.dateCandidature = dateCandidature;
        this.portfolio = portfolio;
        this.idUtilisateur = idUtilisateur;
        this.idOffre = idOffre;
        this.etat = etat;
    }

    public Condidature(int idC, String cv, String lettreMotivation, Timestamp dateCandidature, int idUtilisateur, int idOffre, String portfolio) {
        this.idC = idC;
        this.cv = cv;
        this.lettreMotivation = lettreMotivation;
        this.dateCandidature = dateCandidature;
        this.portfolio = portfolio;
        this.idUtilisateur = idUtilisateur;
        this.idOffre = idOffre;
    }

    public int getIdC() {
        return idC;
    }

    public String getCv() {
        return cv;
    }

    public String getLettreMotivation() {
        return lettreMotivation;
    }

    public Timestamp getDateCandidature() {
        return dateCandidature;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public void setLettreMotivation(String lettreMotivation) {
        this.lettreMotivation = lettreMotivation;
    }

    public void setDateCandidature(Timestamp dateCandidature) {
        this.dateCandidature = dateCandidature;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public User getUser() {
        return user;
    }

    public Offre getOffre() {
        return offre;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }
    
    
    @Override
    public int hashCode() {
        return Objects.hash(idC, cv, lettreMotivation, dateCandidature, idUtilisateur, idOffre, portfolio, etat);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Condidature other = (Condidature) obj;
        return idC == other.idC
                && Objects.equals(cv, other.cv)
                && Objects.equals(lettreMotivation, other.lettreMotivation)
                && Objects.equals(dateCandidature, other.dateCandidature)
                && idUtilisateur == other.idUtilisateur
                && idOffre == other.idOffre
                && Objects.equals(portfolio, other.portfolio)
                && etat == other.etat;
    }
    
    
    @Override
    public String toString() {
        return "Condidature{" + "idC=" + idC + ", cv=" + cv + ", lettreMotivation=" + lettreMotivation + ", dateCandidature="
                + dateCandidature + ", portfilio=" + portfolio + ", idUtilisateur="
                + idUtilisateur + ", idOffre=" + idOffre + ", etat=" + etat + '}';
    }

}
