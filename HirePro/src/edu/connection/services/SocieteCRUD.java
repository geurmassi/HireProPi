/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Societe;
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
public class SocieteCRUD implements ICRUD<Societe>{

    @Override
    public void addEntity(Societe t) {
       String requete ="INSERT INTO societe(nom,adresse,description,logo,tel,email) VALUES "+"(?,?,?,?,?,?)";
        try {
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getNom());
             pst.setString(2, t.getAdresse());
              pst.setString(3, t.getDescription());
            pst.setString(4, t.getLogo());
            pst.setString(5, t.getTel());
             pst.setString(6, t.getEmail());
           
          
            pst.executeUpdate();
            System.out.println("Societe add");
        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Societe> displayEntities() {
      List<Societe> myList = new ArrayList<>();
        try {
            String requete="SELECT*FROM societe";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs =st.executeQuery(requete);
            while (rs.next()){
            Societe U = new Societe();
            U.setIdS(rs.getInt(1));
            U.setNom(rs.getString("nom"));
              U.setAdresse(rs.getString("adresse"));
                U.setEmail(rs.getString("email"));
              U.setLogo(rs.getString("logo"));
          U.setDescription(rs.getString("description"));
              U.setTel(rs.getString("tel"));
             
           
                
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
            String req = "DELETE FROM `societe` WHERE idS = " + id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("Societe deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Societe p) {
         try {
        String req = "UPDATE societe SET nom=?, adresse=?, description=?, logo=?, tel=?, email=? WHERE idS=?";
        PreparedStatement st = MyConnection.getInstance().getCnx().prepareStatement(req);
        st.setString(1, p.getNom());
            st.setString(2, p.getAdresse());
            st.setString(3, p.getDescription());
            st.setString(4, p.getLogo());
            st.setString(5, p.getTel());
              st.setString(6, p.getEmail());
              st.setInt(7, p.getIdS());
            st.executeUpdate();
        System.out.println("Societe updated!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

    
}
