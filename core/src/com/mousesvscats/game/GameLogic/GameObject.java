package com.mousesvscats.game.GameLogic;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Алексей on 16.12.2014.
 */
public class GameObject {
    public static final int SIZE = 14;//размер текструки
    protected float x, y; //координаты
    protected boolean accessible; // можно ли пройти через объект

    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return this.x;
    }
    public float getY() {
        return this.y;
    }

    public void setAccessible(boolean b) {
        this.accessible = b;
    }
    public boolean isAccessible() {
        return this.accessible;
    }
}
