package allumette;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AllumetteInterface extends Remote {

    EtatPartie initPartie() throws RemoteException;
    boolean finDePartie(int allumetteRestante) throws RemoteException;
    EtatPartie traitementCoup(EtatPartie etat) throws RemoteException;
    boolean designGagnant(int allumetteJoueur) throws RemoteException;

}
