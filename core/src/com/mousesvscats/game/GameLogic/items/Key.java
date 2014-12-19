package com.mousesvscats.game.GameLogic.items;

import com.mousesvscats.game.GameLogic.Mouse;
import com.mousesvscats.game.GameLogic.Labyrinth;

/**
 * Created by Артём on 05.12.2014.
 */
public class Key extends Item {
    @Override
    public void taken(Labyrinth labyrinth, Mouse mouse) {
        if (labyrinth.cheeseLeft() == 0)
            labyrinth.openDoor();
    }
}
