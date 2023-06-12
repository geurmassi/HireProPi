/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.services;

import edu.connection.entities.Question;
import edu.connection.interfaces.ICRUD;
import edu.connection.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuestionCRUD implements ICRUD<Question> {

    /**
     *
     * @param t
     */
    @Override
    public void addEntity(Question t) {
        String requete = "INSERT INTO question(question, reponse_1, reponse_2, reponse_3, reponse_4, idTest, rep_vrai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getQuestion());
            pst.setString(2, t.getReponse_1());
            pst.setString(3, t.getReponse_2());
            pst.setString(4, t.getReponse_3());
            pst.setString(5, t.getReponse_4());
            pst.setInt(6, t.getId_test());
            pst.setInt(7, t.getRep_vrai());

            pst.executeUpdate();
            System.out.println("Question added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Question> displayEntities() {
        List<Question> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM Question";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Question q = new Question();
                q.setidQuestion(rs.getInt(1));
                q.setQuestion(rs.getString("Question"));
                q.setReponse_1(rs.getString("Reponse_1"));
                q.setReponse_2(rs.getString("Reponse_2"));
                q.setReponse_3(rs.getString("Reponse_3"));
                q.setReponse_4(rs.getString("Reponse_4"));
                q.setId_test(rs.getInt("IdTest"));
                q.setRep_vrai(rs.getInt("Rep_vrai"));

                myList.add(q);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
   // Update a question
    
    
 
    @Override
    public void updateEntity(Question t) {
        // Implement the logic for updating the entity here
        // Assuming you have the necessary methods in MyConnection to execute the update query
        
        String sql;
        sql = "UPDATE Question SET Question = ?, Reponse_1 = ?, Reponse_2 = ?, Reponse_3 = ?, Reponse_4 = ?, idTest =? , Rep_vrai = ?  WHERE IdQuestion = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(sql);
            pst.setString(1, t.getQuestion());
            pst.setString(2, t.getReponse_1());
            pst.setString(3, t.getReponse_2());
            pst.setString(4, t.getReponse_3());
            pst.setString(5, t.getReponse_4());
            pst.setInt(6, t.getId_test());
            pst.setInt(7, t.getRep_vrai());
            pst.setInt(8,t.getidQuestion());
           
            pst.executeUpdate();
            
            System.out.println("Question has been updated successfully");
        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
        }
    }
    
    /**
     *
     * @param id
     */
    @Override
     public void deleteEntity(int id) {
    try {
        String req = "DELETE FROM Question WHERE idQuestion="+id;
        Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(req);
            System.out.println("row deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



}
     
     
     
 
