package com.mousesvscats.game.GameLogic;

/**
 * Created by Виктория on 19.12.2014.
 */

import com.mousesvscats.game.GameLogic.items.Weapon;

public class Mouse extends Creature {
    public boolean isDead;

    private Weapon weapon;

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Mouse(int x, int y, int speed) {
        setDirection(Direction.UP);
        this.isDead=false;
        this.setX(x);
        this.setY(y);
        this.speed = speed;
        setSpeed(speed);
        setAccessible(true);
    }

}
