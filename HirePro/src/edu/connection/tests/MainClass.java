package edu.connection.tests;

import edu.connection.entities.Question;
import edu.connection.entities.Test;
import edu.connection.services.QuestionCRUD;
import edu.connection.services.TestCRUD;
import edu.connection.utils.MyConnection;

public class MainClass {
    public static void main(String[] args) {
        MyConnection mc = new MyConnection();
        QuestionCRUD pcd = new QuestionCRUD();
        Question t = new Question(1, "Qu'est-ce que le développement logiciel?",
                "Le développement logiciel est le processus de conception",
                "Le développement logiciel est l'activité de planification",
                "Le développement logiciel est un processus itératif comprenant l'analyse des besoins des utilisateurs",
                "Le développement logiciel consiste à traduire les spécifications fonctionnelles et les exigences des utilisateurs en code informatique.",
                1);
        Question t1 = new Question(2, "Which programming language is primarily used for web development?",
                "Java",
                "Python",
                "JavaScript",
                "C++",1);
        Question t3 = new Question(1,"What is CSS used for in web development?"
,"Creating interactive web applications",
"Handling server-side logic",
"Styling and formatting web pages",
"Managing databases",1);
        
       Question t4 = new Question(1, "What is the role of a web browser in web development?",
"To write and execute code for web applications"
,"To store and manage databases"
,"To render and display web pages to users","To handle server requests and responses",1);
pcd.addEntity(t1);
pcd.addEntity(t3);
pcd.addEntity(t4);
     
   //Update Question
        Question t2 = new Question();
        
        t2.setQuestion("Angular");
        t2.setReponse_1("h");
        t2.setReponse_2("a");
        t2.setReponse_3("ma");
        t2.setReponse_4("wajdi");
        t2.setId_test(2);
        t2.setRep_vrai(2);
        t2.setidQuestion(3);
        pcd.updateEntity(t2);
        
  // Delete Question
       
        pcd.deleteEntity(3);
        pcd.deleteEntity(4);
        
        
        Test T = new Test("Developpement Web");
        Test T1 = new Test("cloud");
        Test T2 = new Test("BI"); 
        TestCRUD test = new TestCRUD();
       
        test.addEntity(T);
        test.addEntity(T1);
        test.addEntity(T2);
        
        
   // Update Test
  Test t6 = new Test();   
     
        t6.setTitre("ARCI");
        t6.setId(3);
        test.updateEntity(t6); 
        
   //Delete Question
       test.deleteEntity(5);
        test.deleteEntity(3);
       
        System.out.println(test.displayEntities());
    }
    
}

