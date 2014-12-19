package com.mousesvscats.game.GameLogic;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.io.*;
import java.util.HashMap;


public class GameClass extends ApplicationAdapter {
    SpriteBatch batch;
    Cat cat;
    static final double DELTA_TIME = 0.016; //частота обновления кадров
    private static final int CELL_SIZE = 16;
    Labyrinth level;

    HashMap<SectorType, TextureRegion> textures;

    @Override
    public void create () {
        cat = new Cat(GameObject.Size,GameObject.Size,300);//скорость кошек : 100-медленно, 200 - средне, 300 - быстро
        //здесь спаун кошки происходит в клетку [1][1]
        level = new Labyrinth("level_1.txt");
        batch = new SpriteBatch();
        textures = new HashMap<SectorType, TextureRegion>() {
            {
                put(SectorType.EMPTY, new TextureRegion(new Texture(Gdx.files.internal("empty.png"))));
                put(SectorType.WALL, new TextureRegion(new Texture(Gdx.files.internal("wall.png"))));
                put(SectorType.CLOSED_DOOR, new TextureRegion(new Texture(Gdx.files.internal("door_ver.png"))));
            }
        };
    }

    @Override
    public void render () {
        batch.begin();

        for (int i = 0; i<level.getSectors().length; i++)
            for (int j = 0; j < level.getSectors()[0].length; j++) {
                batch.draw(textures.get(level.getSectors()[j][i].getSectorType()),
                        j*CELL_SIZE,
                        i*CELL_SIZE,
                        CELL_SIZE, CELL_SIZE);
            }
        batch.draw(cat.getTexture(),cat.getX(),cat.getY());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cat.setDirection(Creature.Direction.RIGHT);
            cat.setX(cat.getX() + (int)(cat.getSpeed()*DELTA_TIME));
            /*Перемещаем перса на некоторое расстояние вправо
            а затем проверяем, не "наехал" ли он на объект, на который нельзя наезжать
             getDeltaTime - время, прошедшее после последнего отрисованного кадра*/
            Collision.Collision(cat, level);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cat.setDirection(Creature.Direction.LEFT);
            cat.setX(cat.getX() - (int)(cat.getSpeed()*DELTA_TIME));
            Collision.Collision(cat, level);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cat.setDirection(Creature.Direction.UP);
            cat.setY(cat.getY() + (int)(cat.getSpeed() * DELTA_TIME));
            Collision.Collision(cat, level);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cat.setDirection(Creature.Direction.DOWN);
            cat.setY(cat.getY() - (int)(cat.getSpeed() * DELTA_TIME));
            Collision.Collision(cat, level);
        }
        batch.end();
    }
}
