/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.entities;

/**
 *
 * @author LENOVO
 */
public class Question {
    private int idQuestion;
    private String question;
    private String reponse_1;
    private String reponse_2;
    private String reponse_3;
    private String reponse_4;
    private int idTest;
    private int rep_vrai;

    public Question() {
    }

    public Question(String question, String reponse_1, String reponse_2, String reponse_3, String reponse_4, int idTest, int rep_vrai) {
        this.question = question;
        this.reponse_1 = reponse_1;
        this.reponse_2 = reponse_2;
        this.reponse_3 = reponse_3;
        this.reponse_4 = reponse_4;
        this.idTest = idTest;
        this.rep_vrai = rep_vrai;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public String getQuestion() {
        return question;
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

    public int getIdTest() {
        return idTest;
    }

    public int getRep_vrai() {
        return rep_vrai;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public void setIdTest(int idTest) {
        this.idTest = idTest;
    }

    public void setRep_vrai(int rep_vrai) {
        this.rep_vrai = rep_vrai;
    }

    @Override
    public String toString() {
        return "Question{" + "idQuestion=" + idQuestion + ", question=" + question + ", reponse_1=" + reponse_1 + ", reponse_2=" + reponse_2 + ", reponse_3=" + reponse_3 + ", reponse_4=" + reponse_4 + ", idTest=" + idTest + ", rep_vrai=" + rep_vrai + '}';
    }
    
    
}
