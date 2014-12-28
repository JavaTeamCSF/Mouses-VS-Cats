package com.mousesvscats.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mousesvscats.game.GameLogic.Labyrinth;
import com.mousesvscats.game.GameLogic.Sector;
import com.mousesvscats.game.GameMousesVSCats;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "CatsVsMouses";
        config.width = Labyrinth.LABYRINTH_WIDTH * Sector.SIZE + GameMousesVSCats.BAR_WIDTH;
        config.height = Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE;
        config.resizable = false;
		new LwjglApplication(new GameMousesVSCats(), config);
	}
}
