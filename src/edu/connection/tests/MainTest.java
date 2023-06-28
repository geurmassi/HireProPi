/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.tests;

import edu.connection.entities.Experience;
import edu.connection.entities.Formation;
import edu.connection.entities.Poste;
import edu.connection.entities.Role;
import edu.connection.entities.Skills;
import edu.connection.entities.Societe;
import edu.connection.entities.Universite;
import edu.connection.entities.User;
import edu.connection.services.ExperienceCRUD;
import edu.connection.services.FormationCRUD;
import edu.connection.services.PosteCRUD;
import edu.connection.services.SkillsCRUD;
import edu.connection.services.SocieteCRUD;
import edu.connection.services.UniversiteCRUD;
import edu.connection.services.UserCRUD;
import edu.connection.utils.MyConnection;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author hadil ibenhajfraj
 */
public class MainTest {
     public static void main (String[] args){
    MyConnection mc = new MyConnection();
    /* Ajouter et Afficher une personne */ 
   
        User T = new User("nizar", "mlaouhi",LocalDate.of(1999,7,5),"12345","nizar@esprit.tn","23116224","tunis",Role.recruteur,true);
         UserCRUD U = new UserCRUD();
       // U.addEntity(T);
       User U2 = new User();
         U2.setNom("Mohamed");
         U2.setPrenom("Matoui");
         U2.setDateNaissance(LocalDate.of(1995, 10, 25));
         U2.setPassword("mohamed123");
         U2.setEmail("mohamed.matoui@esprit.tn");
         U2.setTel("92455157");
         U2.setAdresse("Le Kram");
         U2.setRole(Role.candidat);
         U2.setActif(Boolean.FALSE);
         U2.setId(17);
         U.modifier(U2);
         U.supprimer(18);
         
          System.out.println(U.displayEntities());
        //System.out.println("No user found with the email: " + U.getUserByExperience("hadil.ibnhajfraj@esprit.tn"));
          
          
            /* Ajouter et Afficher une SOCIETE */
       Societe S = new Societe("Tanit web","Monplaisir","societe developpement web ",
                "image/téléchargement.jpg","99745294","tanitweb@gmail.com");
       SocieteCRUD Su = new SocieteCRUD();
       //Su.addEntity(S);
       Societe S1 = new Societe();
       S1.setNom("tanit web ");
       S1.setAdresse("Montplaisir");
       S1.setDescription("Societe de developpement web et mobile ");
       S1.setLogo("image/téléchargement.jpg");
       S1.setTel("23116224");
       S1.setEmail("tanit.web@gmail.com");
       S1.setIdS(3);
       Su.modifier(S1);
         Su.supprimer(2);
          System.out.println(Su.displayEntities());
         
         
            /* Ajouter et Afficher SKILLS */
        Skills skills = new Skills("symfony");
         SkillsCRUD skillscrud = new SkillsCRUD();
        // skillscrud.addEntity(skills);
        Skills Sk = new Skills();
        Sk.setLibelle("Laravel");
        Sk.setIdS(1);
        Sk.setUser(1);
        skillscrud.modifier(Sk);
        skillscrud.supprimer(4);
            System.out.println(skillscrud.displayEntities());
            
             /* Ajouter et Afficher UNIVERSITE */
                Universite Univ = new Universite("esen");
         UniversiteCRUD UnivC = new UniversiteCRUD();
           //UnivC.addEntity(Univ);
            System.out.println(UnivC.displayEntities());
           
           
           /* Ajouter et Afficher FORMATION */
             //F.setDateDebutFormation(LocalDate.of(2023, 1, 1));

           Formation F = new Formation("Formation en Angular",LocalDate.of(2020, 3, 1),LocalDate.of(2023, 4, 4),1,10,2);
           FormationCRUD FC = new FormationCRUD();
           FC.supprimer(2);
           //FC.addEntity(F);
          //System.out.println(FC.displayEntities());
            //String st="2015-04-31";  
          // Date date2=Date.valueOf(st);
           
         

          

          Formation F1 = new Formation();
        
          F1.setDiplome("Formation en Angular");
            F1.setDateDebutFormation(LocalDate.of(2023, 1, 1));
        F1.setDateFin(LocalDate.of(2023, 3, 31));
          F1.setIdUser(1);
          F1.setIdUniversity(1);
          F1.setIdSkills(1);
           F1.setIdF(1);
          
          FC.modifier(F1);
          
          //int idF=1;
         // FC.modifier(F,idF);
           
       /* Ajouter et Afficher Offre */
        Experience E = new Experience("Developpeur Backend",LocalDate.of(2020, 5, 21),LocalDate.of(2021, 7, 31),"Developpement Backend",3,1);
     
         Experience E1 = new Experience();
         E1.setTitreExp("Developpeur Backend");
         //E1.setDateDebut(LocalDate.of(2021, 9, 29));
         //E1.setDateFin(LocalDate.of(2023, 3, 31));
         E1.setDetails("Developpement Backend  ");
         E1.setIdsociete(3);
         E1.setIdskills(1);
         E1.setIdEx(1);
         ExperienceCRUD Ex = new ExperienceCRUD();
         //Ex.modifier(E1);
         Ex.supprimer(2);
              Ex.addEntity(E);
        //System.out.println(Ex.displayEntities());
      
         
         Poste po = new Poste("Developpeur IOS");
         PosteCRUD pc = new PosteCRUD();
         //pc.addEntity(po);
         Poste P = new Poste();
         P.setPoste("Developpeur IOS et Android ");
         P.setIdP(2);
         pc.modifier(P);
         pc.supprimer(3);
         System.out.println(pc.displayEntities());
        //pcd.addEntity2(p);
     
      //  Formation F =new Formation("licence informatique de gestion",dateDebut,dateFin,3,10,1);
      Universite U1 = new Universite();
      U1.setLibelle("fseg");
      U1.setIdUniveriste(12);
      UniversiteCRUD UC = new UniversiteCRUD();
      UC.modifier(U1);
      UC.supprimer(11);
       UC.supprimer(13);
     }

    
}
    

