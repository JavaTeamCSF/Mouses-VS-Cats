package com.mousesvscats.game.GameLogic;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameClass {
    private Cat cat;
    private Labyrinth level;
    public int score;

    SpriteBatch batch;

    public GameClass(Labyrinth level) {
        score = 0;
        this.level = level;
        cat = new Cat(GameObject.Size,GameObject.Size,300);//скорость кошек : 100-медленно, 200 - средне, 300 - быстро
    }

    public Labyrinth getLevel(){
        return level;
    }

    public Cat getCat() {
        return cat;
    }
}
