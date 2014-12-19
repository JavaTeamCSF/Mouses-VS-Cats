package com.mousesvscats.game.GameLogic.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mousesvscats.game.GameLogic.Cat;
import com.mousesvscats.game.GameLogic.Labyrinth;

/**
 * Created by Артём on 05.12.2014.
 */
public abstract  class Item {

    //protected int x, y;
    protected TextureRegion texture;

    /*public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
*/
    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public abstract void taken(Labyrinth labyrinth, Cat cat); //todo: заменить кота на героя
}
