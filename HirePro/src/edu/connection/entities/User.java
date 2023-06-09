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
public class User {
 private int id;
    private String nom;
    private String prenom;
    private String password;
    private String email;
    private String tel;
    private String adresse;
    private Role role;
    private int actif;

    public User() {
    }

    public User( String nom, String prenom, String password, String email, String tel, String adresse, Role role, int actif) {
        this.nom = nom;
        this.prenom = prenom;
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


    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getEmail() {
        return email;
    }
    public void setActif(int actif) {
        this.actif = actif;
    }
     public int getActif() {
        return actif;
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
        return "User{" + "id=" + id + ", nom=" + nom + ", password=" + password + ", email=" + email + ", tel=" + tel + ", adresse=" + adresse + ", role=" + role + ", actif=" + actif + '}';
    }
}


    

    
    
    