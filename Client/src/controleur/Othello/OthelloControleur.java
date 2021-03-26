package controleur.othello;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import othello.Couleur;
import modele.othello.Othello;
import othello.OthelloInterface;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OthelloControleur implements Initializable
{
    private final int TAILLE = 8;
    private OthelloInterface othelloInterface;
    private Othello othello;
    private int numeroPartie;
    private boolean finPartie;
    private Button[][] boutonsOthellier = new Button[TAILLE][TAILLE];

    @FXML
    private GridPane gridPaneOthellier;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        connexion();

        try
        {
            URL fxmlURL = getClass().getResource("/vue/othello/AccueilOthelloVue.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();

            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Créer / Rejoindre une partie");

            AccueilOthelloControleur controleur = fxmlLoader.<AccueilOthelloControleur>getController();
            controleur.setOthelloControleur(this);

            stage.setScene(new Scene(root));
            stage.showAndWait();

        }
        catch (Exception e)
        {
            System.out.println("Erreur lors du chargement du FXML : " + e);
        }

        initialisationBouton();
        miseAJourAffichage();

    }

    public void miseAJourAffichage()
    {
        Background fond;
        Couleur[][] othellier = othello.getOthellier();
        Paint couleur;


        for (int ligne = 0; ligne < 8; ligne++)
        {
            for (int colonne = 0; colonne < 8; colonne++)
            {
                if (othellier[ligne][colonne] == Couleur.BLANC)
                {
                    couleur = Color.WHITE;
                }
                else if (othellier[ligne][colonne] == Couleur.NOIR)
                {
                    couleur = Color.BLACK;
                }
                else
                {
                    couleur = Color.GRAY;
                }

                fond = new Background(new BackgroundFill(couleur, new CornerRadii(0.5), new Insets(0.5)));

                boutonsOthellier[ligne][colonne].setBackground(fond);
            }
        }
    }

    public void initialisationBouton()
    {
        Button bouton;

        for (int ligne = 0; ligne < 8; ligne++)
        {
            for (int colonne = 0; colonne < 8; colonne++)
            {
                bouton = new Button();
                bouton.setMinWidth(75);
                bouton.setMinHeight(75);
                boutonsOthellier[ligne][colonne] = bouton;
                gridPaneOthellier.add(bouton, colonne, ligne);

                bouton.setOnAction(new BoutonEvenement(ligne, colonne));
            }
        }
    }

    //TODO
    public boolean ajoutCoup(Othello othello, int ligne, int colonne)
    {

        if (verificationCoup(othello, ligne, colonne) || ligne == -1 && colonne == -1)
        {
            try
            {
                this.finPartie = this.othelloInterface.jouerCoup(this.numeroPartie, ligne, colonne, this.othello.getCouleurJoueur(), othello.getOthellier());
                return true;
            }
            catch (RemoteException e)
            {
                throw new IllegalArgumentException("Impossible de jouer un coup");
            }
        }
        return false;
    }

    public ArrayList<Pair<Integer, Integer>> coupPossible(Othello othello)
    {
        ArrayList<Pair<Integer, Integer>> coordonnees = new ArrayList<>();
        for (int ligne = 0; ligne < TAILLE; ligne++)
        {
            for (int colonne = 0; colonne < TAILLE; colonne++)
            {
                /* Ici on peut faire le test de la fonction directement dans le if car && est un opérateur de court circuit
                dans le cas ou la case de l'othellier ne serai pas vide */
                if (othello.getOthellier()[ligne][colonne].equals(Couleur.VIDE) && verificationCoup(othello, ligne, colonne))
                {
                    coordonnees.add(new Pair<>(ligne, colonne));
                }
            }
        }

        return coordonnees;
    }

    /* Vérifications pour savoir si un coup est possible */
    public boolean verificationCoup(Othello othello, int ligne, int colonne)
    {

        /* Vérification des 8 directions différentes : diagonales, horizontales, verticales */
        return verificationCoupRangee(othello, ligne, colonne, 1, 0) ||
                verificationCoupRangee(othello, ligne, colonne, 1, 1) ||
                verificationCoupRangee(othello, ligne, colonne, 0, 1) ||
                verificationCoupRangee(othello, ligne, colonne, -1, 1) ||
                verificationCoupRangee(othello, ligne, colonne, -1, 0) ||
                verificationCoupRangee(othello, ligne, colonne, -1, -1) ||
                verificationCoupRangee(othello, ligne, colonne, 0, -1) ||
                verificationCoupRangee(othello, ligne, colonne, 1, -1);
    }

    /* Verification pour une seule rangée dans une direction donnée */
    public boolean verificationCoupRangee(Othello othello, int ligne, int colonne, int ligneDirection, int colonneDirection)
    {
        Couleur othellier[][] = othello.getOthellier();
        colonne = +colonneDirection;
        ligne = +ligneDirection;

        if (0 >= colonne || colonne >= 8 || 0 >= ligne || ligne >= 8)
        {
            return false;
        }

        while (othellier[ligne][colonne].equals(othello.getCouleurEnnemi()))
        {
            ligne += ligneDirection;
            colonne += colonneDirection;
            if (0 > colonne || colonne > 8 || 0 > ligne || ligne > 8)
            {
                return false;
            }
            if (othellier[ligne][colonne].equals(othello.getCouleurJoueur()))
            {
                return true;
            }
        }
        return false;
    }

    public int creerPartie()
    {
        this.othello = new Othello(Couleur.NOIR);
        try
        {
            this.numeroPartie = this.othelloInterface.creerPartieServeur();

            return this.numeroPartie;
        }
        catch (RemoteException e)
        {
            throw new IllegalArgumentException("Impossible de créer une partie");
        }
    }

    public boolean rejoindrePartie(int numeroPartie)
    {
        this.othello = new Othello(Couleur.BLANC);

        try
        {
            if (this.othelloInterface.rejoindrePartieServeur(numeroPartie))
            {
                return true;
            }
        }
        catch (RemoteException e)
        {
            throw new IllegalArgumentException("Impossible de rejoindre une partie");
        }
        return false;
    }

    private void connexion()
    {
        int port = 8000;

        try
        {
            this.othelloInterface = (OthelloInterface) Naming.lookup("rmi://localhost:" + port + "/othello");
        }
        catch (NotBoundException | MalformedURLException | RemoteException e)
        {
            System.out.println("Impossible d'établir la connexion avec le serveur : " + e.toString());
        }
    }
}
