package com.example.tp1;

import android.widget.ImageButton;
import java.util.ArrayList;

public class Jeu {

    private int score = 0;
    private int meilleurScore = 0;
    private int niveau = 1;

    /**
     * Méthode servant à déterminer si le joueur est gagnant
     *
     **/
    public boolean estGagnant(ArrayList<Integer> sequenceJoueur, ArrayList<Integer> sequenceOrdi) {
        if (score > meilleurScore) {
            meilleurScore = score;
        }

        if(sequenceJoueur.equals(sequenceOrdi)) {
            score += 1;
            niveau += 1;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Méthode servant à déterminer si le joueur est perdant
     *
     **/
    public boolean estPerdant(ArrayList<Integer> sequenceJoueur, ArrayList<Integer> sequenceOrdi) {
        for (int i = 0; i < sequenceJoueur.size(); i++) {
            if (!sequenceJoueur.get(i).equals(sequenceOrdi.get(i))) {
                score = 0;
                niveau = 1;
                return true;
            }
        }
        return false;
    }

    public int getScore() {
        return score;
    }

    public int getMeilleurScore() {
        return meilleurScore;
    }

    public int getNiveau() {
        return niveau;
    }
}
