/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.services;

import edu.hirepro.interfaces.ICRUD;
import edu.hirepro.utils.MyConnexion;
import edu.hirepro.entities.User;
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
public class UserCRUD implements ICRUD<User> {

    @Override
    public void addEntity(User t) {
        String requete = "INSERT INTO user(nom,prenom,password,email,adresse,tel,role) VALUES"
                + "(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnexion.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getNom());
            pst.setString(2, t.getPrenom());
            pst.setString(3, t.getPassword());
            pst.setString(4, t.getEmail());
            pst.setString(5, t.getAdresse());
            pst.setString(6, t.getTel());
            pst.setString(7, t.getRole().toString());

            pst.executeUpdate();
            System.out.println("User added");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public List<User> displayEntities() {
        List<User> myList = new ArrayList();

        try {
            String requete = "SELECT * FROM USER";
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setNom(rs.getString(2));
                u.setPrenom(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setEmail(rs.getString(5));
                u.setAdresse(rs.getString(6));
                u.setTel(rs.getString(7));

                myList.add(u);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return myList;
    }

    @Override
    public void update(User u) {
        String requet = "UPDATE user SET nom = ?, prenom = ?, password = ?, email = ?, adresse = ?, tel = ?, role = ?, actif = ? WHERE id = ?";

        try (PreparedStatement pst = MyConnexion.getInstance().getCnx().prepareStatement(requet)) {
            pst.setString(1, u.getNom());
            pst.setString(2, u.getPrenom());
            pst.setString(3, u.getPassword());
            pst.setString(4, u.getEmail());
            pst.setString(5, u.getAdresse());
            pst.setString(6, u.getTel());
            pst.setString(7, u.getRole().toString());
            pst.setBoolean(8, u.isActif());
            pst.setInt(9, u.getId());

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("User modifié");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String requet = "DELETE FROM user WHERE id =+id";
        try {
            PreparedStatement pst = MyConnexion.getInstance().getCnx().prepareStatement(requet);
            pst.executeUpdate(requet);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("User supprimé");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
}
