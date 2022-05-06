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

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case PongGame.PLAYER_PADDLE_BIT | PongGame.BALL_BIT:
                if(fixA.getFilterData().categoryBits == PongGame.PLAYER_PADDLE_BIT){
                    ((Ball) fixB.getUserData()).reverse();
                    System.out.println("Impact paddle1");
                }else{
                    ((Ball) fixA.getUserData()).reverse();
                    System.out.println("Impact paddle2");
                }
            case PongGame.BALL_BIT | PongGame.NOTHING_BIT:
                if(fixA.getFilterData().categoryBits == PongGame.NOTHING_BIT){
                    //((Ball) fixB.getUserData()).reverse();
                    System.out.println("Impact Nothing");
                }
                /*
                Idk why but this line breaks the collision system and idk how to fix it rn

                else{
                    ((Ball) fixA.getUserData()).reverse();
                }

                 */
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
