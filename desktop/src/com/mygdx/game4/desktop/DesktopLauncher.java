package com.mygdx.game4.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game4.MainGameClass;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mitt Spill";

		config.width = MainGameClass.WORLD_WIDTH;
		config.height = MainGameClass.WORLD_HEIGHT;
		new LwjglApplication(new MainGameClass(), config);
	}
}
