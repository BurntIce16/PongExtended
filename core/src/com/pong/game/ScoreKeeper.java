package com.pong.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class ScoreKeeper {
    private int lives1 = 7;
    private int lives2 = 7;
    private ArrayList<Texture> hearts;
    private Sprite sprite1;
    private Sprite sprite2;
    PongGame pongGame;

    public ScoreKeeper(PongGame pg){
        pongGame = pg;
        hearts = new ArrayList<>();
        for(int i = 1; i < 8; i++){
            //finish this
            hearts.add(new Texture("heart-" + i + ".png"));
        }

        sprite1 = new Sprite(hearts.get(lives1-1));
        sprite2 = new Sprite(hearts.get(lives2-1));
        sprite1.setPosition(100,200);
    }


    public void render(){
        sprite1.draw(pongGame.getBatch());
        sprite2.draw(pongGame.getBatch());

    }



    public void removeLife(int side){
        if(side == 1){
            if(lives1 > 1){
                lives1--;
            }else{
                resetScores();
            }
        }else if(side == 2){
            if(lives2 > 1){
                lives2--;
            }else{
                resetScores();
            }
        }else{
            System.out.println("Invalid player input");
        }
        updateSprites();
    }

    public void updateSprites(){
        sprite1.setTexture(hearts.get(lives1-1));
        sprite1.setSize(hearts.get(lives1-1).getWidth(), hearts.get(lives1-1).getHeight());
        sprite2.setTexture(hearts.get(lives2-1));
        sprite2.setSize(hearts.get(lives2-1).getWidth(), hearts.get(lives2-1).getHeight());

    }

    public void resetScores(){
        lives1 = 7;
        lives2 = 7;
    }

    public void dispose(){
        for(Texture t: hearts){
            t.dispose();
        }
    }


}
