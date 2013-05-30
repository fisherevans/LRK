package com.fisherevans.lrk.rpg.entitycomponents;

import com.fisherevans.lrk.rpg.RPGEntity;

/**
 * User: Fisher evans
 * Date: 5/29/13
 * Time: 9:21 PM
 */
public abstract class HealthListener
{
    /**
     * called when an entity's health is changed to 0
     * @param entity the entity who "died"
     */
    public abstract void healthDepleted(RPGEntity entity);

    /**
     * Called when the given entity's health changed to it's maximum
     * @param entity the entity who's health maxed out
     */
    public abstract void healthFull(RPGEntity entity);
}
