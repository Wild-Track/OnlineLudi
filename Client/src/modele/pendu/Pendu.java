package modele.pendu;

import pendu.PenduInterface;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Naming;

public class Pendu {
    protected PenduInterface objPenduServ;

    protected void connexion() {
        int port;
        port = 8000;

        try {
            objPenduServ = (PenduInterface) Naming.lookup("rmi://localhost:" + port + "/pendu");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Erreur Client / PenduControleur / connexion : " + e);
        }
    }
}
