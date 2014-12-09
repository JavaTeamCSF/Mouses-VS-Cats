package com.mousesvscats.game.GameLogic.Field;


/**
 * Created by Артём on 04.12.2014.
 */
public class Sector {
    private SectorType sectorType;
    private Item item; //сектор с предметом (item - экземпляр класса Item - предмет)

    /**Конструктор сектора*/
    public Sector(SectorType sectorType) {
        this.sectorType = sectorType;
        this.item = null;
    }

    /**Получить тип сектора*/
    public SectorType getSectorType() {
        return sectorType;
    }

    /**Установить тип сектора*/
    public void setSectorType(SectorType sectorType) {
        this.sectorType = sectorType;
    }

    /**Проверить, является ли сектор стеной*/
    public boolean isWall() {
        return (sectorType == SectorType.WALL);
    }

    /**Проверить, является ли сектор пустым*/
    public boolean isEmpty() {
        return (sectorType == SectorType.EMPTY);
    }

    /**Проверить, является ли сектор дверью*/
    public boolean isDoor() {
        return ((sectorType == SectorType.CLOSED_DOOR)||(sectorType == SectorType.OPENED_DOOR));
    }

    /**Проверить, является ли сектор открытой дверью*/
    public boolean isOpenedDoor() {
        return (sectorType == SectorType.OPENED_DOOR);
    }

    /**Проверить, является ли сектор закрытой дверью*/
    public boolean isClosedDoor() {
        return (sectorType == SectorType.CLOSED_DOOR);
    }

    /**Предмет в секторе*/
    public Item getItem(){return item;}

}
