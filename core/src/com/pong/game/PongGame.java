package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class PongGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	ArrayList<Playerpaddle> paddles = new ArrayList<Playerpaddle>();
	ArrayList<Ball> balls = new ArrayList<Ball>();
	World world;


	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short PLAYER_PADDLE_BIT = 1;
	public static final short AI_PADDLE_BIT = 2;
	public static final short BALL_BIT = 4;



	@Override
	public void create () {
		//create physics world
		world = new World(new Vector2(0f, 0f), false);
		world.setContactListener(new WorldContactListener());

		batch = new SpriteBatch();
		Playerpaddle p1 = new Playerpaddle(batch, 0, world);
		Playerpaddle p2 = new Playerpaddle(batch, 1, world);
		paddles.add(p1);
		paddles.add(p2);

		Ball b1 = new Ball(batch, 0, world);
		balls.add(b1);



	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		batch.begin();
		for(Playerpaddle p : paddles){
			p.draw();
		}
		for(Ball b : balls){
			b.draw();
		}

		batch.end();



	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for(Playerpaddle p : paddles){
			p.dispose();
		}
		for(Ball b : balls){
			b.dispose();
		}
	}
}


// Hi Clayton :D