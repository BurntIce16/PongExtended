package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pong.game.mods.Blink;
import com.pong.game.mods.DanBall;
import com.pong.game.mods.Mitosis;
import com.pong.game.mods.Reverse;

import java.util.ArrayList;
import java.util.Collections;

public class ModifierManager {
    PongGame pongGame;
    private ArrayList<Modifier> mods;
    private Texture noSelection;
    private Texture leftBox;
    private Texture middleBox;
    private Texture rightBox;
    private Sprite sprite;
    private int selection = 1;
    private final float vertOffset = 2f;
    private Sprite mod1;
    private Sprite mod2;
    private Sprite mod3;
    private ArrayList<Modifier> modPool;


    private boolean isEnabled = false;

    public ModifierManager(PongGame p){
        pongGame = p;
        mods = new ArrayList<>();

        //setup Mod Pool
        //This needs to be updated with new mods eventually
        modPool = new ArrayList<>();
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Blink(pongGame));
        modPool.add(new Reverse(pongGame));
        modPool.add(new DanBall(pongGame));
        /*
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));
        modPool.add(new Mitosis(pongGame));

         */

        Collections.shuffle(modPool);


        //setup modifier selector box
        noSelection = new Texture("SelectionBox.png");
        leftBox = new Texture("SelectionBoxLeft.png");
        middleBox = new Texture("SelectionBoxMiddle.png");
        rightBox = new Texture("SelectionBoxRight.png");
        sprite = new Sprite(leftBox);
        sprite.setSize(sprite.getWidth()/pongGame.scaler, sprite.getHeight()/pongGame.scaler);

        sprite.setPosition(((float) Gdx.graphics.getWidth()/pongGame.scaler)/2f - (sprite.getWidth()/2f),
                ((float) Gdx.graphics.getHeight()/pongGame.scaler)/2f - ((sprite.getHeight()/2f) + vertOffset));


        //setup modifier sprites
        //Didn't have time for math, so there are sketchy hard set numbers
        mod1 = new Sprite(modPool.get(0).getIcon());
        mod1.setSize(mod1.getWidth()/pongGame.scaler, mod1.getHeight()/pongGame.scaler);
        mod1.setPosition(sprite.getX() + .6f,((float) Gdx.graphics.getHeight()/pongGame.scaler)/2f - ((sprite.getHeight()/2f) + vertOffset) +.65f);
        mod2 = new Sprite(modPool.get(1).getIcon());
        mod2.setSize(mod2.getWidth()/pongGame.scaler, mod2.getHeight()/pongGame.scaler);
        mod2.setPosition(sprite.getX() + 3.1f,((float) Gdx.graphics.getHeight()/pongGame.scaler)/2f - ((sprite.getHeight()/2f) + vertOffset) +.65f);
        mod3 = new Sprite(modPool.get(2).getIcon());
        mod3.setSize(mod3.getWidth()/pongGame.scaler, mod3.getHeight()/pongGame.scaler);
        mod3.setPosition(sprite.getX() + 5.5f,((float) Gdx.graphics.getHeight()/pongGame.scaler)/2f - ((sprite.getHeight()/2f) + vertOffset) +.65f);

        hideMenu();

    }

    public void render(){
        sprite.draw(pongGame.getBatch());
        mod1.draw(pongGame.getBatch());
        mod2.draw(pongGame.getBatch());
        mod3.draw(pongGame.getBatch());
    }

    public void dispose(){
        noSelection.dispose();
        leftBox.dispose();
        middleBox.dispose();
        rightBox.dispose();
    }

    public void addMod(Modifier modifier){
        mods.add(modifier);
        System.out.println("Added a mod");
        modifier.enable();
    }

    private void showMenu(){
        updateSprite();
        sprite.setAlpha(1f);
        mod1.setAlpha(1f);
        mod2.setAlpha(1f);
        mod3.setAlpha(1f);
    }

    private void hideMenu(){
        sprite.setAlpha(0f);
        mod1.setAlpha(0f);
        mod2.setAlpha(0f);
        mod3.setAlpha(0f);
    }

    public void moveLeft(){
        if(selection > 0){
            selection--;
        }
        updateSprite();
    }
    public void moveRight(){
        if(selection < 2){
            selection++;
        }
        updateSprite();
    }

    public void updateSprite(){
        if(selection == 0){
            sprite.setTexture(leftBox);
        }else if(selection == 1){
            sprite.setTexture(middleBox);
        }else if(selection == 2){
            sprite.setTexture(rightBox);
        }

    }

    public void updatemodSprites(){
        mod1.setTexture(modPool.get(0).getIcon());
        mod1.setSize(mod1.getWidth(), mod1.getHeight());
        mod2.setTexture(modPool.get(1).getIcon());
        mod2.setSize(mod2.getWidth(), mod2.getHeight());
        mod3.setTexture(modPool.get(2).getIcon());
        mod3.setSize(mod3.getWidth(), mod3.getHeight());
    }

    public void enableMenu(){
        isEnabled = true;
        selection = 1;
        Collections.shuffle(modPool);
        updatemodSprites();
        showMenu();

    }
    public void dissableMenu(){
        isEnabled = false;
        hideMenu();
    }

    public void selectCurrentMod(){
        addMod(modPool.get(selection));
        if(modPool.size()>3){
            modPool.remove(selection);
        }
    }

    public boolean isEnabled(){
        return isEnabled;
    }

    public void updateMods(){
        for(Modifier m : mods){
            m.update();
        }
    }

    public void clearMods(){
        modPool.addAll(mods);
        mods.clear();
        pongGame.getInputManager().setReversed(false);
    }



}
