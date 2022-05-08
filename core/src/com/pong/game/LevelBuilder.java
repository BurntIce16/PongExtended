package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class LevelBuilder {
    BarFactory topBar;
    BarFactory bottomBar;
    BarFactory leftBar;
    BarFactory rightBar;
    ArrayList<BarFactory> bars;

    public LevelBuilder(SpriteBatch b, World w){
        topBar = new BarFactory(b,w, 0, Gdx.graphics.getHeight(), "horizontal", 0,-1);
        bottomBar = new BarFactory(b,w, 0, 0, "horizontal",0,0);
        leftBar = new BarFactory(b,w, 0, 0, "vertical",0,0);
        rightBar = new BarFactory(b,w, Gdx.graphics.getWidth(), 0, "vertical",-1,0);
        bars = new ArrayList<>();
        bars.add(topBar);
        bars.add(bottomBar);
        bars.add(leftBar);
        bars.add(rightBar);
    }

    public void render(){
        for(BarFactory b : bars){
            b.render();
        }
    }

    public void dispose(){
        for(BarFactory b : bars){
            b.dispose();
        }
    }

}
