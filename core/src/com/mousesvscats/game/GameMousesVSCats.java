package com.mousesvscats.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mousesvscats.game.GameLogic.*;

public class GameMousesVSCats extends ApplicationAdapter {

    private GameClass mygame;
    SpriteBatch batch;
    static final double DELTA_TIME = 0.016; //частота обновления кадров
    private static final int CELL_SIZE = 16;

    @Override
    public void create () {
        mygame = new GameClass(new Labyrinth("level_1.txt"));
        batch = new SpriteBatch();
    }

    @Override
    public void render () {
        batch.begin();

        for (int i = 0; i<mygame.getLevel().getSectors().length; i++)
            for (int j = 0; j < mygame.getLevel().getSectors()[0].length; j++) {
                batch.draw(mygame.getLevel().getSectors()[j][i].getTexture(),
                        j*CELL_SIZE,
                        i*CELL_SIZE,
                        CELL_SIZE, CELL_SIZE);
            }
        batch.draw(mygame.getCat().getTexture(),mygame.getCat().getX(),mygame.getCat().getY());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mygame.getCat().setDirection(Direction.RIGHT);
            mygame.getCat().setX(mygame.getCat().getX() + (int)(mygame.getCat().getSpeed()*DELTA_TIME));
            /*Перемещаем перса на некоторое расстояние вправо
            а затем проверяем, не "наехал" ли он на объект, на который нельзя наезжать
             getDeltaTime - время, прошедшее после последнего отрисованного кадра*/
            Collision.Collision(mygame.getCat(), mygame.getLevel());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mygame.getCat().setDirection(Direction.LEFT);
            mygame.getCat().setX(mygame.getCat().getX() - (int)(mygame.getCat().getSpeed()*DELTA_TIME));
            Collision.Collision(mygame.getCat(), mygame.getLevel());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            mygame.getCat().setDirection(Direction.UP);
            mygame.getCat().setY(mygame.getCat().getY() + (int)(mygame.getCat().getSpeed() * DELTA_TIME));
            Collision.Collision(mygame.getCat(), mygame.getLevel());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            mygame.getCat().setDirection(Direction.DOWN);
            mygame.getCat().setY(mygame.getCat().getY() - (int)(mygame.getCat().getSpeed() * DELTA_TIME));
            Collision.Collision(mygame.getCat(), mygame.getLevel());
        }
        batch.end();
    }
}
