package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rafaskoberg.gdx.typinglabel.TypingLabel;

import java.util.ArrayList;

public class LabelManager {
    private PongGame pongGame;
    Stage stage;
    Skin skin;
    ArrayList<TypingLabel> labels;

    public LabelManager(PongGame p){
        pongGame = p;
        stage = new Stage();
        //skin = new Skin(Gdx.files.internal("skins/arcade/skin/arcade-ui.json"));
        //skin = new Skin(Gdx.files.internal("skins/commodore64/skin/uiskin.json"));

        //I like this one
        skin = new Skin(Gdx.files.internal("skins/vhs/skin/vhs-ui.json"));
        labels = new ArrayList<>();


    }

    public void makeLabel(String text){
        text = "{COLOR=WHITE}{SLOWER}" + text;
        TypingLabel label = new TypingLabel(text, skin);
        labels.add(label);
        label.setFontScale(5f);
        label.setPosition((float) (Gdx.graphics.getWidth()/2) - (label.getWidth()/2),(float) (Gdx.graphics.getHeight()/2) + (label.getHeight()/2));
        stage.addActor(label);
    }

    public void draw(){
        stage.act();
        stage.draw();

    }

    public void dispose(){
        removeText();
        skin.dispose();
        stage.dispose();
    }

    public void removeText(){
        for(TypingLabel t : labels){
            t.remove();
        }
    }
}
