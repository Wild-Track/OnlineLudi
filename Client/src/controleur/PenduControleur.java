package controleur;

import modele.pendu.TablePendu;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import pendu.PenduInterface;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PenduControleur implements Initializable {
    private ArrayList<Button>   listeBoutonLettre;
    private PenduInterface      objPenduServ;
    private         int         numPartie;
    private static  int         nbEssaisTotal;

    /* ----- ----- Appel de tout les variables pour le jeu du pendu ----- ----- */

    @FXML
    private Pane pane_pendu;

    @FXML
    private Label lbl_reponse;
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
    private Button btn_state;
    @FXML
    private Button btn_nouvellePartiePendu;

    /* ----- ----- Appel de tout les variables pour le jeu du pendu ----- ----- */

    @FXML
    private Pane pane_state;

    @FXML
    private Label lbl_nbPartieJouer;
    @FXML
    private Label lbl_nbPartieG;
    @FXML
    private Label lbl_nbPartieP;
    @FXML
    private Label lbl_ratio;
    @FXML
    private Label lbl_nbEssaisMoy;

    @FXML
    private TableView<TablePendu> tbl_parties;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connexion();
            initialisationListeBouton();

            nbEssaisTotal = objPenduServ.getNbEssaisTotal();

            initialisationTable();

            pane_pendu.setVisible(true);
            pane_state.setVisible(false);

            nouvellePartie();
        } catch (Exception e) {
            System.out.println("Erreur Client / PenduControleur / initialize : " + e);
        }
    }

    // ----- ----- Tout les fonctions intermédiaire pour le jeu du pendu ----- ----- //

    private void nouvellePartie() {
        int i;

        try {
            numPartie = objPenduServ.nouvellePartie();

            lbl_reponse.setText("");
            lbl_messageFin.setText("");
            lbl_affichage.setText(objPenduServ.getAffichage(numPartie));
            lbl_nbEssais.setText(String.valueOf(nbEssaisTotal - objPenduServ.getNbEssais(numPartie)));

            imgView_pendu.setImage(new Image(objPenduServ.getAdresseImage(0)));

            btn_state.setVisible(false);
            btn_nouvellePartiePendu.setVisible(false);

            i = 0;
            while (i < listeBoutonLettre.size()) {
                listeBoutonLettre.get(i).setDisable(false);
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

            btn_state.setVisible(true);
            btn_nouvellePartiePendu.setVisible(true);

            i = 0;
            while (i < listeBoutonLettre.size()) {
                listeBoutonLettre.get(i).setDisable(true);
                i++;
            }

            if (nbEssais >= nbEssaisTotal) {
                lbl_messageFin.setText("Vous avez perdu la partie");
                lbl_reponse.setText(objPenduServ.getMot(numPartie));
            } else {
                lbl_messageFin.setText("Bravo, vous avez gagner la partie !!!");
            }
        } catch (Exception e) {
            System.out.println("Erreur Client / PenduControleur / partieTerminer : " + e);
        }
    }

    private void connexion() {
        int port = 8000;

        try {
            objPenduServ = (PenduInterface) Naming.lookup("rmi://localhost:" + port + "/pendu");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            System.out.println("Erreur Client / PenduControleur / connexion : " + e);
        }
    }

    // ----- ----- Gestion de tous les OnCick pour le jeu du pendu ----- ----- //

    @FXML
    void OnCick_State(ActionEvent event) {
        try {
            pane_state.setVisible(true);
            pane_pendu.setVisible(false);

            miseEnPlaceDesStates();
        } catch (Exception e) {
            System.out.println("Erreur Client / PenduControleur / OnCick_State : " + e);
        }
    }

    @FXML
    void OnCick_NouvellePartiePendu(ActionEvent event) {
        nouvellePartie();
    }

    // ----- ----- Tout les fonctions intermédiaire pour les states ----- ----- //

    private void miseEnPlaceDesStates() {
        int nbPartieJouer;
        int nbPartieGagnee;
        int nbPartiePerdu;

        try {
            nbPartieJouer = objPenduServ.getNbPartiejouer();
            nbPartieGagnee = objPenduServ.getNbPartieGagnee();
            nbPartiePerdu = nbPartieJouer - nbPartieGagnee;

            lbl_nbPartieJouer.setText(String.valueOf(nbPartieJouer));
            lbl_nbPartieG.setText(String.valueOf(nbPartieGagnee));
            lbl_nbPartieP.setText(String.valueOf(nbPartiePerdu));
            lbl_nbEssaisMoy.setText(String.valueOf(objPenduServ.getNbEssaisMoyen()));
            if (nbPartiePerdu == 0) {
                lbl_ratio.setText(String.valueOf((float) nbPartieGagnee));
            } else {
                lbl_ratio.setText(String.valueOf((float) nbPartieGagnee / nbPartiePerdu));
            }

            miseEnPlaceDuTableau();
        } catch (Exception e) {
            System.out.println("Erreur Client / PenduControleur / miseEnPlaceState : " + e);
        }
    }

    private void miseEnPlaceDuTableau() {
        TablePendu ligneTable;

        try {
            ligneTable = new TablePendu();

            ligneTable.setNumPartie(numPartie + 1);
            ligneTable.setMotJouer(objPenduServ.getMot(numPartie));
            ligneTable.setMotTrouver(objPenduServ.getAffichage(numPartie));
            ligneTable.setNbEssais(objPenduServ.getNbEssais(numPartie));

            tbl_parties.getItems().setAll(ligneTable);
        } catch (Exception e) {
            System.out.println("Erreur Client / PenduControleur / miseEnPlaceDuTableau : " + e);
        }
    }

    private void initialisationTable() {
        TableColumn<TablePendu, Integer> colNumPartie = new TableColumn<>("Num partie");
        colNumPartie.setCellValueFactory(new PropertyValueFactory<TablePendu, Integer>("numPartie"));
        colNumPartie.setPrefWidth(75);

        TableColumn<TablePendu, Integer> colMotJouer = new TableColumn<>("Mot jouer");
        colMotJouer.setCellValueFactory(new PropertyValueFactory<TablePendu, Integer>("motJouer"));
        colMotJouer.setPrefWidth(175);

        TableColumn<TablePendu, Integer> colMotTrouver = new TableColumn<>("Mot trouver");
        colMotTrouver.setCellValueFactory(new PropertyValueFactory<TablePendu, Integer>("motTrouver"));
        colMotTrouver.setPrefWidth(175);

        TableColumn<TablePendu, Integer> colNbEssais = new TableColumn<>("Nb essais");
        colNbEssais.setCellValueFactory(new PropertyValueFactory<TablePendu, Integer>("nbEssais"));
        colNbEssais.setPrefWidth(75);

        this.tbl_parties.getColumns().setAll(colNumPartie, colMotJouer, colMotTrouver, colNbEssais);
    }

    // ----- ----- Gestion de tous les OnCick pour les states ----- ----- //

    @FXML
    void OnCick_NouvellePartieState(ActionEvent event) {
        pane_pendu.setVisible(true);
        pane_state.setVisible(false);
        nouvellePartie();
    }

    // ----- ----- Gestion des boutons pour le jeu du pendu : listeBouton et OnCick ----- ----- //

    private void initialisationListeBouton() {
        listeBoutonLettre = new ArrayList<Button>();

        listeBoutonLettre.add(btn_a);
        listeBoutonLettre.add(btn_b);
        listeBoutonLettre.add(btn_c);
        listeBoutonLettre.add(btn_d);
        listeBoutonLettre.add(btn_e);

        listeBoutonLettre.add(btn_f);
        listeBoutonLettre.add(btn_g);
        listeBoutonLettre.add(btn_h);
        listeBoutonLettre.add(btn_i);
        listeBoutonLettre.add(btn_j);

        listeBoutonLettre.add(btn_k);
        listeBoutonLettre.add(btn_l);
        listeBoutonLettre.add(btn_m);
        listeBoutonLettre.add(btn_n);
        listeBoutonLettre.add(btn_o);

        listeBoutonLettre.add(btn_p);
        listeBoutonLettre.add(btn_q);
        listeBoutonLettre.add(btn_r);
        listeBoutonLettre.add(btn_s);
        listeBoutonLettre.add(btn_t);

        listeBoutonLettre.add(btn_u);
        listeBoutonLettre.add(btn_v);
        listeBoutonLettre.add(btn_w);
        listeBoutonLettre.add(btn_x);
        listeBoutonLettre.add(btn_y);

        listeBoutonLettre.add(btn_z);
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
