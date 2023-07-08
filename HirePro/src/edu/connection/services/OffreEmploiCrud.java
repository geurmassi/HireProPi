/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Offre;
import edu.connection.entities.ReceptionOfApplication;
import edu.connection.entities.TypeEmploi;
import edu.connection.entities.TypeLieuTravail;
import edu.connection.interfaces.ICRUD;
import edu.connection.utils.MyConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class OffreEmploiCrud implements ICRUD<Offre> {
    
    public List<String> getAllUserEmails() {
        List<String> emails = new ArrayList<>();

        try (Statement statement = MyConnection.getInstance().getCnx().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT email FROM User")) 
        {

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                emails.add(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emails;
    }


    @Override
    public void addEntity(Offre t) {

        String requete = "INSERT INTO offre(jobHolder,lieuT,descriptif,skills,company,dateDebutOffre,dateFinOffre,typeEmploi,typeLieuTravail,receptionOfApplication) VALUES " + "(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getJobHolder());
            pst.setString(2, t.getLieuT());
            pst.setString(3, t.getDescriptif());
            pst.setString(4, t.getSkills());
            pst.setString(5, t.getCompany());
            pst.setDate(6, Date.valueOf(t.getDateDebutOffre()));
            pst.setDate(7, Date.valueOf(t.getDateFinOffre()));
            pst.setString(8, t.getTypeEmploi().toString());
            pst.setString(9, t.getTypeLieuTravail().toString());
            pst.setString(10, t.getReceptionOfApplication().toString());
            // pst.setInt(11, t.getIdUser());
            // pst.setInt(12, t.getPoste());
            pst.executeUpdate();
            System.out.println("offre added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Offre> displayEntities() {
        List<Offre> myList = new ArrayList<>();
        String query = "SELECT * FROM offre";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String jobHolder = rs.getString("jobHolder");
                String lieuT = rs.getString("lieuT");
                String descriptif = rs.getString("descriptif");
                String skills = rs.getString("skills");
                String company = rs.getString("company");
                LocalDate dateDebutOffre = rs.getDate("dateDebutOffre").toLocalDate();
                LocalDate dateFinOffre = rs.getDate("dateFinOffre").toLocalDate();
                TypeEmploi typeEmploi = TypeEmploi.valueOf(rs.getString("typeEmploi"));
                TypeLieuTravail typeLieuTravail = TypeLieuTravail.valueOf(rs.getString("typeLieuTravail"));
                ReceptionOfApplication receptionOfApplication = ReceptionOfApplication.valueOf(rs.getString("receptionOfApplication"));
                int idOffre = rs.getInt("idOffre");

                Offre offre = new Offre(idOffre,jobHolder, lieuT, descriptif, skills, company, dateDebutOffre, dateFinOffre, typeEmploi, typeLieuTravail, receptionOfApplication);
                myList.add(offre);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `offre` WHERE idOffre = ?";
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("OffreEmploi Deleted.. !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //INSERT INTO `offre` (`idOffre`, `jobHolder`, `lieuT`, `descriptif`, `skills`, `Company`, `dateDebutOffre`, `dateFinOffre`, `typeEmploi`, `TypeLieuTravail`, `ReceptionOfApplication`, `idUser`, `poste`)
    //VALUES (NULL, 'a', 'a', 'a', 'a', 'a', '2023-06-14', '2023-06-23', 'a', 'a', 'a', '1', '1');
 @Override
public void modifier(Offre t) {
    try {
        String req = "UPDATE offre SET jobHolder=?, lieuT=?, descriptif=?, skills=?, company=?, dateDebutOffre=?, dateFinOffre=?, typeEmploi=?, typeLieuTravail=?, receptionOfApplication=? WHERE idOffre=?";
        PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(req);
        pst.setString(1, t.getJobHolder());
        pst.setString(2, t.getLieuT());
        pst.setString(3, t.getDescriptif());
        pst.setString(4, t.getSkills());
        pst.setString(5, t.getCompany());
        pst.setDate(6, java.sql.Date.valueOf(t.getDateDebutOffre()));
        pst.setDate(7, java.sql.Date.valueOf(t.getDateFinOffre()));
        pst.setString(8, t.getTypeEmploi().toString()); // Convert enum to string
        pst.setString(9, t.getTypeLieuTravail().toString());
        pst.setString(10, t.getReceptionOfApplication().toString());
        pst.setInt(11, t.getIdOffre()); // Assuming idOffre is an integer field

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("OffreEmploi updated!");
        } else {
            System.out.println("Failed to update OffreEmploi!");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    
}

    @Override
    public void updateEntity(Offre t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEntity(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}






