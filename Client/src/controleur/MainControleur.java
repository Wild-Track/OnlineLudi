package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
            URL fxmlURL = getClass().getResource("../vue/PenduVue.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();

            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Jeu");
            stage.setScene(new Scene(root, 800, 500));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println("Execption : " + e);
        }
    }

    public void goToAllumettes(MouseEvent mouseEvent)
    {
    }

    public void goToOthello(MouseEvent mouseEvent)
    {
    }
}
