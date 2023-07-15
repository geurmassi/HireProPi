/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;

/**
 *
 * @author Dell
 */
public class Test {
     private int id ;
    private String titre;
    
    
    public Test() {
       
    }

    public Test( String titre) {
      
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "Test{" + "id=" + id + ", titre=" + titre + '}';
    }

    public int getTestCount() {
         int testCount = 0;
    return testCount;
}

    
    
    
}
