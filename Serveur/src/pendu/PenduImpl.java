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
    public String getAffichage(int numPartie) throws RemoteException {
        String  affichage = new String();
        boolean trouver;
        int i;
        int j;

        listeLettreTrouver.get(numPartie).add('a');
        i = 0;
        while (i < listeMotJouer.get(numPartie).length()) {
            trouver = false;

            j = 0;
            while (j < listeLettreTrouver.get(numPartie).size()) {
                if (listeMotJouer.get(numPartie).charAt(i) == listeLettreTrouver.get(numPartie).get(j) || Character.toLowerCase(listeMotJouer.get(numPartie).charAt(i)) == listeLettreTrouver.get(numPartie).get(j)) {
                    affichage += " " + listeMotJouer.get(numPartie).charAt(i);
                    trouver = true;
                }
            }
            if (!trouver) {
                affichage += " _";
            }
        }

        return "affichage";
    }

    @Override
    public String getMot(int numPartie) throws RemoteException {
        return listeMotJouer.get(numPartie);
    }

    @Override
    public int getNbEssais(int numPartie) throws RemoteException {
        return listeNbEssais.get(numPartie);
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