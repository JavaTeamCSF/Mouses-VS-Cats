package com.mousesvscats.game.GameLogic;


import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class GameClass {
    public static final int mouse_x = 16;    //первоначальные координаты мыши на поле
    public static final int mouse_y = 16;
    public static final int howManyCats = 3; //количество кошек на уровне
    private ArrayList<Cat> catList;          //массив кошек
    private Mouse mouse;                     //мышь
    private Labyrinth level;                 //уровень (лабиринт)

    /**конструктор*/
    public GameClass(Labyrinth level){
        this.level = level;
        catList = new ArrayList<Cat>();
        Vector2 temp;
        do {
            temp = level.getRandomEmptyCell();
        }
        while (Math.sqrt((Math.pow((temp.x * Sector.SIZE - mouse_x), 2) + (Math.pow((temp.y * Sector.SIZE - mouse_y), 2)))) < 250);
        for (int i = 0; i < howManyCats; i++) {
            catList.add(i, new Cat((int)temp.x * Sector.SIZE, (int)temp.y * Sector.SIZE, 15*i + 30));
            do {
                temp = level.getRandomEmptyCell();
            }
            while (Math.sqrt((Math.pow((temp.x * Sector.SIZE - mouse_x), 2) + (Math.pow((temp.y * Sector.SIZE - mouse_y), 2)))) < 250);
        }
        mouse = new Mouse(mouse_x, mouse_y, 80);
    }

    /**получить уровень*/
    public Labyrinth getLevel(){
        return level;
    }

    /**получить кошку с индексом i*/
    public Cat getCat(int i) {
        return catList.get(i);
    }

    /**получить мышь*/
    public Mouse getMouse() {
        return mouse;
    }
}
