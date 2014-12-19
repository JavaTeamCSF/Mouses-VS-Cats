package com.mousesvscats.game.GameLogic;

/**
 * Created by Алексей on 16.12.2014.
 */
public class Collision {
    /*Метод-копия Overlaps из import com.badlogic.gdx.math.Rectangle + в начале проверяем, что через соседний блок нельзя пройти */
    public static boolean Overlaps(Cat cat, Labyrinth level, int dx, int dy) {
        if (!level.getSectors()[dx][dy].isAccessible())
            if (cat.getX() < level.getSectors()[dx][dy].x + GameObject.Size && cat.getX() + Cat.Size > level.getSectors()[dx][dy].x &&
                    cat.getY() < level.getSectors()[dx][dy].y + GameObject.Size && cat.getY() + Cat.Size > level.getSectors()[dx][dy].y)
                return true;
        return false;
    }

    /*Возвращает true, если залезли куда-либо. Можно позднее модифицировать для сбора бонусов. */
    public static boolean Collision(Cat cat, Labyrinth level) {
        int dx,dx2,dy,dy2;
        switch (cat.getDirection()) {
            /*При движении в право мы можем задеть блок либо верхним правым углом, либо нижним правым. Проверяем, залезли ли эти точки на блок.*/
            case RIGHT:
                dy2 = (cat.getY() + Cat.Size) / GameObject.Size;
                dx = (cat.getX() + Cat.Size) / GameObject.Size;
                dy = cat.getY() / GameObject.Size;
                /*Далее 2 случая:*/
                if (Overlaps(cat, level, dx, dy)) // Если залезли нижним углом, возвращаем перса назад
                    cat.setX(level.getSectors()[dx][dy2].getX() - Cat.Size);
                if (Overlaps(cat, level, dx, dy2))// Если залезли верхним углом, возвращаем перса назад
                    cat.setX(level.getSectors()[dx][dy].getX() - Cat.Size);
                return true;
            /* В другие стороны - аналогично */
            case DOWN:
                dx2 = (cat.getX() + Cat.Size) / GameObject.Size;
                dy = cat.getY() / GameObject.Size;
                dx = cat.getX() / GameObject.Size;
                if (Overlaps(cat, level, dx, dy))
                    cat.setY(level.getSectors()[dx][dy].getY() + GameObject.Size);
                if (Overlaps(cat, level, dx2, dy))
                    cat.setY(level.getSectors()[dx2][dy].getY() + GameObject.Size);
                return true;
            case LEFT:
                dy = cat.getY() / GameObject.Size;
                dy2 = (cat.getY() + Cat.Size) / GameObject.Size;
                dx = cat.getX() / GameObject.Size;
                if (Overlaps(cat, level, dx, dy2))
                    cat.setX(level.getSectors()[dx][dy2].getX() + GameObject.Size);
                if (Overlaps(cat, level, dx, dy))
                    cat.setX(level.getSectors()[dx][dy].getX() + GameObject.Size);
                return true;
            case UP:
                dx2 = (cat.getX() + Cat.Size) / GameObject.Size;
                dy = (cat.getY() + Cat.Size) / GameObject.Size;
                dx = (cat.getX()) / GameObject.Size;
                if (Overlaps(cat, level, dx, dy))
                    cat.setY(level.getSectors()[dx][dy].getY() - Cat.Size);
                if (Overlaps(cat, level, dx2, dy))
                    cat.setY(level.getSectors()[dx2][dy].getY() - Cat.Size);
                return true;
        }
        return false;
    }
}
