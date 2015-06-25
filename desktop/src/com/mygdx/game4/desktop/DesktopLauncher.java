package com.mygdx.game4.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game4.SummerGame4;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mitt Spill";
		config.width = SummerGame4.WORLD_WIDTH;
		config.height = SummerGame4.WORLD_HEIGHT;
		new LwjglApplication(new SummerGame4(), config);
	}
}
