package controleur;

import pendu.PenduInterface;

import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.Optional;
import java.util.ResourceBundle;

public class PenduControleur implements Initializable {
    private PenduInterface  objPendu;
    private int             numPartie;

    @FXML
    Label lbl_affichage;
    @FXML
    Label lbl_fin;
    @FXML
    Label lbl_nbEssais;

    @FXML
    Label lbl_mot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connexion();

            numPartie = objPendu.nouvellePartie();

            lbl_mot.setText(objPendu.getMot(numPartie));
            lbl_affichage.setText(objPendu.getAffichage(numPartie));
            lbl_nbEssais.setText(String.valueOf(7 - objPendu.getNbEssais(numPartie)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connexion() {
        int port;
        port = 8000;

        try {
            objPendu = (PenduInterface) Naming.lookup("rmi://localhost:" + port + "/pendu");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Erreur connexion : " + e);
        }
    }

    @FXML
    void OnCick_Quitter(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter le jeu");
        alert.setHeaderText("Êtes-vous sûr de vouloir quitter ce jeu ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
        }
    }
}
