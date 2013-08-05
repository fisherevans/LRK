package com.fisherevans.lrk.states.adventure.combat;

import com.fisherevans.lrk.rpg.RPGEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/4/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Attack
{
    private RPGEntity _owner;

    protected Attack(RPGEntity owner)
    {
        _owner = owner;
    }

    public abstract RPGEntity[] dealDamage();

    public RPGEntity getOwner()
    {
        return _owner;
    }
}
