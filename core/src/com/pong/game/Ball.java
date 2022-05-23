package com.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.concurrent.ThreadLocalRandom;

public class Ball {
    PongGame pongGame;
    Texture img;
    SpriteBatch batch;
    int side;
    Sprite sprite;
    Body body;
    World world;
    PolygonShape shape;
    FixtureDef fixtureDef;
    Fixture fixture;
    float moveX = 0;
    float moveY = 0;
    boolean killFlag = false;
    private int paddleBounces = 0;
    private int frameCounter = 0;

    public Ball(PongGame pg, int s){
        pongGame = pg;
        batch = pongGame.getBatch();
        side = s;
        img = new Texture("Ball.png");
        sprite = new Sprite(img);
        sprite.setSize(sprite.getWidth()/pongGame.scaler, sprite.getHeight()/pongGame.scaler);
        config();

        world = pongGame.getWorld();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX() - sprite.getWidth()/2, sprite.getY() - sprite.getHeight()/2);
        body = world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);



        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.000000000000000000000000001f;

        fixture = body.createFixture(fixtureDef);

        body.setFixedRotation(true);
        body.setAwake(true);
        body.setLinearDamping(0f);
        body.setAngularDamping(0f);
        body.setSleepingAllowed(false);
        fixture.setFriction(0f);
        fixture.setRestitution(1f);


        //sets bit data for collision detection
        fixture.setUserData(this);
        Filter filter = new Filter();
        filter.categoryBits = PongGame.BALL_BIT;
        fixture.setFilterData(filter);

        body.setLinearVelocity(0,0);

    }

    public void config(){
        if(side == 0){
            sprite.setPosition(((((float)Gdx.graphics.getWidth()/2f)/ pongGame.scaler) + sprite.getWidth()/2f),(((float) Gdx.graphics.getHeight()/2f)/ pongGame.scaler) + sprite.getHeight());
        }else if(side == 1){
            sprite.setPosition(5, (int) (Gdx.graphics.getHeight()/2 - sprite.getHeight()/2));
        }else if(side == 2){
            sprite.setPosition(Gdx.graphics.getWidth() - sprite.getWidth() - 5, (int) (Gdx.graphics.getHeight()/2 - sprite.getHeight()/2));
        }
    }

    public void draw(){
        frameCounter++;
        body.setLinearVelocity(moveX, moveY);

        sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
        sprite.draw(batch);
    }


    public void dispose(){
        img.dispose();
        shape.dispose();
        world.destroyBody(body);
    }


    //Standard wall impact
    public void reverse(boolean x){
        int randomizerY = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
        float modifier = .05f;
        if(moveY > 0){
            moveY += (modifier + randomizerY);
        }else{
            moveY -= (modifier + randomizerY);
        }
        moveY *= -1;
        if(x){
            moveX*=-1;
        }
        body.setLinearVelocity(moveX, moveY);
    }

    //ball impact on paddle
    public void reversePaddle(Playerpaddle paddle){

        int randomizerX = ThreadLocalRandom.current().nextInt(-1, 1 + 1);
        int randomizerY = ThreadLocalRandom.current().nextInt(-1, 1 + 1);

        float modifier = 1f;

        //paddle zones
        //zone 1: top 1/3rd: Bounce up
        //zone 2: middle 1/3rd: Bounce slightly up or down
        //zone 3: bottom 1/3rd: Bounce down

        float ballY = body.getPosition().y;
        float paddleY = paddle.body.getPosition().y;
        float midZone = (paddle.sprite.getHeight()/3)/2;

        // reverse move x (always done)
        if(moveX > 0){
            moveX += (modifier + randomizerX);
        }else{
            moveX -= (modifier + randomizerX);
        }

        //check collision zone for y modification
        if(ballY > (paddleY+midZone)){
            //High zone

            if(moveY >= 0){
                moveY *= -1;
            }
            moveY += (modifier + randomizerY);


        }else if(ballY < (paddleY-midZone)){
            //Low zone"


            if (moveY > 0) {
                moveY += (modifier + randomizerY);
            }else{
                moveY -= (modifier + randomizerY);
            }
            if(moveY <= 0){
                moveY *= -1;
            }

        }else{
            //Mid zone
            if(moveY > 0){
                moveY += (modifier*2 + randomizerY);
            }else{
                moveY -= (modifier*2 - randomizerY);
            }
        }

        moveY *= -1;
        moveX *=-1;
        body.setLinearVelocity(moveX, moveY);
        incrementPaddleBounce();
    }

    public Vector2 getVelocity(){
        return new Vector2(moveX, moveY);
    }

    public void setVelocity(Vector2 v){
        body.setLinearVelocity(v.x, v.y);
    }
    public Vector2 getPos(){
        return body.getPosition();
    }
    public void setPos(Vector2 v){
        body.setTransform(v, 0);
    }


    public void score(){
        int player;
        if(body.getPosition().x < (float) (Gdx.graphics.getWidth()/2)/ pongGame.scaler){
            player = 1;
        }else{
            player = 2;
        }

        pongGame.getScoreKeeper().removeLife(player);
        pongGame.getGameStageManager().setCurrentState(pongGame.getGameStageManager().SCORED);
    }

    public void startPush(){
        float startSpeed = 5f;

        //jank math
        int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        if(randomNum == 0){
            randomNum = -1;
        }
        moveX = (startSpeed * randomNum);
    }

    public void setKillFlag(){
        killFlag = true;
    }
    public boolean getKillFlag(){
        return killFlag;
    }

    public void setColor(float r, float g, float b){
        sprite.setColor(r,g,b,1f);
    }

    public void setMove(float x, float y){
        moveX = x;
        moveY = y;
    }
    public float getMoveX(){
        return moveX;
    }
    public float getMoveY(){
        return moveY;
    }

    public void incrementPaddleBounce(){
        paddleBounces++;
    }
    public int getPaddleBounces(){
        return paddleBounces;
    }
    public void setPaddleBounces(int x){
        paddleBounces = x;
    }
    public void setAlpha(float a){
        sprite.setAlpha(a);
    }
    public float getAlpha(){
        return sprite.getColor().a;
    }
    public void setFrameCounter(int f){
        frameCounter = f;
    }
    public int getFrameCounter(){
        return frameCounter;
    }

    public void setTexture(Texture newTexture){
        sprite.setTexture(newTexture);
    }


}
