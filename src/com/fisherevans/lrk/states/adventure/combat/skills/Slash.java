package com.fisherevans.lrk.states.adventure.combat.skills;

import com.fisherevans.lrk.managers.SoundManager;
import com.fisherevans.lrk.states.adventure.EntityEffectQueue;
import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.combat.effects.HealthCone;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import org.jbox2d.common.Vec2;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Slash extends Skill
{
    private AdventureEntity.Team[] _effects;

    public Slash(AdventureEntity.Team... effects)
    {
        super("Slash", "Slash with your weapon.", 0.5f);
        _effects = effects;
    }

    @Override
    public boolean execute(AdventureEntity owner)
    {
        SoundManager.play("hit");

        float aimAngle = (float)Math.toRadians(owner.getDegrees());
        float aimWidth = (float) Math.toRadians(90);
        float length = 1f;
        Vec2 position = owner.getBody().getPosition().clone();
        float healthDiff = -5;

        HealthCone effect = new HealthCone(aimAngle, aimWidth, length, position, healthDiff, _effects);

        EntityEffectQueue q = owner.getState().getEntityEffectQueue();
        q.addEntityEffect(effect);

        return true;
    }
}
