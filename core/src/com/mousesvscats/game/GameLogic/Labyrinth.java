package com.mousesvscats.game.GameLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by User on 05.12.2014.
 */
public class Labyrinth {
    public static final int LABYRINCH_WIDTH = 40; //ширина лабиринта
    public static final int LABYRINCH_HEIGHT = 40; //высота лабиринта

    private Sector[][] sectors = new Sector[LABYRINCH_HEIGHT][LABYRINCH_WIDTH]; //лабиринт - матрица секторов

    /**Конструктор лабиринта*/
    public Labyrinth(String path){
        BufferedReader reader = null;
        try {
            File file = new File(path);
            reader = new BufferedReader(new FileReader(file));

            String[] elements = new String[LABYRINCH_HEIGHT];
            int temp;
            for (int i = LABYRINCH_HEIGHT-1; i>=0; i--) {
                elements[i] = reader.readLine();
                for (int j = 0; j < LABYRINCH_HEIGHT; j++) {
                    temp = Character.getNumericValue(elements[i].charAt(j));
                    switch (temp) {
                        case 0:
                            sectors[j][i] = new Sector(SectorType.EMPTY); //МАГИЯ!! почему-то j и i нужно менять местами
                            sectors[j][i].setX(GameObject.Size*j);//копипаст
                            sectors[j][i].setY(GameObject.Size*i);
                            sectors[j][i].setAccessible(true);

                            break;
                        case 1:
                            sectors[j][i] = new Sector(SectorType.WALL); //если элемент матрицы = 1, то сектор - стена
                            sectors[j][i].setX(GameObject.Size*j);
                            sectors[j][i].setY(GameObject.Size*i);
                            sectors[j][i].setAccessible(false);
                            break;
                        case 2:
                            sectors[j][i] = new Sector(SectorType.CLOSED_DOOR); //если элемент матрицы = 2, то сектор - дверь (выход)
                            sectors[j][i].setX(GameObject.Size*j);
                            sectors[j][i].setY(GameObject.Size*i);
                            sectors[j][i].setAccessible(false);
                            break;
                        default:
                            sectors[j][i] = new Sector(SectorType.EMPTY);
                            sectors[j][i].setX(GameObject.Size*j);
                            sectors[j][i].setY(GameObject.Size*i);
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
}
