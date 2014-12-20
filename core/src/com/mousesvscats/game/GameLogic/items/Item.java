package com.mousesvscats.game.GameLogic.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mousesvscats.game.GameLogic.Cat;
import com.mousesvscats.game.GameLogic.Labyrinth;
import com.mousesvscats.game.GameLogic.Mouse;

/**
 * Created by Артём on 05.12.2014.
 */
public abstract class Item {

    public abstract void taken(Labyrinth labyrinth, Mouse mouse, int x, int y);
}
