package com.mousesvscats.game.GameLogic;
import java.util.ArrayList;
/**
 * Created by Виктория on 19.12.2014.
 */

import com.mousesvscats.game.GameLogic.items.DistractGun;
import com.mousesvscats.game.GameLogic.items.FreezerGun;
import com.mousesvscats.game.GameLogic.items.SlowGun;
import com.mousesvscats.game.GameLogic.items.Weapon;

public class Mouse extends Creature {
    private boolean isDead;
    private boolean isWin;
    private Weapon currentWeapon;
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>();

    public ArrayList<Weapon> getWeapons(){return  weapons;}

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon weapon) {
        this.currentWeapon = weapon;
    }

    public Mouse(int x, int y, int speed) {
        setDirection(Direction.UP);
        this.isDead = false;
        this.isWin = false;
        this.setX(x);
        this.setY(y);
        setSpeed(speed);
        setAccessible(true);
    }

    public void setDead() {this.isDead = true;}

    public void  setWin() {this.isWin = true;}

    public boolean isDead() {return this.isDead;}

    public boolean  isWin() {return this.isWin;}

    public void nextWeapon(){
        if (weapons.size() != 0) {
            int index = weapons.indexOf(currentWeapon);//index of current weapon
            if (index != weapons.size() - 1)                                 //проверка на выход за пределы листа
                this.setCurrentWeapon(weapons.get(index + 1));
            else
                this.setCurrentWeapon(weapons.get(0));

        }
    }

    public void prevWeapon(){
        if (weapons.size() != 0) {
            int index = weapons.indexOf(currentWeapon);//index of current weapon
            if (index != 0)                                                                         //проверка на выход за пределы листа
                this.setCurrentWeapon(weapons.get(index - 1));
            else
                this.setCurrentWeapon(weapons.get(weapons.size() - 1));
        }
    }

    public void setTrapInSector(Labyrinth level) {
        if (currentWeapon != null) {
            int x = this.getX() / GameObject.Size;
            int y = this.getY() / GameObject.Size;

            if (currentWeapon instanceof DistractGun)
                level.getSectors()[x][y].setTrapType(TrapType.DISTRACT);
            if (currentWeapon instanceof FreezerGun)
                level.getSectors()[x][y].setTrapType(TrapType.FREEZER);
            if (currentWeapon instanceof SlowGun)
                level.getSectors()[x][y].setTrapType(TrapType.SLOW);

            if (weapons.size() == 1){
                weapons.clear();
                currentWeapon = null;
            }
            else {
                int index = weapons.indexOf(currentWeapon);//index of current weapon
                this.nextWeapon();
                weapons.remove(index);
            }
        }
    }
}
