package com.mousesvscats.game.desktop;

import com.mousesvscats.game.GameLogic.GameObject;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mousesvscats.game.GameLogic.GameClass;
import com.mousesvscats.game.GameLogic.Labyrinth;
import com.mousesvscats.game.GameMousesVSCats;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "CatsVsMouses";
        config.width = Labyrinth.LABYRINTH_WIDTH * GameObject.Size + GameMousesVSCats.BAR_WIDTH;
        config.height = Labyrinth.LABYRINTH_HEIGHT * GameObject.Size;
        config.resizable = false;
		new LwjglApplication(new GameMousesVSCats(), config);
	}
}
