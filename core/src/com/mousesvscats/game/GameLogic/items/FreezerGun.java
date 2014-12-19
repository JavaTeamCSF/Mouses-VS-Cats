package com.mousesvscats.game.GameLogic.items;

import com.mousesvscats.game.GameLogic.Labyrinth;
import com.mousesvscats.game.GameLogic.Mouse;

/**
 * Created by Mashkin on 19.12.2014.
 */
public class FreezerGun extends Weapon {

    public FreezerGun() {
        this.volleys = 1;
    }

    @Override
    public void taken(Labyrinth labyrinth, Mouse mouse) {
        mouse.setWeapon(this);
    }
}
