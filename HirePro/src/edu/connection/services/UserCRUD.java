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
             pst.setInt(9, T.getActif());
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
            st.setInt(9, p.getActif());
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

public boolean checkCredentials(String email, String password) {
        String checkUsername = "SELECT email,password , actif FROM user WHERE email=? and password=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(checkUsername);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                boolean actif = result.getBoolean("actif");
                if (actif) {
                    return true; // Les informations d'identification sont correctes et le compte est actif
                } else {
                    // Mettre à jour le statut de l'utilisateur à actif
                    updateActifStatus(email, true);
                    return true; // Les informations d'identification sont correctes et le compte est maintenant actif
                }
            } else {
                return false; // Les informations d'identification sont incorrectes
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
 private void updateActifStatus(String email, boolean actif) {
    
    
          try {
        String req = "UPDATE user SET actif=? WHERE email=?";
        PreparedStatement updateStmt = MyConnection.getInstance().getCnx().prepareStatement(req);
       
             updateStmt.setBoolean(1, actif);
        updateStmt.setString(2, email);
           
            updateStmt.executeUpdate();
        System.out.println("User updated!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
 public void blockUser(String email) {
    try {
        String req = "UPDATE user SET blocked=? WHERE email=?";
        PreparedStatement updateStmt = MyConnection.getInstance().getCnx().prepareStatement(req);
        updateStmt.setBoolean(1, true);
        updateStmt.setString(2, email);
        updateStmt.executeUpdate();
        System.out.println("User blocked!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}  
public boolean isRecruiter(String email) {
    
    
    try {
       
         String checkUsername = "SELECT role FROM user WHERE email = ?";
       PreparedStatement  prepar = MyConnection.getInstance().getCnx().prepareStatement(checkUsername);
        prepar.setString(1, email);
        
        // Execute the query
        ResultSet resultSet = prepar.executeQuery();
        
        // Check if the user exists and has the role "recruteur"
        if (resultSet.next()) {
            String role = resultSet.getString("role");
            return role.equalsIgnoreCase("recruteur");
        } else {
            // User does not exist or has no role assigned
            return false;
        }
    } catch (SQLException ex) {
        // Handle the exception appropriately
        return false;
    } finally {
    }
    }
  public boolean checkUsernameExists(String email) {
        String checkUsername = "SELECT * FROM user WHERE email=?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(checkUsername);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
  public void insertUser(String nom, String prenom, LocalDate dateNaissance, String password, String email, String tel, String adresse, String role) {
        String requete = "INSERT INTO user(nom,prenom,dateNaissance,password,email,tel,adresse,role) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setDate(3, Date.valueOf(dateNaissance));
            pst.setString(4, password);
            pst.setString(5, email);
            pst.setString(6, tel);
            pst.setString(7, adresse);
            pst.setString(8, role);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
  public void updateUserPassword(String email, String newPassword) {
    String query = "UPDATE user SET password = ? WHERE email = ?";
    try {
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
        pst.setString(1, newPassword);
        pst.setString(2, email);
        pst.executeUpdate();
    } catch (SQLException ex) {
    
    }
}
  public User getUserByUniversity(String email) {
        try {
            String query = "SELECT U.id, F.idF, T.idUniversite, T.libelle, F.diplome, F.dateDebutFormation, F.dateFin, U.email "
                    + "FROM user U "
                    + "INNER JOIN formation F ON F.idUser = U.id "
                    + "INNER JOIN universite T ON T.idUniversite = F.idUniversity "
                    + "WHERE U.email = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
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

                return user;
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by email: " + e.getMessage());
        }

        return null; // No user found or an error occurred
    }
 public User getUserByExperience(String email) {
        try {
            String query = "SELECT U.id, T.idEx, S.idS, T.titreExp, T.dateDebut, T.dateFin, T.details,c.nom, U.email "
                    + "FROM user U "
                    + "INNER JOIN skills S ON S.user = U.id "
                    + "INNER JOIN expriencepro T ON T.idSkills = S.idS "
                    + "INNER JOIN societe C ON C.idS = T.idSociete "
                    + "WHERE U.email = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                Skills skills = new Skills();
                Experience experience = new Experience();
                Societe societe = new Societe();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));

                skills.setIdS(resultSet.getInt("idS"));
                experience.setIdEx(resultSet.getInt("idEx"));
                societe.setIdS(resultSet.getInt("idS"));
                experience.setTitreExp(resultSet.getString("titreExp"));
                experience.setDateDebut(resultSet.getDate("dateDebut").toLocalDate());
                experience.setDateFin(resultSet.getDate("dateFin").toLocalDate());
                experience.setDetails(resultSet.getString("details"));
                societe.setNom(resultSet.getString("nom"));
               

  user.setSkills(skills);
            user.setExperience(experience);
            experience.setSociete(societe);
                return user;
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by email: " + e.getMessage());
        }

        return null; // No user found or an error occurred
    }
public boolean isEmailExists(String email) {
    String query = "SELECT * FROM user WHERE email = ?";
    try {
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        return rs.next(); // Retourne true si l'email existe dans la base de données
    } catch (SQLException ex) {
       
        return false; // En cas d'erreur, retourne false par défaut
    }
}
public void updateUserInActiveStatus(String email, int activeStatus) {
        try {
            String query = "UPDATE user SET actif = ? WHERE email = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setInt(1, activeStatus);
            preparedStatement.setString(2, email);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User active status updated successfully.");
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Error updating user active status: " + e.getMessage());
        }
    }
 public User getUserByEmail(String email) {
        try {
            String query = "SELECT * FROM user WHERE email = ?";
            PreparedStatement preparedStatement = MyConnection.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setAdresse(resultSet.getString("adresse"));
                user.setTel(resultSet.getString("tel"));
                user.setEmail(resultSet.getString("email"));
                return user;
            } else {
                System.out.println("No user found with the email: " + email);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user by email: " + e.getMessage());
        }

        return null; // No user found or an error occurred
    }
public String getPhoneNumberByEmail(String email) {
    String phoneNumber = null;
    
    // Exemple d'utilisation de JDBC pour exécuter la requête
    try (
         PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement("SELECT tel FROM user WHERE email = ?")) {
      
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            phoneNumber = resultSet.getString("tel");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return phoneNumber;
}


}

  


  


    


   