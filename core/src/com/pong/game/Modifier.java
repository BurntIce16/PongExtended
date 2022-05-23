package com.pong.game;

import com.badlogic.gdx.graphics.Texture;

public interface Modifier {


    void enable();

    void config();

    void update();

    Texture getIcon();

}
