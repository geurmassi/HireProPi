/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.entities;

/**
 *
 * @author samsung
 */
public class User {
    private int id;
    private String nom;
    private String prenom;
    private String password;
    private String email;
    private String adresse;
    private String tel;
    private Role role;
    private boolean actif;

    public User() {
    }
    

    public User(String nom, String prenom, String password, String email, String adresse, String tel, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.role = role;
    }

    public User(int id, String nom, String prenom, String password, String email, String tel, String adresse, Role role) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.role = role;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public User(int id, String nom, String prenom, String password, String email, String adresse, String tel, Role role, boolean actif) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.adresse = adresse;
        this.tel = tel;
        this.role = role;
        this.actif = actif;
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

    public String getAdresse() {
        return adresse;
    }

    public String getTel() {
        return tel;
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

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", email=" + email + ", adresse=" + adresse + ", tel=" + tel + ", role=" + role + ", actif=" + actif + '}';
    }
   
    
    
    
}
