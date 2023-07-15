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
public class Test {
    private int idTest;
    private String titre;

    public Test() {
    }

    public Test(String titre) {
        this.titre = titre;
    }

    public void setId(int id) {
        this.idTest = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTitre() {
        return titre;
    }

    @Override
    public String toString() {
        return "Test{" + "idTest=" + idTest + ", titre=" + titre + '}';
    }
    
}
