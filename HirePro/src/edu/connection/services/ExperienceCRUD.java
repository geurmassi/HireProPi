/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Experience;
import edu.connection.entities.Formation;
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
public class ExperienceCRUD implements ICRUD<Experience> {

    public void addEntity(Experience T) {
        String requete = "INSERT INTO expriencepro(titreExp,dateDebut,dateFin,details,idSociete,idSkills) VALUES " + "(?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, T.getTitreExp());
            pst.setDate(2,  Date.valueOf(T.getDateDebut()));
            pst.setDate(3, Date.valueOf(T.getDateFin()));;
            pst.setString(4, T.getDetails());
            pst.setInt(5, T.getIdsociete());
            pst.setInt(6, T.getIdskills());

            pst.executeUpdate();
            System.out.println("skills add");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Experience> displayEntities() {
        List<Experience> myList = new ArrayList<>();
        try {
            String requete = "SELECT*FROM expriencepro";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Experience U = new Experience();
                U.setIdEx(rs.getInt(1));
                U.setTitreExp(rs.getString("TitreExp"));
                 //U.setDateDebut(rs.getDate("DateDebut"));
                  //U.setDateFin(rs.getDate("DateFin"));
                   U.setDetails(rs.getString("Details"));
                   U.setIdskills(rs.getInt("idSkills"));
                   U.setIdsociete(rs.getInt("idSociete"));
             
             
          

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
            String req = "DELETE FROM `expriencepro` WHERE idEX = " + id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("experience deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Experience p) {
        try {
        String req = "UPDATE expriencepro SET titreExp=?, dateDebut=?, dateFin=?, details=?,idSociete=?, idSkills=? WHERE idEX=?";
        PreparedStatement st = MyConnection.getInstance().getCnx().prepareStatement(req);
        st.setString(1, p.getTitreExp());
            st.setDate(2, Date.valueOf(p.getDateDebut()));
            st.setDate(3, Date.valueOf(p.getDateFin()));
            st.setString(4, p.getDetails());
            st.setInt(5, p.getIdsociete());
            st.setInt(6, p.getIdskills());
            st.setInt(7, p.getIdEx());
            st.executeUpdate();
        System.out.println("experience updated!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }


    
}