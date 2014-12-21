package com.mousesvscats.game.GameLogic;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Алексей on 16.12.2014.
 */
public class GameObject {
    public static final int Size = 16;//размер текструки
    protected int x,y;
    protected boolean accessible; // можно ли пройти через объект

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    public void setAccessible(boolean b) {
        this.accessible = b;
    }
    public boolean isAccessible() {
        return this.accessible;
    }
}
