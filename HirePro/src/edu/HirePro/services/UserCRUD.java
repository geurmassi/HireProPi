/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.services;

import edu.HirePro.entities.Role;
import edu.HirePro.entities.User;
import edu.HirePro.interfaces.ICRUD;
import edu.HirePro.utils.MyConnection;
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
 * @author LENOVO
 */
public class UserCRUD implements ICRUD<User>{

    @Override
    public void addEntity(User t) {
         String requete = "INSERT INTO user (nom,prenom,password,email,tel,adresse,role,actif) VALUES" + "(?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setString(2, t.getPrenom());
            pst.setString(3, t.getPassword());
            pst.setString(4, t.getEmail());
            pst.setString(5, t.getTel());
            pst.setString(6, t.getAdresse());
            pst.setString(7, t.getRole().toString());
            pst.setBoolean(8, t.isActif());
            pst.executeUpdate();
            System.out.println("User Added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }

    @Override
    public List<User> displayEntities() {
        String requete = "SELECT * From user";
        List<User> MyListe  = new ArrayList();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
             ResultSet rs = st.executeQuery(requete);
             while (rs.next()){
                 User u = new User();
                 u.setId(rs.getInt(1));
                 u.setNom(rs.getString(2));
                 u.setPrenom(rs.getString(3));
                 u.setPassword(rs.getString(4));
                 u.setEmail(rs.getString(5));
                 u.setTel(rs.getString(6));
                 u.setAdresse(rs.getString(7));
                 MyListe.add(u);
             }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return MyListe;
    }

    @Override
    public void updateEntity(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public void deleteEntity(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 

   

 
   

    
    
}
