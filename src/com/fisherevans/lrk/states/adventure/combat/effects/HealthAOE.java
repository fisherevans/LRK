package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 11:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class HealthAOE extends AOE
{
    private float _healthDiff;

    public HealthAOE(float x, float y, float radius, float duration, float rate, float healthDiff, AdventureEntity.Team... effects)
    {
        super(x, y, radius, duration, rate, effects);
        _healthDiff = healthDiff;
    }

    @Override
    public void effect(AdventureEntity entity)
    {
        entity.getRpgEntity().getHealth().adjustHealth(_healthDiff);
    }
}
