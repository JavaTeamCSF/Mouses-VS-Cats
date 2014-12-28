package com.mousesvscats.game;


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
    private Button button_level_1, button_level_2, button_level_3; //кнопки переключения уровней

    private GameClass my_game; //текущая игра
    SpriteBatch batch;
    static final double DELTA_TIME = 0.016; //частота обновления кадров
    public static final int BAR_WIDTH = 300; //ширина панели меню
    private int selected_level; //текущий выбранный уровень

    /**текстуры предметов*/
    TextureRegion tex_cheese;
    TextureRegion tex_weaponSlower;
    TextureRegion tex_weaponKiller;
    TextureRegion tex_weaponFreezer;
    TextureRegion tex_key;
    HashMap<TrapType, TextureRegion> tex_trap;

    /**текстуры кошек*/
    HashMap<Integer, TextureRegion> tex_cat_up;
    HashMap<Integer, TextureRegion> tex_cat_down;
    HashMap<Integer, TextureRegion> tex_cat_left;
    HashMap<Integer, TextureRegion> tex_cat_right;
    TextureRegion tex_blood;
    TextureRegion tex_frozen;

    /**текстуры мыши*/
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
    TextureRegion tex_win;
    TextureRegion tex_game_over;


    @Override
    public void create () {

        my_game = new GameClass(new Labyrinth("level_1.txt"));
        selected_level = 1;
        batch = new SpriteBatch();

        /**текстуры предметов*/
        tex_cheese = new TextureRegion(new Texture(Gdx.files.internal("cheese.png")));
        tex_weaponSlower = new TextureRegion(new Texture(Gdx.files.internal("slow.gif")));
        tex_weaponKiller = new TextureRegion(new Texture(Gdx.files.internal("distract.png")));
        tex_weaponFreezer = new TextureRegion(new Texture(Gdx.files.internal("freezegun.png")));
        tex_key = new TextureRegion(new Texture(Gdx.files.internal("key.png")));
        tex_trap = new HashMap<TrapType, TextureRegion>() {{
            put(TrapType.KILLER, new TextureRegion(new Texture(Gdx.files.internal("trap_distract.png"))));
            put(TrapType.FREEZER, new TextureRegion(new Texture(Gdx.files.internal("trap_freezer.png"))));
            put(TrapType.SLOWER, new TextureRegion(new Texture(Gdx.files.internal("trap_slow.png"))));
        }
        };

        /**текстуры кошек*/
        tex_cat_up = new HashMap<Integer, TextureRegion>() {{
            put(0, new TextureRegion(new Texture(Gdx.files.internal("cat1up.png"))));
            put(1, new TextureRegion(new Texture(Gdx.files.internal("cat2up.png"))));
            put(2, new TextureRegion(new Texture(Gdx.files.internal("cat3up.png"))));
        }
        };
        tex_cat_down = new HashMap<Integer, TextureRegion>() {{
            put(0, new TextureRegion(new Texture(Gdx.files.internal("cat1down.png"))));
            put(1, new TextureRegion(new Texture(Gdx.files.internal("cat2down.png"))));
            put(2, new TextureRegion(new Texture(Gdx.files.internal("cat3down.png"))));
        }
        };
        tex_cat_left = new HashMap<Integer, TextureRegion>() {{
            put(0, new TextureRegion(new Texture(Gdx.files.internal("cat1left.png"))));
            put(1, new TextureRegion(new Texture(Gdx.files.internal("cat2left.png"))));
            put(2, new TextureRegion(new Texture(Gdx.files.internal("cat3left.png"))));
        }
        };
        tex_cat_right = new HashMap<Integer, TextureRegion>() {{
            put(0, new TextureRegion(new Texture(Gdx.files.internal("cat1right.png"))));
            put(1, new TextureRegion(new Texture(Gdx.files.internal("cat2right.png"))));
            put(2, new TextureRegion(new Texture(Gdx.files.internal("cat3right.png"))));
        }
        };
        tex_blood = new TextureRegion(new Texture(Gdx.files.internal("blood.png")));
        tex_frozen = new TextureRegion(new Texture(Gdx.files.internal("cat_freezed.png")));

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
        tex_win = new TextureRegion(new Texture(Gdx.files.internal("you_win.png")));
        tex_game_over = new TextureRegion(new Texture(Gdx.files.internal("game_over.png")));

        /**создание кнопок*/
        /*кнопка Level 1*/
        button_level_1 = new Button();
        button_level_1.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                my_game = new GameClass(new Labyrinth("level_1.txt"));
                selected_level = 1;
            }
        });
        button_level_1.setPosition(Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
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
                my_game = new GameClass(new Labyrinth("level_2.txt"));
                selected_level = 2;
            }
        });
        button_level_2.setPosition(Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
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
                my_game = new GameClass(new Labyrinth("level_3.txt"));
                selected_level = 3;
            }
        });
        button_level_3.setPosition(Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight()
                        - tex_weapon_menu.getRegionHeight()
                        - tex_cheese_menu.getRegionHeight()
                        - tex_level_1.getRegionHeight()
                        - tex_level_2.getRegionHeight()
                        - tex_level_3.getRegionHeight());
        button_level_3.setSize(tex_level_3.getRegionWidth(),
                tex_level_3.getRegionHeight());

        /**добавляем события смены оружия по кнопкам Q,W*/
        stage = new Stage() {
            @Override
            public boolean keyDown(int keyCode)
            {
                /**предыдущее оружие*/
                if (keyCode == Input.Keys.Q) {
                    my_game.getMouse().prevWeapon();
                }

                /**следующее оружие*/
                if (keyCode == Input.Keys.W) {
                    my_game.getMouse().nextWeapon();
                }

                /**установить ловушку*/
                if (keyCode == Input.Keys.E) {
                    my_game.getMouse().setTrapInSector(my_game.getLevel());
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
                Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                        - tex_title.getRegionHeight(),
                tex_title.getRegionWidth(),
                tex_title.getRegionHeight());
        /*очки*/
        batch.draw(tex_score,
                Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight(),
                tex_score.getRegionWidth(),
                tex_score.getRegionHeight());
        String str_score = String.valueOf(my_game.getLevel().getScore());
        int distanse = Labyrinth.LABYRINTH_WIDTH * Sector.SIZE + tex_score.getRegionWidth() * 2;
        for (int i = 0; i < str_score.length(); i++) {
            switch (str_score.charAt(i)) {
                case '0':
                    batch.draw(tex_score_number.get(0),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight(),
                            tex_score_number.get(0).getRegionWidth(),
                            tex_score_number.get(0).getRegionHeight());
                    break;
                case '1':
                    batch.draw(tex_score_number.get(1),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight(),
                            tex_score_number.get(1).getRegionWidth(),
                            tex_score_number.get(1).getRegionHeight());
                    break;
                case '2':
                    batch.draw(tex_score_number.get(2),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight(),
                            tex_score_number.get(2).getRegionWidth(),
                            tex_score_number.get(2).getRegionHeight());
                    break;
                case '3':
                    batch.draw(tex_score_number.get(3),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight(),
                            tex_score_number.get(3).getRegionWidth(),
                            tex_score_number.get(3).getRegionHeight());
                    break;
                case '4':
                    batch.draw(tex_score_number.get(4),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight(),
                            tex_score_number.get(4).getRegionWidth(),
                            tex_score_number.get(4).getRegionHeight());
                    break;
                case '5':
                    batch.draw(tex_score_number.get(5),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight(),
                            tex_score_number.get(5).getRegionWidth(),
                            tex_score_number.get(5).getRegionHeight());
                    break;
                case '6':
                    batch.draw(tex_score_number.get(6),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight(),
                            tex_score_number.get(6).getRegionWidth(),
                            tex_score_number.get(6).getRegionHeight());
                    break;
                case '7':
                    batch.draw(tex_score_number.get(7),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight(),
                            tex_score_number.get(7).getRegionWidth(),
                            tex_score_number.get(7).getRegionHeight());
                    break;
                case '8':
                    batch.draw(tex_score_number.get(8),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight(),
                            tex_score_number.get(8).getRegionWidth(),
                            tex_score_number.get(8).getRegionHeight());
                    break;
                case '9':
                    batch.draw(tex_score_number.get(9),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
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
                Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight()
                        - tex_weapon_menu.getRegionHeight(),
                tex_weapon_menu.getRegionWidth(),
                tex_weapon_menu.getRegionHeight());
        for (int i = 0; i < my_game.getLevel().getWeaponCount(); i++) {
            batch.draw(tex_weapon_cell,
                    Labyrinth.LABYRINTH_WIDTH * Sector.SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i,
                    Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                            - tex_title.getRegionHeight()
                            - tex_score.getRegionHeight()
                            - tex_weapon_cell.getRegionHeight(),
                    tex_weapon_cell.getRegionWidth(),
                    tex_weapon_cell.getRegionHeight());
        }
        for (int i = 0; i < my_game.getMouse().getWeapons().size(); i++) {
            if (my_game.getMouse().getWeapons().get(i) instanceof KillerGun)
                batch.draw(tex_weaponKiller,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i + 5,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_cell.getRegionHeight() + 5,
                        tex_weapon_cell.getRegionWidth() - 10,
                        tex_weapon_cell.getRegionHeight() - 10);
            if (my_game.getMouse().getWeapons().get(i) instanceof FreezerGun)
                batch.draw(tex_weaponFreezer,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i + 7,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_cell.getRegionHeight() + 7,
                        tex_weapon_cell.getRegionWidth() - 15,
                        tex_weapon_cell.getRegionHeight() - 15);
            if (my_game.getMouse().getWeapons().get(i) instanceof SlowGun)
                batch.draw(tex_weaponSlower,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i + 5,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_cell.getRegionHeight() + 5,
                        tex_weapon_cell.getRegionWidth() - 10,
                        tex_weapon_cell.getRegionHeight() - 10);
            if (my_game.getMouse().getCurrentWeapon().getClass() == my_game.getMouse().getWeapons().get(i).getClass())
                batch.draw(tex_weapon_current,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE + tex_weapon_menu.getRegionWidth() + tex_weapon_cell.getRegionWidth() * i,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_cell.getRegionHeight(),
                        tex_weapon_current.getRegionWidth(),
                        tex_weapon_current.getRegionHeight());
        }



        /*сыр*/
        batch.draw(tex_cheese_menu,
                Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                        - tex_title.getRegionHeight()
                        - tex_score.getRegionHeight()
                        - tex_weapon_menu.getRegionHeight()
                        - tex_cheese_menu.getRegionHeight(),
                tex_cheese_menu.getRegionWidth(),
                tex_cheese_menu.getRegionHeight());
        String str_cheese = String.valueOf(my_game.getLevel().cheeseCollected());
        distanse = Labyrinth.LABYRINTH_WIDTH * Sector.SIZE + tex_score.getRegionWidth() * 2;
        for (int i = 0; i < str_cheese.length(); i++) {
            switch (str_cheese.charAt(i)) {
                case '0':
                    batch.draw(tex_score_number.get(0),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight(),
                            tex_score_number.get(0).getRegionWidth(),
                            tex_score_number.get(0).getRegionHeight());
                    break;
                case '1':
                    batch.draw(tex_score_number.get(1),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight(),
                            tex_score_number.get(1).getRegionWidth(),
                            tex_score_number.get(1).getRegionHeight());
                    break;
                case '2':
                    batch.draw(tex_score_number.get(2),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight(),
                            tex_score_number.get(2).getRegionWidth(),
                            tex_score_number.get(2).getRegionHeight());
                    break;
                case '3':
                    batch.draw(tex_score_number.get(3),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight(),
                            tex_score_number.get(3).getRegionWidth(),
                            tex_score_number.get(3).getRegionHeight());
                    break;
                case '4':
                    batch.draw(tex_score_number.get(4),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight(),
                            tex_score_number.get(4).getRegionWidth(),
                            tex_score_number.get(4).getRegionHeight());
                    break;
                case '5':
                    batch.draw(tex_score_number.get(5),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight(),
                            tex_score_number.get(5).getRegionWidth(),
                            tex_score_number.get(5).getRegionHeight());
                    break;
                case '6':
                    batch.draw(tex_score_number.get(6),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight(),
                            tex_score_number.get(6).getRegionWidth(),
                            tex_score_number.get(6).getRegionHeight());
                    break;
                case '7':
                    batch.draw(tex_score_number.get(7),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight(),
                            tex_score_number.get(7).getRegionWidth(),
                            tex_score_number.get(7).getRegionHeight());
                    break;
                case '8':
                    batch.draw(tex_score_number.get(8),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight(),
                            tex_score_number.get(8).getRegionWidth(),
                            tex_score_number.get(8).getRegionHeight());
                    break;
                case '9':
                    batch.draw(tex_score_number.get(9),
                            distanse,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
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
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1_selected.getRegionHeight(),
                        tex_level_1_selected.getRegionWidth(),
                        tex_level_1_selected.getRegionHeight());
                batch.draw(tex_level_2,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight()
                                - tex_level_2.getRegionHeight(),
                        tex_level_2.getRegionWidth(),
                        tex_level_2.getRegionHeight());
                batch.draw(tex_level_3,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
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
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight(),
                        tex_level_1.getRegionWidth(),
                        tex_level_1.getRegionHeight());
                batch.draw(tex_level_2_selected,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight()
                                - tex_level_2_selected.getRegionHeight(),
                        tex_level_2_selected.getRegionWidth(),
                        tex_level_2_selected.getRegionHeight());
                batch.draw(tex_level_3,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
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
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight(),
                        tex_level_1.getRegionWidth(),
                        tex_level_1.getRegionHeight());
                batch.draw(tex_level_2,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                - tex_title.getRegionHeight()
                                - tex_score.getRegionHeight()
                                - tex_weapon_menu.getRegionHeight()
                                - tex_cheese_menu.getRegionHeight()
                                - tex_level_1.getRegionHeight()
                                - tex_level_2.getRegionHeight(),
                        tex_level_2.getRegionWidth(),
                        tex_level_2.getRegionHeight());
                batch.draw(tex_level_3_selected,
                        Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                        Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
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
            default:
                break;
        }


        /**отрисовка секторов и предметов*/
        for (int i = 0; i < my_game.getLevel().getSectors().length; i++)
            for (int j = 0; j < my_game.getLevel().getSectors()[0].length; j++) {
                batch.draw(tex_sector.get(my_game.getLevel().getSectors()[j][i].getSectorType()),
                        j * Sector.SIZE,
                        i * Sector.SIZE,
                        Sector.SIZE, Sector.SIZE);
                if (my_game.getLevel().getSectors()[j][i].getTrapType() != null) {
                    batch.draw(tex_trap.get(my_game.getLevel().getSectors()[j][i].getTrapType()),
                            j * Sector.SIZE,
                            i * Sector.SIZE,
                            Sector.SIZE, Sector.SIZE);
                }
                if (my_game.getLevel().getSectors()[j][i].getItem() instanceof Cheese) {
                    batch.draw(tex_cheese,
                            j * Sector.SIZE,
                            i * Sector.SIZE,
                            Sector.SIZE, Sector.SIZE);
                } else if (my_game.getLevel().getSectors()[j][i].getItem() instanceof Key) {
                    batch.draw(tex_key,
                            j * Sector.SIZE,
                            i * Sector.SIZE,
                            Sector.SIZE, Sector.SIZE);
                } else if (my_game.getLevel().getSectors()[j][i].getItem() instanceof KillerGun) {
                    batch.draw(tex_weaponKiller,
                            j * Sector.SIZE,
                            i * Sector.SIZE,
                            Sector.SIZE, Sector.SIZE);
                } else if (my_game.getLevel().getSectors()[j][i].getItem() instanceof FreezerGun) {
                    batch.draw(tex_weaponFreezer,
                            j * Sector.SIZE,
                            i * Sector.SIZE,
                            Sector.SIZE, Sector.SIZE);
                } else if (my_game.getLevel().getSectors()[j][i].getItem() instanceof SlowGun) {
                    batch.draw(tex_weaponSlower,
                            j * Sector.SIZE,
                            i * Sector.SIZE,
                            Sector.SIZE, Sector.SIZE);
                }
            }



        /**отрисовка кошек*/
        for (int i = 0; i < GameClass.howManyCats; i++) {
            if (my_game.getCat(i).getCatType() == CatType.DEAD)
                batch.draw(tex_blood, my_game.getCat(i).getX(), my_game.getCat(i).getY());
            else if (my_game.getCat(i).getCatType() == CatType.FROZEN)
                batch.draw(tex_frozen, my_game.getCat(i).getX(), my_game.getCat(i).getY());
            else {
                if (my_game.getCat(i).getDirection() == Direction.UP)
                    batch.draw(tex_cat_up.get(i), my_game.getCat(i).getX(), my_game.getCat(i).getY());
                else if (my_game.getCat(i).getDirection() == Direction.DOWN)
                    batch.draw(tex_cat_down.get(i), my_game.getCat(i).getX(), my_game.getCat(i).getY());
                else if (my_game.getCat(i).getDirection() == Direction.LEFT)
                    batch.draw(tex_cat_left.get(i), my_game.getCat(i).getX(), my_game.getCat(i).getY());
                else if (my_game.getCat(i).getDirection() == Direction.RIGHT)
                    batch.draw(tex_cat_right.get(i), my_game.getCat(i).getX(), my_game.getCat(i).getY());
            }

        }

        /**отрисовка мыши*/
        batch.draw(tex_mouse.get(my_game.getMouse().getDirection()), my_game.getMouse().getX(), my_game.getMouse().getY());

        /**Проверка на смерть/выигрыш*/
        if (!my_game.getMouse().isDead() && !my_game.getMouse().isWin()) {

            /**передвижение кошек*/
            for (int i = 0; i < GameClass.howManyCats; i++) {
                my_game.getCat(i).catMove(my_game.getLevel(), my_game.getMouse());
            }

            /**передвижение мыши*/
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                my_game.getMouse().setDirection(Direction.RIGHT);
                my_game.getMouse().setX((float) (my_game.getMouse().getX() + (my_game.getMouse().getSpeed() * DELTA_TIME)));
            /*Перемещаем перса на некоторое расстояние вправо
            а затем проверяем, не "наехал" ли он на объект, на который нельзя наезжать
             getDeltaTime - время, прошедшее после последнего отрисованного кадра (частота кадров)*/

                Collision.collision(my_game.getMouse(), my_game.getLevel());
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                my_game.getMouse().setDirection(Direction.LEFT);
                my_game.getMouse().setX((float) (my_game.getMouse().getX() - (my_game.getMouse().getSpeed() * DELTA_TIME)));
                Collision.collision(my_game.getMouse(), my_game.getLevel());
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                my_game.getMouse().setDirection(Direction.UP);
                my_game.getMouse().setY((float) (my_game.getMouse().getY() + (my_game.getMouse().getSpeed() * DELTA_TIME)));
                Collision.collision(my_game.getMouse(), my_game.getLevel());
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                my_game.getMouse().setDirection(Direction.DOWN);
                my_game.getMouse().setY((float) (my_game.getMouse().getY() - (my_game.getMouse().getSpeed() * DELTA_TIME)));
                Collision.collision(my_game.getMouse(), my_game.getLevel());
            }

            for (int i = 0; i < GameClass.howManyCats; i++) {
                if (Collision.defineMouseCatched(my_game.getMouse(), my_game.getCat(i)))
                    my_game.getMouse().setDead();
            }

        } else {
            /*если выиграл*/
            if (my_game.getMouse().isWin()) {
                batch.draw(tex_win, 20, 200);
            }
            /*если проиграл*/
            if (my_game.getMouse().isDead()) {
                batch.draw(tex_game_over, 20, 200);
            }

            /*сбрасываем кнопки выбора уровня*/
            switch (selected_level) {
                case 1:
                    batch.draw(tex_level_1,
                            Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight()
                                    - tex_level_1_selected.getRegionHeight(),
                            tex_level_1_selected.getRegionWidth(),
                            tex_level_1_selected.getRegionHeight());
                    break;
                case 2:
                    batch.draw(tex_level_2,
                            Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
                                    - tex_title.getRegionHeight()
                                    - tex_score.getRegionHeight()
                                    - tex_weapon_menu.getRegionHeight()
                                    - tex_cheese_menu.getRegionHeight()
                                    - tex_level_1.getRegionHeight()
                                    - tex_level_2_selected.getRegionHeight(),
                            tex_level_2_selected.getRegionWidth(),
                            tex_level_2_selected.getRegionHeight());
                    break;
                case 3:
                    batch.draw(tex_level_3,
                            Labyrinth.LABYRINTH_WIDTH * Sector.SIZE,
                            Labyrinth.LABYRINTH_HEIGHT * Sector.SIZE
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
                default:
                    break;
            }
        }

        batch.end();
    }
}
