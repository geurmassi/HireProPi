/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.interfaces.ICRUD;
import edu.connection.entities.Etat;
import edu.connection.entities.Reclamation;
import edu.connection.interfaces.ICRUDAmina;
import edu.connection.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samsung
 */
public class ReclamationCRUD implements ICRUDAmina<Reclamation> {

    @Override
    public void addEntity(Reclamation t) {
        String requete = "INSERT INTO Reclamation(contenue, objet, time, idUser, idUserReclame) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getContenue());
            pst.setString(2, t.getObjet());
            pst.setTimestamp(3, t.getTime());
            pst.setInt(4, t.getIdUser());
            pst.setInt(5, t.getIdUserReclame());

            pst.executeUpdate();
            System.out.println("Reclamation added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Reclamation> displayEntities() {
        List<Reclamation> myList = new ArrayList();

        try {
            String requete = "SELECT * FROM RECLAMATION";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setIdR(rs.getInt(1));
                r.setContenue(rs.getString(2));
                r.setObjet(rs.getString(3));
                r.setTime(rs.getTimestamp(4));
                r.setIdUser(rs.getInt(5));
                String etatString = rs.getString(6);
                // Convert the string value to Etat enum
                Etat etat = Etat.valueOf(etatString);
                r.setEtat(etat);
                myList.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return myList;
    }

    @Override
    public void update(Reclamation r) {
        String requet = "UPDATE reclamation SET contenue= ?, objet= ? ,time = ?, idUser= ?,etat =? WHERE idR = ?";

        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requet)) {
            pst.setString(1, r.getContenue());
            pst.setString(2, r.getObjet());
            pst.setTimestamp(3, r.getTime());
            pst.setInt(4, r.getIdUser());
            String etatString = r.getEtat().name(); // Convert Etat enum to string representation
            pst.setString(5, etatString);
            pst.setInt(6, r.getIdR());

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("Reclamation modifié");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String requet = "DELETE FROM reclamation WHERE idR =" + id;
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requet);

            pst.executeUpdate();

            System.out.println("Raclamtion supprimé");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    

}
