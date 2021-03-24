package modele.pendu;

public class TablePendu {
    private int numPartie;
    private String motJouer;
    private String motTrouver;
    private int nbEssais;

    /**
     * constructeur vide
     */
    public TablePendu() { }


    public void setNumPartie(int numPartie) {
        this.numPartie = numPartie;
    }

    public void setMotJouer(String motJouer) {
        this.motJouer = motJouer;
    }

    public void setMotTrouver(String motTrouver) {
        this.motTrouver = motTrouver;
    }

    public void setNbEssais(int nbEssais) {
        this.nbEssais = nbEssais;
    }
}
