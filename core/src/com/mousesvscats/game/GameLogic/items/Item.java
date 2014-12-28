package com.mousesvscats.game.GameLogic.items;

import com.mousesvscats.game.GameLogic.Labyrinth;
import com.mousesvscats.game.GameLogic.Mouse;

/**
 * Created by Артём on 05.12.2014.
 */
public abstract class Item {
    /**предмет поднят*/
    public abstract void taken(Labyrinth labyrinth, Mouse mouse, int x, int y);
}
