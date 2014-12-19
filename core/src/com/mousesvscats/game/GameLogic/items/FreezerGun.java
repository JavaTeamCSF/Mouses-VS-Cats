package com.mousesvscats.game.GameLogic.items;

import com.mousesvscats.game.GameLogic.Cat;
import com.mousesvscats.game.GameLogic.Labyrinth;

/**
 * Created by Mashkin on 19.12.2014.
 */
public class FreezerGun extends Weapon {

    public FreezerGun() {
        this.volleys = 1;
    }

    @Override
    public void taken(Labyrinth labyrinth, Cat cat) {
        cat.setWeapon(this);
    }
}
