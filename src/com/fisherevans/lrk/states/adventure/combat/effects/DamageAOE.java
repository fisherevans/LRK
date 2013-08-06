package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class DamageAOE extends AOE
{
    private float damage;

    public DamageAOE(float x, float y, float radius, float duration, float rate, float damage, AdventureEntity.Team... effects)
    {
        super(x, y, radius, duration, rate, effects);
        this.damage = damage;
    }

    @Override
    public void effect(AdventureEntity entity)
    {
        entity.getRpgEntity().getHealth().subtractHealth(damage);
    }
}
