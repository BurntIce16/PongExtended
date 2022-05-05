package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Playerpaddle {
    Texture img;
    SpriteBatch batch;
    int side;
    Sprite sprite;
    private float speed = 5f;
    Body body;
    World world;
    PolygonShape shape;
    FixtureDef fixtureDef;
    Fixture fixture;

    public Playerpaddle(SpriteBatch b, int s, World w){
        batch = b;
        side = s;
        img = new Texture("paddle.png");
        sprite = new Sprite(img);
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



    }

    public void config(){
        if(side == 0){
            sprite.setPosition(5, (int) (Gdx.graphics.getHeight()/2 - sprite.getHeight()/2));
        }else if(side == 1){
            sprite.setPosition(Gdx.graphics.getWidth() - sprite.getWidth() - 5, (int) (Gdx.graphics.getHeight()/2 - sprite.getHeight()/2));
        }

        //sprite.setSize(25f, 100f);
    }

    public void draw(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            sprite.translateY(speed);
        }if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            sprite.translateY(-speed);
        }
        sprite.draw(batch);
    }



    public void dispose(){
        img.dispose();
        shape.dispose();
    }


}
