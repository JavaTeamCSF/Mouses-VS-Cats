package com.mousesvscats.game.GameLogic;
import java.util.ArrayList;
/**
 * Created by Виктория on 19.12.2014.
 */

import com.mousesvscats.game.GameLogic.items.KillerGun;
import com.mousesvscats.game.GameLogic.items.FreezerGun;
import com.mousesvscats.game.GameLogic.items.SlowGun;
import com.mousesvscats.game.GameLogic.items.Weapon;

public class Mouse extends Creature {
    private boolean isDead; //флаг смерти мыши
    private boolean isWin;  //флаг победы мыши
    private Weapon currentWeapon; //текущее оружие
    private ArrayList<Weapon> weapons = new ArrayList<Weapon>(); //массив всего собранного оружия

    /**получить массив всего собранного оружия*/
    public ArrayList<Weapon> getWeapons(){return  weapons;}

    /**получить текузее оружие*/
    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    /**установить текущее оружие*/
    public void setCurrentWeapon(Weapon weapon) {
        this.currentWeapon = weapon;
    }

    /**конструктор*/
    public Mouse(float x, float y, float speed) {
        setDirection(Direction.UP);
        this.isDead = false;
        this.isWin = false;
        this.setX(x);
        this.setY(y);
        setSpeed(speed);
        setAccessible(true);
    }

    /**установить флаг мертва*/
    public void setDead() {this.isDead = true;}

    /**установить флаг победила*/
    public void  setWin() {this.isWin = true;}

    /**мертва ли?*/
    public boolean isDead() {return this.isDead;}

    /**победила ли?*/
    public boolean  isWin() {return this.isWin;}

    /**переключить на следующее оружие*/
    public void nextWeapon(){
        if (weapons.size() != 0) {
            int index = weapons.indexOf(currentWeapon);//index of current weapon
            if (index != weapons.size() - 1)           //проверка на выход за пределы листа
                this.setCurrentWeapon(weapons.get(index + 1));
            else
                this.setCurrentWeapon(weapons.get(0));

        }
    }

    /**переключить на предыдущее оружие*/
    public void prevWeapon(){
        if (weapons.size() != 0) {
            int index = weapons.indexOf(currentWeapon);//index of current weapon
            if (index != 0)                            //проверка на выход за пределы листа
                this.setCurrentWeapon(weapons.get(index - 1));
            else
                this.setCurrentWeapon(weapons.get(weapons.size() - 1));
        }
    }

    /**установить ловушку в текущий сектор*/
    public void setTrapInSector(Labyrinth level) {
        if (currentWeapon != null) {
            int x = (int) (this.getX() + Mouse.SIZE/2) / Sector.SIZE;
            int y = (int) (this.getY() + Mouse.SIZE/2) / Sector.SIZE;

            if (currentWeapon instanceof KillerGun)
                level.getSectors()[x][y].setTrapType(TrapType.KILLER);
            if (currentWeapon instanceof FreezerGun)
                level.getSectors()[x][y].setTrapType(TrapType.FREEZER);
            if (currentWeapon instanceof SlowGun)
                level.getSectors()[x][y].setTrapType(TrapType.SLOWER);

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
