/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.services;

import edu.HirePro.entities.Offre;
import edu.HirePro.entities.TypeLieuTravail;
import edu.HirePro.entities.TypeTravail;
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
public class OffreCRUD implements ICRUD<Offre> {

    @Override
    public void addEntity(Offre t) {
        String requete = "INSERT INTO offre (lieuT, descriptif, dateDebutOffre,dateFinOffre, typeEmploi, TypeLieuTravail, idUser, poste, question) VALUES" + "(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getLieuT());
            pst.setString(2, t.getDescriptif());

            pst.setDate(3, t.getDateDebutOffre());
            pst.setDate(4, t.getDateFinOffre());
            pst.setString(5, t.getTypeEmploi().toString());
            pst.setString(6, t.getTypeLieuTravail().toString());
            pst.setInt(7, t.getIdUser());
            pst.setInt(8, t.getIdposte());
            pst.setInt(9, t.getQuestion());
            pst.executeUpdate();
            System.out.println("offre add");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Offre> displayEntities() {
        List<Offre> myList = new ArrayList<>();
        try {
            String requete = "SELECT*FROM offre";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Offre U = new Offre();
                U.setIdOffre(rs.getInt(1));
                U.setLieuT(rs.getString("lieuT"));
                U.setDescriptif(rs.getString("descriptif"));
                U.setDateDebutOffre(rs.getDate("dateDebutOffre"));
                U.setDateFinOffre(rs.getDate("dateFinOffre"));
                U.setTypeEmploi(TypeTravail.valueOf(rs.getString("typeEmploi")));
                U.setTypeLieuTravail(TypeLieuTravail.valueOf(rs.getString("TypeLieuTravail")));
                U.setIdUser(rs.getInt("IdUser"));
                U.setIdposte(rs.getInt("poste"));
                U.setQuestion(rs.getInt("question"));

                myList.add(U);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
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
