import allumette.AllumetteImpl;
import pendu.PenduImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MainServeur {
    public static void main(String[] args) {
        try {
            int port = 8000;
            LocateRegistry.createRegistry(port);

            Naming.rebind("rmi://localhost:" + port + "/pendu", new PenduImpl());
            Naming.rebind("rmi://localhost:" + port + "/allumette", new AllumetteImpl());

            System.out.println("Hello Serveur");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
