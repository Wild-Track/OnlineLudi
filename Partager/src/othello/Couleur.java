
package othello;
public enum Couleur
{
    NOIR, BLANC, VIDE;

    public Couleur getCouleurEnnemi()
    {
        if (this == Couleur.BLANC)
        {
            return Couleur.NOIR;
        }
        else
        {
            return Couleur.BLANC;
        }
    }
}


