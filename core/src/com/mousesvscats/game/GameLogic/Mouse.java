package com.mousesvscats.game.GameLogic;
import java.util.ArrayList;
/**
 * Created by Виктория on 19.12.2014.
 */

import com.mousesvscats.game.GameLogic.items.Weapon;

public class Mouse extends Creature {
    public boolean isDead;
    private Weapon currentWeapon;
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();

    //private void setWeapons(Weapon weapon){return  weapons;}

    public ArrayList<Weapon> getWeapons(){return  weapons;}

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon weapon) {
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
