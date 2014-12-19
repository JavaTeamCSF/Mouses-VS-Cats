package com.mousesvscats.game.GameLogic;

import java.awt.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Артём on 04.12.2014.
 */
public class Sector extends GameObject {
    private SectorType sectorType;

    /**Конструктор сектора*/
    public Sector(SectorType sectorType) {
        setSectorType(sectorType);
    }

    /**Получить тип сектора*/
    public SectorType getSectorType() {
        return sectorType;
    }
    /**Установить тип сектора*/
    public void setSectorType(SectorType sectorType) {
        this.sectorType = sectorType;
    }

}
