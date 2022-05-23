package com.pong.game.mods;

import com.badlogic.gdx.graphics.Texture;
import com.pong.game.Ball;
import com.pong.game.Modifier;
import com.pong.game.PongGame;

public class DanBall implements Modifier {
    PongGame pongGame;
    private Texture icon;
    private Texture newBall;

    public DanBall(PongGame p){
        pongGame = p;
        config();
    }


    @Override
    public void enable() {

    }

    @Override
    public void config() {
        icon = new Texture("DanIco.png");
        newBall = new Texture("DanBall.png");
    }

    @Override
    public void update() {
        //This is really innefficent
        for(int i = pongGame.getBalls().size()-1; i >= 0; i--){
            Ball b = pongGame.getBalls().get(i);
            b.setTexture(newBall);
        }
    }

    @Override
    public Texture getIcon() {
        return icon;
    }


}
