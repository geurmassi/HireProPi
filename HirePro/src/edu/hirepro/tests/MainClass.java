/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.tests;

import edu.hirepro.entities.Etat;
import edu.hirepro.services.ReclamationCRUD;
import edu.hirepro.services.UserCRUD;
import edu.hirepro.entities.Reclamation;
import edu.hirepro.entities.Role;
import edu.hirepro.entities.User;
import edu.hirepro.services.reclamationMetier;
import edu.hirepro.utils.MyConnexion;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samsung
 */
public class MainClass {
     public static void main(String[]args){
       // MyConnexion mc =new MyConnexion();
//       User u=new User("amina", "athmouni", "amina33", "athmouniamina2019@gmail.com", "tozeur", "95543804", Role.condidat);
//       UserCRUD pcd = new UserCRUD();
//       pcd.addEntity(u);
//         System.out.println(pcd.displayEntities());
//          String tst="2023-04-12";  
//           Date time=Date.valueOf(tst);
//           Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//         Reclamation r=new Reclamation("instatifaction", "qualité de service", timestamp, 11);
//         ReclamationCRUD rcd =new ReclamationCRUD();
//         rcd.addEntity(r);
//         System.out.println(rcd.displayEntities());
    List<Reclamation> reclamations = new ArrayList<>();

    try {
        String query = "SELECT * FROM reclamation WHERE idUserReclame = ?";
        PreparedStatement preparedStatement = MyConnexion.getInstance().getCnx().prepareStatement(query);
        preparedStatement.setInt(1, 6);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Reclamation reclamation = new Reclamation();
            reclamation.setIdR(resultSet.getInt("idR"));
            reclamation.setContenue(resultSet.getString("contenue"));
            reclamation.setObjet(resultSet.getString("objet"));
            reclamation.setTime(resultSet.getTimestamp("time"));
            reclamation.setIdUser(resultSet.getInt("idUser"));
            reclamation.setEtat(Etat.valueOf(resultSet.getString("etat")));
            reclamation.setIdUserReclame(resultSet.getInt("idUserReclame"));

            reclamations.add(reclamation);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

         System.out.println(reclamations);

         //System.out.println(rm.displayEntitiesNonvue());
        
//         Reclamation r1 = new Reclamation();
//        r1.setIdR(12);
//        r1.setContenue("vbn,");
//        r1.setObjet("hjk");
//        
//        r1.setTime(timestamp);
//        r1.setIdUser(11);
//        
//        rcd.update(r1);
//        rcd.delete(11);
         
    }
     
    
}
