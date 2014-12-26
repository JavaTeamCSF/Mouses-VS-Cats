package com.mousesvscats.game.GameLogic;

import java.util.Random;

public class Cat extends Creature {
    public static final int Size = 16;
    protected Instance instance;
    protected int normalSpeed; //запоминаем скорость в нормальном состоянии
    protected enum Instance {Normal, Slow, Freezed, Dead };
    private Random random = new Random();

    protected int destX, destY;

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

    public void setTrapped(TrapType trapType){
        switch (trapType) {
            case DISTRACT:
                setInstance(Instance.Dead);
                break;
            case FREEZER:
                setInstance(Instance.Freezed);
                break;
            case SLOW:
                setInstance(Instance.Slow);
                break;
            default:
                setInstance(Instance.Normal);
        }
    }

    public Cat(int x, int y, int speed) {
        setDirection(Direction.UP);
        setInstance(Instance.Normal);
        this.setX(x);
        this.setY(y);
        this.normalSpeed = speed;
        setSpeed(speed);
        setAccessible(true);
    }

    public void CatMove(Labyrinth level, int mouseX, int mouseY) {
        int dx = this.x / GameObject.Size;//номер клетки в которой cat
        int dy = this.y / GameObject.Size;
        if (getDistance(this.x, this.y, mouseX, mouseY) < 100) { //на каком расстоянии начинаем атаковать
            if (level.getSectors()[dx][dy].isCrossRoad()) {
                direction = WhereToGo(level, mouseX, mouseY);
            }
        }
        switch (direction) {
            case DOWN:
                setY(getY() - (int) (this.getSpeed() * 0.016));
                break;
            case LEFT:
                setX(getX() - (int) (this.getSpeed() * 0.016));
                break;
            case RIGHT:
                setX(getX() + (int) (this.getSpeed() * 0.016));
                break;
            case UP:
                setY(getY() + (int) (this.getSpeed() * 0.016));
                break;
        }
        if (Collision.Collision(this, level))
            CatAI(level);
    }

    public void CatAI(Labyrinth level) {
        int dx = this.x / GameObject.Size;//номер клетки в которой cat
        int dy = this.y / GameObject.Size;
        Direction dir = this.direction;
        switch (dir) {
            case DOWN:
                do {
                    direction = getRandomDirection();
                }
                while (direction == Direction.DOWN);
                break;

            case LEFT:
                do {
                    direction = getRandomDirection();
                }
                while (direction == Direction.LEFT);
                break;
            case RIGHT:
                do {
                    direction = getRandomDirection();
                }
                while (direction == Direction.RIGHT);
                break;

            case UP:
                do {
                    direction = getRandomDirection();
                }
                while (direction == Direction.UP);
                break;
        }
    }

    public double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((Math.pow((x2 - x1), 2) + (Math.pow((y2 - y1), 2))));
    }

    public Direction getRandomDirection(){
        Direction dir;
        int index = random.nextInt(4);
        switch (index) {
            case 0:
                dir = Direction.UP;
                break;
            case 1:
                dir = Direction.DOWN;
                break;
            case 2:
                dir = Direction.LEFT;
                break;
            case 3:
                dir = Direction.RIGHT;
                break;
            default:
                dir = Direction.UP;
                break;
        }
        return dir;
    }

    public Direction WhereToGo(Labyrinth level, int mouseX, int mouseY) {
        double r1, r2, r3, r4;
        int dx = this.x / GameObject.Size;//номер клетки в которой cat
        int dy = this.y / GameObject.Size;
        int px = level.getSectors()[dx][dy].getX();//координаты клетки в которой cat
        int py = level.getSectors()[dx][dy].getY();

        /*Клетка слева */
        r1 = getDistance(this.x - GameObject.Size, this.y, mouseX, mouseY);
        /*Клетка справа*/
        r2 = getDistance(this.x + GameObject.Size, this.y, mouseX, mouseY);
        /*Клетка сверху*/
        r3 = getDistance(this.x, this.y + GameObject.Size, mouseX, mouseY);
        /*Клетка снизу*/
        r4 = getDistance(this.x, this.y - GameObject.Size, mouseX, mouseY);

        double min = Math.min(Math.min(r1, r2), Math.min(r3, r4));
        if (r1 == min) {

            return Direction.LEFT;
        } else if (r2 == min) {
            return Direction.RIGHT;
        } else if (r3 == min) {
            return Direction.UP;
        } else
            return Direction.DOWN;
    }
}
