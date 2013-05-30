package com.fisherevans.lrk.rpg.entitycomponents;

import com.fisherevans.lrk.rpg.RPGEntity;

/**
 * User: Fisher evans
 * Date: 5/29/13
 * Time: 9:04 PM
 */
public abstract class LevelListener
{
    public abstract void leveledUp(int newLevel, RPGEntity entity);
}
