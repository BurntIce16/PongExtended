package com.pong.game.mods;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.pong.game.Ball;
import com.pong.game.Modifier;
import com.pong.game.PongGame;

public class Mitosis implements Modifier {

    //setup & setup methods
    Texture img;
    Sprite sprite;
    PongGame pongGame;

    public Mitosis(PongGame p){
        pongGame = p;
        config();
    }


    @Override
    public void config() {
        //assign texture
        img = new Texture("Ball.png");
        sprite = new Sprite(img);
        System.out.println("1 mitosis modifier was created");


    }


    //Methods for window
    @Override
    public void showSelf(int pos) {

    }

    @Override
    public void hideSelf() {

    }


    //Modifier Methods
    @Override
    public void enable() {
        Ball ball = new Ball(pongGame, 0);
        pongGame.addBall(ball);
        ball.setVelocity(pongGame.getBalls().get(0).getVelocity());
        ball.setPos(ball.getPos());

    }




}
