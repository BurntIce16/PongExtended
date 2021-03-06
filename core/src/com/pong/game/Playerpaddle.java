package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Playerpaddle {
    Texture img;
    SpriteBatch batch;
    int side;
    Sprite sprite;
    private float speed = .2f;
    Body body;
    World world;
    PolygonShape shape;
    FixtureDef fixtureDef;
    Fixture fixture;
    PongGame pongGame;

    private boolean moveUp = true;
    private boolean moveDown = true;

    public Playerpaddle(SpriteBatch b, int s, World w, PongGame p){
        pongGame = p;
        batch = b;
        side = s;
        img = new Texture("paddle.png");
        sprite = new Sprite(img);
        sprite.setSize(sprite.getWidth()/pongGame.scaler, sprite.getHeight()/pongGame.scaler);
        config();


        world = w;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX(), sprite.getY());
        body = world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);


        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        fixture = body.createFixture(fixtureDef);

        //sets bit data for collision detection
        fixture.setUserData(this);
        Filter filter = new Filter();
        filter.categoryBits = PongGame.PLAYER_PADDLE_BIT;
        fixture.setFilterData(filter);

    }

    public void config(){
        final int wallOffset = 1;

        if(side == 0){
            sprite.setPosition((float) wallOffset, (int) (((Gdx.graphics.getHeight()/2)/pongGame.scaler) - sprite.getHeight()/2));
        }else if(side == 1){
            sprite.setPosition((Gdx.graphics.getWidth()/ pongGame.scaler) - sprite.getWidth() - (float) wallOffset, (int) (((Gdx.graphics.getHeight()/2)/ pongGame.scaler) - sprite.getHeight()/2));
        }
    }


    public void draw(){
        body.setTransform(sprite.getX() + sprite.getWidth()/2, sprite.getY() + sprite.getHeight()/2, sprite.getRotation());
        sprite.draw(batch);
    }



    public void dispose(){
        img.dispose();
        shape.dispose();
    }

    public void limitMovement(){
        if(body.getPosition().y>( Gdx.graphics.getHeight()/2f)/pongGame.scaler){
            moveUp = false;
        }else{
            moveDown = false;
        }
    }

    public void releaseMovement(){
        moveDown = true;
        moveUp = true;

    }


    public void Up(){
        if(moveUp){
            sprite.translateY(speed);
        }
    }

    public void Down(){
        if(moveDown){
            sprite.translateY(-speed);
        }
    }
}