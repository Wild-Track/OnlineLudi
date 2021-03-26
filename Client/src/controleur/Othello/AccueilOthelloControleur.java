package controleur.Othello;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class AccueilOthelloControleur
{
    private OthelloControleur othelloControleur;

    @FXML
    ToggleGroup radioButtonDebutPartie;
    @FXML
    TextField textFieldNumeroPartie;
    @FXML
    Label labelNumeroPartieCreer, labelNumeroPartieRejoindre;
    @FXML
    Button buttonValider;
    @FXML
    RadioButton radioButtonCreer,radioButtonRejoindre;
    @FXML
    VBox vBoxNumeroPartie, vBoxChoixPartie;


    public void onClickDebutPartie(ActionEvent actionEvent)
    {
        if (radioButtonDebutPartie.getSelectedToggle().equals(radioButtonCreer))
        {
            labelNumeroPartieRejoindre.setDisable(true);
            textFieldNumeroPartie.setDisable(true);
        }
        else
        {
            labelNumeroPartieRejoindre.setDisable(false);
            textFieldNumeroPartie.setDisable(false);
        }
    }

    public void onClickValider(ActionEvent actionEvent)
    {
        if (radioButtonDebutPartie.getSelectedToggle().equals(radioButtonCreer))
        {
            vBoxChoixPartie.setVisible(false);

            int numeroPartie = othelloControleur.creerPartie();

            labelNumeroPartieCreer.setText(String.valueOf(numeroPartie));

            vBoxNumeroPartie.setVisible(true);
        }
        else
        {
            int numeroPartie = Integer.valueOf(textFieldNumeroPartie.getText());

            othelloControleur.rejoindrePartie(numeroPartie);
        }
    }





    public OthelloControleur getOthelloControleur()
    {
        return othelloControleur;
    }

    public void setOthelloControleur(OthelloControleur othelloControleur)
    {
        this.othelloControleur = othelloControleur;
    }
}
