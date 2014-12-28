package com.mousesvscats.game.GameLogic;

import com.mousesvscats.game.GameLogic.items.Cheese;
import com.mousesvscats.game.GameLogic.items.Key;
import com.mousesvscats.game.GameLogic.items.Weapon;

/**
 * Created by Алексей on 16.12.2014.
 */
public class Collision {
    /**в начале проверяем, что через соседний блок нельзя пройти или он содержит ловушку, затем проверяем задет ли он данной точкой*/
    public static boolean overlaps(Cat cat, Labyrinth level, int dx, int dy) {
        if (!level.getSectors()[dx][dy].isAccessible() || defineTrap(level, dx, dy))
            if (cat.getX() < level.getSectors()[dx][dy].getX() + Sector.SIZE && cat.getX() > level.getSectors()[dx][dy].getX() - Sector.SIZE &&
                    cat.getY() < level.getSectors()[dx][dy].getY() + Sector.SIZE && cat.getY() > level.getSectors()[dx][dy].getY() - Sector.SIZE)
                return true;
        return false;
    }

    /**в начале проверяем, что через соседний блок нельзя пройти или он содержит предмет или открытую дверь, затем проверяем задет ли он данной точкой*/
    public static boolean overlaps(Mouse mouse, Labyrinth level, int dx, int dy) {

        if (!level.getSectors()[dx][dy].isAccessible() || defineItem(level, dx, dy) || defineOpenedDoor(level, dx, dy))
            if (mouse.getX() < level.getSectors()[dx][dy].getX() + Sector.SIZE && mouse.getX() > level.getSectors()[dx][dy].getX() - Sector.SIZE &&
                    mouse.getY() < level.getSectors()[dx][dy].getY() + Sector.SIZE && mouse.getY() > level.getSectors()[dx][dy].getY() - Sector.SIZE)
                return true;
        return false;
    }

    /**определяем наличие предмета в секторе*/
    private static boolean defineItem(Labyrinth level, int dx, int dy){
        return level.getSectors()[dx][dy].getItem() instanceof Cheese ||
                level.getSectors()[dx][dy].getItem() instanceof Weapon
                || level.getSectors()[dx][dy].getItem() instanceof Key;
    }

    /**определяем наличие ловушки в секторе*/
    private static boolean defineTrap(Labyrinth level, int dx, int dy){
        return level.getSectors()[dx][dy].getTrapType() != null;
    }

    /**определяем наличие открытой двери в секторе*/
    private static boolean defineOpenedDoor(Labyrinth level, int dx, int dy){
        return level.getSectors()[dx][dy].getSectorType() == SectorType.OPENED_DOOR;
    }

    /**определяем столкновение кошки с мышью*/
    public static boolean defineMouseCatched(Mouse mouse, Cat cat){
        if ((cat.getCatType() != CatType.DEAD && cat.getCatType() != CatType.FROZEN) &&
                (cat.getX() >= mouse.getX() && cat.getX() <= mouse.getX() + Mouse.SIZE && cat.getY() >= mouse.getY() && cat.getY() <= mouse.getY() + Mouse.SIZE ||
                cat.getX() + Cat.SIZE >= mouse.getX() && cat.getX() + Cat.SIZE <= mouse.getX() + Mouse.SIZE && cat.getY() >= mouse.getY() && cat.getY() <= mouse.getY() + Mouse.SIZE ||
                cat.getX() >= mouse.getX() && cat.getX() <= mouse.getX() + Mouse.SIZE && cat.getY() + Cat.SIZE >= mouse.getY() && cat.getY() + Cat.SIZE <= mouse.getY() + Mouse.SIZE ||
                cat.getX() + Cat.SIZE >= mouse.getX() && cat.getX() + Cat.SIZE <= mouse.getX() + Mouse.SIZE && cat.getY() + Cat.SIZE >= mouse.getY() && cat.getY() + Cat.SIZE <= mouse.getY() + Mouse.SIZE))
        {
            return true;
        }
        else return false;
    }

    /**возвращает истину при столкновении мыши с предметами, открытой дверью и стенами*/
    public static boolean collision(Mouse mouse, Labyrinth level) {
        int dx,dx2,dy,dy2;
        switch (mouse.getDirection()) {
            /*При движении в право мы можем задеть блок либо верхним правым углом, либо нижним правым. Проверяем, залезли ли эти точки на блок.*/
            case RIGHT:
                dy2 = (int) (mouse.getY() + Mouse.SIZE) / Sector.SIZE;
                dx = (int) (mouse.getX() + Mouse.SIZE) / Sector.SIZE;
                dy = (int) mouse.getY() / Sector.SIZE;
                /*Далее 2 случая:*/
                if (overlaps(mouse, level, dx, dy))  // Если залезли нижним углом
                    if (defineOpenedDoor(level, dx, dy)) //если это была дверь
                        mouse.setWin(); //мы выиграли
                    else if (defineItem(level, dx, dy)) //если это был предмет
                        level.getSectors()[dx][dy].getItem().taken(level, mouse, dx, dy); //поднимаем предмет
                    else //иначе - стена
                        mouse.setX(level.getSectors()[dx][dy2].getX() - Sector.SIZE); //возвращаем мышь назад
                if (overlaps(mouse, level, dx, dy2))// Если залезли верхним углом
                    if (defineOpenedDoor(level, dx, dy2))
                        mouse.setWin();
                    else if (defineItem(level, dx, dy2))
                        level.getSectors()[dx][dy2].getItem().taken(level, mouse, dx, dy2);
                    else
                        mouse.setX(level.getSectors()[dx][dy2].getX() - Sector.SIZE);
                return true;
            /* В другие стороны - аналогично */
            case DOWN:
                dx2 = (int)(mouse.getX() + Mouse.SIZE) / Sector.SIZE;
                dy = (int)mouse.getY() / Sector.SIZE;
                dx = (int)mouse.getX() / Sector.SIZE;

                if (overlaps(mouse, level, dx, dy))
                    if (defineOpenedDoor(level, dx, dy))
                        mouse.setWin();
                    else if (defineItem(level, dx, dy))
                        level.getSectors()[dx][dy].getItem().taken(level, mouse, dx, dy);
                    else
                        mouse.setY(level.getSectors()[dx][dy].getY() + Sector.SIZE);
                if (overlaps(mouse, level, dx2, dy))
                    if (defineOpenedDoor(level, dx2, dy))
                        mouse.setWin();
                    else if (defineItem(level, dx2, dy))
                        level.getSectors()[dx2][dy].getItem().taken(level, mouse, dx2, dy);
                    else
                        mouse.setY(level.getSectors()[dx2][dy].getY() + Sector.SIZE);
                return true;
            case LEFT:
                dy = (int)mouse.getY() / Sector.SIZE;
                dy2 = (int)(mouse.getY() + Mouse.SIZE) / Sector.SIZE;
                dx = (int) mouse.getX() / Sector.SIZE;
                if (overlaps(mouse, level, dx, dy2))
                    if (defineOpenedDoor(level, dx, dy2))
                        mouse.setWin();
                    else if (defineItem(level, dx, dy2))
                        level.getSectors()[dx][dy2].getItem().taken(level, mouse, dx, dy2);
                    else
                        mouse.setX(level.getSectors()[dx][dy2].getX() + Sector.SIZE);
                if (overlaps(mouse, level, dx, dy))
                    if (defineOpenedDoor(level, dx, dy))
                        mouse.setWin();
                    else if (defineItem(level, dx, dy))
                        level.getSectors()[dx][dy].getItem().taken(level, mouse, dx, dy);
                    else
                        mouse.setX(level.getSectors()[dx][dy].getX() + Sector.SIZE);
                return true;
            case UP:
                dx2 = (int)(mouse.getX() + Mouse.SIZE) / Sector.SIZE;
                dy = (int)(mouse.getY() + Mouse.SIZE) / Sector.SIZE;
                dx = (int)(mouse.getX()) / Sector.SIZE;
                if (overlaps(mouse, level, dx, dy))
                    if (defineOpenedDoor(level, dx, dy))
                        mouse.setWin();
                    else if (defineItem(level, dx, dy))
                        level.getSectors()[dx][dy].getItem().taken(level, mouse, dx, dy);
                    else
                        mouse.setY(level.getSectors()[dx][dy].getY() - Sector.SIZE);
                if (overlaps(mouse, level, dx2, dy))
                    if (defineOpenedDoor(level, dx2, dy))
                        mouse.setWin();
                    else if (defineItem(level, dx2, dy))
                        level.getSectors()[dx2][dy].getItem().taken(level, mouse, dx2, dy);
                    else
                        mouse.setY(level.getSectors()[dx2][dy].getY() - Sector.SIZE);
                return true;
        }
        return false;
    }

    /**Возвращает true, если залезли на ловушку или стену */
    public static boolean collision(Cat cat, Labyrinth level) {
        int dx, dx2, dy, dy2;
        boolean flag = false;
        switch (cat.getDirection()) {
            /*При движении в право мы можем задеть блок либо верхним правым углом, либо нижним правым. Проверяем, залезли ли эти точки на блок.*/
            case RIGHT:
                dy2 = (int)(cat.getY() + Cat.SIZE) / Sector.SIZE;
                dx = (int)(cat.getX() + Cat.SIZE) / Sector.SIZE;
                dy = (int)cat.getY() / Sector.SIZE;
                /*Далее 2 случая:*/
                if (overlaps(cat, level, dx, dy)) {// Если залезли нижним углом
                    flag = true;
                    if (defineTrap(level, dx, dy)) {//если это была ловушка
                        cat.setTrapped(level, dx, dy); //кошка попалась
                    }
                    else if (cat.getCatType() != CatType.FROZEN) //иначе (если непроходимый сектор), и если кошка не заморожена (возвращать назад ее не надо, она устанавливатся на место ловушки)
                        cat.setX(level.getSectors()[dx][dy].getX() - Sector.SIZE);//возвращаем перса назад
                }
                if (overlaps(cat, level, dx, dy2)) {// Если залезли верхним углом
                    flag = true;
                    if (defineTrap(level, dx, dy2)){
                        cat.setTrapped(level, dx, dy2);
                    }
                    else if (cat.getCatType() != CatType.FROZEN)
                        cat.setX(level.getSectors()[dx][dy2].getX() - Sector.SIZE);
                }
                return flag;
            /* В другие стороны - аналогично */
            case DOWN:
                dx2 = (int)(cat.getX() + Cat.SIZE) / Sector.SIZE;
                dy = (int)cat.getY() / Sector.SIZE;
                dx = (int)cat.getX() / Sector.SIZE;
                if (overlaps(cat, level, dx, dy)) {
                    flag = true;
                    if (defineTrap(level, dx, dy)){
                        cat.setTrapped(level, dx, dy);
                    }
                    else if (cat.getCatType() != CatType.FROZEN)
                        cat.setY(level.getSectors()[dx][dy].getY() + Sector.SIZE);
                }
                if (overlaps(cat, level, dx2, dy)) {
                    flag = true;
                    if (defineTrap(level, dx2, dy)){
                        cat.setTrapped(level, dx2, dy);
                    }
                    else if (cat.getCatType() != CatType.FROZEN)
                        cat.setY(level.getSectors()[dx2][dy].getY() + Sector.SIZE);
                }
                return flag;
            case LEFT:
                dy = (int)cat.getY() / Sector.SIZE;
                dy2 = (int)(cat.getY() + Cat.SIZE) / Sector.SIZE;
                dx = (int)cat.getX() / Sector.SIZE;
                if (overlaps(cat, level, dx, dy2)) {
                    flag = true;
                    if (defineTrap(level, dx, dy2)){
                        cat.setTrapped(level, dx, dy2);
                    }
                    else if (cat.getCatType() != CatType.FROZEN)
                        cat.setX(level.getSectors()[dx][dy2].getX() + Sector.SIZE);
                }
                if (overlaps(cat, level, dx, dy)) {
                    flag = true;
                    if (defineTrap(level, dx, dy)){
                        cat.setTrapped(level, dx, dy);
                    }
                    else if (cat.getCatType() != CatType.FROZEN)
                        cat.setX(level.getSectors()[dx][dy].getX() + Sector.SIZE);
                }
                return flag;
            case UP:
                dx2 = (int)(cat.getX() + Cat.SIZE) / Sector.SIZE;
                dy = (int)(cat.getY() + Cat.SIZE) / Sector.SIZE;
                dx = (int)(cat.getX()) / Sector.SIZE;
                if (overlaps(cat, level, dx, dy)) {
                    flag = true;
                    if (defineTrap(level, dx, dy)){
                        cat.setTrapped(level, dx, dy);
                    }
                    else if (cat.getCatType() != CatType.FROZEN)
                        cat.setY(level.getSectors()[dx][dy].getY() - Sector.SIZE);

                }
                if (overlaps(cat, level, dx2, dy)) {
                    flag = true;
                    if (defineTrap(level, dx2, dy)){
                        cat.setTrapped(level, dx2, dy);
                    }
                    else if (cat.getCatType() != CatType.FROZEN)
                        cat.setY(level.getSectors()[dx2][dy].getY() - Sector.SIZE);
                }
                return flag;
        }
        return flag;
    }

    /**возвращает истину ТОЛЬКО при столкновении со стенами (необходима для ИИ)*/
    public static boolean collisionWithWall(Cat cat, Labyrinth level) {
        int dx, dx2, dy, dy2;
        boolean flag = false;
        switch (cat.getDirection()) {
            /*При движении в право мы можем задеть блок либо верхним правым углом, либо нижним правым. Проверяем, залезли ли эти точки на блок.*/
            case RIGHT:
                dy2 = (int)(cat.getY() + Cat.SIZE) / Sector.SIZE;
                dx = (int)(cat.getX() + Cat.SIZE) / Sector.SIZE;
                dy = (int)cat.getY() / Sector.SIZE;
                /*Далее 2 случая:*/
                if (overlaps(cat, level, dx, dy)) {
                    flag = true;
                    cat.setX(level.getSectors()[dx][dy].getX() - Sector.SIZE);// Если залезли нижним углом, возвращаем перса назад
                }
                if (overlaps(cat, level, dx, dy2)) {
                    flag = true;
                    cat.setX(level.getSectors()[dx][dy2].getX() - Sector.SIZE);// Если залезли верхним углом, возвращаем перса назад
                }
                return flag;
            /* В другие стороны - аналогично */
            case DOWN:
                dx2 = (int)(cat.getX() + Cat.SIZE) / Sector.SIZE;
                dy = (int)cat.getY() / Sector.SIZE;
                dx = (int)cat.getX() / Sector.SIZE;
                if (overlaps(cat, level, dx, dy)) {
                    cat.setY(level.getSectors()[dx][dy].getY() + Sector.SIZE);
                }
                if (overlaps(cat, level, dx2, dy)) {
                    cat.setY(level.getSectors()[dx2][dy].getY() + Sector.SIZE);
                }
                return flag;
            case LEFT:
                dy = (int)cat.getY() / Sector.SIZE;
                dy2 = (int)(cat.getY() + Cat.SIZE) / Sector.SIZE;
                dx = (int)cat.getX() / Sector.SIZE;
                if (overlaps(cat, level, dx, dy2)) {
                    flag = true;
                    cat.setX(level.getSectors()[dx][dy2].getX() + Sector.SIZE);
                }
                if (overlaps(cat, level, dx, dy)) {
                    flag = true;
                    cat.setX(level.getSectors()[dx][dy].getX() + Sector.SIZE);
                }
                return flag;
            case UP:
                dx2 = (int)(cat.getX() + Cat.SIZE) / Sector.SIZE;
                dy = (int)(cat.getY() + Cat.SIZE) / Sector.SIZE;
                dx = (int)(cat.getX()) / Sector.SIZE;
                if (overlaps(cat, level, dx, dy)) {
                    flag = true;
                    cat.setY(level.getSectors()[dx][dy].getY() - Sector.SIZE);
                }
                if (overlaps(cat, level, dx2, dy)) {
                    flag = true;
                    cat.setY(level.getSectors()[dx2][dy].getY() - Sector.SIZE);
                }
                return flag;
        }
        return flag;
    }
}
