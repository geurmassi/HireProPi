/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Universite;
import edu.connection.interfaces.ICRUD;
import edu.connection.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hadil ibenhajfraj
 */
public class UniversiteCRUD implements ICRUD<Universite> {

    @Override
    public void addEntity(Universite t) {
         String requete ="INSERT INTO universite(libelle) VALUES "+"(?)";
        try {
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getLibelle());

            pst.executeUpdate();
            System.out.println("universite add");
        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Universite> displayEntities() {
       List<Universite> myList = new ArrayList<>();
        try {
            String requete="SELECT*FROM universite";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs =st.executeQuery(requete);
            while (rs.next()){
            Universite U = new Universite();
            U.setIdUniveriste(rs.getInt(1));
            U.setLibelle(rs.getString("libelle"));
           
             
           
                
              myList.add(U);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    
    }

    @Override
    public void supprimer(int id) {
         try {
            String req = "DELETE FROM `universite` WHERE idUniversite = " + id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("Universite deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

  

    @Override
    public void modifier(Universite p) {
          String req = "UPDATE universite SET libelle=? WHERE idUniversite=?";
        PreparedStatement st;
        try {
            st = MyConnection.getInstance().getCnx().prepareStatement(req);
              st.setString(1, p.getLibelle());
              st.setInt(2, p.getIdUniveriste());
            st.executeUpdate();
        System.out.println("Societe updated!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      
    
    }

    @Override
    public void updateEntity(Universite t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEntity(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
