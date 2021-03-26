package modele.allumette;

public class EtatPartie {
    private int allumetteRestante;
    private int allumetteJoueur;
    private int allumetteBot;

    public EtatPartie(int allumetteRestante, int allumetteJoueur, int allumetteBot) {
        this.allumetteRestante = allumetteRestante;
        this.allumetteJoueur = allumetteJoueur;
        this.allumetteBot = allumetteBot;
    }

    public int getAllumetteRestante() {
        return allumetteRestante;
    }

    public void setAllumetteRestante(int allumetteRestante) {
        this.allumetteRestante = allumetteRestante;
    }

    public int getAllumetteJoueur() {
        return allumetteJoueur;
    }

    public void setAllumetteJoueur(int allumetteJoueur) {
        this.allumetteJoueur = allumetteJoueur;
    }

    public int getAllumetteBot() {
        return allumetteBot;
    }

    public void setAllumetteBot(int allumetteBot) {
        this.allumetteBot = allumetteBot;
    }

    public void coupJoueur(int allumettePrise) {
        this.allumetteRestante = allumetteRestante - allumettePrise;
        this.allumetteJoueur = allumetteJoueur + allumettePrise;
    }

    public void coupBot(int allumettePrise) {
        this.allumetteRestante = allumetteRestante - allumettePrise;
        this.allumetteBot = allumetteBot + allumettePrise;
    }
}
