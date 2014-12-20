package com.mousesvscats.game.GameLogic;
import java.util.ArrayList;
/**
 * Created by Виктория on 19.12.2014.
 */

import com.mousesvscats.game.GameLogic.items.Weapon;

public class Mouse extends Creature {
    public boolean isDead;

    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();

    public ArrayList<Weapon> getWeapons(){return  weapons;}

    //private void setWeapons(Weapon weapon){return  weapons;}

    private Weapon currentWeapon;

    public Weapon getWeapon() {
        return currentWeapon;
    }

    public void setWeapon(Weapon weapon) {
        this.currentWeapon = weapon;
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
