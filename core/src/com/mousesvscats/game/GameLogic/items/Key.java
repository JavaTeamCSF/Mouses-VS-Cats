package com.mousesvscats.game.GameLogic.items;

import com.mousesvscats.game.GameLogic.Mouse;
import com.mousesvscats.game.GameLogic.Labyrinth;

/**
 * Created by Артём on 05.12.2014.
 */
public class Key extends Item {
    /**предмет поднят*/
    @Override
    public void taken(Labyrinth labyrinth, Mouse mouse, int x, int y) {
        labyrinth.openDoor();
        labyrinth.collectItem(x, y);
    }
}
