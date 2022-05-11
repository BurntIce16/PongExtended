package com.pong.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getFilterData().categoryBits == PongGame.BALL_BIT){
            if(fixB.getFilterData().categoryBits == PongGame.WALL_BIT){
                ((Ball) fixA.getUserData()).reverse(false);
            }
            if(fixB.getFilterData().categoryBits == PongGame.PLAYER_PADDLE_BIT){
                ((Ball) fixA.getUserData()).reversePaddle((Playerpaddle) fixB.getUserData());
            }
            if(fixB.getFilterData().categoryBits == PongGame.SCORE_BIT){
                //needs to be changed
                ((Ball) fixA.getUserData()).score();
                //update score system
            }
            if(fixB.getFilterData().categoryBits == PongGame.BALL_BIT){
                ((Ball) fixA.getUserData()).reverse(true);
                ((Ball) fixB.getUserData()).reverse(true);
            }
        }
        if(fixB.getFilterData().categoryBits == PongGame.BALL_BIT){
            if(fixA.getFilterData().categoryBits == PongGame.WALL_BIT){
                ((Ball) fixB.getUserData()).reverse(false);
            }
            if(fixA.getFilterData().categoryBits == PongGame.PLAYER_PADDLE_BIT){
                ((Ball) fixB.getUserData()).reversePaddle((Playerpaddle) fixA.getUserData());
            }
            if(fixA.getFilterData().categoryBits == PongGame.SCORE_BIT){
                //needs to be changed
                ((Ball) fixB.getUserData()).score();
                //Update score system
            }
            if(fixA.getFilterData().categoryBits == PongGame.BALL_BIT){
                ((Ball) fixA.getUserData()).reverse(true);
                ((Ball) fixB.getUserData()).reverse(true);
            }
        }

        if(fixA.getFilterData().categoryBits == PongGame.PLAYER_PADDLE_BIT){
            if(fixB.getFilterData().categoryBits == PongGame.WALL_BIT){
                ((Playerpaddle) fixA.getUserData()).limitMovement();
                System.out.println("test");
            }
        }
        if(fixB.getFilterData().categoryBits == PongGame.PLAYER_PADDLE_BIT){
            if(fixA.getFilterData().categoryBits == PongGame.WALL_BIT){
                ((Playerpaddle) fixB.getUserData()).limitMovement();
                System.out.println("test");
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getFilterData().categoryBits == PongGame.PLAYER_PADDLE_BIT){
            if(fixB.getFilterData().categoryBits == PongGame.WALL_BIT){
                ((Playerpaddle) fixA.getUserData()).releaseMovement();
            }
        }
        if(fixB.getFilterData().categoryBits == PongGame.PLAYER_PADDLE_BIT){
            if(fixA.getFilterData().categoryBits == PongGame.WALL_BIT){
                ((Playerpaddle) fixB.getUserData()).releaseMovement();
            }
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
