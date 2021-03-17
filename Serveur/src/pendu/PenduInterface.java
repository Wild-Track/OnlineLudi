package pendu;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PenduInterface extends Remote {
    int nouvellePartie() throws RemoteException;
    String getAffichage(int numPartie) throws RemoteException;
    Boolean finPartie(int numPartie) throws RemoteException;
}
