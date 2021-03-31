package pendu;

import java.util.ArrayList;
import java.util.Locale;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PenduImpl extends UnicastRemoteObject implements PenduInterface {
    /* ArrayList générique */
    private static ArrayList<String>                listeAdresseImage;
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

        listeAdresseImage = new ArrayList<String>();
        implementationDesAdresseImages();

        listeTousLesMots = new ArrayList<String>();
        implementationDeTousLesMots();
    }

    /* ----- ----- Les fonctions pour les implémentations au lancement du serveur ----- ----- */

    /**
     * Implémentation de tous les mots dans l'ArrayList listeTousLesMots
     */
    private static void implementationDeTousLesMots() {
        /* Permet de récupérer tous les mots dans le fichier saveMots.txt */
        BufferedReader fichierMots;
        String lineFichier;

        try {
            fichierMots = new BufferedReader(new FileReader(new File("Serveur/src/pendu/saveMots.txt")));
            while((lineFichier = fichierMots.readLine()) != null ) {
                listeTousLesMots.add(lineFichier.replace('\n', ' ').trim());
            }
            fichierMots.close();
        } catch(Exception e) {
            System.out.println("Erreur Serveur / PenduImpl / implementationDeTousLesMots : " + e);
        }

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

    /**
     * Implementation de toutes les adresses des images pour l'état de la partie dans l'ArrayList listeAdresseImage
     */
    private static void implementationDesAdresseImages() {
        int i;
        for (i = 0; i <= nbEssaisTotal; i++) {
            listeAdresseImage.add("modele/pendu/image/Pendu" + i + ".png");
        }
    }

    /* ----- ----- Fonction pour le jeu ----- ----- */

    /**
     * Mise ne place d'une nouvelle partie du coté serveur.
     * @return le numéro de la partie en cours.
     * @throws RemoteException
     */
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

    /**
     * Vérifier si la lettre ce trouve dans le mot.
     * @param numPartie : indicateur permettant de récupérer tout les information de la partie ciblé.
     * @param lettre : lettre que le jouer à jouer
     * @throws RemoteException
     */
    @Override
    public void lettreTrouver(int numPartie, char lettre) throws RemoteException {
        if (listeMotJouer.get(numPartie).toLowerCase(Locale.ROOT).contains(Character.toString(lettre))) {
            listeLettreTrouver.get(numPartie).add(lettre);
        } else {
            listeNbEssais.set(numPartie, listeNbEssais.get(numPartie) + 1);
        }
    }

    /**
     * @param numPartie : indicateur permettant de récupérer tout les information de la partie ciblé.
     * @return l'affichage sur un format "_ _ X _" de type String
     * @throws RemoteException
     */
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

    /**
     * @param nbEssais
     * @return l'adresse de l'image en fonction du nbEssais
     * @throws RemoteException
     */
    @Override
    public String getAdresseImage(int nbEssais) throws RemoteException {
        return listeAdresseImage.get(nbEssais);
    }

    /**
     * @param numPartie : indicateur permettant de récupérer tout les information de la partie ciblé.
     * @return le mot à trouver en fonction de numPartie.
     * @throws RemoteException
     */
    @Override
    public String getMotJouer(int numPartie) throws RemoteException {
        return listeMotJouer.get(numPartie);
    }

    /**
     * @param numPartie : indicateur permettant de récupérer tout les information de la partie ciblé.
     * @return le nombre d'essais en fonction le numPartie.
     * @throws RemoteException
     */
    @Override
    public int getNbEssais(int numPartie) throws RemoteException {
        return listeNbEssais.get(numPartie);
    }

    /**
     * @return le nombre d'essais max possible.
     * @throws RemoteException
     */
    @Override
    public int getNbEssaisTotal() throws RemoteException {
        return nbEssaisTotal;
    }

    /**
     * Indiquant si la partie est terminer mais ne dit pas si le jouer à gagner ou pas.
     * @param numPartie : indicateur permettant de récupérer tout les information de la partie ciblé.
     * @return un booléen indiquant si la partie est terminer.
     * @throws RemoteException
     */
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

    /**
     * @return le nombre de partie jouer.
     * @throws RemoteException
     */
    @Override
    public int getNbPartiejouer() throws RemoteException {
        return listeNbEssais.size();
    }

    /**
     * @return le nombre de partie gagner.
     * @throws RemoteException
     */
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

    /**
     * @return le nombre d'essais moyenne en fonction de tout les parties.
     * @throws RemoteException
     */
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