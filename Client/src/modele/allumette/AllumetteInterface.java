package modele.allumette;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AllumetteInterface extends Remote {

    EtatPartie setEtat(int allumetteRestante, int allumetteJoueur, int allumetteBot) throws RemoteException;

}
