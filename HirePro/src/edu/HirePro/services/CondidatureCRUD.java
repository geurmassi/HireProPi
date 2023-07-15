/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.services;

import edu.HirePro.entities.Condidature;
import edu.HirePro.entities.Offre;
import edu.HirePro.interfaces.ICRUD;
import edu.HirePro.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class CondidatureCRUD implements ICRUD<Condidature> {

    @Override
    public void addEntity(Condidature t) {
        String requete = "INSERT INTO Candidature (cv, lettreMotivation, dateCandidature, idUtilisateur, idOffre) VALUES" + "(?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getCv());
            pst.setString(2, t.getLettreMotivation());

            pst.setTimestamp(3, t.getDateCandidature());
            pst.setInt(4, t.getIdUtilisateur());
            pst.setInt(5, t.getIdOffre());
            pst.executeUpdate();
            System.out.println("condidature add");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addEntityP(Condidature t) {
        String requete = "INSERT INTO Candidature (cv, lettreMotivation, dateCandidature, idUtilisateur, idOffre, portfolio) VALUES"
                + "(?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getCv());
            pst.setString(2, t.getLettreMotivation());

            pst.setTimestamp(3, t.getDateCandidature());
            pst.setInt(4, t.getIdUtilisateur());
            pst.setInt(5, t.getIdOffre());
            pst.setString(6, t.getPortfolio());
            pst.executeUpdate();
            System.out.println("condidature add");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Condidature> displayEntities() {
        List<Condidature> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM candidature";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Condidature U = new Condidature(rs.getString(2), rs.getString(3), rs.getTimestamp(4), rs.getInt(5), rs.getInt(6),rs.getString(7));
                U.setIdC(rs.getInt(1));
                myList.add(U);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public void updateEntity(Condidature t) {
        String requet = "UPDATE candidature SET cv=?, lettreMotivation=?, dateCandidature=?, idUtilisateur=?, idOffre=?, etat=? "
                + "WHERE idC=?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requet)) {
            pst.setString(1, t.getCv());
            pst.setString(2, t.getLettreMotivation());
            pst.setTimestamp(3, t.getDateCandidature());
            pst.setInt(4, t.getIdUtilisateur());
            pst.setInt(5, t.getIdOffre());
            pst.setString(6, t.getEtat().name());
            pst.setInt(7, t.getIdC());

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Candidature modifiée");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id) {
        String requet = "DELETE FROM candidature WHERE idC = " + id;
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requet)) {
            pst.executeUpdate(requet);

            pst.executeUpdate();

            System.out.println("condidature supprimé");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
