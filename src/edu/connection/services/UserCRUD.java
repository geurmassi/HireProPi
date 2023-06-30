/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Experience;
import edu.connection.entities.Formation;
import edu.connection.entities.Role;
import edu.connection.entities.Skills;
import edu.connection.entities.Societe;
import edu.connection.entities.Universite;
import edu.connection.entities.User;
import edu.connection.utils.MyConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.connection.interfaces.UserCrud;
import java.time.LocalDate;

/**
 *
 * @author hadil ibenhajfraj
 */
public class UserCRUD implements  UserCrud<User>  {

   
    public void addEntity(User T) {
      String requete ="INSERT INTO user(nom,prenom,dateNaissance,password,email,tel,adresse,role,actif) VALUES "+"(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, T.getNom());
            pst.setString(2, T.getPrenom());
            pst.setDate(3,  Date.valueOf(T.getDateNaissance()));
            pst.setString(4, T.getPassword());
            pst.setString(5, T.getEmail());
            pst.setString(6, T.getTel());
            pst.setString(7, T.getAdresse());
            pst.setString(8, T.getRole().toString());
             pst.setBoolean(9, T.getActif());
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
           try {
            String req = "DELETE FROM `user` WHERE id = " + id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("user deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(User p) {
         try {
        String req = "UPDATE user SET nom=?, prenom=?, dateNaissance=?, password=?,email=?, tel=?,adresse=?,role=?,actif=? WHERE id=?";
        PreparedStatement st = MyConnection.getInstance().getCnx().prepareStatement(req);
        st.setString(1, p.getNom());
            st.setString(2, p.getPrenom());
            st.setDate(3, Date.valueOf(p.getDateNaissance()));
            st.setString(4, p.getPassword());
            st.setString(5, p.getEmail());
            st.setString(6, p.getTel());
            st.setString(7, p.getAdresse());
           st.setString(8, p.getRole().toString());
            st.setBoolean(9, p.getActif());
             st.setInt(10, p.getId());
            st.executeUpdate();
        System.out.println("User updated!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

 



    public List<User> getUsersByUniversity(String email) {
    List<User> userList = new ArrayList<>();

    try {
        String query = "SELECT U.id, F.idF, T.idUniversite, T.libelle, F.diplome, F.dateDebutFormation, F.dateFin, U.email "
                + "FROM user U "
                + "INNER JOIN formation F ON F.idUser = U.id "
                + "INNER JOIN universite T ON T.idUniversite = F.idUniversity "
                + "WHERE U.email = ?";
        PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            User user = new User();
            Formation formation = new Formation();
            Universite universite = new Universite();
            user.setId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));

            formation.setIdF(resultSet.getInt("idF"));
            formation.setDiplome(resultSet.getString("diplome"));
            formation.setDateDebutFormation(resultSet.getDate("dateDebutFormation").toLocalDate());
            formation.setDateFin(resultSet.getDate("dateFin").toLocalDate());

            universite.setIdUniveriste(resultSet.getInt("idUniversite"));
            universite.setLibelle(resultSet.getString("libelle"));

            formation.setUniversite(universite);
            user.setFormation(formation);

            userList.add(user);
        }

        if (userList.isEmpty()) {
            System.out.println("No users found with the email: " + email);
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving users by email: " + e.getMessage());
    }

    return userList;
}


 


}

  


  

   
    


   