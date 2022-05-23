package com.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pong.game.mods.Mitosis;

import java.util.ArrayList;

public class PongGame extends Game {
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
	SoundManager soundManager;
	InputManager inputManager;
	LabelManager labelManager;
	GameStageManager gameStageManager;



	private final boolean renderColliders = false;


	//Box2D Collision Bits
	//public static final short NOTHING_BIT = 0;
	public static final short PLAYER_PADDLE_BIT = 1;
	public static final short WALL_BIT = 2;
	public static final short BALL_BIT = 4;
	public static final short SCORE_BIT = 8;


	//camera constants for pixel to meter conversion
	//DONT TOUNCH THESE NUMBERS IF YOU DONT KNOW WHAT YOUR DOING!!!!
	//---------------------------------------------------------------
	private final int VIEWPORT_WIDTH = 16;
	private final int VIEWPORT_HEIGHT = 9;
	public final float scaler = 80;
	//---------------------------------------------------------------


	@Override
	public void create () {

		controllerManager = new ControllerController();

		//setup vfx
		vfx = new VfxController(true);


		//setup camera
		camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		camera.position.set((float) VIEWPORT_WIDTH / 2, (float) VIEWPORT_HEIGHT / 2, 0);
		camera.update();

		resize(1280, 720);

		//create physics world
		world = new World(new Vector2(0f, 0f), false);
		world.setContactListener(new WorldContactListener(this));

		batch = new SpriteBatch();
		Playerpaddle p1 = new Playerpaddle(batch, 0, world, this);
		Playerpaddle p2 = new Playerpaddle(batch, 1, world, this);
		paddles.add(p1);
		paddles.add(p2);


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


		//add sound manager
		soundManager = new SoundManager();


		//create and set input processor
		inputManager = new InputManager(this);
		Gdx.input.setInputProcessor(inputManager);

		//create label manager
		labelManager = new LabelManager(this);

		//create Game State Manager
		gameStageManager = new GameStageManager(this);

		//draw startup text
		gameStageManager.setCurrentState(gameStageManager.getState());

	}


	@Override
	public void render () {

		//System.out.println(balls.get(0).getVelocity());

		//start vfx
		vfx.startRender();

		ScreenUtils.clear(0, 0, 0, 1);

		//step world physics
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		//draw entities
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//render level
		level.render();

		//render the ScoreKeeper
		sk.render();



		//THIS IS TEMPORARY!!!
		if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
			modManager.addMod(new Mitosis(this));
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
			Ball tempBall = balls.get(0);
			balls.remove(0);
			tempBall.dispose();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.G)){
			modManager.enableMenu();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
			modManager.dissableMenu();
		}
		//THIS IS TEMPORARY!!!


		for(Playerpaddle p : paddles){
			p.draw();
		}
		for(Ball b : balls){
			b.draw();
		}

		modManager.render();


		//close batch
		batch.end();


		//render box2d
		if(renderColliders){
			box2dDebugRenderer.render(world, camera.combined);
		}

		//end vfx
		vfx.endRender();

		//check for input
		inputManager.pollInputs();

		labelManager.draw();

		modManager.updateMods();




		//This is kept seperate from the field reset method due to some box2d weirdness
		//The world.dispose methoth cannot be called during a world step so here is this jank instead
		ballCleaner();

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

		soundManager.dispose();

		labelManager.dispose();

		modManager.dispose();
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
	public SoundManager getSoundManager(){
		return soundManager;
	}
	public ArrayList<Playerpaddle> getPaddles() {
		return  paddles;
	}
	public GameStageManager getGameStageManager(){
		return gameStageManager;
	}
	public LabelManager getLabelManager(){
		return labelManager;
	}
	public ModifierManager getModManager(){
		return modManager;
	}
	public InputManager getInputManager(){
		return inputManager;
	}


	public void addBall(Ball ball){
		balls.add(ball);
	}

	public Ball newBall(int side){
		Ball ball = new Ball(this, side);
		balls.add(ball);
		return ball;
	}

	//resize camera to actual dimensions
	public void resize(int width, int height) {
		camera.viewportHeight = ((float) VIEWPORT_WIDTH / width) * height;
		camera.update();
	}



	public void resetField(){
		//System.out.println("field reset called");
		for(Ball b: balls){
			b.setKillFlag();
		}

	}

	public void ballCleaner(){
		boolean cleaned = false;
		//check for balls to be disposed
		for(int i = balls.size()-1; i >= 0; i--){
			//System.out.println(balls.size());
			if(balls.get(i).getKillFlag()){
				balls.get(i).dispose();
				balls.remove(i);
				i--;
				cleaned= true;
			}
		}
		if(cleaned && sk.getWinner() == 3){
			gameStageManager.setCurrentState(gameStageManager.PRE_GAME);
			System.out.println("ball cleaner set pregame");
			cleaned = false;
		}
	}
}
