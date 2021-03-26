package allumette;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class AllumetteImpl extends UnicastRemoteObject implements AllumetteInterface {
    private EtatPartie etat;

    public AllumetteImpl() throws RemoteException {}

    @Override
    public EtatPartie initPartie() {
        etat = new EtatPartie(15, 0, 0);
        return etat;
    }

    @Override
    public boolean finDePartie(int allumetteRestante) {
        return allumetteRestante == 0;
    }

    @Override
    public EtatPartie traitementCoup(EtatPartie etatClient) {
        try {
            etat = etatClient;
            TimeUnit.SECONDS.sleep(3);
            int randomNum = ThreadLocalRandom.current().nextInt(1,3);
            etat.coupBot(randomNum);
        } catch (Exception e) {
            System.out.println("Erreur Client / AllumetteControleur / traitementCoup : " + e);
        }
        return etat;
    }


}
