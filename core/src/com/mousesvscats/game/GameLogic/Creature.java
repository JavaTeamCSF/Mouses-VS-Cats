package com.mousesvscats.game.GameLogic;

public class Creature extends GameObject {
    protected float speed; //скорость
    protected Direction direction; //направление движения

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public float getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return this.direction;
    }
    public void setDirection(Direction direction) {
        this.direction=direction;
    }
}