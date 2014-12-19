package com.mousesvscats.game.desktop;

import com.mousesvscats.game.GameLogic.GameObject;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mousesvscats.game.GameLogic.Labyrinth;
import com.mousesvscats.game.GameMousesVSCats;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title="CatsVsMouses";
        config.width=Labyrinth.LABYRINTH_WIDTH* GameObject.Size;
        config.height=Labyrinth.LABYRINTH_WIDTH*GameObject.Size;
		new LwjglApplication(new GameMousesVSCats(), config);
	}
}
