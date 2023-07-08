/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Formation;
import edu.connection.entities.User;
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
 * @author hadil ibenhajfraj
 */
public class FormationCRUD implements ICRUD<Formation> {

    public void addEntity(Formation T) {
        String requete = "INSERT INTO formation(diplome,dateDebutFormation,dateFin,idUser,idUniversity,idSkills) VALUES " + "(?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, T.getDiplome());
            pst.setDate(2, Date.valueOf(T.getDateDebutFormation()));
            pst.setDate(3, Date.valueOf(T.getDateFin()));
            pst.setInt(4, T.getIdUser());
            pst.setInt(5, T.getIdUniversity());
            pst.setInt(6, T.getIdSkills());

            pst.executeUpdate();
            System.out.println("formation add");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Formation> displayEntities() {
        List<Formation> myList = new ArrayList<>();
        try {
            String requete = "SELECT*FROM formation";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Formation U = new Formation();
                U.setIdF(rs.getInt(1));
                U.setDiplome(rs.getString("diplome"));
                U.setDateDebutFormation(LocalDate.MIN);
                U.setDateFin(LocalDate.MIN);

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
            String req = "DELETE FROM `formation` WHERE idF = " + id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("formation deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Formation p) {
        try {
        String req = "UPDATE formation SET diplome=?, dateDebutFormation=?, dateFin=?, idUser=?, idUniversity=?, idSkills=? WHERE idF=?";
        PreparedStatement st = MyConnection.getInstance().getCnx().prepareStatement(req);
        st.setString(1, p.getDiplome());
            st.setDate(2, Date.valueOf(p.getDateDebutFormation()));
            st.setDate(3, Date.valueOf(p.getDateFin()));
            st.setInt(4, p.getIdUser());
            st.setInt(5, p.getIdUniversity());
            st.setInt(6, p.getIdSkills());
            st.setInt(7, p.getIdF());
            st.executeUpdate();
        System.out.println("Formation updated!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

    @Override
    public void updateEntity(Formation t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEntity(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  

}
