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
    public HealthSkill(String name, String description, float coolDown, float actionTime)
    {
        super(name, description, coolDown, actionTime);
    }

    public abstract float getHealthDiff();

    public void damage(RPGEntity dealer, RPGEntity receiver)
    {
        receiver.getHealth().adjustHealth((float) (Math.log((dealer.getTotalPower()/receiver.getTotalDefence()) + 1)*getHealthDiff()));
    }
}
