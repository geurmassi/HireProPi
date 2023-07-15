/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.services;

import edu.HirePro.entities.Poste;
import edu.HirePro.interfaces.ICRUD;
import edu.HirePro.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class PosteCRUD implements ICRUD<Poste>{

    @Override
    public void addEntity(Poste t) {
        String requete = "INSERT INTO poste (poste) VALUES" + "(?)";
        
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getPoste());
            pst.executeUpdate();
            System.out.println("Poste Added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Poste> displayEntities() {
        String requete = "SELECT * From poste";
        List<Poste> MyListe  = new ArrayList();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
             ResultSet rs = st.executeQuery(requete);
             while (rs.next()){
                 Poste p = new Poste();
                 p.setIdP(rs.getInt(1));
                 p.setPoste(rs.getString(2));
                 MyListe.add(p);
             }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return MyListe;
    }

    @Override
    public void updateEntity(Poste t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEntity(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    
    
}
