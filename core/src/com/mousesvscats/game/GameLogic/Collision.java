package com.mousesvscats.game.GameLogic;

import com.mousesvscats.game.GameLogic.items.Cheese;
import com.mousesvscats.game.GameLogic.items.Key;
import com.mousesvscats.game.GameLogic.items.Weapon;

/**
 * Created by Алексей on 16.12.2014.
 */
public class Collision {
    /*Метод-копия Overlaps из import com.badlogic.gdx.math.Rectangle + в начале проверяем, что через соседний блок нельзя пройти */
    public static boolean Overlaps(Cat cat, Labyrinth level, int dx, int dy) {
        if (!level.getSectors()[dx][dy].isAccessible() || DefineTrap(level, dx, dy))
            if (cat.getX() < level.getSectors()[dx][dy].x + GameObject.Size && cat.getX() + Cat.Size > level.getSectors()[dx][dy].x &&
                    cat.getY() < level.getSectors()[dx][dy].y + GameObject.Size && cat.getY() + Cat.Size > level.getSectors()[dx][dy].y)
                return true;
        return false;
    }

    public static boolean Overlaps(Mouse mouse, Labyrinth level, int dx, int dy) {

        if (!level.getSectors()[dx][dy].isAccessible() || DefineItem(level, dx, dy) || DefineOpenedDoor(level, dx, dy))
            if (mouse.getX() < level.getSectors()[dx][dy].x + GameObject.Size && mouse.getX() + Mouse.Size > level.getSectors()[dx][dy].x &&
                    mouse.getY() < level.getSectors()[dx][dy].y + GameObject.Size && mouse.getY() + Mouse.Size > level.getSectors()[dx][dy].y)
                return true;
        return false;
    }

    private static boolean DefineItem(Labyrinth level, int dx, int dy){
        return level.getSectors()[dx][dy].getItem() instanceof Cheese ||
                level.getSectors()[dx][dy].getItem() instanceof Weapon
                || level.getSectors()[dx][dy].getItem() instanceof Key;
    }

    private static boolean DefineTrap(Labyrinth level, int dx, int dy){
        return level.getSectors()[dx][dy].getTrapType() != null;
    }

    private static boolean DefineOpenedDoor(Labyrinth level, int dx, int dy){
        return level.getSectors()[dx][dy].getSectorType() == SectorType.OPENED_DOOR;
    }

    public static boolean DefineMouseCatched(Mouse mouse, Cat cat){
        if (cat.getX() >= mouse.getX() && cat.getX() <= mouse.getX() + Mouse.Size && cat.getY() >= mouse.getY() && cat.getY() <= mouse.getY() + 8 ||
                cat.getX() + GameObject.Size >= mouse.getX() && cat.getX() + GameObject.Size <= mouse.getX() + Mouse.Size && cat.getY() >= mouse.getY() && cat.getY() <= mouse.getY() + 8 ||
                cat.getX() >= mouse.getX() && cat.getX() <= mouse.getX() + Mouse.Size && cat.getY() + GameObject.Size >= mouse.getY() && cat.getY() + GameObject.Size <= mouse.getY() + 8 ||
                cat.getX() + GameObject.Size >= mouse.getX() && cat.getX() + GameObject.Size <= mouse.getX() + Mouse.Size && cat.getY() + GameObject.Size >= mouse.getY() && cat.getY() + GameObject.Size <= mouse.getY() + 8)
        {
            return true;
        }
        else return false;
    }

    public static boolean Collision(Mouse mouse, Labyrinth level) {
        int dx,dx2,dy,dy2;
        switch (mouse.getDirection()) {
            /*При движении в право мы можем задеть блок либо верхним правым углом, либо нижним правым. Проверяем, залезли ли эти точки на блок.*/
            case RIGHT:
                dy2 = (mouse.getY() + Mouse.Size) / GameObject.Size;
                dx = (mouse.getX() + Mouse.Size) / GameObject.Size;
                dy = mouse.getY() / GameObject.Size;
                /*Далее 2 случая:*/
                if (Overlaps(mouse, level, dx, dy))  // Если залезли нижним углом, возвращаем перса назад
                    if (DefineOpenedDoor(level, dx, dy))
                        mouse.setWin();
                    else if (DefineItem(level, dx, dy))
                        level.getSectors()[dx][dy].getItem().taken(level, mouse, dx, dy);
                    else
                        mouse.setX(level.getSectors()[dx][dy2].getX() - GameObject.Size);
                if (Overlaps(mouse, level, dx, dy2))// Если залезли верхним углом, возвращаем перса назад
                    if (DefineOpenedDoor(level, dx, dy2))
                        mouse.setWin();
                    else if (DefineItem(level, dx, dy2))
                        level.getSectors()[dx][dy2].getItem().taken(level, mouse, dx, dy2);
                    else
                        mouse.setX(level.getSectors()[dx][dy].getX() - GameObject.Size);
                return true;
            /* В другие стороны - аналогично */
            case DOWN:
                dx2 = (mouse.getX() + Mouse.Size) / GameObject.Size;
                dy = mouse.getY() / GameObject.Size;
                dx = mouse.getX() / GameObject.Size;

                if (Overlaps(mouse, level, dx, dy))
                    if (DefineOpenedDoor(level, dx, dy))
                        mouse.setWin();
                    else if (DefineItem(level, dx, dy))
                        level.getSectors()[dx][dy].getItem().taken(level, mouse, dx, dy);
                    else
                        mouse.setY(level.getSectors()[dx][dy].getY() + GameObject.Size);

                if (Overlaps(mouse, level, dx2, dy))
                    if (DefineOpenedDoor(level, dx2, dy))
                        mouse.setWin();
                    else if (DefineItem(level, dx2, dy))
                        level.getSectors()[dx2][dy].getItem().taken(level, mouse, dx2, dy);
                    else
                        mouse.setY(level.getSectors()[dx2][dy].getY() + GameObject.Size);
                return true;
            case LEFT:
                dy = mouse.getY() / GameObject.Size;
                dy2 = (mouse.getY() + Mouse.Size) / GameObject.Size;
                dx = mouse.getX() / GameObject.Size;
                if (Overlaps(mouse, level, dx, dy2))
                    if (DefineOpenedDoor(level, dx, dy2))
                        mouse.setWin();
                    else if (DefineItem(level, dx, dy2))
                        level.getSectors()[dx][dy2].getItem().taken(level, mouse, dx, dy2);
                    else
                        mouse.setX(level.getSectors()[dx][dy2].getX() + GameObject.Size);

                if (Overlaps(mouse, level, dx, dy))
                    if (DefineOpenedDoor(level, dx, dy))
                        mouse.setWin();
                    else if (DefineItem(level, dx, dy))
                        level.getSectors()[dx][dy].getItem().taken(level, mouse, dx, dy);
                    else
                        mouse.setX(level.getSectors()[dx][dy].getX() + GameObject.Size);
                return true;
            case UP:
                dx2 = (mouse.getX() + Mouse.Size) / GameObject.Size;
                dy = (mouse.getY() + Mouse.Size) / GameObject.Size;
                dx = (mouse.getX()) / GameObject.Size;
                if (Overlaps(mouse, level, dx, dy))
                    if (DefineOpenedDoor(level, dx, dy))
                        mouse.setWin();
                    else if (DefineItem(level, dx, dy))
                        level.getSectors()[dx][dy].getItem().taken(level, mouse, dx, dy);
                    else
                        mouse.setY(level.getSectors()[dx][dy].getY() - GameObject.Size);

                if (Overlaps(mouse, level, dx2, dy))
                    if (DefineOpenedDoor(level, dx2, dy))
                        mouse.setWin();
                    else if (DefineItem(level, dx2, dy))
                        level.getSectors()[dx2][dy].getItem().taken(level, mouse, dx2, dy);
                    else
                        mouse.setY(level.getSectors()[dx2][dy].getY() - GameObject.Size);
                return true;
        }
        return false;
    }

    /*Возвращает true, если залезли куда-либо. Можно позднее модифицировать для сбора бонусов. */
    public static boolean Collision(Cat cat, Labyrinth level) {
        int dx, dx2, dy, dy2;
        boolean flag = false;
        switch (cat.getDirection()) {
            /*При движении в право мы можем задеть блок либо верхним правым углом, либо нижним правым. Проверяем, залезли ли эти точки на блок.*/
            case RIGHT:
                dy2 = (cat.getY() + Cat.Size) / GameObject.Size;
                dx = (cat.getX() + Cat.Size) / GameObject.Size;
                dy = cat.getY() / GameObject.Size;
                /*Далее 2 случая:*/
                if (Overlaps(cat, level, dx, dy)) {
                    flag=true;
                    if (DefineTrap(level, dx, dy)) {
                        cat.setTrapped(level.getSectors()[dx][dy].getTrapType());
                        level.getSectors()[dx][dy].setTrapType(null);
                    }
                    else
                        cat.setX(level.getSectors()[dx][dy2].getX() - Cat.Size);// Если залезли нижним углом, возвращаем перса назад
                }
                if (Overlaps(cat, level, dx, dy2)) {// Если залезли верхним углом, возвращаем перса назад
                    flag=true;
                    if (DefineTrap(level, dx, dy2)){
                        cat.setTrapped(level.getSectors()[dx][dy2].getTrapType());
                        level.getSectors()[dx][dy2].setTrapType(null);
                    }
                    else
                        cat.setX(level.getSectors()[dx][dy].getX() - Cat.Size);
                }
                return flag;
            /* В другие стороны - аналогично */
            case DOWN:
                dx2 = (cat.getX() + Cat.Size) / GameObject.Size;
                dy = cat.getY() / GameObject.Size;
                dx = cat.getX() / GameObject.Size;
                if (Overlaps(cat, level, dx, dy)) {
                    flag = true;
                    if (DefineTrap(level, dx, dy)){
                        cat.setTrapped(level.getSectors()[dx][dy].getTrapType());
                        level.getSectors()[dx][dy].setTrapType(null);
                    }
                    else
                        cat.setY(level.getSectors()[dx][dy].getY() + GameObject.Size);
                }
                if (Overlaps(cat, level, dx2, dy)) {
                    flag = true;
                    if (DefineTrap(level, dx2, dy)){
                        cat.setTrapped(level.getSectors()[dx2][dy].getTrapType());
                        level.getSectors()[dx2][dy].setTrapType(null);
                    }
                    else
                        cat.setY(level.getSectors()[dx2][dy].getY() + GameObject.Size);
                }
                return flag;
            case LEFT:
                dy = cat.getY() / GameObject.Size;
                dy2 = (cat.getY() + Cat.Size) / GameObject.Size;
                dx = cat.getX() / GameObject.Size;
                if (Overlaps(cat, level, dx, dy2)) {
                    flag = true;
                    if (DefineTrap(level, dx, dy2)){
                        cat.setTrapped(level.getSectors()[dx][dy2].getTrapType());
                        level.getSectors()[dx][dy2].setTrapType(null);
                    }
                    else
                        cat.setX(level.getSectors()[dx][dy2].getX() + GameObject.Size);
                }
                if (Overlaps(cat, level, dx, dy)) {
                    flag = true;
                    if (DefineTrap(level, dx, dy)){
                        cat.setTrapped(level.getSectors()[dx][dy].getTrapType());
                        level.getSectors()[dx][dy].setTrapType(null);
                    }
                    else
                        cat.setX(level.getSectors()[dx][dy].getX() + GameObject.Size);
                }
                return flag;
            case UP:
                dx2 = (cat.getX() + Cat.Size) / GameObject.Size;
                dy = (cat.getY() + Cat.Size) / GameObject.Size;
                dx = (cat.getX()) / GameObject.Size;
                if (Overlaps(cat, level, dx, dy)) {
                    flag = true;
                    if (DefineTrap(level, dx, dy)){
                        cat.setTrapped(level.getSectors()[dx][dy].getTrapType());
                        level.getSectors()[dx][dy].setTrapType(null);
                    }
                    else
                        cat.setY(level.getSectors()[dx][dy].getY() - Cat.Size);
                }
                if (Overlaps(cat, level, dx2, dy)) {
                    flag = true;
                    if (DefineTrap(level, dx2, dy)){
                        cat.setTrapped(level.getSectors()[dx2][dy].getTrapType());
                        level.getSectors()[dx2][dy].setTrapType(null);
                    }
                    else
                        cat.setY(level.getSectors()[dx2][dy].getY() - Cat.Size);
                }
                return flag;
        }
        return flag;
    }
}
