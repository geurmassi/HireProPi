/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Offre;
import edu.connection.entities.Poste;
import edu.connection.entities.ReceptionOfApplication;
import edu.connection.entities.TypeEmploi;
import edu.connection.entities.TypeLieuTravail;
import edu.connection.interfaces.ICRUD;
import edu.connection.utils.MyConnection;
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
public class PosteCrud implements ICRUD<Poste>{


    @Override
    public void addEntity(Poste poste) {
        String requete ="INSERT INTO poste(poste) VALUES "+"(?)";
        try {
            PreparedStatement pst= MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, poste.getPoste());
            pst.executeUpdate();
            System.out.println("Poste Added!!");
        } catch (SQLException ex) {
                System.out.println(ex.getMessage());
        
    }  
    }

    @Override
    public List<Poste> displayEntities() {
     List<Poste> myList = new ArrayList<>();
        String query = "SELECT * FROM Poste";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
                ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
               
                int idP = rs.getInt("idP");
                String poste = rs.getString("poste");
               

                Poste p = new Poste(idP,poste);
                myList.add(p);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public void supprimer(int id) {
         String query = "DELETE FROM Poste WHERE idP = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Poste with ID " + id + " has been deleted successfully.");
        } catch (SQLException ex) {
            System.out.println("Error deleting Poste: " + ex.getMessage());
        }
            /* try {
            String req = "DELETE FROM `poste` WHERE idP = " + id;
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("poste Deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
    }

    @Override
    public void modifier(Poste poste) {
        
        String query = "UPDATE Poste SET poste = ? WHERE idP = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, poste.getPoste());
            pst.setInt(2, poste.getIdP()); // Assuming you have an idP property in the Poste class

            pst.executeUpdate();
            System.out.println("Poste updated successfully.");
        } catch (SQLException ex) {
            System.out.println("Error updating Poste: " + ex.getMessage());
        }
    
    }

 
}
     
    

