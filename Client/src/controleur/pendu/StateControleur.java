package controleur.pendu;

import pendu.PenduInterface;
import modele.pendu.Pendu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;

import java.net.URL;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StateControleur extends Pendu implements Initializable {
    private PenduInterface      objPenduServ;
    private static  int         nbEssaisTotal;
    private         boolean     tablePartieEstAfficher;

    @FXML
    private Label lbl_titre;

    @FXML
    private Button btn_state;
    @FXML
    private Pane pane_state;

    @FXML
    private Label lbl_nbPartieTotal;
    @FXML
    private Label lbl_nbPartieG;
    @FXML
    private Label lbl_nbPartieP;
    @FXML
    private Label lbl_ratio;
    @FXML
    private Label lbl_nbEssaisMoy;

    @FXML
    private Button btn_partie;
    @FXML
    private Pane pane_partie;

    @FXML
    private TableView<?> tabView_state;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*try {

        } catch (RemoteException e) {
            System.out.println("Erreur Client / StateControleur / initialize : " + e);
        }*/
    }

    // ----- ----- Tout les fonctions intermédiaire ----- ----- //

    private void afficheTablePartie() {

    }

    private void afficheStatePartie() {

    }

    // ----- ----- Gestion des OnCick ----- ----- //

    @FXML
    void OnCick_MiseAJour(ActionEvent event) {
        try {
            if (tablePartieEstAfficher) {
                afficheTablePartie();
            } else {
                afficheStatePartie();
            }
        } catch (Exception e) {
            System.out.println("Erreur Client / StateControleur / OnCick_MiseAJour : " + e);
        }
    }

    @FXML
    void OnCick_TabPartie(ActionEvent event) {
        try {
            tablePartieEstAfficher = true;

        } catch (Exception e) {
            System.out.println("Erreur Client / StateControleur / OnCick_TabPartie : " + e);
        }
    }

    @FXML
    void OnCick_TabState(ActionEvent event) {
        try {
            tablePartieEstAfficher = false;

        } catch (Exception e) {
            System.out.println("Erreur Client / StateControleur / OnCick_TabState : " + e);
        }
    }
}
