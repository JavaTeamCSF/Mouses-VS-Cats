package com.mousesvscats.game.GameLogic;

import java.awt.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Rectangle;
import com.mousesvscats.game.GameLogic.items.Item;

/**
 * Created by Артём on 04.12.2014.
 */
public class Sector extends GameObject {
    private Item item;
    private SectorType sectorType;
    private boolean isCrossRoad;

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
    /**Получить предмет из сектора*/
    public Item getItem() {
        return item;
    }
    /**Установить предмет в сектор*/
    public void setItem(Item item) {
        this.item = item;
    }

    public void setCrossRoad(boolean isCrossRoad) {
        this.isCrossRoad=isCrossRoad;
    }
    public boolean isCrossRoad() {
        return this.isCrossRoad;
    }
}
