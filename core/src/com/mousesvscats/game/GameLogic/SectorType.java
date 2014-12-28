package com.mousesvscats.game.GameLogic;

/**
 * Created by User on 09.12.2014.
 */
public enum SectorType {
    EMPTY,          //свободная клетка
    WALL,           //стена
    CLOSED_VER_DOOR,//закрытая вертикальная дверь
    CLOSED_GOR_DOOR,//закрытая горизонтальная стена
    OPENED_DOOR;    //открытая дверь
}
