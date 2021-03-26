package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import modele.allumette.AllumetteInterface;
import modele.allumette.EtatPartie;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllumetteControleur implements Initializable {

    // Appel de toutes les zones utiles de l'interface
    @FXML
    private ImageView imgAllumette1, imgAllumette2, imgAllumette3, imgAllumette4, imgAllumette5, imgAllumette6,
            imgAllumette7, imgAllumette8, imgAllumette9, imgAllumette10, imgAllumette11, imgAllumette12, imgAllumette13,
            imgAllumette14, imgAllumette15;

    @FXML
    private Label lblAffichAllumetteRestante, lblAffichAllumetteJoueur, lblAffichAllumetteBot, lblMessage;

    @FXML
    private RadioButton radiobtnChoix1, radiobtnChoix2;

    @FXML
    private Button btnConfirmer;

    // Variable globale
    private ArrayList<ImageView> listAllumette = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        remplirListAllumette();
        connexion();


    }

    private void connexion() {
        int port = 8000;
        try {
            AllumetteInterface objAllumette = (AllumetteInterface) Naming.lookup("rmi://localhost:" + port + "/allumette");
            objAllumette.setEtat(15, 0, 0);
        }
        catch(NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Erreur Client / AllumetteControleur / connexion : " + e);
        }
    }

    public void appuieConfirmer(ActionEvent actionEvent) {

    }

    private void verifApresCoup(EtatPartie etat) {
        if (etat.getAllumetteRestante() == 1) {
            radiobtnChoix1.setSelected(true);
            radiobtnChoix2.setDisable(true);
        }
    }

    private void remplirListAllumette() {
        listAllumette.add(imgAllumette1);
        listAllumette.add(imgAllumette2);
        listAllumette.add(imgAllumette3);
        listAllumette.add(imgAllumette4);
        listAllumette.add(imgAllumette5);
        listAllumette.add(imgAllumette6);
        listAllumette.add(imgAllumette7);
        listAllumette.add(imgAllumette8);
        listAllumette.add(imgAllumette9);
        listAllumette.add(imgAllumette10);
        listAllumette.add(imgAllumette11);
        listAllumette.add(imgAllumette12);
        listAllumette.add(imgAllumette13);
        listAllumette.add(imgAllumette14);
        listAllumette.add(imgAllumette15);
    }

}
