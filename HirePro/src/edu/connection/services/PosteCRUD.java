/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Poste;
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
public class PosteCRUD implements ICRUD<Poste>{

    @Override
    public void addEntity(Poste t) {
       String requete ="INSERT INTO poste(poste) VALUES "+"(?)";
        try {
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getPoste());
             
           
          
            pst.executeUpdate();
            System.out.println("Poste add");
        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Poste> displayEntities() {
      List<Poste> myList = new ArrayList<>();
        try {
            String requete="SELECT*FROM poste";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs =st.executeQuery(requete);
            while (rs.next()){
            Poste U = new Poste();
            U.setIdP(rs.getInt(1));
            U.setPoste(rs.getString("nom"));
       
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
            String req = "DELETE FROM `poste` WHERE idP = " + id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("poste deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Poste p) {
       try {
        String req = "UPDATE poste SET poste=? WHERE idP=?";
        PreparedStatement st = MyConnection.getInstance().getCnx().prepareStatement(req);
        st.setString(1, p.getPoste());
            st.setInt(2,p.getIdP() );
            
            st.executeUpdate();
        System.out.println("poste updated!");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

  

   
    
}
