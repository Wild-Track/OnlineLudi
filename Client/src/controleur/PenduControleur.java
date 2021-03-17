package controleur;

import javafx.scene.control.Label;
import pendu.PenduInterface;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class PenduControleur implements Initializable {
    private PenduInterface objPendu;
    private int numPartie;

    @FXML
    Label lbl_affichage;
    @FXML
    Label lbl_fin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connexion();

            numPartie = objPendu.nouvellePartie();

            lbl_affichage.setText(objPendu.getAffichage(numPartie));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connexion() {
        int port;
        port = 8000;

        try {
            objPendu = (PenduInterface) Naming.lookup("rmi://localhost:" + port + "/pendu");
        } catch (NotBoundException e) {
            System.out.println("Errer connecxion ");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
