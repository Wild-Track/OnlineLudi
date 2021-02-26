package modele.Othello;

public class Othello
{
    private final int TAILLE = 8;
    private Pion othellier[][] = new Pion[TAILLE][TAILLE];
    private Couleur couleurJoueur;
    private Couleur couleurEnnemi;
    /*Qui est la couleur du joueur qui tourne ce client */

    public Othello(Couleur couleurJoueur)
    {
        for (Pion[] ligne: othellier)
        {
            for (Pion pion: ligne)
            {
                pion.setCouleur(Couleur.VIDE);
            }
        }
        othellier[5][4].setCouleur(Couleur.NOIR);
        othellier[4][5].setCouleur(Couleur.NOIR);
        othellier[4][4].setCouleur(Couleur.NOIR);
        othellier[5][5].setCouleur(Couleur.NOIR);

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

    public Pion[][] getOthellier()
    {
        return othellier;
    }

    public void setOthellier(Pion[][] othellier)
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
