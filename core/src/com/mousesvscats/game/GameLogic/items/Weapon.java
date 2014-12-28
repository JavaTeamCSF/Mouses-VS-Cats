package com.mousesvscats.game.GameLogic.items;

import com.mousesvscats.game.GameLogic.Labyrinth;
import com.mousesvscats.game.GameLogic.Mouse;

/**
 * Created by Артём on 05.12.2014.
 */
public abstract class Weapon extends Item {
    /**предмет поднят*/
    @Override
    public void taken(Labyrinth labyrinth, Mouse mouse, int x, int y) {
        mouse.getWeapons().add(this);
        mouse.setCurrentWeapon(this);
        labyrinth.collectItem(x, y);
    }
}
