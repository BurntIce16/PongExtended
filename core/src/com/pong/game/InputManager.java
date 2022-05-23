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

    private boolean reversed = false;


    public InputManager(PongGame p){
        pongGame = p;
        p1 = pongGame.getPaddles().get(0);
        p2 = pongGame.getPaddles().get(1);

    }

    @Override
    public boolean keyDown(int keycode) {

        //Paddle controls
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

        //start game
        if(keycode == Input.Keys.SPACE){
            //System.out.println(pongGame.getGameStageManager().getState());
            if(pongGame.getGameStageManager().getState() == pongGame.getGameStageManager().PRE_GAME){
                if(pongGame.getModManager().isEnabled()){
                    //space selects the modifier
                    pongGame.getModManager().selectCurrentMod();
                    pongGame.getModManager().dissableMenu();
                }else{
                    //space starts the ball
                    pongGame.getBalls().get(0).startPush();
                    pongGame.getGameStageManager().setCurrentState(pongGame.getGameStageManager().IN_GAME);
                }
            }

            //selection for end game
            if(pongGame.getGameStageManager().getState() == pongGame.getGameStageManager().END_GAME){
                pongGame.getLabelManager().removeText();
                pongGame.getScoreKeeper().setWinner(3);
            }
        }

        if(pongGame.getModManager().isEnabled()){
            if(keycode == Input.Keys.LEFT){
                pongGame.getModManager().moveLeft();
            }
            if(keycode == Input.Keys.RIGHT){
                pongGame.getModManager().moveRight();
            }
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
            if(!reversed){
                p2.Up();
            }else{
                p2.Down();
            }
        }
        if(downPressed){
            if(!reversed){
                p2.Down();
            }else{
                p2.Up();
            }

        }
        if(WPressed){
            if(!reversed) {
                p1.Up();
            }else{
                p1.Down();
            }
        }
        if(SPressed){
            if(!reversed) {
                p1.Down();
            }else{
                p1.Up();
            }
        }
    }

    public void setReversed(boolean r){
        reversed = r;
    }

}
