package pendu;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.Locale;

public class PenduImpl extends UnicastRemoteObject implements PenduInterface {
    /* ArrayList générique */
    private static ArrayList<String>                listeImage;
    private static ArrayList<String>                listeTousLesMots;
    /* ArrayList pour le joueur */
    private static ArrayList<ArrayList<Character>>  listeLettreTrouver;
    private static ArrayList<Integer>               listeNbEssais;
    private static ArrayList<String>                listeMotJouer;

    private static int nbEssaisTotal;
    private static int numPartie;

    public PenduImpl() throws RemoteException {
        super();
        numPartie = -1; // nouvellePartie() fait que numPartie commence à 0 et non à -1
        nbEssaisTotal = 7; // C'est pour initialisé le nombre d'essais d'une partie (Serveur et Client)

        listeLettreTrouver = new ArrayList<ArrayList<Character>>();
        listeNbEssais = new ArrayList<Integer>();
        listeMotJouer = new ArrayList<String>();

        listeImage = new ArrayList<String>();
        implementationDesAdresseImages();

        listeTousLesMots = new ArrayList<String>();
        implementationDeTousLesMots();
    }

    /* ----- ----- Fonction pour le jeu ----- ----- */

    private static void implementationDeTousLesMots() {
        listeTousLesMots.add("Brice");
        listeTousLesMots.add("Nicolas");
        listeTousLesMots.add("Leo");
        listeTousLesMots.add("Jean-Paul Gaultier");
        listeTousLesMots.add("Java");
        listeTousLesMots.add("OCaml");
        listeTousLesMots.add("AFK Arena");
        listeTousLesMots.add("Genshin Impact");
        listeTousLesMots.add("Neko");
        listeTousLesMots.add("Anime");
        listeTousLesMots.add("Manga");
    }

    private static void implementationDesAdresseImages() {
        int i;
        for (i = 0; i <= nbEssaisTotal; i++) {
            listeImage.add("modele/pendu/image/Pendu" + i + ".png");
        }
    }

    @Override
    public int nouvellePartie() throws RemoteException {
        numPartie++;

        listeLettreTrouver.add(new ArrayList<>());
        listeLettreTrouver.get(numPartie).add(' '); // les mots peuvent avoir un espace
        listeLettreTrouver.get(numPartie).add('-'); // les mots peuvent avoir un -

        listeNbEssais.add(0);
        listeMotJouer.add(listeTousLesMots.get((int) (Math.random() * listeTousLesMots.size())));

        return numPartie;
    }

    @Override
    public String getAffichage(int numPartie) throws RemoteException {
        String  affichage = new String();
        boolean trouver;
        char  lettreMotJouer;
        int i;
        int j;

        i = 0;
        while (i < listeMotJouer.get(numPartie).length()) {
            lettreMotJouer = listeMotJouer.get(numPartie).toLowerCase().charAt(i);
            trouver = false;

            j = 0;
            while (j < listeLettreTrouver.get(numPartie).size()) {
                if (lettreMotJouer == listeLettreTrouver.get(numPartie).get(j)) {
                    affichage += " " + listeMotJouer.get(numPartie).toUpperCase(Locale.ROOT).charAt(i);
                    trouver = true;

                }
                j++;
            }
            if (!trouver) {
                affichage += " _";
            }
            i++;
        }

        return affichage;
    }

    @Override
    public void lettreTrouver(int numPartie, char lettre) throws RemoteException {
        if (listeMotJouer.get(numPartie).toLowerCase(Locale.ROOT).contains(Character.toString(lettre))) {
            listeLettreTrouver.get(numPartie).add(lettre);
        } else {
            listeNbEssais.set(numPartie, listeNbEssais.get(numPartie) + 1);
        }
    }

    @Override
    public String getAdresseImage(int nbEssais) throws RemoteException {
        return listeImage.get(nbEssais);
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
    public int getNbEssaisTotal() throws RemoteException {
        return nbEssaisTotal;
    }

    @Override
    public Boolean finPartie(int numPartie) throws RemoteException {
        if (listeNbEssais.get(numPartie) >= nbEssaisTotal) {
            return true;
        } else {
            int i;
            boolean trouver;

            i = 0;
            trouver = true;
            while (i < listeMotJouer.get(numPartie).length() && trouver) {
                if (!listeLettreTrouver.get(numPartie).contains(listeMotJouer.get(numPartie).toLowerCase(Locale.ROOT).charAt(i))) {
                    trouver = false;
                }
                i++;
            }

            return trouver;
        }
    }

    /* ----- ----- Fonction pour les states du jeu ----- ----- */

    @Override
    public int getNbPartiejouer() throws RemoteException {
        return listeNbEssais.size();
    }

    @Override
    public int getNbPartieGagnee() throws RemoteException {
        int nbVictoire;
        int i;

        i = 0;
        nbVictoire = 0;
        while (i < listeNbEssais.size()) {
            if (listeNbEssais.get(i) < nbEssaisTotal) {
                nbVictoire++;
            }
            i++;
        }

        return nbVictoire;
    }

    @Override
    public double getNbEssaisMoyen() throws RemoteException {
        int sommeEssaisTotal;
        int i;

        i = 0;
        sommeEssaisTotal = 0;
        while (i < listeNbEssais.size()) {
            sommeEssaisTotal += listeNbEssais.get(i);
            i++;
        }

        return ((float) sommeEssaisTotal / listeNbEssais.size());
    }
}