/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.entities;

/**
 *
 * @author Dell
 */
public class Question {
     private int idQuestion;
    private int id_test;
    private String question;
     private String reponse_1;
      private String reponse_2;
       private String reponse_3;
        private String reponse_4;
         private int rep_vrai ;
    
         public Question (){}

    public Question(int id_test, String question, String reponse_1, String reponse_2, String reponse_3, String reponse_4, int rep_vrai) {
     
        this.id_test = id_test;
        this.question = question;
        this.reponse_1 = reponse_1;
        this.reponse_2 = reponse_2;
        this.reponse_3 = reponse_3;
        this.reponse_4 = reponse_4;
        this.rep_vrai = rep_vrai;
    }

    Question(int idQuestion, int id_test, String question, String reponse_1, String reponse_2, String reponse_3, String reponse_4, int rep_vrai) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   



    public int getidQuestion() {
        return idQuestion;
    }

    public void setidQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse_1() {
        return reponse_1;
    }

    public String getReponse_2() {
        return reponse_2;
    }

    public String getReponse_3() {
        return reponse_3;
    }

    public String getReponse_4() {
        return reponse_4;
    }

    public int getRep_vrai() {
        return rep_vrai;
    }

    public void setRep_vrai(int rep_vrai) {
        this.rep_vrai = rep_vrai;
    }

    public void setReponse_1(String reponse_1) {
        this.reponse_1 = reponse_1;
    }

    public void setReponse_2(String reponse_2) {
        this.reponse_2 = reponse_2;
    }

    public void setReponse_3(String reponse_3) {
        this.reponse_3 = reponse_3;
    }

    public void setReponse_4(String reponse_4) {
        this.reponse_4 = reponse_4;
    }

    @Override
    public String toString() {
        return "Question{" + "id=" + idQuestion + ", id_test=" + id_test + ", question=" + question + ", reponse_1=" + reponse_1 + ", reponse_2=" + reponse_2 + ", reponse_3=" + reponse_3 + ", reponse_4=" + reponse_4 + ", rep_vrai=" + rep_vrai + '}';
    }

   
         
         
    
    
}
