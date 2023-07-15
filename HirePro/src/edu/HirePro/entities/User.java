/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.entities;

/**
 *
 * @author LENOVO
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
    private boolean actif;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String nom, String prenom, String password, String email, String tel, String adresse, Role role, boolean actif) {
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

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean isActif() {
        return actif;
    }

    

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public Role getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom +  ", password=" + password + ", email=" + email + ", tel=" + tel + ", adresse=" + adresse + ", role=" + role + '}';
    }
    
    
    
}
