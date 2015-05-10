package com.fisherevans.lrk.states.adventure.combat.skills;

import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 11/23/13
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class HealthSkill extends Skill
{
    private float _base, _shift;
    private boolean _constant = false;

    public HealthSkill(String name, String description, float coolDown, float actionTime, float damageLow, float damageHigh)
    {
        super(name, description, coolDown, actionTime);
        _base = damageLow;
        _shift = damageHigh - damageLow;
    }

    public HealthSkill(String name, String description, float coolDown, float actionTime, float damage)
    {
        super(name, description, coolDown, actionTime);
        _base = damage;
        _shift = 0;
        _constant = true;
    }

    public float getHealthDiff()
    {
        if(_constant)
            return _base;

        return (float) (_base + Math.random()*_shift);
    }

    public void damage(RPGEntity dealer, RPGEntity receiver)
    {
        float compareScale = (float) Math.log((dealer.getTotalPower() / receiver.getTotalDefence()) + 1);
        int damage = (int) (compareScale*getHealthDiff());
        receiver.getHealth().adjustHealth(damage);
    }
}
