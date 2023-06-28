/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author hadil ibenhajfraj
 */
public class Experience {
    private int idEx;
    private String titreExp;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String details;
    private int idskills;
    private int idsociete;
   private Societe societe;
    private List<Formation> experience;
   public List<Experience> getFormations(Object experience) {
    if (experience == null) {
        experience = new ArrayList<>();
    }
    return (List<Experience>) experience;
}

public void setFormations(List<Formation> experience) {
    this.experience = experience;
}
    public Experience() {
    }

    public Experience(int idEx, String titreExp, LocalDate dateDebut, LocalDate dateFin, String details) {
        this.idEx = idEx;
        this.titreExp = titreExp;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.details = details;
    }
    
     public Experience( String titreExp, LocalDate dateDebut, LocalDate dateFin, String details) {
        
        this.titreExp = titreExp;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.details = details;
    }

    public Experience( String titreExp, LocalDate dateDebut, LocalDate dateFin, String details, int idskills, int idsociete) {
       
        this.titreExp = titreExp;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.details = details;
        this.idskills = idskills;
        this.idsociete = idsociete;
    }

    public int getIdskills() {
        return idskills;
    }

    public int getIdsociete() {
        return idsociete;
    }

    public void setIdskills(int idskills) {
        this.idskills = idskills;
    }

    public void setIdsociete(int idsociete) {
        this.idsociete = idsociete;
    }
 
   

    public int getIdEx() {
        return idEx;
    }

    public String getTitreExp() {
        return titreExp;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public String getDetails() {
        return details;
    }

    public void setIdEx(int idEx) {
        this.idEx = idEx;
    }

    public void setTitreExp(String titreExp) {
        this.titreExp = titreExp;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Experience{" + "idEx=" + idEx + ", titreExp=" + titreExp + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", details=" + details + ", idskills=" + idskills + ", idsociete=" + idsociete + ",societe="+societe+'}';
    }

    public void setSociete(Societe societe) {
       this.societe=societe;
    }

    public Societe getSociete() {
        return societe;
    }

    public Experience get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
     
    
    
    
}