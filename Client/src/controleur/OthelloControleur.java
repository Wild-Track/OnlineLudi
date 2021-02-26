package controleur;

import modele.Othello.Othello;
import modele.Othello.Pion;

public class OthelloControleur
{

    /* Vérifications pour savoir si un coup est possible */
    public boolean verificationCoup(Othello othello, int ligne, int colonne)
    {

        /* Vérification des 8 directions différentes : diagonales, horizontales, verticales */
        return  verificationCoupRangee(othello, ligne, colonne, 1, 0) ||
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
        Pion othellier[][] = othello.getOthellier();
        colonne =+ colonneDirection;
        ligne =+ ligneDirection;

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
}
