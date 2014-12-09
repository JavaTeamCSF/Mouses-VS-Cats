package com.mousesvscats.game.GameLogic.Field;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

/**
 * Created by User on 05.12.2014.
 */
public class Labyrinth {
    public static final int LABYRINCH_WIDTH = 40; //ширина лабиринта
    public static final int LABYRINCH_HEIGHT = 40; //высота лабиринта

    private Sector[][] sectors = new Sector[LABYRINCH_HEIGHT][LABYRINCH_WIDTH]; //лабиринт - матрица секторов

    /**Конструктор лабиринта*/
    public Labyrinth(String path){
        /*try {
            File file = new File(path); //файл, хранящий матрицу лабиринта в виде матрицы цифр {0,1,2}
            Scanner in = new Scanner(file);
            int temp;
            for (int i = 0; i < LABYRINCH_HEIGHT; i++)
                for (int j = 0; i < LABYRINCH_WIDTH; j++) {
                    if (in.hasNextInt()) {
                        temp = in.nextInt();
                        switch (temp) {
                            case 0:
                                sectors[i][j] = new Sector(SectorType.EMPTY); //если элемент матрицы = 0, то сектор - пустой
                                break;
                            case 1:
                                sectors[i][j] = new Sector(SectorType.WALL); //если элемент матрицы = 1, то сектор - стена
                                break;
                            case 2:
                                sectors[i][j] = new Sector(SectorType.CLOSED_DOOR); //если элемент матрицы = 2, то сектор - дверь (выход)
                                break;
                            default:
                                sectors[i][j] = new Sector(SectorType.EMPTY);
                                break;
                        }
                    }
                }
        } catch (IOException e){e.printStackTrace();}
        finaly {in.close();}*/

        BufferedReader reader = null;
        try {
            File file = new File(path);
            reader = new BufferedReader(new FileReader(file));

            String[][] elements = new String[LABYRINCH_HEIGHT][LABYRINCH_WIDTH];
            int temp;
            for (int i = 0; i < LABYRINCH_HEIGHT; i++) {
                elements[i] = reader.readLine().split(" ");
                for (int j = 0; i < LABYRINCH_WIDTH; j++) {
                    temp = Integer.parseInt(elements[i][j]);
                    switch (temp) {
                        case 0:
                            sectors[i][j] = new Sector(SectorType.EMPTY); //если элемент матрицы = 0, то сектор - пустой
                            break;
                        case 1:
                            sectors[i][j] = new Sector(SectorType.WALL); //если элемент матрицы = 1, то сектор - стена
                            break;
                        case 2:
                            sectors[i][j] = new Sector(SectorType.CLOSED_DOOR); //если элемент матрицы = 2, то сектор - дверь (выход)
                            break;
                        default:
                            sectors[i][j] = new Sector(SectorType.EMPTY);
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
