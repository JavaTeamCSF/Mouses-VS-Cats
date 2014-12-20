package com.mousesvscats.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mousesvscats.game.GameLogic.*;
import com.mousesvscats.game.GameLogic.items.*;

import java.util.HashMap;

public class GameMousesVSCats extends ApplicationAdapter {

    private GameClass mygame;
    SpriteBatch batch;
    static final double DELTA_TIME = 0.016; //частота обновления кадров
    private static final int CELL_SIZE = 16;

    /**текстуры предметов*/
    TextureRegion tex_cheese;
    TextureRegion tex_weaponSlower;
    TextureRegion tex_weaponDistractor;
    TextureRegion tex_weaponFreezer;
    TextureRegion tex_key;

    /**спрайты кошек*/
    HashMap<Direction, TextureRegion> tex_cat;

    /**спрайты мыши*/
    HashMap<Direction, TextureRegion> tex_mouse;

    /**текстуры секторов*/
    HashMap<SectorType, TextureRegion> tex_sector;


    @Override
    public void create () {
        mygame = new GameClass(new Labyrinth("level_2.txt"));
        mygame.getLevel().generateItems(); //генерация рандомного расположения предметов на уровне
        batch = new SpriteBatch();

        /**текстуры предметов*/
        tex_cheese = new TextureRegion(new Texture(Gdx.files.internal("cheese.png")));
        tex_weaponSlower = new TextureRegion(new Texture(Gdx.files.internal("slow.gif")));
        tex_weaponDistractor = new TextureRegion(new Texture(Gdx.files.internal("distract.png")));
        tex_weaponFreezer = new TextureRegion(new Texture(Gdx.files.internal("freezer.jpg")));
        tex_key = new TextureRegion(new Texture(Gdx.files.internal("key.png")));

        /**спрайты кошек*/
        tex_cat = new HashMap<Direction, TextureRegion>() {{
            put(Direction.DOWN, new TextureRegion(new Texture(Gdx.files.internal("catdown.png"))));
            put(Direction.UP, new TextureRegion(new Texture(Gdx.files.internal("catup.png"))));
            put(Direction.LEFT, new TextureRegion(new Texture(Gdx.files.internal("catleft.png"))));
            put(Direction.RIGHT, new TextureRegion(new Texture(Gdx.files.internal("catright.png"))));
        }
        };

        /**спрайты мыши*/
        tex_mouse = new HashMap<Direction, TextureRegion>() {{
            put(Direction.DOWN, new TextureRegion(new Texture(Gdx.files.internal("mousedown.png"))));
            put(Direction.UP, new TextureRegion(new Texture(Gdx.files.internal("mouseup.png"))));
            put(Direction.LEFT, new TextureRegion(new Texture(Gdx.files.internal("mouseleft.png"))));
            put(Direction.RIGHT, new TextureRegion(new Texture(Gdx.files.internal("mouseright.png"))));
        }
        };

        /**текстуры секторов*/
        tex_sector = new HashMap<SectorType, TextureRegion>() {{
            put(SectorType.EMPTY, new TextureRegion(new Texture(Gdx.files.internal("empty.png"))));
            put(SectorType.WALL, new TextureRegion(new Texture(Gdx.files.internal("wall.png"))));
            put(SectorType.CLOSED_VER_DOOR, new TextureRegion(new Texture(Gdx.files.internal("door_ver.png"))));
            put(SectorType.CLOSED_GOR_DOOR, new TextureRegion(new Texture(Gdx.files.internal("door_gor.png"))));
            put(SectorType.OPENED_DOOR, new TextureRegion(new Texture(Gdx.files.internal("door_open.png"))));
        }
        };
        mygame.getLevel().getSectors()[17][3].setCrossRoad(true);
        mygame.getLevel().getSectors()[17][1].setCrossRoad(true);
        mygame.getLevel().getSectors()[9][1].setCrossRoad(true);
        mygame.getLevel().getSectors()[9][3].setCrossRoad(true);
        mygame.getLevel().getSectors()[9][6].setCrossRoad(true);
        mygame.getLevel().getSectors()[17][6].setCrossRoad(true);

    }

    @Override
    public void render () {
        batch.begin();

        /**отрисовка секторов и предметов*/
        for (int i = 0; i < mygame.getLevel().getSectors().length; i++)
            for (int j = 0; j < mygame.getLevel().getSectors()[0].length; j++) {
                batch.draw(tex_sector.get(mygame.getLevel().getSectors()[j][i].getSectorType()),
                        j*CELL_SIZE,
                        i*CELL_SIZE,
                        CELL_SIZE, CELL_SIZE);
                if (mygame.getLevel().getSectors()[j][i].getItem() instanceof Cheese) {
                    batch.draw(tex_cheese,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (mygame.getLevel().getSectors()[j][i].getItem() instanceof Key) {
                    batch.draw(tex_key,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (mygame.getLevel().getSectors()[j][i].getItem() instanceof DistractGun) {
                    batch.draw(tex_weaponDistractor,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (mygame.getLevel().getSectors()[j][i].getItem() instanceof FreezerGun) {
                    batch.draw(tex_weaponFreezer,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
                else if (mygame.getLevel().getSectors()[j][i].getItem() instanceof SlowGun) {
                    batch.draw(tex_weaponSlower,
                            j*CELL_SIZE,
                            i*CELL_SIZE,
                            CELL_SIZE, CELL_SIZE);
                }
            }

        /**отрисовка кошек*/
        batch.draw(tex_cat.get(mygame.getCat().getDirection()),mygame.getCat().getX(),mygame.getCat().getY());
/*        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mygame.getCat().setDirection(Direction.RIGHT);
            mygame.getCat().setX(mygame.getCat().getX() + (int) (mygame.getCat().getSpeed() * DELTA_TIME));
            Collision.Collision(mygame.getCat(), mygame.getLevel());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mygame.getCat().setDirection(Direction.LEFT);
            mygame.getCat().setX(mygame.getCat().getX() - (int) (mygame.getCat().getSpeed() * DELTA_TIME));
            Collision.Collision(mygame.getCat(), mygame.getLevel());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            mygame.getCat().setDirection(Direction.UP);
            mygame.getCat().setY(mygame.getCat().getY() + (int) (mygame.getCat().getSpeed() * DELTA_TIME));
            Collision.Collision(mygame.getCat(), mygame.getLevel());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            mygame.getCat().setDirection(Direction.DOWN);
            mygame.getCat().setY(mygame.getCat().getY() - (int) (mygame.getCat().getSpeed() * DELTA_TIME));
            Collision.Collision(mygame.getCat(), mygame.getLevel());
        }*/

        mygame.getCat().CatMove(mygame.getLevel(),mygame.getMouse().getX(),mygame.getMouse().getY());


        /**отрисовка мыши*/

        batch.draw(tex_mouse.get(mygame.getMouse().getDirection()),mygame.getMouse().getX(),mygame.getMouse().getY());

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mygame.getMouse().setDirection(Direction.RIGHT);
            mygame.getMouse().setX(mygame.getMouse().getX() + (int) (mygame.getMouse().getSpeed() * DELTA_TIME));
            /*Перемещаем перса на некоторое расстояние вправо
            а затем проверяем, не "наехал" ли он на объект, на который нельзя наезжать
             getDeltaTime - время, прошедшее после последнего отрисованного кадра*/

        Collision.Collision(mygame.getMouse(), mygame.getLevel());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mygame.getMouse().setDirection(Direction.LEFT);
            mygame.getMouse().setX(mygame.getMouse().getX() - (int) (mygame.getMouse().getSpeed() * DELTA_TIME));
            Collision.Collision(mygame.getMouse(), mygame.getLevel());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            mygame.getMouse().setDirection(Direction.UP);
            mygame.getMouse().setY(mygame.getMouse().getY() + (int) (mygame.getMouse().getSpeed() * DELTA_TIME));
            Collision.Collision(mygame.getMouse(), mygame.getLevel());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            mygame.getMouse().setDirection(Direction.DOWN);
            mygame.getMouse().setY(mygame.getMouse().getY() - (int) (mygame.getMouse().getSpeed() * DELTA_TIME));
            Collision.Collision(mygame.getMouse(), mygame.getLevel());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            int index=mygame.getMouse().getWeapons().indexOf(mygame.getMouse().getWeapon());//index of current weapon
           // if (mygame.getMouse().getWeapons()) проверка на выход за пределы листа, когда только одно оружие!
            mygame.getMouse().setWeapon(mygame.getMouse().getWeapons().get(index-1));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            int index=mygame.getMouse().getWeapons().indexOf(mygame.getMouse().getWeapon());//index of current weapon
            // if (mygame.getMouse().getWeapons()) проверка на выход за пределы листа, когда только одно оружие!
            mygame.getMouse().setWeapon(mygame.getMouse().getWeapons().get(index+1));
        }

        batch.end();
    }
}
