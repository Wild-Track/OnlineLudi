package othello;

public class Partie
{
    private final int TAILLE = 8;

    private int partieComplete = 0;
    private Couleur[][] othellier = new Couleur[TAILLE][TAILLE];
    // L'objet syncronisation sert à synchroniser les 2 clients lorsque que l'un des 2 envoie son coup et que l'autre attend
    private Object synchronisation = new Object();
    private int tourPasser = 0;


    public Partie()
    {
        super();

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
    }


    public void setPartieComplete(int partieComplete)
    {
        this.partieComplete = partieComplete;
    }

    public void setOthellier(Couleur[][] othellier)
    {
        this.othellier = othellier;
    }

    public void setSynchronisation(Object synchronisation)
    {
        this.synchronisation = synchronisation;
    }

    public int getPartieComplete()
    {
        return partieComplete;
    }

    public Couleur[][] getOthellier()
    {
        return othellier;
    }

    public Object getSynchronisation()
    {
        return synchronisation;
    }

    public int getTourPasser() { return tourPasser; }

    public void setTourPasser(int tourPasser) { this.tourPasser = tourPasser; }
}
