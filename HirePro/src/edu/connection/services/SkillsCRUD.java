/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Skills;
import edu.connection.interfaces.ICRUD;
import edu.connection.utils.MyConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hadil ibenhajfraj
 */
public class SkillsCRUD implements ICRUD<Skills>{

    @Override
    public void addEntity(Skills t) {
        String requete ="INSERT INTO skills(libelle,user) VALUES "+"(?,?)";
        try {
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getLibelle());
pst.setInt(2, t.getUser());
            pst.executeUpdate();
            System.out.println("Skills add");
        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Skills> displayEntities() {
         List<Skills> myList = new ArrayList<>();
        try {
            String requete="SELECT*FROM skills";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs =st.executeQuery(requete);
            while (rs.next()){
            Skills U = new Skills();
            U.setIdS(rs.getInt(1));
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
            String req = "DELETE FROM `skills` WHERE idS = " + id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("Skills deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Skills p) {
         try {
        String req = "UPDATE skills  SET libelle=? , user=? WHERE idS=?";
        PreparedStatement st = MyConnection.getInstance().getCnx().prepareStatement(req);
        st.setString(1, p.getLibelle());
         st.setInt(2, p.getUser());
            st.setInt(3, p.getIdS());
            
            st.executeUpdate();
        System.out.println("Skills updated!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

    @Override
    public void updateEntity(Skills t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEntity(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

   
    
}
