package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.rafaskoberg.gdx.typinglabel.TypingAdapter;
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
        skin = new Skin(Gdx.files.internal("skins/vhs/skin/vhs-ui.json"));
        labels = new ArrayList<>();


    }

    public void makeLabel(String text){
        TypingLabel label = new TypingLabel(text, skin);
        labels.add(label);
        label.setFontScale(2.1f);
        label.setWrap(true);
        label.setAlignment(1);
        label.setPosition((float) (Gdx.graphics.getWidth()/2) - (label.getWidth()/2),(float) (Gdx.graphics.getHeight()/2) + (label.getHeight()/2));
        stage.addActor(label);

        label.setTypingListener(new TypingAdapter() {
            public void end () {
                System.out.println("This is called when the text reaches the end.");
            }
        });

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
