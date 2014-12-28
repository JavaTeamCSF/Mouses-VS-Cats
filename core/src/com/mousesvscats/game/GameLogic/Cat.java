package com.mousesvscats.game.GameLogic;

import java.util.Random;

public class Cat extends Creature {
    protected CatType catType; //состояние кошки
    protected float normalSpeed; //запоминаем скорость в нормальном состоянии

    /**установить тип кошки*/
    public void setCatType(CatType catType) {
        this.catType = catType;
        switch (catType) {
            case NORMAL: //нормальная
                this.setSpeed(normalSpeed);
                break;
            case DEAD: //мертвая
                this.setSpeed(0);
                this.setAccessible(true);
                break;
            case FROZEN: //замороженная
                this.setSpeed(0);
                break;
            case SLOW: //медленная
                this.setSpeed(normalSpeed/2);
                break;
        }
    }

    /**получить тип кошки*/
    public CatType getCatType() {
        return this.catType;
    }

    /**установить как попавшую в ловушку*/
    public void setTrapped(Labyrinth level, int dx, int dy){
        TrapType trapType = level.getSectors()[dx][dy].getTrapType();
        level.getSectors()[dx][dy].setTrapType(null);
        switch (trapType) { //если попала в ловушку, изменить состояние кошки в зависимости от типа ловушки
            case KILLER:
                this.setCatType(CatType.DEAD);
                break;
            case FREEZER:
                this.setX(dx*Sector.SIZE); //переместить кошку в клетку, где была ловушка
                this.setY(dy*Sector.SIZE);
                this.setCatType(CatType.FROZEN); //заморозить кошку
                level.getSectors()[(int)this.getX()/Sector.SIZE][(int)this.getY()/Sector.SIZE].setAccessible(false); //установить сектор с замороженной кошкой непроходимым
                break;
            case SLOWER:
                this.setCatType(CatType.SLOW);
                break;
            default:
                this.setCatType(CatType.NORMAL);
                break;
        }
    }

    /**конструктор*/
    public Cat(float x, float y, float speed) {
        setDirection(getRandomDirection()); //устанавливаем рандомное направление
        this.setCatType(CatType.NORMAL); //состояние - нормальное
        this.setX(x); //координаты
        this.setY(y);
        this.normalSpeed = speed; //запоминаем нормальную скорость
        this.setSpeed(speed); //устанавливаем скорость
        setAccessible(true);
    }

    /**движение кошки*/
    public void catMove(Labyrinth level, Mouse mouse) {
        int dx = (int) this.getX() / Sector.SIZE;//координаты клетки (в матрице лабиринта), в которой cat
        int dy = (int) this.getY() / Sector.SIZE;
        if (getDistance(this.x, this.y, mouse.getX(), mouse.getY()) < 200) { //на каком расстоянии начинаем атаковать
            if (level.getSectors()[dx][dy].isCrossRoad() && this.getX() <= level.getSectors()[dx][dy].getX() + Sector.SIZE && this.getX() + Cat.SIZE >= level.getSectors()[dx][dy].getX() &&
                    this.getY() <= level.getSectors()[dx][dy].getY() + Sector.SIZE && this.getY() + Cat.SIZE >= level.getSectors()[dx][dy].getY()) { //если перекресток и кошка целиком на нем
                direction = whereToGo(level, mouse); //определяем кратчайшее направление до мыши с учетом препятствий вокруг кошки
            }
        }
        /*двигаем кошку*/
        switch (direction) {
            case DOWN:
                this.setY((getY() - (float)(this.getSpeed() * 0.016)));
                break;
            case LEFT:
                this.setX(getX() - (float)(this.getSpeed() * 0.016));
                break;
            case RIGHT:
                this.setX(getX() + (float) (this.getSpeed() * 0.016));
                break;
            case UP:
                this.setY(getY() + (float) (this.getSpeed() * 0.016));
                break;
        }
        if (Collision.collision(this, level)) //если возникает препятствие по ходу движения
            catAI(); //ИИ - рандомное определение направления, отличного от текущего
    }

    /**ИИ - рандомное определение направления, отличного от текущего*/
    public void catAI() {
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

    /**расстояние между точками*/
    public double getDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt((Math.pow((x2 - x1), 2) + (Math.pow((y2 - y1), 2))));
    }

    /**получить рандомное направление*/
    public Direction getRandomDirection(){
        Direction dir;
        Random random = new Random();
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

    /**определение направления кратчайшего пути с учетом окружающих препятствий*/
    public Direction whereToGo(Labyrinth level, Mouse mouse) {
        double r1, r2, r3, r4;

        /*Клетка слева */
        if (Collision.collisionWithWall(new Cat(this.getX() - 1, this.getY(), 0), level)) //если есть препятствие, устанавливаем макс значение
            r1 = Integer.MAX_VALUE;
        else  //если нет - считаем расстояние между котом и мышью
            r1 = getDistance(this.x - Cat.SIZE, this.y, mouse.getX(), mouse.getY());
        /*Клетка справа*/
        if (Collision.collisionWithWall(new Cat(this.getX() + 1, this.getY(), 0), level))
            r2 = Integer.MAX_VALUE;
        else
            r2 = getDistance(this.x + Cat.SIZE, this.y, mouse.getX(), mouse.getY());
        /*Клетка сверху*/
        if (Collision.collisionWithWall(new Cat(this.getX(), this.getY() + 1, 0), level))
            r3 = Integer.MAX_VALUE;
        else
            r3 = getDistance(this.x, this.y + Cat.SIZE, mouse.getX(), mouse.getY());
        /*Клетка снизу*/
        if (Collision.collisionWithWall(new Cat(this.getX(), this.getY() - 1, 0), level))
            r4 = Integer.MAX_VALUE;
        else
            r4 = getDistance(this.x, this.y - Cat.SIZE, mouse.getX(), mouse.getY());

        double min = Math.min(Math.min(r1, r2), Math.min(r3, r4)); //определяем кратчайший путь и соответствующее направление...
        //...и соответствующее направление:
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
