package edu.connection.gui;

import edu.connection.AdminAddTest.TestController;
import edu.connection.Quiz.QTController;
import edu.connection.entities.UserConnect;
import edu.connection.gui.MessageController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import edu.connection.AdminAddTest.afficheDetailsFXMLController;

public class MainController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private VBox VB;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnViewProfile;
    @FXML
    private Button btnOfferMang;
    @FXML
    private Button btnPosteMang;
    @FXML
    private Button btnAppMang;
    @FXML
    private Button btnComplaints;
    @FXML
    private Button btnMessaging;
    @FXML
    private Button btnLogout;
    @FXML
    private BorderPane BP;
    @FXML
    private Label idUser;
    @FXML
    private Label roleUser;
    private Stage primaryStage;
    @FXML
    private Button btnAdminQuizTools;
    @FXML
    private Button btnQuiz;
    @FXML
    private ImageView imViewLogo;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // String id = "1";
        // setIdUser(id);
        //  idUser.setText("2");
        //  roleUser.setText("recruteur");

    }

    public void loadPage(String page) {
        loadMain(page);
    }
    private AddOffreFXMLController addOffreController;

    public void setAddOffreController(AddOffreFXMLController addOffreController) {
        this.addOffreController = addOffreController;
    }

    public void loadMain(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            if (page.equals("AfficherOffre.fxml")) {
                AfficherOffreController afficherOffreController = loader.getController();
                afficherOffreController.setLabelIdUserValue(idUser.getText());
                afficherOffreController.setLabelRoleUserValue(roleUser.getText());
                afficherOffreController.setMainController(this); // Pass a reference to the MainController
                afficherOffreController.initialize(null, null);
            } else if (page.equals("AddOffreFXML.fxml")) {
                AddOffreFXMLController addOffreController = loader.getController();
                addOffreController.setLabelIdUserValue(idUser.getText());
                addOffreController.setMainController(this); // Pass a reference to the MainController
            } else if (page.equals("Test.fxml")) {
                TestController testController = loader.getController();
                // Set necessary references and values in the TestController
            } else if (page.equals("QT.fxml")) {
                QTController qtController = loader.getController();
                // Set necessary references and values in the QTController
            } else if (page.equals("LoginFXML.fxml")) {
                LoginFXMLController loginController = loader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                // Close the current stage (MainController)
                Stage currentStage = (Stage) ap.getScene().getWindow();
                currentStage.close();

                // Set necessary references and values in the QTController
            } else if (page.equals("message.fxml")) {
                MessageController msgController = loader.getController();
                // Set necessary references and values in the QTController
            }

            ap.getChildren().clear(); // Clear existing children
            ap.getChildren().add(root); // Add the new root as a child

            // Set the alignment to center for the new root
            AnchorPane.setTopAnchor(root, (ap.getHeight() - root.prefHeight(-1)) / 2);
            AnchorPane.setLeftAnchor(root, (ap.getWidth() - root.prefWidth(-1)) / 2);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getIdUser() {
        return idUser.getText();
    }

    public void setIdUser(String id) {
        idUser.setText(id);
        System.out.println(idUser.toString());
    }

    public String getRoleUser() {
        return roleUser.getText();
    }

    public void setRoleUser(String role) {
        roleUser.setText(role);
        System.out.println(roleUser.toString());
    }

    @FXML
    private void home(MouseEvent event) {

        loadMain("AfficherOffre.fxml");
    }

    @FXML
    private void offreMg(MouseEvent event) {
        loadMain("AfficherOffre.fxml");
    }

    @FXML
    private void posteMg(MouseEvent event) {
        if ("candidat".equals(roleUser.getText())) {
            System.out.println("Sorry.. you are'nt a recruteur!!");
        } else {
            loadMain("AfficherPoste.fxml");
        }
    }

    @FXML
    private void mViewProfile(MouseEvent event) {
        if ("admin".equals(roleUser.getText())) {
            loadMain("AfficherUser.fxml");
        } else if ("recruteur".equals(roleUser.getText())) {
            //System.out.println("Sorry.. it's adminstration task!!");
            loadMain("RecruteurFXML.fxml");

        } else {
            loadMain("ProfilFXML.fxml");
        }
    }

    @FXML
    private void mAdminQuizTools(MouseEvent event) {
        if ("admin".equals(roleUser.getText())) {
            loadMain("/edu/connection/AdminAddTest/Test.fxml");
        } else {
            System.out.println("Sorry.. it's adminstration task!!");
        }

    }

    @FXML
    private void appMg(MouseEvent event) {
        if ("candidat".equals(UserConnect.roleUserConnect)) {
            System.out.println("Sorry.. you are'nt a recruteur!!");
        } else if (("recruteur".equals(UserConnect.roleUserConnect))) {
            loadMain("RecruteurCandidature.fxml");
        } else {
            loadMain("Condidature.fxml");
        }
    }

    @FXML
    private void mMessaging(MouseEvent event
    ) {
        //loadMain("/edu/connection/messages/Messagerie.java");
        loadMain("message.fxml");
        //loadMain("AfficherPoste.fxml");
    }

    @FXML
    private void mComplaints(MouseEvent event) {
     if (("admin".equals(UserConnect.roleUserConnect))) {
            loadMain("ReclamationFXML.fxml");
        } else {
            loadMain("AjoutReclamation.fxml");
        }
    }

    @FXML
    private void mLogout(MouseEvent event) {
        loadMain("LoginFXML.fxml");
    }

    @FXML
    private void mQuiz(MouseEvent event
    ) {
        loadMain("/edu/connection/Quiz/QT.fxml");
    }

}
