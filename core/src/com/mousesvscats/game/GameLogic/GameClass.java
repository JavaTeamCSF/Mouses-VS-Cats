package com.mousesvscats.game.GameLogic;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mousesvscats.game.GameLogic.items.*;

import java.util.HashMap;


public class GameClass extends ApplicationAdapter {
    SpriteBatch batch;
    Cat cat;
    static final double DELTA_TIME = 0.016; //частота обновления кадров
    private static final int CELL_SIZE = 16;
    Labyrinth level;

    TextureRegion cheese;
    TextureRegion weaponFreezer;
    TextureRegion weaponDistractor;
    TextureRegion weaponSlower;
    TextureRegion key;

    HashMap<SectorType, TextureRegion> textures;

    @Override
    public void create () {
        cat = new Cat(GameObject.Size,GameObject.Size,300);//скорость кошек : 100-медленно, 200 - средне, 300 - быстро
        //здесь спаун кошки происходит в клетку [1][1]
        level = new Labyrinth("core/assets/level_1.txt");
        cheese = new TextureRegion(new Texture(Gdx.files.internal("core/assets/cheese.png")));

        weaponSlower = new TextureRegion(new Texture(Gdx.files.internal("core/assets/testGun.gif")));
        weaponDistractor = new TextureRegion(new Texture(Gdx.files.internal("core/assets/redLightSaber.png")));
        weaponFreezer = new TextureRegion(new Texture(Gdx.files.internal("core/assets/freezer.png")));
        key = new TextureRegion(new Texture(Gdx.files.internal("core/assets/key.png")));

        level.generateItems();

        batch = new SpriteBatch();
        textures = new HashMap<SectorType, TextureRegion>() {
            {
                put(SectorType.EMPTY, new TextureRegion(new Texture(Gdx.files.internal("core/assets/empty.png"))));
                put(SectorType.WALL, new TextureRegion(new Texture(Gdx.files.internal("core/assets/wall.png"))));
                put(SectorType.CLOSED_DOOR, new TextureRegion(new Texture(Gdx.files.internal("core/assets/door_ver.png"))));
            }
        };
    }

    private void createItemsAndCheese() {

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

                if (level.getSectors()[j][i].getItem() instanceof Cheese) {
                    batch.draw(cheese,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (level.getSectors()[j][i].getItem() instanceof Key) {
                    batch.draw(key,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (level.getSectors()[j][i].getItem() instanceof DistractGun) {
                    batch.draw(weaponDistractor,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (level.getSectors()[j][i].getItem() instanceof FreezerGun) {
                    batch.draw(weaponFreezer,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (level.getSectors()[j][i].getItem() instanceof SlowGun) {
                    batch.draw(weaponSlower,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
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
