package com.mousesvscats.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mousesvscats.game.GameLogic.*;
import com.mousesvscats.game.GameLogic.items.*;

import java.util.HashMap;

public class GameMousesVSCats extends ApplicationAdapter {

    private GameClass mygame;

    SpriteBatch batch;
    static final double DELTA_TIME = 0.016; //частота обновления кадров
    private static final int CELL_SIZE = 16;
    HashMap<SectorType, TextureRegion> textures;

    TextureRegion cheese;
    TextureRegion weaponSlower;
    TextureRegion weaponDistractor;
    TextureRegion weaponFreezer;
    TextureRegion key;

    @Override
    public void create () {
        mygame = new GameClass(new Labyrinth("core/assets/level_1.txt"));
        mygame.getLevel().generateItems();
        batch = new SpriteBatch();
        textures = new HashMap<SectorType, TextureRegion>() {
            {
                put(SectorType.EMPTY, new TextureRegion(new Texture(Gdx.files.internal("core/assets/empty.png"))));
                put(SectorType.WALL, new TextureRegion(new Texture(Gdx.files.internal("core/assets/wall.png"))));
                put(SectorType.CLOSED_DOOR, new TextureRegion(new Texture(Gdx.files.internal("core/assets/door_ver.png"))));
            }
        };

        cheese = new TextureRegion(new Texture(Gdx.files.internal("core/assets/cheese.png")));

        weaponSlower = new TextureRegion(new Texture(Gdx.files.internal("core/assets/slow.gif")));
        weaponDistractor = new TextureRegion(new Texture(Gdx.files.internal("core/assets/distract.png")));
        weaponFreezer = new TextureRegion(new Texture(Gdx.files.internal("core/assets/freezer.jpg")));
        key = new TextureRegion(new Texture(Gdx.files.internal("core/assets/key.png")));
    }

    @Override
    public void render () {
        batch.begin();

        for (int i = 0; i<mygame.getLevel().getSectors().length; i++)
            for (int j = 0; j < mygame.getLevel().getSectors()[0].length; j++) {
                batch.draw(textures.get(mygame.getLevel().getSectors()[j][i].getSectorType()),
                        j*CELL_SIZE,
                        i*CELL_SIZE,
                        CELL_SIZE, CELL_SIZE);

                if (mygame.getLevel().getSectors()[j][i].getItem() instanceof Cheese) {
                    batch.draw(cheese,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (mygame.getLevel().getSectors()[j][i].getItem() instanceof Key) {
                    batch.draw(key,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (mygame.getLevel().getSectors()[j][i].getItem() instanceof DistractGun) {
                    batch.draw(weaponDistractor,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (mygame.getLevel().getSectors()[j][i].getItem() instanceof FreezerGun) {
                    batch.draw(weaponFreezer,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (mygame.getLevel().getSectors()[j][i].getItem() instanceof SlowGun) {
                    batch.draw(weaponSlower,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
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
