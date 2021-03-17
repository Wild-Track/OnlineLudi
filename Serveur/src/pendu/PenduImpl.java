package pendu;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;

public class PenduImpl extends UnicastRemoteObject implements PenduInterface {
    /* ArrayList générique */
    private static ArrayList<String> listeTousLesMots;

    /* ArrayList pour le joueur */
    private static ArrayList<ArrayList<Character>> listeLettreTrouver;
    private static ArrayList<Integer> listeNbEssais;
    private static ArrayList<String> listeMotJouer;

    private int numPartie;

    public PenduImpl() throws RemoteException {
        super();
        numPartie = -1; // nouvellePartie() fait que numPartie commence à 0 et non à -1

        listeLettreTrouver = new ArrayList<ArrayList<Character>>();
        listeNbEssais = new ArrayList<Integer>();
        listeMotJouer = new ArrayList<String>();

        listeTousLesMots = new ArrayList<String>();
        implementationDeTousLesMots();
    }

    private static void implementationDeTousLesMots() {
        listeTousLesMots.add("Brice");
        listeTousLesMots.add("Nicolas");
        listeTousLesMots.add("Leo");
        listeTousLesMots.add("Java");
        listeTousLesMots.add("OCaml");
        listeTousLesMots.add("AFK");
        listeTousLesMots.add("Genshin");
        listeTousLesMots.add("Neko");
        listeTousLesMots.add("Anime");
        listeTousLesMots.add("Manga");
    }

    @Override
    public int nouvellePartie() throws RemoteException {
        numPartie++;

        listeMotJouer.add(listeTousLesMots.get((int) (Math.random() * listeTousLesMots.size())));
        listeLettreTrouver.add(new ArrayList<>());
        listeNbEssais.add(0);

        return numPartie;
    }

    @Override
    public String getAffichage(int numParie) throws RemoteException {
        return listeMotJouer.get(numParie);
    }

    @Override
    public Boolean finPartie(int numPartie) throws RemoteException {
        if (listeNbEssais.get(numPartie) >= 7) {
            return true;
        } else {
            return false;
        }
    }
}