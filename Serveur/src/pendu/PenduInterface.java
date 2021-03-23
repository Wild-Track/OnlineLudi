package pendu;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PenduInterface extends Remote {
    /* ----- ----- Fonction pour le jeu ----- ----- */
    int nouvellePartie() throws RemoteException;
    String getAffichage(int numPartie) throws RemoteException;
    void lettreTrouver(int numPartie, char lettre) throws RemoteException;
    String getAdresseImage(int nbEssais) throws RemoteException;
    String getMot(int numPartie) throws RemoteException;
    int getNbEssais(int numPartie) throws RemoteException;
    int getNbEssaisTotal() throws RemoteException;
    Boolean finPartie(int numPartie) throws RemoteException;

    /* ----- ----- Fonction pour les states du jeu ----- ----- */
    int getNbPartiejouer() throws RemoteException;
    int getNbPartieGagnee() throws RemoteException;
    double getNbEssaisMoyen() throws RemoteException;
}
