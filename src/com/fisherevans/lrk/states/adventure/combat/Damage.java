package com.fisherevans.lrk.states.adventure.combat;

import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/5/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Damage
{
    public AdventureEntity[] _immune;

    public Damage(AdventureEntity[] immune)
    {
        _immune = immune;
    }

    public abstract boolean isEligible(AdventureEntity entity);

    public abstract void dealDamage(AdventureEntity entity);
}
