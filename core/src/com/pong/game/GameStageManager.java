package com.pong.game;

public class GameStageManager {

    /*
    There are 4 states
    Pre game: Display pregame text and randomize ball position on button press
    In game: The ball is in movement and players are activly controlling the paddles
    Scored: A life is subtracted and the modifier manager appears
    Loop back to Pre Game:
    End game: Display the winner in text
    Reset: The game is reset and sent back to Pre Game
     */

    public final int PRE_GAME = 0;
    public final int IN_GAME = 1;
    public final int SCORED = 2;
    public final int END_GAME = 3;

    private PongGame pongGame;
    private int currentState = PRE_GAME;

    public GameStageManager(PongGame p){
        pongGame = p;
    }

    public int getState(){
        return currentState;
    }




    public void setCurrentState(int state){
        currentState = state;
        if(state == SCORED){
            pongGame.resetField();
            if(pongGame.getScoreKeeper().getWinner() != 3){
                setCurrentState(END_GAME);
            }
        }
        if(state == PRE_GAME){
            //make modifier selector appear
            pongGame.getLabelManager().makeLabel("{EASE}Press space to start{ENDEASE}");
            pongGame.newBall(0);
        }
        if(state == IN_GAME){
            pongGame.getLabelManager().removeText();
        }
        if(state == END_GAME){
            pongGame.getLabelManager().removeText();
            pongGame.getLabelManager().makeLabel("Player " + pongGame.getScoreKeeper().getWinner() + " Won!");
        }
    }



}
