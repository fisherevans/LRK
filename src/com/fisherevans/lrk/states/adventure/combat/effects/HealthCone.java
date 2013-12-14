package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.states.adventure.combat.skills.HealthSkill;
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
    private RPGEntity _server;
    private HealthSkill _skill;

    public HealthCone(float aimAngle, float widthAngle, float length, Vec2 pos, RPGEntity server, HealthSkill skill, AdventureEntity.Team... effects)
    {
        super(aimAngle, widthAngle, length, pos, effects);
        _server = server;
        _skill = skill;
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
