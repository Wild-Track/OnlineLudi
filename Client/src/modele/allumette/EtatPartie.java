package modele.allumette;

import java.io.Serializable;

public class EtatPartie implements Serializable {
    private int allumetteRestante;      // Sert a garder les allumettes restantes
    // Sachant que le nombre d'allumettes est constant on pourrait enlever une des deux variables suivantes mais c'est plus claire avec deux
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

    public void setAllumetteBot(int allumetteBot) { this.allumetteBot = allumetteBot; }

    // Mise à jour après un coup pour le joueur
    public void coupJoueur(int allumettePrise) {
        this.allumetteRestante = allumetteRestante - allumettePrise;
        this.allumetteJoueur = allumetteJoueur + allumettePrise;
    }

    // Mise à jour après un coup pour le bot
    public void coupBot(int allumettePrise) {
        this.allumetteRestante = allumetteRestante - allumettePrise;
        this.allumetteBot = allumetteBot + allumettePrise;
    }
}
