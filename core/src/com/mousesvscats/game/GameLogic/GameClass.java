package com.mousesvscats.game.GameLogic;


public class GameClass {
    private Cat cat;
    private Mouse mouse;
    private Labyrinth level;
    public int score;

    public GameClass(Labyrinth level){
        score = 0;
        this.level = level;
        cat = new Cat(GameObject.Size,GameObject.Size,300);//скорость кошек : 100-медленно, 200 - средне, 300 - быстро
        //здесь спаун кошки происходит в клетку [1][1]
        mouse = new Mouse(GameObject.Size + 16,GameObject.Size + 16, 300);
    }

    public Labyrinth getLevel(){
        return level;
    }

    public Cat getCat() {
        return cat;
    }

    public Mouse getMouse() {
        return mouse;
    }
}
