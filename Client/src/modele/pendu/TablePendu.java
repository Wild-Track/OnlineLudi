package modele.pendu;

public class TablePendu {
    private int numPartie;
    private String motJouer;
    private String motTrouver;
    private int nbEssais;

    public TablePendu() { }

    public TablePendu(int numPartie, String motJouer, String motTrouver, int nbEssais) {
        this.numPartie = numPartie;
        this.motJouer = motJouer;
        this.motTrouver = motTrouver;
        this.nbEssais = nbEssais;
    }

    public int getNumPartie() {
        return numPartie;
    }
    public void setNumPartie(int numPartie) {
        this.numPartie = numPartie;
    }

    public String getMotJouer() {
        return motJouer;
    }
    public void setMotJouer(String motJouer) {
        this.motJouer = motJouer;
    }

    public String getMotTrouver() {
        return motTrouver;
    }
    public void setMotTrouver(String motTrouver) {
        this.motTrouver = motTrouver;
    }

    public int getNbEssais() {
        return nbEssais;
    }
    public void setNbEssais(int nbEssais) {
        this.nbEssais = nbEssais;
    }
}
