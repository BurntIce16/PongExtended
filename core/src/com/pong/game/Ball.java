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

public class Ball {
    Texture img;
    SpriteBatch batch;
    int side;
    Sprite sprite;
    Body body;
    World world;
    PolygonShape shape;
    FixtureDef fixtureDef;
    Fixture fixture;

    public Ball(SpriteBatch b, int s, World w){
        batch = b;
        side = s;
        img = new Texture("ball.png");
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
        fixtureDef.density = 0.000000000000001f;

        fixture = body.createFixture(fixtureDef);

        body.setFixedRotation(true);
        body.setAwake(true);
        body.setLinearDamping(0f);
        body.setAngularDamping(0f);
        body.setSleepingAllowed(false);
        fixture.setFriction(0f);


        //sets bit data for collision detection
        fixture.setUserData(this);
        Filter filter = new Filter();
        filter.categoryBits = PongGame.BALL_BIT;
        fixture.setFilterData(filter);

        body.setLinearVelocity(0,0);

    }

    public void config(){
        if(side == 0){
            sprite.setPosition((int) (Gdx.graphics.getWidth()/2 - sprite.getWidth()/2), (int) (Gdx.graphics.getHeight()/2 - sprite.getHeight()/2));
        }else if(side == 1){
            sprite.setPosition(5, (int) (Gdx.graphics.getHeight()/2 - sprite.getHeight()/2));
        }else if(side == 2){
            sprite.setPosition(Gdx.graphics.getWidth() - sprite.getWidth() - 5, (int) (Gdx.graphics.getHeight()/2 - sprite.getHeight()/2));
        }
    }

    public void draw(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            //body.applyLinearImpulse(10f, 0f, sprite.getWidth()/2, sprite.getHeight()/2, true);
            body.setLinearVelocity(100f,0f);
            System.out.println("Force Applied!");
        }
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
        sprite.draw(batch);
    }


    public void dispose(){
        img.dispose();
        shape.dispose();
    }


    public void reverse(){
        int modifyX;
        int modifyY;
        int modifier = 50;

        //this code is atrocious
        if(body.getLinearVelocity().x > 0){
            modifyX = modifier;
        }else{
            modifyX = -1*modifier;
        }
        if(body.getLinearVelocity().y > 0){
            modifyY = modifier;
        }else{
            modifyY = -1*modifier;
        }


        body.setLinearVelocity((body.getLinearVelocity().x + modifyX) * -1 , (body.getLinearVelocity().y + modifyY) * -1);
        System.out.println("Boop");
    }


}
