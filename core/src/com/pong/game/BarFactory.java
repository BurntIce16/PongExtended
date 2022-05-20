package com.pong.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class BarFactory {
    Texture img;
    Sprite sprite;
    Body body;
    PolygonShape shape;
    FixtureDef fixtureDef;
    Fixture fixture;
    PongGame pongGame;

    public BarFactory(PongGame p, int x, int y, String type, int xOffset, int yOffset){
        pongGame = p;

        //assign image
        if(type.equalsIgnoreCase("horizontal")){
            img = new Texture("HorizontalBar.png");
        }else if(type.equalsIgnoreCase("vertical")){
            img = new Texture("VerticalBar.png");
        }else{
            System.out.println("Invalid Type");
        }

        sprite = new Sprite(img);
        sprite.setSize(sprite.getWidth()/pongGame.scaler, sprite.getHeight()/pongGame.scaler);
        sprite.setPosition((float) (x/ pongGame.scaler) + (xOffset * sprite.getWidth()),(float) (y/pongGame.scaler) + (yOffset * sprite.getHeight()));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth()/2, sprite.getY()+ sprite.getHeight()/2);
        body = pongGame.getWorld().createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setFriction(0f);
        fixture.setRestitution(1f);

        //sets bit data for collision detection
        fixture.setUserData(this);
        Filter filter = new Filter();
        if(type.equalsIgnoreCase("horizontal")){
            filter.categoryBits = PongGame.WALL_BIT;
        }else if(type.equalsIgnoreCase("vertical")){
            filter.categoryBits = PongGame.SCORE_BIT;
        }else{
            filter.categoryBits = PongGame.WALL_BIT;
        }



        fixture.setFilterData(filter);

    }


    public void render(){
        sprite.draw(pongGame.getBatch());
    }

    public void dispose(){
        img.dispose();
        shape.dispose();
    }

    public float getHeight(){
        return sprite.getHeight();
    }

}
