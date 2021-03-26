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

    // Variables globales
    private ArrayList<ImageView> listAllumette = new ArrayList<>(); // Liste pour pouvoir cacher les images de manière efficace
    private AllumetteInterface objAllumette;                        // Objet de connexion avec le serveur
    private EtatPartie etatPartie;                                  // Etat de la partie stocker localement

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connexion();
            remplirListAllumette();
            etatPartie = objAllumette.initPartie();
        } catch(Exception e) {
            System.out.println("Erreur Client / AllumetteControleur / initialize : " + e);
        }
    }

    // Mise en place de la connexion
    private void connexion() {
        int port = 8000;
        try {
            objAllumette = (AllumetteInterface) Naming.lookup("rmi://localhost:" + port + "/allumette");
        }
        catch(NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Erreur Client / AllumetteControleur / connexion : " + e);
        }
    }

    // Fin du tour du joueur
    public void appuieConfirmer(ActionEvent actionEvent) {
        try {
            btnConfirmer.setDisable(true);                      // On empêche le joueur de clicker plusieurs fois pendant le traitement
            etatPartie.coupJoueur(valeurSelection());           // Update locale de ce que le joueur vient de faire
            miseajourAffichage();
            miseajourRadiobtn2();
            if(!objAllumette.finDePartie(etatPartie.getAllumetteRestante())) {          // Verification de si la partie prend fin
                etatPartie = objAllumette.traitementCoup(etatPartie);                   // Coup du bot
                miseajourAffichage();
                miseajourRadiobtn2();
                if(!objAllumette.finDePartie(etatPartie.getAllumetteRestante())) {      // Verification de fin de partie pour le bot
                    btnConfirmer.setDisable(false);                                     // On permet au joueur de faire son tour
                }
                else {
                    ecritGagnant();                     // Si fin de partie -> designation du gagnant
                }
            }
            else {
                ecritGagnant();
            }
        } catch (Exception e) {
            System.out.println("Erreur Client / AllumetteControleur / appuieConfirmer : " + e);
        }
    }

    // Met à jour tout l'affichage
    public void miseajourAffichage() {
        lblAffichAllumetteRestante.setText(String.valueOf(etatPartie.getAllumetteRestante()));
        lblAffichAllumetteJoueur.setText(String.valueOf(etatPartie.getAllumetteJoueur()));
        lblAffichAllumetteBot.setText(String.valueOf(etatPartie.getAllumetteBot()));
        miseajourAffichageAllumette();
    }

    // Met à jour l'affichage des images
    public void miseajourAffichageAllumette() {
        for(int i = etatPartie.getAllumetteRestante(); i < etatPartie.getAllumetteRestante() + valeurSelection(); i++) {
            listAllumette.get(i+1).setVisible(false);
        }   // On a besion de cacher, grâce à la liste seulement ceux qui viennent d'être enlever
    }

    // Simple fonction qui renvoie le nombre d'allumettes en fonction de la séléction
    public int valeurSelection() {
        if(radiobtnChoix1.isSelected()) {
            return 1;
        }
        return 2;
    }

    // La fonction permet d'empêcher d'avoir des coups illégaux comme ceux qui ferais passer en dessous de 0
    private void miseajourRadiobtn2() {
        if (etatPartie.getAllumetteRestante() == 1) {
            radiobtnChoix1.setSelected(true);
            radiobtnChoix2.setDisable(true);
        }
    }

    // Affiche un message à la fin de la partie
    public void ecritGagnant() {
        try {
            if(objAllumette.designGagnant(etatPartie.getAllumetteJoueur())) {
                lblMessage.setText("Vous avez gagné(e) !");
            }
            else {
                lblMessage.setText("Vous avez perdu(e) !");
            }
        } catch (RemoteException e) {
            System.out.println("Erreur Client / AllumetteControleur / ecritGagnant : " + e);
        }
    }

    // Remplit la liste avec toutes les images
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
