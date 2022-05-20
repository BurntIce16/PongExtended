package com.pong.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor {


    private PongGame pongGame;
    private Playerpaddle p1;
    private Playerpaddle p2;



    //messy stuff
    private boolean upPressed;
    private boolean WPressed;
    private boolean SPressed;
    private boolean downPressed;


    public InputManager(PongGame p){
        pongGame = p;
        p1 = pongGame.getPaddles().get(0);
        p2 = pongGame.getPaddles().get(1);

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.W){
            WPressed = true;
        }
        if(keycode == Input.Keys.UP){
            upPressed = true;
        }
        if(keycode == Input.Keys.S){
            SPressed = true;
        }
        if(keycode == Input.Keys.DOWN){
            downPressed = true;
        }

        //keep this
        return false;

    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.W){
            WPressed = false;
        }
        if(keycode == Input.Keys.UP){
            upPressed = false;
        }
        if(keycode == Input.Keys.S){
            SPressed = false;
        }
        if(keycode == Input.Keys.DOWN){
            downPressed = false;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void pollInputs(){
        if(upPressed){
            p2.Up();
        }
        if(downPressed){
            p2.Down();
        }
        if(WPressed){
            p1.Up();
        }
        if(SPressed){
            p1.Down();
        }
    }

}
