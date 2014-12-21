package com.mousesvscats.game.GameLogic;

public class Cat extends Creature {
    public static final int Size = 16;
    protected Instance instance;
    protected int normalSpeed; //запоминаем скорость в нормальном состоянии
    protected enum Instance {Normal, Slow, Freezed, Dead }

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
        Direction dir=direction;
        switch (direction) {
            case DOWN:
                setY(getY() - (int) (getSpeed() * 0.016));
                break;
            case LEFT:
                setX(getX() - (int) (getSpeed() * 0.016));
                break;
            case RIGHT:
                setX(getX() + (int) (getSpeed() * 0.016));
                break;
            case UP:
                setY(getY() + (int) (getSpeed() * 0.016));
                break;
        }
        if (level.getSectors()[dx][dy].isCrossRoad()) {
            direction = WhereToGo(level, mouseX, mouseY);
        }

        if (Collision.Collision(this, level))
            CatAI(level);

    }

    public void CatAI(Labyrinth level) {
        int dx = this.x / GameObject.Size;//номер клетки в которой cat
        int dy = this.y / GameObject.Size;
        switch (this.direction) {
            case DOWN:
                if (level.getSectors()[dx - 1][dy].isAccessible())
                    this.setDirection(Direction.LEFT);
                else if (level.getSectors()[dx + 1][dy].isAccessible())
                    this.setDirection(Direction.RIGHT);
                else
                    this.setDirection(Direction.UP);
                break;

            case LEFT:
                if (level.getSectors()[dx][dy + 1].isAccessible())
                    this.setDirection(Direction.UP);
                else if (level.getSectors()[dx][dy - 1].isAccessible())
                    this.setDirection(Direction.DOWN);
                else
                    this.setDirection(Direction.RIGHT);
                break;
            case RIGHT:
                if (level.getSectors()[dx][dy + 1].isAccessible())
                    this.setDirection(Direction.UP);
                else if (level.getSectors()[dx][dy - 1].isAccessible())
                    this.setDirection(Direction.DOWN);
                else
                    this.setDirection(Direction.LEFT);
                break;

            case UP:
                if (level.getSectors()[dx - 1][dy].isAccessible())
                    this.setDirection(Direction.LEFT);
                else if (level.getSectors()[dx + 1][dy].isAccessible())
                    this.setDirection(Direction.RIGHT);
                else
                    this.setDirection(Direction.DOWN);
                break;
        }
    }

    public double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((Math.pow((x2 - x1), 2) + (Math.pow((y2 - y1), 2))));
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
