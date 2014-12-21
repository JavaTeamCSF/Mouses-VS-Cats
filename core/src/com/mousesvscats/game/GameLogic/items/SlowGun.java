package com.mousesvscats.game.GameLogic.items;

import com.mousesvscats.game.GameLogic.Labyrinth;
import com.mousesvscats.game.GameLogic.Mouse;

/**
 * Created by Mashkin on 19.12.2014.
 */
public class SlowGun extends Weapon {

    public SlowGun() {
        this.volleys = 2;
    }

    @Override
    public void taken(Labyrinth labyrinth, Mouse mouse, int x, int y) {
        mouse.getWeapons().add(this);
        mouse.setCurrentWeapon(this);
        labyrinth.collectItem(x, y);
    }
}
