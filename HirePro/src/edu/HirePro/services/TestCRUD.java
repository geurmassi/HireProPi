/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.services;

import edu.HirePro.entities.Poste;
import edu.HirePro.entities.Test;
import edu.HirePro.interfaces.ICRUD;
import edu.HirePro.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class TestCRUD implements ICRUD<Test>{

    @Override
    public void addEntity(Test t) {
        String requete = "INSERT INTO test (titre) VALUES" + "(?)";
        
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getTitre());
            pst.executeUpdate();
            System.out.println("test Added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Test> displayEntities() {
        String requete = "SELECT * From test";
        List<Test> MyListe  = new ArrayList();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
             ResultSet rs = st.executeQuery(requete);
             while (rs.next()){
                 Test p = new Test();
                 p.setId(rs.getInt(1));
                 p.setTitre(rs.getString(2));
                 MyListe.add(p);
             }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return MyListe;
    }

    @Override
    public void updateEntity(Test t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEntity(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
