package com.pong.game.mods;

import com.badlogic.gdx.graphics.Texture;
import com.pong.game.Modifier;
import com.pong.game.PongGame;

public class Reverse implements Modifier {
    PongGame pongGame;
    private Texture icon;

    public Reverse(PongGame p){
        pongGame = p;
        config();
    }

    @Override
    public void enable() {
        pongGame.getInputManager().setReversed(true);
    }

    @Override
    public void config() {
        icon = new Texture("Reverse.png");
    }

    @Override
    public void update() {

    }

    @Override
    public Texture getIcon() {
        return icon;
    }
}
