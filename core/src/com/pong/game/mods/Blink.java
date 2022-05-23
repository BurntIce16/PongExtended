package com.pong.game.mods;

import com.badlogic.gdx.graphics.Texture;
import com.pong.game.Ball;
import com.pong.game.Modifier;
import com.pong.game.PongGame;

//Make the ball dissapear and reapear frequently

public class Blink implements Modifier {
    PongGame pongGame;
    private final int minFrames = 30;
    private Texture icon;

    public Blink(PongGame p){
        pongGame = p;
        config();
    }

    @Override
    public void enable() {

    }

    @Override
    public void config() {
        icon = new Texture("Blink.png");
    }

    @Override
    public void update() {
        for(int i = pongGame.getBalls().size()-1; i >= 0; i--){
            Ball b = pongGame.getBalls().get(i);
            if(b.getAlpha() == 1f && b.getFrameCounter() >= minFrames){
                b.setAlpha(0f);
                b.setFrameCounter(0);
            }
            if(b.getAlpha() == 0 && b.getFrameCounter() >= minFrames){
                b.setAlpha(1f);
                b.setFrameCounter(0);
            }
        }
    }

    @Override
    public Texture getIcon() {
        return icon;
    }
}
