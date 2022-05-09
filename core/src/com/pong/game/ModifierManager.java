package com.pong.game;

import java.util.ArrayList;

public class ModifierManager {
    PongGame pongGame;
    private ArrayList<Modifier> mods;



    public ModifierManager(PongGame p){
        pongGame = p;
        mods = new ArrayList<>();

    }

    public void addMod(Modifier modifier){
        mods.add(modifier);
        modifier.enable();
    }

    public void displayMenu(){

    }

    public void hideMenu(){

    }


}
