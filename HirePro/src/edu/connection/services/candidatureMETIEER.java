/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Condidature;
import edu.connection.entities.EtatCandidature;
import edu.connection.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class candidatureMETIEER {

    public String rechercheNameUserbyId(int id) {
        String name = null;
        String requete = "SELECT CONCAT(nom, ' ', prenom) AS fullname FROM user WHERE id = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                name = rs.getString("fullname");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return name;
    }

    public int rechercheUserByName(String nom) {
        String requete = "SELECT id FROM user WHERE nom = '" + nom + "'";
        int id = 0;
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public String recherchePosteNameByIdOffre(int id) {
        String requete = "SELECT jobHolder From offre WHERE idOffre = '" + id + "'";
        String posteName = null;
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                posteName = rs.getString("jobHolder");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    

        return posteName;
    }

    public int conterCondidature() {
        String requete = "SELECT COUNT(*) AS total_count FROM candidature";
        int totalCount = 0;
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
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

    public List<tNumCanParPos> nombreCandidatureParPoste() {
        List<tNumCanParPos> myList = new ArrayList<>();
        String requete = "SELECT p.poste, COUNT(c.idC) AS candidature_count\n"
                + "FROM poste p\n"
                + "LEFT JOIN offre o ON p.idP = o.poste\n"
                + "LEFT JOIN candidature c ON o.idOffre = c.idOffre\n"
                + "GROUP BY p.poste;";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                String poste = rs.getString("poste");
                int candidatureCount = rs.getInt("candidature_count");
                tNumCanParPos rowData = new tNumCanParPos(poste, candidatureCount);
                myList.add(rowData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;

    }

    public List<tNumOffCanParPos> nombreCandidatureEtNumOffreParPoste() {
        List<tNumOffCanParPos> myList = new ArrayList<>();
        String requete = "SELECT COUNT(DISTINCT o.idOffre) AS num_offres, p.poste AS nom_poste, COUNT(c.idC) AS num_candidatures "
                + "FROM offre o "
                + "INNER JOIN poste p ON o.poste = p.idP "
                + "LEFT JOIN candidature c ON o.idOffre = c.idOffre "
                + "GROUP BY p.poste";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                String poste = rs.getString("nom_poste");
                int offreCount = rs.getInt("num_offres");
                int candidatureCount = rs.getInt("num_candidatures");
                tNumOffCanParPos rowData = new tNumOffCanParPos(poste, candidatureCount, offreCount);
                myList.add(rowData);
                System.out.println(rowData);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;

    }

    public String generateUniqueFileName(String originalFileName) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int dotIndex = originalFileName.lastIndexOf(".");
        String fileNameWithoutExtension = originalFileName.substring(0, dotIndex);
        String extension = originalFileName.substring(dotIndex);
        String uniqueFileName = fileNameWithoutExtension + "_" + timestamp + extension;
        return uniqueFileName;
    }

    public String retrieveUserRoleById(int id) {
        String query = "SELECT role FROM user WHERE id = " + id;
        String role = null;
        try {
            Statement statement = MyConnection.getInstance().getCnx().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                role = resultSet.getString("role");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return role;
    }

    public String retrieveUserEmailByIdOffre(int idOffre) {
        String query = "SELECT u.email "
                + "FROM user u "
                + "JOIN offre o ON u.id = o.idUser "
                + "WHERE o.idOffre = " + idOffre;
        String email = null;
        try {
            Statement statement = MyConnection.getInstance().getCnx().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                email = resultSet.getString("email");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return email;
    }

    public String retrieveUserEmailByIdUser(int idUser) {
        String query = "SELECT email "
                + "FROM user "
                + "WHERE id = " + idUser;
        String email = null;
        try {
            Statement statement = MyConnection.getInstance().getCnx().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                email = resultSet.getString("email");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return email;
    }

    public String retrieveUserNameByIdOffre(int idOffre) {
        String query = "SELECT u.nom "
                + "FROM user u "
                + "JOIN offre o ON u.id = o.idUser "
                + "WHERE o.idOffre = " + idOffre;
        String userName = null;
        try {
            Statement statement = MyConnection.getInstance().getCnx().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                userName = resultSet.getString("nom");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userName;
    }

    public List<Condidature> displayEntitiesCandidature(int userId) {
        List<Condidature> myList = new ArrayList<>();
        try {
            String requete = "SELECT c.idC, c.cv, c.lettreMotivation, c.dateCandidature, c.idUtilisateur, c.idOffre, c.portfolio "
                    + "FROM candidature c "
                    + "JOIN offre o ON c.idOffre = o.idOffre "
                    + "WHERE o.idUser = ? AND c.etat = 'en_attente'";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Condidature U = new Condidature(rs.getString("cv"), rs.getString("lettreMotivation"),
                        rs.getTimestamp("dateCandidature"), rs.getInt("idUtilisateur"), rs.getInt("idOffre"),
                        rs.getString("portfolio"));
                U.setIdC(rs.getInt("idC"));
                myList.add(U);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    public boolean checkCondidatureExists(int idOffre, int idUser) {
        try {
            String query = "SELECT COUNT(*) FROM candidature WHERE idOffre = ? and idUtilisateur=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setInt(1, idOffre);
            pst.setInt(2, idUser);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                
                int count = rs.getInt(1);
                if (count>0){
                  return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public List<String> getPostesWithDateByUserId(int userId) {
        List<String> postesWithDate = new ArrayList<>();
        try {
            String query = "SELECT jobHolder, dateDebutOffre FROM offre WHERE idUser = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String poste = rs.getString("JobHolder");
                Date dateDebutOffre = rs.getDate("dateDebutOffre");
                String posteWithDate = poste + " (Date Debut: " + dateDebutOffre.toString() + ")";
                postesWithDate.add(posteWithDate);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return postesWithDate;
    }

    public List<Condidature> getCandidaturesByPosteEnAttente(String poste) {
        List<Condidature> candidatures = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT c.* FROM candidature c "
                    + "JOIN offre o ON c.idOffre = o.idOffre "
                    + "WHERE o.jobHolder=? AND c.etat = 'en_attente'";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);

            pst.setString(1, poste);
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int idC = resultSet.getInt("idC");
                String cv = resultSet.getString("cv");
                String lettreMotivation = resultSet.getString("lettreMotivation");
                Timestamp dateCandidature = resultSet.getTimestamp("dateCandidature");
                int idUtilisateur = resultSet.getInt("idUtilisateur");
                int idOffre = resultSet.getInt("idOffre");
                String portfolio = resultSet.getString("portfolio");
                String etatString = resultSet.getString("etat");
                EtatCandidature etat = EtatCandidature.valueOf(etatString);

                Condidature condidature = new Condidature(idC, cv, lettreMotivation, dateCandidature, idUtilisateur, idOffre, portfolio, etat);
                candidatures.add(condidature);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return candidatures;
    }

    public List<Condidature> getCandidaturesByPosteAcceptee(String poste) {
        List<Condidature> candidatures = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT c.* FROM candidature c "
                    + "JOIN offre o ON c.idOffre = o.idOffre "
                    + "WHERE o.jobHolder=? AND c.etat = 'accepté'";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);

            pst.setString(1, poste);
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int idC = resultSet.getInt("idC");
                String cv = resultSet.getString("cv");
                String lettreMotivation = resultSet.getString("lettreMotivation");
                Timestamp dateCandidature = resultSet.getTimestamp("dateCandidature");
                int idUtilisateur = resultSet.getInt("idUtilisateur");
                int idOffre = resultSet.getInt("idOffre");
                String portfolio = resultSet.getString("portfolio");
                String etatString = resultSet.getString("etat");
                EtatCandidature etat = EtatCandidature.valueOf(etatString);

                Condidature condidature = new Condidature(idC, cv, lettreMotivation, dateCandidature, idUtilisateur, idOffre, portfolio, etat);
                candidatures.add(condidature);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return candidatures;
    }

    public List<Condidature> getCandidaturesByPosteRefuser(String poste) {
        List<Condidature> candidatures = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT c.* FROM candidature c "
                    + "JOIN offre o ON c.idOffre = o.idOffre "
                    + "WHERE o.jobHolder=? AND c.etat = 'refusé'";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);

            pst.setString(1, poste);
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                int idC = resultSet.getInt("idC");
                String cv = resultSet.getString("cv");
                String lettreMotivation = resultSet.getString("lettreMotivation");
                Timestamp dateCandidature = resultSet.getTimestamp("dateCandidature");
                int idUtilisateur = resultSet.getInt("idUtilisateur");
                int idOffre = resultSet.getInt("idOffre");
                String portfolio = resultSet.getString("portfolio");
                String etatString = resultSet.getString("etat");
                EtatCandidature etat = EtatCandidature.valueOf(etatString);

                Condidature condidature = new Condidature(idC, cv, lettreMotivation, dateCandidature, idUtilisateur, idOffre, portfolio, etat);
                candidatures.add(condidature);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return candidatures;
    }

    public List<String> getSkillsByIdOffre(int idOffre) {
        List<String> skills = new ArrayList<>();

        // Replace this code with your actual database query to fetch skills based on idOffre
        try {
            String query = "SELECT skills FROM offre WHERE idOffre = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setInt(1, idOffre);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String skillsString = rs.getString("skills");
                String[] skillArray = skillsString.split(","); // Assuming skills are separated by commas
                skills = Arrays.asList(skillArray);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return skills;
    }

}
