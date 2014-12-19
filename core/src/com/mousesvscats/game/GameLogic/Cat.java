package com.mousesvscats.game.GameLogic;

import com.badlogic.gdx.graphics.Texture;

public class Cat extends Creature {
    protected Instance instance;
    protected int normalSpeed; //запоминаем скорость в нормальном состоянии
    protected enum Instance {Normal, Slow, Freezed, Dead }

    public void setInstance(Instance instance_arg) {
        this.instance=instance_arg;
        switch (instance_arg) {
            case Normal:
                setSpeed(normalSpeed);
                break;
            case Dead: //?????
                setSpeed(0);
                setAccessible(true);
                break;
            case Freezed:
                setSpeed(0);
                break;
            case Slow:
                setSpeed(normalSpeed/2);
                break;
        }
    }
    public Instance getInstance() {
        return this.instance;
    }

    public Cat(int x, int y, int speed) {
        texture_down = new Texture("catdown.png");
        texture_up = new Texture("catup.png");
        texture_left = new Texture("catleft.png");
        texture_right = new Texture("catright.png");
        setTexture(texture_up);
        setDirection(Direction.UP);
        setInstance(Instance.Normal);
        this.setX(x);
        this.setY(y);
        this.normalSpeed=speed;
        setSpeed(speed);
        setAccessible(true);
    }
}
