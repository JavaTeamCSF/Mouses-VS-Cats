package com.mousesvscats.game.GameLogic;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mousesvscats.game.GameLogic.items.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Created by User on 05.12.2014.
 */
public class Labyrinth {
    public static final int LABYRINTH_WIDTH = 40; //ширина лабиринта
    public static final int LABYRINTH_HEIGHT = 40; //высота лабиринта

    private Random random = new Random();

    private Sector[][] sectors = new Sector[LABYRINTH_HEIGHT][LABYRINTH_WIDTH]; //лабиринт - матрица секторов

    private int weaponCount; //количество оружия на уровне
    private int cheeseCount; //количество сыра на уровне
    private int cheeseRemaining; //количество несобранного сыра
    private boolean doorOpened;
    private int score;

    /**Конструктор лабиринта*/
    public Labyrinth(String path){
        weaponCount = 3;
        score = 0;
        doorOpened = false;
        BufferedReader reader = null;
        try {
            File file = new File(path);
            reader = new BufferedReader(new FileReader(file));

            String[] elements = new String[LABYRINTH_HEIGHT];
            int temp;
            for (int i = LABYRINTH_HEIGHT-1; i>=0; i--) {
                elements[i] = reader.readLine();
                for (int j = 0; j < LABYRINTH_WIDTH; j++) {
                    temp = Character.getNumericValue(elements[i].charAt(j));
                    switch (temp) {
                        case 0:
                            sectors[j][i] = new Sector(SectorType.EMPTY); //МАГИЯ!! почему-то j и i нужно менять местами
                            sectors[j][i].setX(GameObject.Size * j);//копипаст
                            sectors[j][i].setY(GameObject.Size * i);
                            sectors[j][i].setAccessible(true);
                            break;
                        case 1:
                            sectors[j][i] = new Sector(SectorType.WALL); //если элемент матрицы = 1, то сектор - стена
                            sectors[j][i].setX(GameObject.Size * j);
                            sectors[j][i].setY(GameObject.Size * i);
                            sectors[j][i].setAccessible(false);
                            break;
                        case 2:
                            if (j == 0 || j == LABYRINTH_WIDTH - 1) {
                                sectors[j][i] = new Sector(SectorType.CLOSED_GOR_DOOR);  //если на левой/правой границе лабиринта
                            }
                            else if (i == 0 || i == LABYRINTH_HEIGHT - 1)
                                sectors[j][i] = new Sector(SectorType.CLOSED_VER_DOOR);  //если на верхней/нижней границе лабиринта
                            sectors[j][i].setX(GameObject.Size * j);
                            sectors[j][i].setY(GameObject.Size * i);
                            sectors[j][i].setAccessible(false);
                            break;
                        default:
                            sectors[j][i] = new Sector(SectorType.EMPTY);
                            sectors[j][i].setX(GameObject.Size * j);
                            sectors[j][i].setY(GameObject.Size * i);
                            sectors[j][i].setAccessible(true);
                            break;
                    }
                }
            }
        }
        catch (IOException e) {e.printStackTrace();}
        finally {
            assert reader != null;
            try {
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.generateItems(); //генерация рандомного расположения предметов на уровне
        this.setCrossRoadsAndCorners();
    }

    /**Получить матрицу лабиринта*/
    public Sector[][] getSectors(){return sectors;}

    /**Получаем рандомный пустой сектор*/
    public Vector2 getRandomEmptyCell() {
        Vector2 test;
        while (true) {
            test = new Vector2(random.nextInt(LABYRINTH_WIDTH), random.nextInt(LABYRINTH_HEIGHT));
            if (sectors[(int) test.x][(int) test.y].getSectorType() == SectorType.EMPTY && sectors[(int) test.x][(int) test.y].getItem() == null) {
                return test;
            }
        }
    }

    /**Генерируем рандомное расположение предметов на уровне*/
    public void generateItems() {
        cheeseCount = 5;
        cheeseRemaining = cheeseCount;

        for (int i = 0; i < cheeseCount; i++) {
            Vector2 coords = getRandomEmptyCell();
            sectors[(int)coords.x][(int)coords.y].setItem(new Cheese());
        }

        Vector2 rand;

        rand = getRandomEmptyCell();
        sectors[(int)rand.x][(int)rand.y].setItem(new Key());

        rand = getRandomEmptyCell();
        sectors[(int)rand.x][(int)rand.y].setItem(new DistractGun());

        rand = getRandomEmptyCell();
        sectors[(int)rand.x][(int)rand.y].setItem(new SlowGun());

        rand = getRandomEmptyCell();
        sectors[(int)rand.x][(int)rand.y].setItem(new FreezerGun());
    }

    /**Собрали сыр из сектора*/
    public void collectCheese(int x, int y) {
        this.cheeseRemaining--;
        this.collectItem(x,y);
        this.score += 500;
    }

    /**Получить количество очков*/
    public int getScore() {
        return score;
    }

    /**Получить количество оружия на уровне*/
    public int getWeaponCount() {
        return weaponCount;
    }

    /**Установить пустой сектор (после поднятия предмета)*/
    public void collectItem(int x, int y) {
        this.sectors[x][y].setItem(null);
    }

    /**Количество несобранного сыра*/
    public int cheeseLeft() {
        return cheeseRemaining;
    }

    /**Количество собранного сыра*/
    public int cheeseCollected() {
        return cheeseCount - cheeseRemaining;
    }

    /**Открываем дверь*/
    public void openDoor() {
        for (int j = 0; j < LABYRINTH_WIDTH; j++) {
            if (doorOpened)
                break;

            for (int i = 0; i < LABYRINTH_HEIGHT; i++) {
                if (sectors[i][j].getSectorType() == SectorType.CLOSED_GOR_DOOR || sectors[i][j].getSectorType() == SectorType.CLOSED_VER_DOOR) {
                    sectors[i][j].setSectorType(SectorType.OPENED_DOOR);
                    doorOpened = true;
                    break;
                }
            }
        }
    }

    /**Дверь открыта?*/
    public boolean isDoorOpened() {return doorOpened;}

    /**Устанавливаем перекрестки*/
    public void setCrossRoadsAndCorners() {
        int count_way = 0;
        for (int i = 0; i < this.LABYRINTH_HEIGHT; i++)
            for (int j = 0; j < this.LABYRINTH_WIDTH; j++){
                if (this.getSectors()[j][i].getSectorType() == SectorType.EMPTY) {
                    count_way = 0;
                    if (i - 1 >= 0 && this.getSectors()[j][i-1].getSectorType() == SectorType.EMPTY)                count_way++;
                    if (i + 1 < LABYRINTH_HEIGHT && this.getSectors()[j][i+1].getSectorType() == SectorType.EMPTY)  count_way++;
                    if (j - 1 >= 0 && this.getSectors()[j - 1][i].getSectorType() == SectorType.EMPTY)              count_way++;
                    if (j + 1 < LABYRINTH_WIDTH && this.getSectors()[j + 1][i].getSectorType() == SectorType.EMPTY) count_way++;
                }
                if (count_way < 3){
                    this.getSectors()[j][i].setCrossRoad(false);
                }
                else this.getSectors()[j][i].setCrossRoad(true);
            }

    }
}
