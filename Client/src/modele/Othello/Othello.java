package modele.othello;

import othello.Couleur;

public class Othello
{
    private final int TAILLE = 8;
    private Couleur othellier[][] = new Couleur[TAILLE][TAILLE];
    private Couleur couleurJoueur;
    private Couleur couleurEnnemi;
    /* Qui est la couleur du joueur qui tourne ce client */

    public Othello(Couleur couleurJoueur)
    {
        for (Couleur[] ligne: othellier)
        {
            for (Couleur couleur: ligne)
            {
                couleur = Couleur.VIDE;
            }
        }
        othellier[4][3] = Couleur.NOIR;
        othellier[3][4] = Couleur.NOIR;
        othellier[3][3] = Couleur.BLANC;
        othellier[4][4] = Couleur.BLANC;

        this.couleurJoueur = couleurJoueur;
        if (couleurJoueur == Couleur.BLANC)
        {
            this.couleurEnnemi = Couleur.NOIR;
        }
        else
        {
            this.couleurEnnemi = Couleur.BLANC;
        }
    }

    public Couleur[][] getOthellier()
    {
        return othellier;
    }

    public void setOthellier(Couleur[][] othellier)
    {
        this.othellier = othellier;
    }

    public Couleur getCouleurJoueur()
    {
        return couleurJoueur;
    }

    public void setCouleurJoueur(Couleur couleurJoueur)
    {
        this.couleurJoueur = couleurJoueur;
    }

    public Couleur getCouleurEnnemi() { return couleurEnnemi; }

    public void setCouleurEnnemi(Couleur couleurEnnemi) { this.couleurEnnemi = couleurEnnemi; }
}
