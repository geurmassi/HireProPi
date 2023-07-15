/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Test;
import edu.connection.interfaces.ICRUDAla;
import edu.connection.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class TestCRUD implements ICRUDAla<Test> {
  
    
  @Override
   public void addEntity(Test t) {
        String requete = "INSERT INTO Test (idTest,titre) VALUES (?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, t.getId());
            pst.setString(2, t.getTitre());
            pst.executeUpdate();
            System.out.println("Test added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Test> displayEntities() {
        List<Test> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM Test";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Test q = new Test();
                q.setId(rs.getInt(1));
                q.setTitre(rs.getString("Titre"));

                myList.add(q);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
   // Update a question
    
    
 
    
  @Override
    public void updateEntity(Test t) {
        
        String sql;
        sql = "UPDATE Test SET Titre = ? WHERE IdTest = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(sql);
            pst.setString(1, t.getTitre());
           pst.setInt(2,t.getId());
            pst.executeUpdate();
            
            System.out.println("Test has been updated successfully");
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
    }
    
   

   
  @Override
     public void deleteEntity(int id) {
    try {
        String req = "DELETE FROM Test WHERE idTest="+id;
        Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("row deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   

   
     
      
      
}
     
     
     
 
