package allumette;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class AllumetteImpl extends UnicastRemoteObject implements AllumetteInterface {
    private EtatPartie etat;                // Variable de l'etat de la partie coté serveur

    public AllumetteImpl() throws RemoteException {}

    // Initialisation de l'etat de la partie
    public EtatPartie initPartie() throws RemoteException {
        etat = new EtatPartie(15, 0, 0);
        return etat;
    }

    // Fin de partie lorsqu'il n'y a plus d'allumette sur le plateau
    public boolean finDePartie(int allumetteRestante) throws RemoteException {
        return allumetteRestante == 0;
    }

    // Traitement de coup pour le bot appeler après le joueur
    public EtatPartie traitementCoup(EtatPartie etatClient) throws RemoteException {
        try {
            etat = etatClient;
            TimeUnit.SECONDS.sleep(3);          // On laisse 3 secondes après le coup du joueur pour ne pas avoir un retour instantané
            int randomNum = ThreadLocalRandom.current().nextInt(1,3);       // Random pour definir si le bot joue 1 ou 2
            etat.coupBot(randomNum);
        } catch (Exception e) {
            System.out.println("Erreur Client / AllumetteControleur / traitementCoup : " + e);
        }
        return etat;
    }

    // Envoie le gagnant grâce à un bouléen
    public boolean designGagnant(int allumetteJoueur) throws RemoteException {
        return allumetteJoueur % 2 == 1;                // Vrai si le joueur à un nombre impair d'allumette
    }


}
