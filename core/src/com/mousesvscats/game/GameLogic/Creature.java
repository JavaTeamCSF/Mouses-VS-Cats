package com.mousesvscats.game.GameLogic;

import com.badlogic.gdx.graphics.Texture;

public class Creature extends GameObject {
    public static final int Size=14;//у персов текстуры меньше, чтобы можно было пройти в дырку
    protected Texture texture_down,texture_up,texture_left,texture_right;
    protected enum Direction{ UP, DOWN, RIGHT, LEFT } //куда смотрит перс
    protected int speed;
    protected Direction direction;

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return this.direction;
    }
    public void setDirection(Direction direction) {
        this.direction=direction;
        switch (direction) {
            case DOWN:
                setTexture(texture_down);
                break;
            case RIGHT:
                setTexture(texture_right);
                break;
            case UP:
                setTexture(texture_up);
                break;
            case LEFT:
                setTexture(texture_left);
                break;
        }
    }
}