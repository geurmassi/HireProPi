/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;


import java.time.LocalDate;






/**
 *
 * @author hadil ibenhajfraj
 */
public class Formation {
     private int idF;
    private String diplome;
     private LocalDate dateDebutFormation;
    private LocalDate dateFin;
    private int idUser;
    private int idUniversity;
    private int idSkills;
    private Universite universite;


    public Formation() {
    }

    public Formation( String diplome, LocalDate dateDebutFormation, LocalDate dateFin, int idUser, int idUniversity, int idSkills) {
      
        this.diplome = diplome;
        this.dateDebutFormation = dateDebutFormation;
        this.dateFin = dateFin;
        this.idUser = idUser;
        this.idUniversity = idUniversity;
        this.idSkills = idSkills;
    }

   

   

   

    public int getIdUser() {
        return idUser;
    }

    public int getIdUniversity() {
        return idUniversity;
    }

    public int getIdSkills() {
        return idSkills;
    }

   

    public int getIdF() {
        return idF;
    }

    public LocalDate getDateDebutFormation() {
        return dateDebutFormation;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

   

    

   


   

    public String getDiplome() {
        return diplome;
    }

    public void setIdF(int idF) {
        this.idF = idF;
    }

    public void setDateDebutFormation(LocalDate dateDebutFormation) {
        this.dateDebutFormation = dateDebutFormation;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }


    

   

   

  


    public void setIdSkills(int idSkills) {
        this.idSkills = idSkills;
    }

    public void setIdUniversity(int idUniversity) {
        this.idUniversity = idUniversity;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
   

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    @Override
    public String toString() {
        return universite+"Formation{" + "idF=" + idF + ", diplome=" + diplome + ", dateDebutFormation=" + dateDebutFormation + ", dateFin=" + dateFin + ", idUser=" + idUser + ", idUniversity=" + idUniversity + ", idSkills=" + idSkills + ", Universite="+universite+ '}';
    }

   

    public void setUniversite(Universite universite) {
    this.universite = universite;
}
public Universite getUniversite() {
        return universite;
    }
// Assuming you have the following instance variable in the Formation class

   

    
}
