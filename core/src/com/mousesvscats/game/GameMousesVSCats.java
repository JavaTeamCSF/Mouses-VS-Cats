package com.mousesvscats.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMousesVSCats extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 1, 1); //цвет очищения
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //само очищение
		batch.begin();
		batch.end();
	}
}
