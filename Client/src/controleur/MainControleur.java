package controleur;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.net.URI;
import java.awt.*;

public class MainControleur
{

    public void hyperlink_Github_OnClick(ActionEvent actionEvent)
    {
        try {
            Desktop.getDesktop().browse(URI.create("https://github.com/Wild-Track/OnlineLudi"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToPendu(ActionEvent event) {
        try {
            URL fxmlURL = getClass().getResource("../vue/pendu/PenduVue.fxml"); // PenduVue
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();

            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Jeu du Pendu");
            stage.setScene(new Scene(root, 800, 500));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Erreur Client / MainControleur / goToPendu : " + e);
        }
    }

    public void goToAllumettes(MouseEvent mouseEvent) {
        try {
            URL fxmlURL = getClass().getResource("../vue/allumette/AllumetteVue.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();

            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Jeu des Allumettes");
            stage.setScene(new Scene(root, 800, 500));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Erreur Client / MainControleur / goToAllumettes : " + e);
        }
    }

    public void goToOthello(MouseEvent mouseEvent)
    {
        try {
            Stage parentStage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
            parentStage.hide();

            URL fxmlURL = getClass().getResource("/vue/othello/OthelloVue.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();

            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("OTHELLO");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();


        } catch (Exception e) {
            System.out.println("Erreur en chargeant la vue : " + e.toString());
        }
    }
}
