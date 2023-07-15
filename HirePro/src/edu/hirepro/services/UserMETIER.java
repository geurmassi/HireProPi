/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.services;

import edu.hirepro.utils.MyConnexion;
import edu.hirepro.entities.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author samsung
 */
public class UserMETIER {

    public User rechercheUserByID(int id) {
        String requete = "SELECT * From user WHERE id= " + id;
        User u = new User();
        try {
            Statement st = MyConnexion.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            if (rs.next()) {
                u.setId(rs.getInt(1));
                u.setNom(rs.getString(2));
                u.setPrenom(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setEmail(rs.getString(5));
                u.setTel(rs.getString(6));
                u.setAdresse(rs.getString(7));
            } else {
                System.out.println("u est vide");
                return u;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

  
}
