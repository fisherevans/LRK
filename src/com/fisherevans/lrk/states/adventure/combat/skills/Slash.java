package com.fisherevans.lrk.states.adventure.combat.skills;

import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.combat.effects.HealthCone;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Slash extends Skill
{
    private AdventureEntity _owner;
    private AdventureEntity.Team[] _effects;

    public Slash(AdventureEntity owner, AdventureEntity.Team... effects)
    {
        super("Slash", "Slash with your weapon.");
        _effects = effects;
    }

    @Override
    public boolean execute(AdventureEntity owner)
    {
        owner.getState().getEntityEffectQueue().addEntityEffect(
                new HealthCone(
                        (float)Math.toRadians(owner.getDegrees()),
                        90, 1.5f, owner.getBody().getPosition().clone(),
                        getDamage(owner.getRpgEntity(), null), _effects
                )
        );
        return true;
    }
}
