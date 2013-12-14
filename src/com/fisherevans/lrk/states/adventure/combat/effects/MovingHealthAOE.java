package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.states.adventure.combat.skills.HealthSkill;
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

    public MovingHealthAOE(float radius, float duration, float rate, AdventureEntity target, HealthSkill skill, AdventureEntity.Team... effects)
    {
        super(0, 0, radius, duration, rate, target.getRpgEntity(), skill, effects);
        _target = target;
        setPos(_target.getBody().getPosition().clone());
    }

    @Override
    public void update(float delta)
    {
        setPos(_target.getBody().getPosition().clone());
        super.update(delta);
    }
}
