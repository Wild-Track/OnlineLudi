package modele.Othello;

public class Pion
{
    private Couleur couleur;

    public Pion(Couleur couleur)
    {
        this.couleur = couleur;
    }

    public Couleur getCouleur()
    {
        return couleur;
    }

    public void setCouleur(Couleur couleur)
    {
        this.couleur = couleur;
    }
}
