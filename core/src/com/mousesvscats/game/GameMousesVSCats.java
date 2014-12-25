package com.mousesvscats.game;


import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mousesvscats.game.GameLogic.*;
import com.mousesvscats.game.GameLogic.items.*;

import java.util.HashMap;

public class GameMousesVSCats extends ApplicationAdapter {

    private Stage stage;
    private Button button_level_1, button_level_2, button_level_3;

    private GameClass mygame;
    SpriteBatch batch;
    static final double DELTA_TIME = 0.016; //частота обновления кадров
    private static final int CELL_SIZE = 16;
    public static final int BAR_WIDTH = 300;
    private int selected_level;

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

    /**текстуры для панели*/
    TextureRegion tex_title;
    TextureRegion tex_score;
    TextureRegion tex_weapon_menu;
    TextureRegion tex_weapon_cell;
    TextureRegion tex_weapon_current;
    TextureRegion tex_cheese_menu;
    TextureRegion tex_level_1;
    TextureRegion tex_level_2;
    TextureRegion tex_level_3;
    TextureRegion tex_level_1_selected;
    TextureRegion tex_level_2_selected;
    TextureRegion tex_level_3_selected;
    HashMap<Integer, TextureRegion> tex_score_number;

    @Override
    public void create () {
        mygame = new GameClass(new Labyrinth("level_1.txt"));
        selected_level = 1;
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

        /**текстуры для панели*/
        tex_title = new TextureRegion(new Texture(Gdx.files.internal("title.png")));
        tex_score = new TextureRegion(new Texture(Gdx.files.internal("score.png")));
        tex_weapon_menu = new TextureRegion(new Texture(Gdx.files.internal("weapon.png")));
        tex_weapon_cell = new TextureRegion(new Texture(Gdx.files.internal("cell.png")));
        tex_weapon_current = new TextureRegion(new Texture(Gdx.files.internal("current.png")));
        tex_cheese_menu = new TextureRegion(new Texture(Gdx.files.internal("cheese_menu.png")));
        tex_level_1 = new TextureRegion(new Texture(Gdx.files.internal("level_1.png")));
        tex_level_2 = new TextureRegion(new Texture(Gdx.files.internal("level_2.png")));
        tex_level_3 = new TextureRegion(new Texture(Gdx.files.internal("level_3.png")));
        tex_level_1_selected = new TextureRegion(new Texture(Gdx.files.internal("level_1_selected.png")));
        tex_level_2_selected = new TextureRegion(new Texture(Gdx.files.internal("level_2_selected.png")));
        tex_level_3_selected = new TextureRegion(new Texture(Gdx.files.internal("level_3_selected.png")));
        tex_score_number = new HashMap<Integer, TextureRegion>() {{
            put(0, new TextureRegion(new Texture(Gdx.files.internal("0.png"))));
            put(1, new TextureRegion(new Texture(Gdx.files.internal("1.png"))));
            put(2, new TextureRegion(new Texture(Gdx.files.internal("2.png"))));
            put(3, new TextureRegion(new Texture(Gdx.files.internal("3.png"))));
            put(4, new TextureRegion(new Texture(Gdx.files.internal("4.png"))));
            put(5, new TextureRegion(new Texture(Gdx.files.internal("5.png"))));
            put(6, new TextureRegion(new Texture(Gdx.files.internal("6.png"))));
            put(7, new TextureRegion(new Texture(Gdx.files.internal("7.png"))));
            put(8, new TextureRegion(new Texture(Gdx.files.internal("8.png"))));
            put(9, new TextureRegion(new Texture(Gdx.files.internal("9.png"))));
        }
        };

        /**создание кнопок*/
        /*кнопка Level 1*/
        button_level_1 = new Button();
        button_level_1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                mygame = new GameClass(new Labyrinth("level_1.txt"));
                selected_level = 1;
            }
        });
        button_level_1.setPosition(Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight()
                        - tex_weapon_menu.getRegionHeight()
                        - tex_cheese_menu.getRegionHeight()
                        - tex_level_1.getRegionHeight());
        button_level_1.setSize(tex_level_1.getRegionWidth(),
                tex_level_1.getRegionHeight());

        /*кнопка Level 2*/
        button_level_2 = new Button();
        button_level_2.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                mygame = new GameClass(new Labyrinth("level_2.txt"));
                selected_level = 2;
            }
        });
        button_level_2.setPosition(Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight()
                        - tex_weapon_menu.getRegionHeight()
                        - tex_cheese_menu.getRegionHeight()
                        - tex_level_1.getRegionHeight()
                        - tex_level_2.getRegionHeight());
        button_level_2.setSize(tex_level_2.getRegionWidth(),
                tex_level_2.getRegionHeight());

        /*кнопка Level 3*/
        button_level_3 = new Button();
        button_level_3.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                mygame = new GameClass(new Labyrinth("level_3.txt"));
                selected_level = 3;
            }
        });
        button_level_3.setPosition(Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight()
                        - tex_weapon_menu.getRegionHeight()
                        - tex_cheese_menu.getRegionHeight()
                        - tex_level_1.getRegionHeight()
                        - tex_level_2.getRegionHeight()
                        - tex_level_3.getRegionHeight());
        button_level_3.setSize(tex_level_3.getRegionWidth(),
                tex_level_3.getRegionHeight());

        /*добавляем события смены оружия по кнопкам Q,W*/
        stage = new Stage() {
            @Override
            public boolean keyDown(int keyCode)
            {
                if (keyCode == Input.Keys.Q) {
                    if (mygame.getMouse().getWeapons().size() != 0) {
                        int index = mygame.getMouse().getWeapons().indexOf(mygame.getMouse().getCurrentWeapon());//index of current weapon
                        if (index != 0)                                                                         //проверка на выход за пределы листа
                            mygame.getMouse().setCurrentWeapon(mygame.getMouse().getWeapons().get(index - 1));
                        else
                            mygame.getMouse().setCurrentWeapon(mygame.getMouse().getWeapons().get(mygame.getMouse().getWeapons().size() - 1));
                    }
                }

                if (keyCode == Input.Keys.W) {
                    if (mygame.getMouse().getWeapons().size() != 0) {
                        int index = mygame.getMouse().getWeapons().indexOf(mygame.getMouse().getCurrentWeapon());//index of current weapon
                        if (index != mygame.getMouse().getWeapons().size() - 1)                                 //проверка на выход за пределы листа
                            mygame.getMouse().setCurrentWeapon(mygame.getMouse().getWeapons().get(index + 1));
                        else
                            mygame.getMouse().setCurrentWeapon(mygame.getMouse().getWeapons().get(0));

                    }
                }
                return true;
            }
        };

        /*добавляем кнопки переключения уровней*/
        stage.addActor(button_level_1);
        stage.addActor(button_level_2);
        stage.addActor(button_level_3);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        /**отрисовка панели меню*/
        /*заголовок*/
        batch.draw(tex_title,
                Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                        - tex_title.getRegionHeight(),
                tex_title.getRegionWidth(),
                tex_title.getRegionHeight());
        /*очки*/
        batch.draw(tex_score,
                Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight(),
                tex_score.getRegionWidth(),
                tex_score.getRegionHeight());
        String str_score = String.valueOf(mygame.getLevel().getScore());
        int distanse = Labyrinth.LABYRINTH_WIDTH * CELL_SIZE + tex_score.getRegionWidth()*2;
        for (int i = 0; i < str_score.length(); i++){
            switch (str_score.charAt(i)) {
                case '0': batch.draw(tex_score_number.get(0),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(0).getRegionWidth(),
                        tex_score_number.get(0).getRegionHeight());
                    break;
                case '1': batch.draw(tex_score_number.get(1),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(1).getRegionWidth(),
                        tex_score_number.get(1).getRegionHeight());
                    break;
                case '2': batch.draw(tex_score_number.get(2),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(2).getRegionWidth(),
                        tex_score_number.get(2).getRegionHeight());
                    break;
                case '3': batch.draw(tex_score_number.get(3),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(3).getRegionWidth(),
                        tex_score_number.get(3).getRegionHeight());
                    break;
                case '4': batch.draw(tex_score_number.get(4),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(4).getRegionWidth(),
                        tex_score_number.get(4).getRegionHeight());
                    break;
                case '5': batch.draw(tex_score_number.get(5),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(5).getRegionWidth(),
                        tex_score_number.get(5).getRegionHeight());
                    break;
                case '6': batch.draw(tex_score_number.get(6),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(6).getRegionWidth(),
                        tex_score_number.get(6).getRegionHeight());
                    break;
                case '7': batch.draw(tex_score_number.get(7),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(7).getRegionWidth(),
                        tex_score_number.get(7).getRegionHeight());
                    break;
                case '8': batch.draw(tex_score_number.get(8),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(8).getRegionWidth(),
                        tex_score_number.get(8).getRegionHeight());
                    break;
                case '9': batch.draw(tex_score_number.get(9),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight(),
                        tex_score_number.get(9).getRegionWidth(),
                        tex_score_number.get(9).getRegionHeight());
                    break;
                default:
                    break;
            }
            distanse += tex_score_number.get(0).getRegionWidth();
        }

        /*оружие*/
        batch.draw(tex_weapon_menu,
                Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight()
                        - tex_weapon_menu.getRegionHeight(),
                tex_weapon_menu.getRegionWidth(),
                tex_weapon_menu.getRegionHeight());
        for (int i = 0; i < mygame.getLevel().getWeaponCount(); i++) {
            batch.draw(tex_weapon_cell,
                    Labyrinth.LABYRINTH_WIDTH * CELL_SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i,
                    Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                            - tex_title.getRegionHeight()
                            - tex_score.getRegionHeight()
                            - tex_weapon_cell.getRegionHeight(),
                    tex_weapon_cell.getRegionWidth(),
                    tex_weapon_cell.getRegionHeight());
        }
        for (int i = 0; i < mygame.getMouse().getWeapons().size(); i++){
            if (mygame.getMouse().getWeapons().get(i) instanceof DistractGun)
                batch.draw(tex_weaponDistractor,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_cell.getRegionHeight(),
                        tex_weapon_cell.getRegionWidth() - 10,
                        tex_weapon_cell.getRegionHeight() - 10);
            if (mygame.getMouse().getWeapons().get(i) instanceof FreezerGun)
                batch.draw(tex_weaponFreezer,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_cell.getRegionHeight(),
                        tex_weapon_cell.getRegionWidth() - 10,
                        tex_weapon_cell.getRegionHeight() - 10);
            if (mygame.getMouse().getWeapons().get(i) instanceof SlowGun)
                batch.draw(tex_weaponSlower,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_cell.getRegionHeight(),
                        tex_weapon_cell.getRegionWidth() - 10,
                        tex_weapon_cell.getRegionHeight() - 10);
            if (mygame.getMouse().getCurrentWeapon().getClass() == mygame.getMouse().getWeapons().get(i).getClass())
                batch.draw(tex_weapon_current,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_cell.getRegionHeight(),
                        tex_weapon_current.getRegionWidth(),
                        tex_weapon_current.getRegionHeight());
        }



        /*сыр*/
        batch.draw(tex_cheese_menu,
                Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight()
                        - tex_weapon_menu.getRegionHeight()
                        - tex_cheese_menu.getRegionHeight(),
                tex_cheese_menu.getRegionWidth(),
                tex_cheese_menu.getRegionHeight());
        String str_cheese = String.valueOf(mygame.getLevel().cheeseCollected());
        distanse = Labyrinth.LABYRINTH_WIDTH * CELL_SIZE + tex_score.getRegionWidth()*2;
        for (int i = 0; i < str_cheese.length(); i++){
            switch (str_cheese.charAt(i)) {
                case '0': batch.draw(tex_score_number.get(0),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(0).getRegionWidth(),
                        tex_score_number.get(0).getRegionHeight());
                    break;
                case '1': batch.draw(tex_score_number.get(1),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(1).getRegionWidth(),
                        tex_score_number.get(1).getRegionHeight());
                    break;
                case '2': batch.draw(tex_score_number.get(2),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(2).getRegionWidth(),
                        tex_score_number.get(2).getRegionHeight());
                    break;
                case '3': batch.draw(tex_score_number.get(3),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(3).getRegionWidth(),
                        tex_score_number.get(3).getRegionHeight());
                    break;
                case '4': batch.draw(tex_score_number.get(4),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(4).getRegionWidth(),
                        tex_score_number.get(4).getRegionHeight());
                    break;
                case '5': batch.draw(tex_score_number.get(5),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(5).getRegionWidth(),
                        tex_score_number.get(5).getRegionHeight());
                    break;
                case '6': batch.draw(tex_score_number.get(6),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(6).getRegionWidth(),
                        tex_score_number.get(6).getRegionHeight());
                    break;
                case '7': batch.draw(tex_score_number.get(7),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(7).getRegionWidth(),
                        tex_score_number.get(7).getRegionHeight());
                    break;
                case '8': batch.draw(tex_score_number.get(8),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(8).getRegionWidth(),
                        tex_score_number.get(8).getRegionHeight());
                    break;
                case '9': batch.draw(tex_score_number.get(9),
                        distanse,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight(),
                        tex_score_number.get(9).getRegionWidth(),
                        tex_score_number.get(9).getRegionHeight());
                    break;
                default:
                    break;
            }
            distanse += tex_score_number.get(0).getRegionWidth();
        }

        /*кнопки выбора уровня*/
        switch (selected_level) {
            case 1:
                batch.draw(tex_level_1_selected,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1_selected.getRegionHeight(),
                        tex_level_1_selected.getRegionWidth(),
                        tex_level_1_selected.getRegionHeight());
                batch.draw(tex_level_2,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight()
                                - tex_level_2.getRegionHeight(),
                        tex_level_2.getRegionWidth(),
                        tex_level_2.getRegionHeight());
                batch.draw(tex_level_3,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight()
                                - tex_level_2.getRegionHeight()
                                - tex_level_3.getRegionHeight(),
                        tex_level_3.getRegionWidth(),
                        tex_level_3.getRegionHeight());
                break;
            case 2:
                batch.draw(tex_level_1,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight(),
                        tex_level_1.getRegionWidth(),
                        tex_level_1.getRegionHeight());
                batch.draw(tex_level_2_selected,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight()
                                - tex_level_2_selected.getRegionHeight(),
                        tex_level_2_selected.getRegionWidth(),
                        tex_level_2_selected.getRegionHeight());
                batch.draw(tex_level_3,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight()
                                - tex_level_2.getRegionHeight()
                                - tex_level_3.getRegionHeight(),
                        tex_level_3.getRegionWidth(),
                        tex_level_3.getRegionHeight());
                break;
            case 3:
                batch.draw(tex_level_1,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight(),
                        tex_level_1.getRegionWidth(),
                        tex_level_1.getRegionHeight());
                batch.draw(tex_level_2,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight()
                                - tex_level_2.getRegionHeight(),
                        tex_level_2.getRegionWidth(),
                        tex_level_2.getRegionHeight());
                batch.draw(tex_level_3_selected,
                        Labyrinth.LABYRINTH_WIDTH * CELL_SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * CELL_SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight()
                                - tex_level_2.getRegionHeight()
                                - tex_level_3_selected.getRegionHeight(),
                        tex_level_3_selected.getRegionWidth(),
                        tex_level_3_selected.getRegionHeight());
                break;
            default: break;
        }


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
        for (int i=0;i<mygame.howManyCats;i++) {
            batch.draw(tex_cat.get(mygame.getCat(i).getDirection()), mygame.getCat(i).getX(), mygame.getCat(i).getY());
            mygame.getCat(i).CatMove(mygame.getLevel(), mygame.getMouse().getX(), mygame.getMouse().getY());
        }
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

        batch.end();
    }
}
