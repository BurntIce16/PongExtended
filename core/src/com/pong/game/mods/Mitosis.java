package com.pong.game.mods;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.pong.game.Ball;
import com.pong.game.Modifier;
import com.pong.game.PongGame;

public class Mitosis implements Modifier {

    //setup & setup methods
    PongGame pongGame;
    private final int maxBalls = 16;
    private Texture icon;

    public Mitosis(PongGame p){
        pongGame = p;
        config();
    }


    @Override
    public void config() {
        //assign texture
        icon = new Texture("Mitosis.png");
    }

    @Override
    public void update() {
        for(int i = pongGame.getBalls().size()-1; i >= 0; i--){
            Ball b = pongGame.getBalls().get(i);
            if(b.getPaddleBounces() >= 5){
                b.setPaddleBounces(0);
                Ball ball = pongGame.newBall(0);
                System.out.println("Mitosis made a new ball");
                ball.setPos(new Vector2(b.getPos().x, b.getPos().y+ 1f));
                ball.setMove(b.getMoveX() * .5f, b.getMoveY() *.5f);
                //ball.setColor(255, 0, 0);
            }
        }

    }

    @Override
    public Texture getIcon() {
        return icon;
    }


    //Modifier Methods
    @Override
    public void enable() {

    }
}
