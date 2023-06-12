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
public class User {
 private int id;
    private String nom;
    private String prenom;
    private String password;
    private LocalDate dateNaissance;
    private String email;
    private String tel;
    private String adresse;
    private Role role;
    private boolean actif;

    public User() {
    }

    public User( String nom, String prenom, LocalDate dateNaissance,String password,String email, String tel, String adresse, Role role, boolean actif) {
      
        this.nom = nom;
        this.prenom = prenom;
        
        this.dateNaissance = dateNaissance;
        this.password = password;
        this.email = email;
        this.tel = tel;
        this.adresse = adresse;
        this.role = role;
        this.actif = actif;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
   

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getEmail() {
        return email;
    }
 public void setActif(Boolean actif) {
        this.actif = actif;
    }
    public String getAdresse() {
        return adresse;
    }

    public boolean getActif() {
        return actif;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }
    public String getNom() {
        return nom;
    }

    public String getPassword() {
        return password;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", dateNaissance=" + dateNaissance + ", email=" + email + ", tel=" + tel + ", adresse=" + adresse + ", role=" + role + ", actif=" + actif + '}';
    }

   
}


    

    
    
    