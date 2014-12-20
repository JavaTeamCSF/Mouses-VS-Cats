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

    private int cheeseCount; //количество сыра на уровне
    private int cheeseRemaining; //количество несобранного сыра
    private boolean doorOpened;

    /**Конструктор лабиринта*/
    public Labyrinth(String path){
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
                            if (i == 0 || i == LABYRINTH_WIDTH - 1) {
                                sectors[j][i] = new Sector(SectorType.CLOSED_GOR_DOOR);  //если на левой/правой границе лабиринта
                            }
                            else if (j == 0 || j == LABYRINTH_HEIGHT - 1)
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
    }

    /**Получить матрицу лабиринта*/
    public Sector[][] getSectors(){return sectors;}

    /**Получаем рандомный пустой сектор*/
    private Vector2 getRandomEmptyCell() {
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
    }

    public void collectItem(int x, int y) {
        this.sectors[x][y].setItem(null);
    }

    /**Количество несобранного сыра*/
    public int cheeseLeft() {
        return cheeseRemaining;
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
}
