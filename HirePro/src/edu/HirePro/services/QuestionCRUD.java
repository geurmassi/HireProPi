/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.services;

import edu.HirePro.entities.Question;
import edu.HirePro.entities.User;
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
public class QuestionCRUD implements ICRUD<Question>{

    @Override
    public void addEntity(Question t) {
        String requete = "INSERT INTO question (question, reponse_1, reponse_2, reponse_3, reponse_4, idTest, rep_vrai) VALUES" + "(?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getQuestion());
            pst.setString(2, t.getReponse_1());
            pst.setString(3, t.getReponse_2());
            pst.setString(4, t.getReponse_3());
            pst.setString(5, t.getReponse_4());
            pst.setInt(6, t.getIdTest());
            pst.setInt(7, t.getRep_vrai());
            pst.executeUpdate();
            System.out.println("Question Added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Question> displayEntities() {
        String requete = "SELECT * From question";
        List<Question> MyListe  = new ArrayList();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
             ResultSet rs = st.executeQuery(requete);
             while (rs.next()){
                 Question q = new Question();
                 q.setIdQuestion(rs.getInt(1));
                 q.setQuestion(rs.getString(2));
                 q.setReponse_1(rs.getString(3));
                 q.setReponse_2(rs.getString(4));
                 q.setReponse_3(rs.getString(5));
                 q.setReponse_4(rs.getString(6));
                 q.setIdTest(rs.getInt(7));
                 q.setRep_vrai(rs.getInt(8));
                 
                 MyListe.add(q);
             }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return MyListe;
    }

    @Override
    public void updateEntity(Question t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEntity(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    
}
