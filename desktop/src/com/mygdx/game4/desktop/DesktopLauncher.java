package com.mygdx.game4.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game4.MainGameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mitt Spill";
		config.width = 1024;
		config.height = 720;
		config.resizable = false;
		new LwjglApplication(new MainGameClass(), config);
	}
}
