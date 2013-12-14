package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.states.adventure.combat.skills.HealthSkill;
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
    private RPGEntity _server;
    private HealthSkill _skill;

    public HealthAOE(float x, float y, float radius, float duration, float rate, RPGEntity server, HealthSkill skill, AdventureEntity.Team... effects)
    {
        super(x, y, radius, duration, rate, effects);
        _skill = skill;
        _server = server;
    }

    @Override
    public void effect(AdventureEntity receiverAdventure)
    {
        float serverP, receiverD, statDifference, damage;
        RPGEntity receiver = receiverAdventure.getRpgEntity();

        serverP = _server.getTotalPower();
        receiverD = receiver.getTotalDefence();

        statDifference = serverP - receiverD;

        damage = statDifference < 0 ? 0 : statDifference*statDifference;

        receiver.getHealth().adjustHealth(-damage);
    }
}
