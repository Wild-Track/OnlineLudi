package othello;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class OthelloImplemente extends UnicastRemoteObject implements OthelloInterface
{
    private final int TAILLE = 8;
    // SimpleEntry pour mettre l'othellier avec un int pour indiquer si la partie est pleine ou non
    private HashMap<Integer, Partie> parties;

    public OthelloImplemente(HashMap<Integer, Partie> parties) throws RemoteException
    {
        this.parties = parties;
    }

    @Override
    public int creerPartieServeur() throws RemoteException
    {
        int numeroPartie = parties.size();
        parties.put(numeroPartie, new Partie());

        return numeroPartie;
    }

    @Override
    public boolean rejoindrePartieServeur(int numeroPartie) throws RemoteException
    {
        if (!parties.containsKey(numeroPartie) || parties.get(numeroPartie).getPartieComplete() == 1)
        {
            return false;
        }

        parties.get(numeroPartie).setPartieComplete(1);


        System.out.println(parties.get(numeroPartie).getPartieComplete());
        return true;
    }

    @Override
    public void supprimerPartieServeur(int numeroPartie) throws RemoteException
    {
        if (!this.parties.containsKey(numeroPartie))
        {
            this.parties.remove(numeroPartie);
        }

        return;
    }




    public boolean verificationCoupRangee(Couleur[][] othellier, int ligne, int colonne, int ligneDirection, int colonneDirection, Couleur couleurJoueur)
    {
        colonne = +colonneDirection;
        ligne = +ligneDirection;

        if (0 >= colonne || colonne >= 8 || 0 >= ligne || ligne >= 8)
        {
            return false;
        }

        Couleur couleurEnnemi = couleurJoueur.getCouleurEnnemi();

        while (othellier[ligne][colonne] == couleurEnnemi)
        {
            ligne += ligneDirection;
            colonne += colonneDirection;
            if (0 > colonne || colonne > 8 || 0 > ligne || ligne > 8)
            {
                return false;
            }
            if (othellier[ligne][colonne] == couleurEnnemi)
            {
                return true;
            }
        }
        return false;
    }

    private boolean calculCoupRangee(Couleur[][] othellier, int ligne, int colonne, int ligneDirection, int colonneDirection, Couleur couleurJoueur)
    {
        Couleur couleurEnnemi = couleurJoueur.getCouleurEnnemi();

        if (verificationCoupRangee(othellier, ligne, colonne, ligneDirection, colonneDirection, couleurJoueur))
        {
            while (othellier[ligne][colonne] == couleurEnnemi)
            {
                ligne += ligneDirection;
                colonne += colonneDirection;
                if (0 > colonne || colonne > 8 || 0 > ligne || ligne > 8)
                {
                    return false;
                }
                if (othellier[ligne][colonne] == couleurJoueur)
                {
                    return true;
                }

                othellier[ligne][colonne] = couleurJoueur;
            }
        }
        return false;
    }

    private boolean calculCoup(Couleur[][] othellier, int ligne, int colonne, Couleur couleurJoueur)
    {

        return  calculCoupRangee(othellier, ligne, colonne, 1, 0, couleurJoueur) ||
                calculCoupRangee(othellier, ligne, colonne, 1, 1, couleurJoueur) ||
                calculCoupRangee(othellier, ligne, colonne, 0, 1, couleurJoueur) ||
                calculCoupRangee(othellier, ligne, colonne, -1, 1, couleurJoueur) ||
                calculCoupRangee(othellier, ligne, colonne, -1, 0, couleurJoueur) ||
                calculCoupRangee(othellier, ligne, colonne, -1, -1, couleurJoueur) ||
                calculCoupRangee(othellier, ligne, colonne, 0, -1, couleurJoueur) ||
                calculCoupRangee(othellier, ligne, colonne, 1, -1, couleurJoueur);
    }

    @Override
    public boolean jouerCoup(int numeroPartie, int ligne, int colonne, Couleur couleurJoueur, Couleur[][] othellier) throws RemoteException
    {
        Partie partie = parties.get(numeroPartie);

        if (ligne == -1 && colonne == -1)
        {
            partie.setPartieComplete(partie.getTourPasser() + 1);
        }
        else
        {
            calculCoup(partie.getOthellier(), ligne, colonne, couleurJoueur);
        }

        synchronized (partie.getSynchronisation())
        {
            partie.getSynchronisation().notifyAll();
        }
        othellier = partie.getOthellier();

        return finPartie(partie);
    }

    @Override
    public boolean miseAJour(int numeroPartie, Couleur[][] othellier) throws InterruptedException, RemoteException
    {
        Partie partie = parties.get(numeroPartie);
        synchronized (partie.getSynchronisation())
        {
            partie.getSynchronisation().wait();
        }

        othellier = partie.getOthellier();

        return finPartie(partie);
    }

    @Override
    public Couleur gagnant(int numeroPartie)  throws RemoteException
    {
        int couleurBlanc = 0;
        int couleurNoir = 0;

        Partie partie = parties.get(numeroPartie);

        for (Couleur[] ligne : partie.getOthellier())
        {
            for (Couleur couleur : ligne)
            {
                if (couleur == Couleur.BLANC)
                {
                    couleurBlanc++;
                }
                else if (couleur == Couleur.NOIR)
                {
                    couleurNoir++;
                }
            }
        }
        if (couleurBlanc > couleurNoir)
        {
            return Couleur.BLANC;
        }
        else if (couleurBlanc < couleurNoir)
        {
            return Couleur.NOIR;
        }
        else
        {
            return Couleur.VIDE;
        }
    }

    private boolean finPartie(Partie partie)
    {
        for (Couleur[] ligne : partie.getOthellier())
        {
            for (Couleur couleur : ligne)
            {
                if (couleur == Couleur.VIDE)
                {
                    return false;
                }
            }
        }

        if (partie.getTourPasser() != 2)
        {
            return false;
        }
        return true;
    }
}
