package com.pong.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(120);
		config.setIdleFPS(120);
		config.setTitle("PongExtended");
		config.setWindowedMode(1280, 720);
		config.setResizable(false);
		config.setWindowIcon("ico.png");
		new Lwjgl3Application(new PongGame(), config);
	}
}
