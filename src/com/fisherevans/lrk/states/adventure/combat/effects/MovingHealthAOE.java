package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class MovingHealthAOE extends HealthAOE
{
    private AdventureEntity _target;

    public MovingHealthAOE(float x, float y, float radius, float duration, float rate, float healthDiff, AdventureEntity target, AdventureEntity.Team... effects)
    {
        super(x, y, radius, duration, rate, healthDiff, effects);
        _target = target;
    }

    @Override
    public void update(float delta)
    {
        setPos(_target.getBody().getPosition().clone());
        super.update(delta);
    }
}
