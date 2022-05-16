package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pong.game.mods.Mitosis;

import java.util.ArrayList;

public class PongGame extends ApplicationAdapter {
	SpriteBatch batch;
	ArrayList<Playerpaddle> paddles = new ArrayList<>();
	ArrayList<Ball> balls = new ArrayList<>();
	World world;
	Box2DDebugRenderer box2dDebugRenderer;
	Camera camera;
	LevelBuilder level;
	VfxController vfx;
	ControllerController controllerManager;
	ModifierManager modManager;
	ScoreKeeper sk;



	private final boolean renderColliders = false;

	//Box2D Collision Bits
	//public static final short NOTHING_BIT = 0;
	public static final short PLAYER_PADDLE_BIT = 1;
	public static final short WALL_BIT = 2;
	public static final short BALL_BIT = 4;
	public static final short SCORE_BIT = 8;




	@Override
	public void create () {

		controllerManager = new ControllerController();

		//setup vfx
		vfx = new VfxController(true);


		//setup camera
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(width, height);
		camera.position.set((float) width / 2, (float) height / 2, 0);
		camera.update();

		//create physics world
		world = new World(new Vector2(0f, 0f), false);
		world.setContactListener(new WorldContactListener());

		batch = new SpriteBatch();
		Playerpaddle p1 = new Playerpaddle(batch, 0, world, this);
		Playerpaddle p2 = new Playerpaddle(batch, 1, world, this);
		paddles.add(p1);
		paddles.add(p2);

		Ball b1 = new Ball(this, 0);
		balls.add(b1);

		//Create Level
		level = new LevelBuilder(this);


		//create debug renderer
		if(renderColliders){
			box2dDebugRenderer = new Box2DDebugRenderer();

		}

		//Add modifier system
		modManager = new ModifierManager(this);

		//add score keeper
		sk = new ScoreKeeper(this);

	}


	@Override
	public void render () {
		//start vfx
		vfx.startRender();

		ScreenUtils.clear(0, 0, 0, 1);

		//step world physics
		world.step(Gdx.graphics.getDeltaTime()*25, 6, 2);

		//draw entities
		batch.begin();

		//render level
		level.render();

		//render the ScoreKeeper
		sk.render();



		//THIS IS TEMPORARY!!!
		if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
			modManager.addMod(new Mitosis(this));
		}
		//THIS IS TEMPORARY!!!


		for(Playerpaddle p : paddles){
			p.draw();
		}
		for(Ball b : balls){
			b.draw();
		}


		//close batch
		batch.end();


		//render box2d
		if(renderColliders){
			box2dDebugRenderer.render(world, camera.combined);
		}

		//end vfx
		vfx.endRender();

		System.out.println(balls.get(0).getVelocity());

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
		level.dispose();

		//dispose vfx
		vfx.dispose();

		//dispose score keeper
		sk.dispose();
	}

	public World getWorld(){
		return world;
	}
	public SpriteBatch getBatch(){
		return batch;
	}
	public ArrayList<Ball> getBalls(){
		return balls;
	}
	public Camera getCamera(){
		return camera;
	}
	public VfxController getVfx(){
		return vfx;
	}
	public ScoreKeeper getScoreKeeper(){
		return sk;
	}


	public void addBall(Ball ball){
		balls.add(ball);
	}
}
