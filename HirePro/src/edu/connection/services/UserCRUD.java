/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Role;
import edu.connection.entities.User;
import edu.connection.interfaces.ICRUD;
import edu.connection.utils.MyConnection;
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
public class UserCRUD implements  ICRUD<User>  {

   
    public void addEntity(User T) {
      String requete ="INSERT INTO user(nom,prenom,password,email,tel,adresse,role,actif) VALUES "+"(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, T.getNom());
            pst.setString(2, T.getPrenom());
           
            pst.setString(3, T.getPassword());
            pst.setString(4, T.getEmail());
            pst.setString(5, T.getTel());
            pst.setString(6, T.getAdresse());
            pst.setString(7, T.getRole().toString());
             pst.setBoolean(8, T.getAdresse().contains(requete));
            pst.executeUpdate();
            System.out.println("personne add");
        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        }
    }

  

    @Override
    public List<User> displayEntities() {
      List<User> myList = new ArrayList<>();
        try {
            String requete="SELECT*FROM user";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs =st.executeQuery(requete);
            while (rs.next()){
            User U = new User();
            U.setId(rs.getInt(1));
            U.setNom(rs.getString("nom"));
            U.setPrenom(rs.getString("prenom"));
            // U.setLogin(rs.getString("login"));
            U.setPassword(rs.getString("password"));
            U.setEmail(rs.getString("email"));
            U.setTel(rs.getString("tel"));
            U.setAdresse(rs.getString("adresse"));
            U.setActif(rs.getInt("actif"));
            // U.setRole(rs.getRole(Role).toString());
           
                
              myList.add(U);
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public void supprimer(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifier(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

   
    }


   