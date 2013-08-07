package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import org.jbox2d.common.Vec2;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class HealthCone extends Cone
{
    private float _healthDiff;

    public HealthCone(float aimAngle, float widthAngle, float length, Vec2 pos, float healthDiff, AdventureEntity.Team... effects)
    {
        super(aimAngle, widthAngle, length, pos, effects);
        _healthDiff = healthDiff;
    }

    @Override
    public void effect(AdventureEntity entity)
    {
        LRK.log("Health Cone, ACTIVATE! " + _healthDiff);
        entity.getRpgEntity().getHealth().adjustHealth(_healthDiff);
    }
}
