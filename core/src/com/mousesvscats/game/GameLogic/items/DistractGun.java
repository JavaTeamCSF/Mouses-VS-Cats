package com.mousesvscats.game.GameLogic.items;

import com.mousesvscats.game.GameLogic.Labyrinth;
import com.mousesvscats.game.GameLogic.Mouse;

/**
 * Created by Mashkin on 19.12.2014.
 */
public class DistractGun extends Weapon{

    public DistractGun() {
        this.volleys = 2;
    }

    @Override
    public void taken(Labyrinth labyrinth, Mouse mouse, int x, int y) {
        mouse.setWeapon(this);
        labyrinth.collectItem(x, y);
    }
}
