/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.services;

import edu.hirepro.entities.Etat;
import edu.hirepro.entities.Reclamation;
import edu.hirepro.entities.Role;
import edu.hirepro.entities.User;
import edu.hirepro.utils.MyConnexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author samsung
 */
public class reclamationMetier {

    public int conterReclamation() {
        String requete = "SELECT COUNT(*) AS total_count FROM reclamation";
        int totalCount = 0;
        try {
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                // Retrieve the value from the "total_count" column as an int
                totalCount = rs.getInt("total_count");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return totalCount;
    }

    public int conterReclamationNonVue() {
        String requete = "SELECT COUNT(*) AS total_count FROM reclamation WHERE etat = 'non_vue'";
        int totalCount = 0;
        try {
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                // Retrieve the value from the "total_count" column as an int
                totalCount = rs.getInt("total_count");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return totalCount;
    }
    
      public int conterReclamationVue() {
        String requete = "SELECT COUNT(*) AS total_count FROM reclamation WHERE etat = 'vue'";
        int totalCount = 0;
        try {
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                // Retrieve the value from the "total_count" column as an int
                totalCount = rs.getInt("total_count");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return totalCount;
    }
      
      public int conterReclamationInactive() {
        String requete = "SELECT COUNT(*) AS total_count FROM reclamation WHERE etat = 'inactive'";
        int totalCount = 0;
        try {
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                // Retrieve the value from the "total_count" column as an int
                totalCount = rs.getInt("total_count");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return totalCount;
    }

    public int getReclamationCountByUserIdAndDate(int userId) {
        int count = 0;
        try {
            // Get the current date
            LocalDate currentDate = LocalDate.now();
            // Set the start and end timestamps for the current date
            LocalDateTime startDateTime = currentDate.atStartOfDay();
            LocalDateTime endDateTime = currentDate.atTime(23, 59, 59);

            String request = "SELECT COUNT(*) FROM reclamation WHERE idUser = ? AND time >= ? AND time <= ?";
            PreparedStatement ps = MyConnexion.getInstance().getCnx().prepareStatement(request);
            ps.setInt(1, userId);
            ps.setTimestamp(2, Timestamp.valueOf(startDateTime));
            ps.setTimestamp(3, Timestamp.valueOf(endDateTime));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println(count);

        return count;
    }

    public String getUSerNameById(int id) {
        String name = null;
        String requete = "SELECT nom FROM user WHERE id = " + id;
        try {
            PreparedStatement ps = MyConnexion.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                name = rs.getString("nom");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return name;
    }

    public String getUSerMailById(int id) {
        String email = null;
        String requete = "SELECT email FROM user WHERE id = " + id;
        try {
            PreparedStatement ps = MyConnexion.getInstance().getCnx().prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return email;
    }

    public boolean containsBadWord(String input) {
        List<String> badWords;
        badWords = new ArrayList<>();
        badWords.add("bande d");
        badWords.add("fait chien");
        badWords.add("putain");
        badWords.add("à la con");
        badWords.add("salope");
        badWords.add("salaud");

        if (badWords.stream().anyMatch((badWord) -> (input.contains(badWord)))) {
            return true;
        }
        return false;
    }

    public ObservableList<String> performSearch(String searchQuery) {
        ObservableList<String> searchResults = FXCollections.observableArrayList();
        try {
            String query = "SELECT prenom, nom, email FROM user WHERE prenom LIKE ? OR nom LIKE ?";
            PreparedStatement ps = MyConnexion.getInstance().getCnx().prepareStatement(query);
            ps.setString(1, "%" + searchQuery + "%");
            ps.setString(2, "%" + searchQuery + "%");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("prenom");
                String lastName = resultSet.getString("nom");
                String fullName = firstName + " " + lastName;
                String email = resultSet.getString("email");
                String fullNameEmail = fullName + " - " + email;
                searchResults.add(fullNameEmail);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return searchResults;
    }

    public int getUserIdByEmail(String email) {
        int userId = -1;
        try {
            String query = "SELECT id FROM user WHERE email = ?";
            PreparedStatement ps = MyConnexion.getInstance().getCnx().prepareStatement(query);
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
                System.out.println(userId);
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userId;
    }

    public String getEmailByUserId(int userId) {
        String email = null;
        try {
            String query = "SELECT email FROM user WHERE id = ?";
            PreparedStatement ps = MyConnexion.getInstance().getCnx().prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                email = resultSet.getString("email");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return email;
    }

    public String getFullNameById(int idUser) {
        String fullName = null;

        try {
            String query = "SELECT CONCAT(nom, ' ', prenom) AS fullname FROM user WHERE id = ?";
            PreparedStatement ps = MyConnexion.getInstance().getCnx().prepareStatement(query);
            ps.setInt(1, idUser);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                fullName = resultSet.getString("fullname");
            }

            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return fullName;
    }

    public Role getUserRoleById(int iduser) {
        Role role = null;

        try {
            String query = "SELECT role FROM User WHERE id = ?";
            PreparedStatement preparedStatement = MyConnexion.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setInt(1, iduser);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String roleString = resultSet.getString("role");
                role = Role.valueOf(roleString);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return role;
    }

    public List<Reclamation> displayEntitiesNonvue() {
        List<Reclamation> myList = new ArrayList<>();

        try {
            String requete = "SELECT * FROM reclamation WHERE etat = 'non_vue'";
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setIdR(rs.getInt(1));
                r.setContenue(rs.getString(2));
                r.setObjet(rs.getString(3));
                r.setTime(rs.getTimestamp(4));
                r.setIdUser(rs.getInt(5));
                String etatString = rs.getString(6);
                // Convert the string value to Etat enum
                Etat etat = Etat.valueOf(etatString);
                r.setEtat(etat);
                r.setIdUserReclame(rs.getInt(7));
                myList.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    public List<Reclamation> displayEntitiesVue() {
    List<Reclamation> myList = new ArrayList<>();

    try {
        String requete = "SELECT * FROM reclamation WHERE etat = 'vue'";
        Statement st = MyConnexion.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            Reclamation r = new Reclamation();
            r.setIdR(rs.getInt(1));
            r.setContenue(rs.getString(2));
            r.setObjet(rs.getString(3));
            r.setTime(rs.getTimestamp(4));
            r.setIdUser(rs.getInt(5));
            String etatString = rs.getString(6);
            // Convert the string value to Etat enum
            Etat etat = Etat.valueOf(etatString);
            r.setEtat(etat);
            r.setIdUserReclame(rs.getInt(7));
            myList.add(r);
        }

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return myList;
}

   public List<Reclamation> displayEntitiesInactive() {
    List<Reclamation> myList = new ArrayList<>();

    try {
        String requete = "SELECT * FROM reclamation WHERE etat = 'inactive'";
        Statement st = MyConnexion.getInstance().getCnx().createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            Reclamation r = new Reclamation();
            r.setIdR(rs.getInt(1));
            r.setContenue(rs.getString(2));
            r.setObjet(rs.getString(3));
            r.setTime(rs.getTimestamp(4));
            r.setIdUser(rs.getInt(5));
            String etatString = rs.getString(6);
            // Convert the string value to Etat enum
            Etat etat = Etat.valueOf(etatString);
            r.setEtat(etat);
            r.setIdUserReclame(rs.getInt(7));
            myList.add(r);
        }

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return myList;
}



    public User getUserByIdUserReclame(int idUserReclame) {
        User user = null;

        try {
            String query = "SELECT u.* FROM user u JOIN reclamation r ON u.id = r.idUserReclame WHERE r.idUserReclame = ?";
            PreparedStatement preparedStatement = MyConnexion.getInstance().getCnx().prepareStatement(query);
            preparedStatement.setInt(1, idUserReclame);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                String tel = resultSet.getString("tel");
                String adresse = resultSet.getString("adresse");
                String roleString = resultSet.getString("role");
                Role role = Role.valueOf(roleString);
                boolean actif = resultSet.getBoolean("actif");

                user = new User(id, nom, prenom, password, email, tel, adresse, role, actif);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;

    }

    public void setReclamationsEtatInactive(int idUserReclame) {
        try {
            String query = "UPDATE reclamation SET etat = 'inactive' WHERE idUserReclame = ?";
            PreparedStatement ps = MyConnexion.getInstance().getCnx().prepareStatement(query);
            ps.setInt(1, idUserReclame);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Reclamation> getReclamationsByUserIdReclame(int idUserReclame) {
    List<Reclamation> reclamations = new ArrayList<>();

    try {
        String query = "SELECT * FROM reclamation WHERE idUserReclame = ?";
        PreparedStatement preparedStatement = MyConnexion.getInstance().getCnx().prepareStatement(query);
        preparedStatement.setInt(1, idUserReclame);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Reclamation reclamation = new Reclamation();
            reclamation.setIdR(resultSet.getInt("idR"));
            reclamation.setContenue(resultSet.getString("contenue"));
            reclamation.setObjet(resultSet.getString("objet"));
            reclamation.setTime(resultSet.getTimestamp("time"));
            reclamation.setIdUser(resultSet.getInt("idUser"));
            reclamation.setEtat(Etat.valueOf(resultSet.getString("etat")));
            reclamation.setIdUserReclame(resultSet.getInt("idUserReclame"));

            reclamations.add(reclamation);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return reclamations;
}
    public void updateReclamationsByUserIdReclame(int idUserReclame) {
        List<Reclamation> reclamations = this.getReclamationsByUserIdReclame(idUserReclame);

        for (Reclamation reclamation : reclamations) {
            reclamation.setEtat(Etat.inactive);
            ReclamationCRUD rc = new ReclamationCRUD();
            rc.update(reclamation);
        }
    }


}
