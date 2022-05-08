package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.*;


import java.util.ArrayList;

public class VfxController {
    boolean run;
    VfxManager vfxManager;
    //com.crashinvaders.vfx.effects.CrtEffect
    ArrayList<ChainVfxEffect> effects;

    public VfxController(boolean r){
        run = r;
        if(run){
            //setup effect manager
            vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
            effects = new ArrayList<>();
            effects.add(new OldTvEffect());
            effects.add(new CrtEffect());


            for(ChainVfxEffect e : effects){
                vfxManager.addEffect(e);
            }
        }

    }

    public void startRender(){
        if(run){
            vfxManager.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            vfxManager.cleanUpBuffers();
            vfxManager.beginInputCapture();
        }
    }

    public void endRender(){
        if(run){
            vfxManager.endInputCapture();
            vfxManager.applyEffects();
            vfxManager.renderToScreen();
        }
    }

    public void dispose(){
        if(run){
            vfxManager.dispose();
            for(ChainVfxEffect e : effects){
                e.dispose();
            }
        }
    }
}
