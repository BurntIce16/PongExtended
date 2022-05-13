package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class ScoreKeeper {
    private int lives1 = 7;
    private int lives2 = 7;
    private ArrayList<Texture> hearts;

    public ScoreKeeper(){
        hearts = new ArrayList<>();
        for(int i = 0; i < 8; i++){

            //finish this
            hearts.set(i, new Texture("Ball.png"));
        }
    }


    public void removeLife(){

    }

    public void resetScores(){
        lives1 = 7;
        lives2 = 7;
    }


}
