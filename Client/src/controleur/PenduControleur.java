package controleur;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pendu.PenduInterface;

import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/*
- Gérer le contenue des labels
- Gérer l'image du pendu
- . . .
 */

public class PenduControleur implements Initializable {
    private ArrayList<Button>   listeBouton;
    private PenduInterface      objPenduServ;
    private         int         numPartie;
    private static  int         nbEssaisTotal;

    @FXML
    private Label lbl_affichage;
    @FXML
    private Label lbl_messageFin;
    @FXML
    private Label lbl_nbEssais;

    @FXML
    private ImageView imgView_pendu;

    @FXML
    private Button btn_a;
    @FXML
    private Button btn_z;
    @FXML
    private Button btn_e;
    @FXML
    private Button btn_r;
    @FXML
    private Button btn_t;
    @FXML
    private Button btn_y;
    @FXML
    private Button btn_u;
    @FXML
    private Button btn_i;
    @FXML
    private Button btn_o;
    @FXML
    private Button btn_p;

    @FXML
    private Button btn_q;
    @FXML
    private Button btn_s;
    @FXML
    private Button btn_d;
    @FXML
    private Button btn_f;
    @FXML
    private Button btn_g;
    @FXML
    private Button btn_h;
    @FXML
    private Button btn_j;
    @FXML
    private Button btn_k;
    @FXML
    private Button btn_l;
    @FXML
    private Button btn_m;

    @FXML
    private Button btn_w;
    @FXML
    private Button btn_x;
    @FXML
    private Button btn_c;
    @FXML
    private Button btn_v;
    @FXML
    private Button btn_b;
    @FXML
    private Button btn_n;

    @FXML
    private Button btn_quitte;
    @FXML
    private Button btn_nouvellePartie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connexion();
            initialisationListeBouton();

            nbEssaisTotal = objPenduServ.getNbEssaisTotal();

            nouvellePartie();
        } catch (Exception e) {
            System.out.println("Erreur Client / PenduControleur / initialize : " + e);
        }
    }

    // ----- ----- Tout les fonctions intermédiaire ----- ----- //

    private void nouvellePartie() {
        int i;

        try {
            numPartie = objPenduServ.nouvellePartie();

            lbl_messageFin.setText("");
            lbl_affichage.setText(objPenduServ.getAffichage(numPartie));
            lbl_nbEssais.setText(String.valueOf(nbEssaisTotal - objPenduServ.getNbEssais(numPartie)));

            imgView_pendu.setImage(new Image(objPenduServ.getAdresseImage(0)));

            btn_quitte.setVisible(false);
            btn_nouvellePartie.setVisible(false);

            i = 0;
            while (i < listeBouton.size()) {
                listeBouton.get(i).setDisable(false);
                i++;
            }
        } catch (Exception e) {
            System.out.println("Erreur Client / PenduControleur / OnCick_nouvellePartie : " + e);
        }
    }

    private void actualiseAffichage(char lettre) {
        int nbEssais;

        try {
            objPenduServ.lettreTrouver(numPartie, lettre);
            nbEssais = objPenduServ.getNbEssais(numPartie);

            lbl_affichage.setText(objPenduServ.getAffichage(numPartie));
            lbl_nbEssais.setText(Integer.toString(nbEssaisTotal - nbEssais));
            imgView_pendu.setImage(new Image(objPenduServ.getAdresseImage(nbEssais)));

            if (objPenduServ.finPartie(numPartie)) {
                partieTerminer();
            }
        } catch (Exception e) {
            System.out.println("Erreur Client / PenduControleur / actualiseAffichage : " + e);
        }
    }

    private void partieTerminer() {
        int nbEssais;
        int i;

        try {
            nbEssais = objPenduServ.getNbEssais(numPartie);

            btn_quitte.setVisible(true);
            btn_nouvellePartie.setVisible(true);

            i = 0;
            while (i < listeBouton.size()) {
                listeBouton.get(i).setDisable(true);
                i++;
            }

            if (nbEssais >= nbEssaisTotal) {
                lbl_messageFin.setText("Vous avez perdu la partie");
            } else {
                lbl_messageFin.setText("Bravo, vous avez gagner la partie !!!");
            }
        } catch (Exception e) {
            System.out.println("Erreur Client / PenduControleur / partieTerminer : " + e);
        }
    }

    private void connexion() {
        int port;
        port = 8000;

        try {
            objPenduServ = (PenduInterface) Naming.lookup("rmi://localhost:" + port + "/pendu");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Erreur Client / PenduControleur / connexion : " + e);
        }
    }

    // ----- ----- Gestion des OnCick ----- ----- //

    @FXML
    void OnCick_nouvellePartie(ActionEvent event) {
        nouvellePartie();
    }

    @FXML
    void OnCick_Quitter(ActionEvent event) {
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter le jeu");
        alert.setHeaderText("Êtes-vous sûr de vouloir quitter ce jeu ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
        }
    }

    // ----- ----- Gestion des boutons : listeBouton et OnCick ----- ----- //

    private void initialisationListeBouton() {
        listeBouton = new ArrayList<Button>();

        listeBouton.add(btn_a);
        listeBouton.add(btn_b);
        listeBouton.add(btn_c);
        listeBouton.add(btn_d);
        listeBouton.add(btn_e);

        listeBouton.add(btn_f);
        listeBouton.add(btn_g);
        listeBouton.add(btn_h);
        listeBouton.add(btn_i);
        listeBouton.add(btn_j);

        listeBouton.add(btn_k);
        listeBouton.add(btn_l);
        listeBouton.add(btn_m);
        listeBouton.add(btn_n);
        listeBouton.add(btn_o);

        listeBouton.add(btn_p);
        listeBouton.add(btn_q);
        listeBouton.add(btn_r);
        listeBouton.add(btn_s);
        listeBouton.add(btn_t);

        listeBouton.add(btn_u);
        listeBouton.add(btn_v);
        listeBouton.add(btn_w);
        listeBouton.add(btn_x);
        listeBouton.add(btn_y);

        listeBouton.add(btn_z);
    }

    @FXML
    void OnCick_boutonA(ActionEvent event) {
        actualiseAffichage('a');
        btn_a.setDisable(true);
    }

    @FXML
    void OnCick_boutonB(ActionEvent event) {
        actualiseAffichage('b');
        btn_b.setDisable(true);
    }

    @FXML
    void OnCick_boutonC(ActionEvent event) {
        actualiseAffichage('c');
        btn_c.setDisable(true);
    }

    @FXML
    void OnCick_boutonD(ActionEvent event) {
        actualiseAffichage('d');
        btn_d.setDisable(true);
    }

    @FXML
    void OnCick_boutonE(ActionEvent event) {
        actualiseAffichage('e');
        btn_e.setDisable(true);
    }

    @FXML
    void OnCick_boutonF(ActionEvent event) {
        actualiseAffichage('f');
        btn_f.setDisable(true);
    }

    @FXML
    void OnCick_boutonG(ActionEvent event) {
        actualiseAffichage('g');
        btn_g.setDisable(true);
    }

    @FXML
    void OnCick_boutonH(ActionEvent event) {
        actualiseAffichage('h');
        btn_h.setDisable(true);
    }

    @FXML
    void OnCick_boutonI(ActionEvent event) {
        actualiseAffichage('i');
        btn_i.setDisable(true);
    }

    @FXML
    void OnCick_boutonJ(ActionEvent event) {
        actualiseAffichage('j');
        btn_j.setDisable(true);
    }

    @FXML
    void OnCick_boutonK(ActionEvent event) {
        actualiseAffichage('k');
        btn_k.setDisable(true);
    }

    @FXML
    void OnCick_boutonL(ActionEvent event) {
        actualiseAffichage('l');
        btn_l.setDisable(true);
    }

    @FXML
    void OnCick_boutonM(ActionEvent event) {
        actualiseAffichage('m');
        btn_m.setDisable(true);
    }

    @FXML
    void OnCick_boutonN(ActionEvent event) {
        actualiseAffichage('n');
        btn_n.setDisable(true);
    }

    @FXML
    void OnCick_boutonO(ActionEvent event) {
        actualiseAffichage('o');
        btn_o.setDisable(true);
    }

    @FXML
    void OnCick_boutonP(ActionEvent event) {
        actualiseAffichage('p');
        btn_p.setDisable(true);
    }

    @FXML
    void OnCick_boutonQ(ActionEvent event) {
        actualiseAffichage('q');
        btn_q.setDisable(true);
    }

    @FXML
    void OnCick_boutonR(ActionEvent event) {
        actualiseAffichage('r');
        btn_r.setDisable(true);
    }

    @FXML
    void OnCick_boutonS(ActionEvent event) {
        actualiseAffichage('s');
        btn_s.setDisable(true);
    }

    @FXML
    void OnCick_boutonT(ActionEvent event) {
        actualiseAffichage('t');
        btn_t.setDisable(true);
    }

    @FXML
    void OnCick_boutonU(ActionEvent event) {
        actualiseAffichage('u');
        btn_u.setDisable(true);
    }

    @FXML
    void OnCick_boutonV(ActionEvent event) {
        actualiseAffichage('v');
        btn_v.setDisable(true);
    }

    @FXML
    void OnCick_boutonW(ActionEvent event) {
        actualiseAffichage('w');
        btn_w.setDisable(true);
    }

    @FXML
    void OnCick_boutonX(ActionEvent event) {
        actualiseAffichage('x');
        btn_x.setDisable(true);
    }

    @FXML
    void OnCick_boutonY(ActionEvent event) {
        actualiseAffichage('y');
        btn_y.setDisable(true);
    }

    @FXML
    void OnCick_boutonZ(ActionEvent event) {
        actualiseAffichage('z');
        btn_z.setDisable(true);
    }
}
