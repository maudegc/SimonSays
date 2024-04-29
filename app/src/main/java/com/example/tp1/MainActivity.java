package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Classe main permettant au jeu de fonctionner, appelle les méthodes de Jeu
 *
 **/
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Jeu jeu;

    private int stadePartie = 0;

    private final ImageButton[] boutonCouleur = new ImageButton[4];
    private Button boutonSimon;
    private ArrayList<Integer> sequenceJoueur = new ArrayList<>();
    private ArrayList<Integer> sequenceOrdi = new ArrayList<>();

    private TextView score;
    private TextView meilleurScore;
    private TextView niveau;
    private TextView texteSimon;
    private TextView scoreInt;
    private TextView meilleurScoreInt;
    private TextView niveauInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jeu = new Jeu();

        boutonCouleur[0] = findViewById(R.id.triangleRouge);
        boutonCouleur[1] = findViewById(R.id.triangleBleu);
        boutonCouleur[2] = findViewById(R.id.triangleJaune);
        boutonCouleur[3] = findViewById(R.id.triangleVert);

        boutonSimon = findViewById(R.id.buttonSimon);

        score = findViewById(R.id.score);
        meilleurScore = findViewById(R.id.meilleurScore);
        niveau = findViewById(R.id.niveau);
        texteSimon = findViewById(R.id.simon);
        scoreInt = findViewById(R.id.scoreInt);
        meilleurScoreInt = findViewById(R.id.meilleurScoreInt);
        niveauInt = findViewById(R.id.niveauInt);

        boutonSimon.setOnClickListener(v -> onClickBoutonCommencer());
    }

    @Override
    public void onClick(View v) {
        if (stadePartie == 1) {
            if (v.equals(boutonCouleur[0])) {
                sequenceJoueur.add(0);
                animerCouleur(0);
            } else if (v.equals(boutonCouleur[1])) {
                sequenceJoueur.add(1);
                animerCouleur(1);
            } else if (v.equals(boutonCouleur[2])) {
                sequenceJoueur.add(2);
                animerCouleur(2);
            } else if (v.equals(boutonCouleur[3])) {
                sequenceJoueur.add(3);
                animerCouleur(3);
            }

            if (jeu.estGagnant(sequenceJoueur, sequenceOrdi)) {
                continuer();
            } else if (jeu.estPerdant(sequenceJoueur, sequenceOrdi)) {
                recommencer();
            }
        }
    }

    /**
     * Méthode servant à animer le bouton quand il est sélectionnée
     *
     **/
    public void animerCouleur (int couleur) {
        boutonCouleur[couleur].setAlpha(0f);
        boutonCouleur[couleur].animate().alpha(1f).setDuration(1000);
    }

    /**
     * Méthode servant à montrer la séquence de Simon une fois que la partie est commencée
     *
     **/
    public void onClickBoutonCommencer() {
        if (stadePartie == 0) {
            boutonSimon.setText("SIMON");

            int couleur = (int) Math.round((Math.random() * (boutonCouleur.length-1)));
            sequenceOrdi.add(couleur);

            new CountDownTimer(sequenceOrdi.size() * 1000,1000) {
                int positionCouleur = 0;

                @Override
                public void onTick(long millisUntilFinished) {
                    désactiverBoutons();
                    int timer = sequenceOrdi.get(positionCouleur);
                    animerCouleur(timer);

                    positionCouleur ++;
                }
                @Override
                public void onFinish()
                {
                    activerBoutons();
                }
            } .start();

            score.setText(score.getText());
            scoreInt.setText(String.valueOf(jeu.getScore()));
            meilleurScore.setText(meilleurScore.getText());
            meilleurScoreInt.setText(String.valueOf(jeu.getMeilleurScore()));
            niveau.setText(niveau.getText());
            niveauInt.setText(String.valueOf(jeu.getNiveau()));

            stadePartie = 1;
        }
    }

    /**
     * Méthode servant à recommencer la partie si le joueur perd
     *
     **/
    public void recommencer() {
        texteSimon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f);
        texteSimon.setText(getString(R.string.finDePartie));
        boutonSimon.setText(getString(R.string.recommencer));

        sequenceJoueur.clear();
        sequenceOrdi.clear();

        stadePartie = 0;
    }

    /**
     * Méthode servant à continuer la partie si le joueur gagne
     *
     **/
    public void continuer() {
        texteSimon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25f);
        texteSimon.setText(getString(R.string.felicitaion));
        boutonSimon.setText(getString(R.string.continuer));

        sequenceJoueur.clear();

        stadePartie = 0;
    }

    public void activerBoutons() {
        boutonCouleur[0].setEnabled(true);
        boutonCouleur[1].setEnabled(true);
        boutonCouleur[2].setEnabled(true);
        boutonCouleur[3].setEnabled(true);
    }

    public void désactiverBoutons() {
        boutonCouleur[0].setEnabled(false);
        boutonCouleur[1].setEnabled(false);
        boutonCouleur[2].setEnabled(false);
        boutonCouleur[3].setEnabled(false);
    }
}