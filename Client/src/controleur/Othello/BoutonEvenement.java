package controleur.Othello;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class BoutonEvenement implements EventHandler<ActionEvent>
{
    private int ligne, colonne;

    public BoutonEvenement(int ligne, int colonne)
    {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    @Override
    public void handle(ActionEvent actionEvent)
    {

    }
}
