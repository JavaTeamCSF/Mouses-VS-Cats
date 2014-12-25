package com.mousesvscats.game.GameLogic;


import java.util.ArrayList;
import java.util.List;

public class GameClass {
    public final int howManyCats = 3;
    //private Cat cat;
    private ArrayList<Cat> catList;
    private Mouse mouse;
    private Labyrinth level;

    public GameClass(Labyrinth level){
        this.level = level;
        catList = new ArrayList<Cat>();
        for (int i=0;i<howManyCats;i++) {
            catList.add(i,new Cat(60+60*i,60,300));
        }
        //cat = new Cat(GameObject.Size,GameObject.Size,300);//скорость кошек : 100-медленно, 200 - средне, 300 - быстро
        //здесь спаун кошки происходит в клетку [1][1]
        mouse = new Mouse(GameObject.Size + 16,GameObject.Size + 16, 300);
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
