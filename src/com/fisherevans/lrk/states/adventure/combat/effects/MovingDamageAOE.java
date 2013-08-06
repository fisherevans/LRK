package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class MovingDamageAOE extends DamageAOE
{
    private AdventureEntity _target;

    public MovingDamageAOE(float x, float y, float radius, float duration, float rate, float damage, AdventureEntity target, AdventureEntity.Team... effects)
    {
        super(x, y, radius, duration, rate, damage, effects);
        _target = target;
    }

    @Override
    public void update(float delta)
    {
        setPos(_target.getBody().getPosition().clone());
        super.update(delta);
    }
}
