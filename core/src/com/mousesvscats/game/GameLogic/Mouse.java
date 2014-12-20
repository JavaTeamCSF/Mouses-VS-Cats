package com.mousesvscats.game.GameLogic;

/**
 * Created by Виктория on 19.12.2014.
 */

import com.badlogic.gdx.graphics.Texture;
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
        //check
        texture_down = new Texture("mousedown.png");
        texture_up = new Texture("mouseup.png");
        texture_left = new Texture("mouseleft.png");
        texture_right = new Texture("mouseright.png");
        setTexture(texture_up);
        setDirection(Direction.UP);
        this.isDead=false;
        this.setX(x);
        this.setY(y);
        this.speed = speed;
        setSpeed(speed);
        setAccessible(true);
    }

}
