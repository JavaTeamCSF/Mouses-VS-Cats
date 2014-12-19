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

    /**Конструктор сектора*/
    public Sector(SectorType sectorType) {
        setSectorType(sectorType);
        if (sectorType == SectorType.EMPTY) this.setTexture(new Texture(Gdx.files.internal("empty.png")));
        else if (sectorType == SectorType.WALL) this.setTexture(new Texture(Gdx.files.internal("wall.png")));
        else if (sectorType == SectorType.CLOSED_VER_DOOR) this.setTexture(new Texture(Gdx.files.internal("door_ver.png")));
        else if (sectorType == SectorType.CLOSED_GOR_DOOR) this.setTexture(new Texture(Gdx.files.internal("door_gor.png")));
        else if (sectorType == SectorType.OPENED_DOOR) this.setTexture(new Texture(Gdx.files.internal("opened_door.png")));

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
}
