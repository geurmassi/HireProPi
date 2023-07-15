/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.HirePro.tests;

import edu.HirePro.api.PDFReader;
import edu.HirePro.entities.Condidature;
import edu.HirePro.entities.Etat;
import edu.HirePro.entities.Offre;
import edu.HirePro.entities.Poste;
import edu.HirePro.entities.Question;
import edu.HirePro.entities.Role;
import edu.HirePro.entities.Test;
import edu.HirePro.entities.TypeLieuTravail;
import edu.HirePro.entities.TypeTravail;
import edu.HirePro.entities.User;
import edu.HirePro.services.CondidatureCRUD;
import edu.HirePro.services.OffreCRUD;
import edu.HirePro.services.PosteCRUD;
import edu.HirePro.services.QuestionCRUD;
import edu.HirePro.services.TestCRUD;
import edu.HirePro.services.UserCRUD;
import edu.HirePro.utils.MyConnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class MainClasse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//       User u1 = new User("abir", "wardi", "o5ti", "abir@gmail.com","252525252","Ghar el melh",Role.recruteur,true);
//       UserCRUD pcd = new UserCRUD();
//        pcd.addEntity(u1);
//        
//        System.out.println(pcd.displayEntities());
//        
//        Poste p = new Poste("Responsable RH");
//        PosteCRUD pcd1 = new PosteCRUD();
//        pcd1.addEntity(p);
//        for(Poste p1 : pcd1.displayEntities()){
//            System.out.println(p1.getIdP());
//            System.out.println(p1.getPoste());
//        }
//        Test t = new Test("teste d'inscription");
//        TestCRUD td=new TestCRUD();
//        td.addEntity(t);
//        System.out.println(td.displayEntities());
//        
//        Question q = new Question("Lequel des éléments suivants n’est pas un concept POO en Java?", "Héritage", "Encapsulation", "Polymorphisme", "Compilation", 1, 4);
//        QuestionCRUD qc= new QuestionCRUD();
//        qc.addEntity(q);
//        System.out.println(qc.displayEntities());
//        
//          String sts="2023-03-21";  
//           Date dateDebutOffre=Date.valueOf(sts);
//            String tst="2023-04-12";  
//           Date dateFinOffre=Date.valueOf(tst);
////        
//        Offre o =new Offre("Tunis", "développeur", dateDebutOffre, dateFinOffre, TypeTravail.TempsPlein, TypeLieuTravail.distance, 20, 15, 17);
//        OffreCRUD ocd=new OffreCRUD();
//        ocd.addEntity(o);
//        System.out.println(ocd.displayEntities());
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
////        Condidature c = new Condidature("cv/cv.pdf", "lettre_de_motivation/lettre de motivation administratif .pdf", timestamp, 8, 7);
//        CondidatureCRUD ccd = new CondidatureCRUD();
////        ccd.addEntity(c);
////        
//        Condidature c1 = new Condidature();
//        c1.setIdC(23);
//        c1.setCv("cv/cv1.pdf");
//        c1.setLettreMotivation("lettre_de_motivation/lettre de motivation administratif1 .pdf");
//        c1.setDateCandidature(timestamp);
//        c1.setIdUtilisateur(13);
//        c1.setIdOffre(9);
//        ccd.updateEntity(c1);
////        ccd.deleteEntity(6);
//        System.out.println(ccd.displayEntities());

//        String sql =
//                "SELECT COUNT(*) AS num_offres, p.poste, COUNT(c.idC) AS num_candidatures " +
//                    "FROM offre o " +
//                    "INNER JOIN poste p ON o.poste = p.idP " +
//                    "LEFT JOIN candidature c ON o.idOffre = c.idOffre " +
//                    "GROUP BY p.poste";
////                "SELECT p.poste, COUNT(c.idC) AS candidature_count\n"
////                + "FROM poste p\n"
////                + "LEFT JOIN offre o ON p.idP = o.poste\n"
////                + "LEFT JOIN candidature c ON o.idOffre = c.idOffre\n"
////                + "GROUP BY p.poste;";
//
//        try {
//            Statement st = MyConnection.getInstance().getCnx().createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            while (rs.next()) {
//                int numOffres = rs.getInt("num_offres");
//                String poste = rs.getString("poste");
//                int numCandidatures = rs.getInt("num_candidatures");
//
//                System.out.println("Poste: " + poste);
//                System.out.println("Nombre d'offres: " + numOffres);
//                System.out.println("Nombre de candidatures: " + numCandidatures);
//                System.out.println("----------------------------------------");
////                String poste = rs.getString("poste");
////                int candidatureCount = rs.getInt("candidature_count");
////
////                System.out.println("Poste: " + poste + ", Candidature Count: " + candidatureCount);
//            }
//
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//        
//        List<Condidature> candidatures = new ArrayList<>();
//        String poste = "Technicien de Maintenance CMS h/f";
//
//        try {
//            String query = "SELECT c.* FROM candidature c "
//                    + "JOIN offre o ON c.idOffre = o.idOffre "
//                    + "JOIN poste p ON o.poste = p.idP "
//                    + "WHERE p.poste = ? AND c.etat = 'en_attente' "
//                    + "GROUP BY c.idC";
//            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
//
//            pst.setString(1, poste);
//            ResultSet resultSet = pst.executeQuery();
//
//            while (resultSet.next()) {
//                int idC = resultSet.getInt("idC");
//                String cv = resultSet.getString("cv");
//                String lettreMotivation = resultSet.getString("lettreMotivation");
//                Timestamp dateCandidature = resultSet.getTimestamp("dateCandidature");
//                int idUtilisateur = resultSet.getInt("idUtilisateur");
//                int idOffre = resultSet.getInt("idOffre");
//                String portfolio = resultSet.getString("portfolio");
//                String etatString = resultSet.getString("etat");
//                Etat etat = Etat.valueOf(etatString);
//
//                Condidature c = new Condidature(idC, cv, lettreMotivation, dateCandidature, idUtilisateur, idOffre, portfolio);
//                System.out.println(c);
////                candidatures.add(c);
//  
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println(candidatures);
        String cvFilePath = "Evaluation-en-Calcul-Scientifique-3A2-Corrigé-1_20230710041353.pdf";
        String skills = "Evaluation en Calcul Scientifique 3A2-Corrig?\n"
                + "December 23, 2019\n"
                + "Ecole Supérieure PRivée d’Ingénierie et de Technologies\n"
                + "Durée de l’évaluation : 30 minutes\n"
                + "NOM et Prénom :\n"
                + "Question 1 : Importer les modules numpy, matplotlib.pyplot et sympy en utilisant des alias\n"
                + "différents.\n"
                + "[1]: import numpy as np\n"
                + "import matplotlib.pyplot as plt\n"
                + "import sympy as sp\n"
                + "Question 2 : Créer une liste t composée de 10ˆ3 points répartis uniformément sur [􀀀p, 3p].\n"
                + "[2]: t=np.linspace(-np.pi,3*np.pi,10**3)\n"
                + "Question 3 : On considère la fonction f définie par :\n"
                + "f (t) = 2 sin(t + p), 8t 2 [􀀀p, 3p]\n"
                + "Programmer la fonction f .\n"
                + "[3]: f=lambda t: 2*np.sin(t+np.pi)\n"
                + "Question 4 : Donner l’instruction de la représentation graphique de la fonction f sur [􀀀p, 3p].\n"
                + "Utiliser la couleur orange pour la courbe.\n"
                + "[4]: plt.plot(t,f(t),color=\"orange\")\n"
                + "[4]: [<matplotlib.lines.Line2D at 0x177331fba90>]\n"
                + "1\n"
                + "Question 5 : Observer la courbe représentative de f et indiquer le nombre de racines de f sur\n"
                + "[􀀀p, 3p]. Justifier votre réponse.\n"
                + "Sur [􀀀p, 3p], la courbe représentative de f intersecte 5 fois l’axe des abscisses. Donc, f admet\n"
                + "5 racines sur cet intervalle.\n"
                + "Question 6 : Soit\n"
                + "I( f ) =\n"
                + "∫ 3p\n"
                + "􀀀p\n"
                + "f (x)dx\n"
                + "Observer la courbe représentative de f et donner la valeur de I( f ). Justifier votre réponse.\n"
                + "Graphiquement, I( f ) = 0, L’aire délimitée par la courbe représentative de f , les deux axes (x =\n"
                + "􀀀p) et (x = 3p) et la droite des abscisses est composée de 4 aires de valeurs égales : deux situées\n"
                + "au-dessous de (y = 0), donc positives, et deux situées au-dessous de (y = 0), donc négatives. Le\n"
                + "total donne 0.\n"
                + "Question 7 : Retrouver le résultat de la question 6 en calculant I( f ) en utilisant la fonction\n"
                + "integrate du module sympy. Donner les instructions nécessaires.\n"
                + "[5]: x=sp.Symbol('x')\n"
                + "sp.integrate(2*sp.sin(x+np.pi), (x,-np.pi,3*np.pi))\n"
                + "[5]: 0\n"
                + "Question 8 : On souhaite maintenant approcher I( f ) par IcR\n"
                + "m( f ), la valeur donnée par la méthode\n"
                + "d’intégration composite du rectangle du milieu dont la formule est rappelée ci-dessous :\n"
                + "IcR\n"
                + "m( f ) = 2h\n"
                + "p􀀀1\n"
                + "å\n"
                + "i=0\n"
                + "f (x2i+1)\n"
                + "avec h désigne le pas de discrétisation de l’intervalle [a, b] sur lequel f est définie, n = 2p le\n"
                + "nombre de sous intervalles et xi, i 2 f0,    , ng, les points d’intégration.\n"
                + "2\n"
                + "Ecrire une fonction RM(f,a,b,n) qui renvoie la valeur de IcR\n"
                + "m( f ).\n"
                + "[6]: def RM(f,a,b,n):\n"
                + "h=(b-a)/n\n"
                + "p=n/2\n"
                + "S=0\n"
                + "for i in np.arange(0,p):\n"
                + "S+=f(a+(2*i+1)*h)\n"
                + "return 2*h*S\n"
                + "Question 9 : Pour n = 8, utiliser la fonction RM pour approcher I( f ) de la question 6. Comparer\n"
                + "le résultat avec la valeur exacte .\n"
                + "[7]: a=-np.pi\n"
                + "b=3*np.pi\n"
                + "n=8\n"
                + "RM(f,a,b,n)\n"
                + "[7]: 0.0\n"
                + "Le résultats trouvé correspond bien à la valeur exacte de I( f ).\n"
                + "Question 10 : Sans faire de calcul, que serait le résultat attendu pour n = 10? Calculer IcR\n"
                + "M( f )\n"
                + "pour n = 10 etiInterpréter graphiquement les résultats obtenus des questions 9 et 10.\n"
                + "[8]: # Sans faire de calcul, on s'attend à ce que le résultat soit également nul,\n"
                + "# puisqu'on sait que lorsque l'on augmente le nombre de points d'intégrations␣\n"
                + ",!on améliore l'approximation.\n"
                + "# Vérification\n"
                + "n=10\n"
                + "RM(f,a,b,n)\n"
                + "# Le résultat obtenu n'est pas celui attendu. Cela s'explique par le fait que␣\n"
                + ",!pour n=8, les points milieu\n"
                + "# considérés correspondaient aux extrêmums de la fonction qui sont de valeurs␣\n"
                + ",!opposées : 2 et -2.\n"
                + "# Ce qui donnait un résultat nul.\n"
                + "[8]: -5.580589596813811e-16\n"
                + "3";

        List<String> cvContent = PDFReader.readPDFCv(cvFilePath);

        if (cvContent != null) {
            System.out.println("CV Content:\n" + cvContent);

            // Split the skills into individual words
            String[] skillWords = skills.split("\\s+");

            int matchCount = 0;

            // Compare each word in the CV content with the skills
            for (String word : cvContent) {
                for (String skillWord : skillWords) {
                    if (word.toLowerCase().equals(skillWord.toLowerCase())) {
                        matchCount++;
                        break; // Move to the next word in the CV content
                    }
                }
            }

            System.out.println("Number of matching skills: " + matchCount);
        } else {
            System.out.println("Failed to read the CV file.");
        }

    }

}
