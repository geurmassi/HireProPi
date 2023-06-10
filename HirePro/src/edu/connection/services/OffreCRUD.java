/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Offre;
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
public class OffreCRUD implements  ICRUD<Offre>  {

   
    public void addEntity(Offre T) {
      String requete ="INSERT INTO offre(lieu,descriptif,dateDebutOffre,dateFinOffre,typeEmploi,typeLieuTravail,idUser,poste,question) VALUES "+"(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, T.getDescriptif());
            pst.setString(2, T.getLieuT());
           
            pst.setDate(3, T.getDateDebutOffre());
            pst.setDate(4, T.getDateFinOffre());
            pst.setString(5, T.getTyepTravail().toString());
            pst.setString(6, T.getTypeLieuTravail().toString());
            pst.setInt(7, T.getIdUser());
             pst.setInt(8, T.getPoste());
              pst.setInt(9, T.getQuestion());
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
            String requete="SELECT*FROM offre";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs =st.executeQuery(requete);
            while (rs.next()){
            Offre U = new Offre();
            U.setIdOffre(rs.getInt(1));
            U.setDescriptif(rs.getString("descrptif"));
              U.setLieuT(rs.getString("lieu"));
              U.setDateDebutOffre(rs.getDate("date debut"));
                U.setDateFinOffre(rs.getDate("date fin"));
             U.setIdUser(rs.getInt("Id User"));
               U.setPoste(rs.getInt("Id Poste"));
                 U.setQuestion(rs.getInt("Id question"));
           
                
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
    public void modifier(Offre t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }

