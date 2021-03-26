import allumette.AllumetteImpl;
import othello.Couleur;
import othello.OthelloImplemente;
import othello.Partie;
import pendu.PenduImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;

public class MainServeur {
    public static void main(String[] args) {

        HashMap<Integer, Partie> parties = new HashMap<>();

        try {
            int port = 8000;
            LocateRegistry.createRegistry(port);

            Naming.rebind("rmi://localhost:" + port + "/pendu", new PenduImpl());
            Naming.rebind("rmi://localhost:" + port + "/othello", new OthelloImplemente(parties));
            Naming.rebind("rmi://localhost:" + port + "/allumette", new AllumetteImpl());

            System.out.println("Hello Serveur");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
