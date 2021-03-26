package modele.othello;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OthelloInterface extends Remote
{
    int creerPartieServeur() throws RemoteException;
    boolean rejoindrePartieServeur(int numeroPartie) throws RemoteException;
    void supprimerPartieServeur(int numeroPartie) throws RemoteException;
    boolean jouerCoup(int numeroPartie, int ligne, int colonne, Couleur couleurJoueur, Couleur[][] othellier) throws RemoteException;
    boolean miseAJour(int numeroPartie, Couleur[][] othellier) throws InterruptedException, RemoteException;
    Couleur gagnant(int numeroPartie);
}
