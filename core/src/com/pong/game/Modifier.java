package com.pong.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public interface Modifier {

    void enable();

    void config();

    void showSelf(int pos);

    void hideSelf();

}
