/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connection.tests;

import edu.connection.entities.Offre;
import edu.connection.entities.Poste;
import edu.connection.entities.ReceptionOfApplication;
import edu.connection.entities.TypeEmploi;
import edu.connection.entities.TypeLieuTravail;
import edu.connection.services.OffreEmploiCrud;
import edu.connection.services.PosteCrud;
import edu.connection.utils.MyConnection;
import java.time.LocalDate;

/**
 *
 * @author ASUS
 */
public class MainTest {
    
      public static void main (String[] args){
    MyConnection mc = new MyConnection();
    // Add and Display Poste  
       Poste p1 = new Poste("Software Engineer");
         PosteCrud PS = new PosteCrud();
          PS.addEntity(p1);
          System.out.println(PS.displayEntities());
    // Offre CRUD
    Poste p2 = new Poste("Senior Full stack JS");
     Offre o1 = new Offre ("Software Engineer","Canada", "Ingénieur full stack avec des connaissances","NodeJS  JavaScript","SagemCom", LocalDate.of(2020, 5, 17),LocalDate.of(2021, 8, 31),TypeEmploi.TempsPlein,TypeLieuTravail.Hybride,ReceptionOfApplication.ByEmail);
     Offre o2 = new Offre ("Senior Full stack JS","Suisse", "Ingénieur full stack avec des connaissances","NodeJS  JavaScript","SagemCom", LocalDate.of(2020, 5, 17),LocalDate.of(2021, 8, 31),TypeEmploi.TempsPlein,TypeLieuTravail.SurSite,ReceptionOfApplication.ByEmail);
     
         OffreEmploiCrud OES = new OffreEmploiCrud();
          OES.addEntity(o1);
          OES.addEntity(o2); 
         // OES.modifier(o2);
          //OES.supprimer(8);
          System.out.println(OES.displayEntities());
          


    
}
}
    

