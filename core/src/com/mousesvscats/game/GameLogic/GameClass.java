package com.mousesvscats.game.GameLogic;


import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GameClass {
    public final int howManyCats = 3;
    private ArrayList<Cat> catList;
    private Mouse mouse;
    private Labyrinth level;

    public GameClass(Labyrinth level){
        this.level = level;
        catList = new ArrayList<Cat>();
        Vector2 temp = level.getRandomEmptyCell();
        for (int i = 0; i < howManyCats; i++) {
            catList.add(i, new Cat((int)temp.x * GameObject.Size, (int)temp.y * GameObject.Size, 63*i + 63));
            temp = level.getRandomEmptyCell();
        }
        //cat = new Cat(GameObject.Size,GameObject.Size,300);//скорость кошек : 100-медленно, 200 - средне, 300 - быстро
        //здесь спаун кошки происходит в клетку [1][1]
        mouse = new Mouse((int)temp.x * GameObject.Size, (int)temp.y * GameObject.Size, 250);
    }

    public Labyrinth getLevel(){
        return level;
    }

    public Cat getCat(int i) {
        return catList.get(i);
    }

    public Mouse getMouse() {
        return mouse;
    }
}
