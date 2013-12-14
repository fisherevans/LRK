package com.fisherevans.lrk.states.adventure.combat.skills;

import com.fisherevans.lrk.managers.SoundManager;
import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.combat.effects.HealthCone;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.sprites.SpriteGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Slash extends HealthSkill
{
    private AdventureEntity.Team[] _effects;
    private boolean _clockWise = true;

    public Slash(AdventureEntity.Team... effects)
    {
        super("Slash", "Slash with your weapon.", 0.5f, 0.25f);
        _effects = effects;
    }

    @Override
    public boolean execute(AdventureEntity owner)
    {
        SoundManager.play("slash"); // PLAY THE SOUND

        HealthCone effect = new HealthCone((float)Math.toRadians(owner.getDegrees()), (float) Math.toRadians(90), // CREATE THE HEALTH CONE EFFECT
                1f, owner.getBody().getPosition().clone(), owner.getRpgEntity(), this, _effects);
        owner.getState().getEffectManager().addEntityEffect(effect); // AND ADD IT TO THE EFFECT QUEUE

        owner.getState().getBackgroundSpriteManager().addSprite(SpriteGenerator.getSlash(owner, _clockWise)); // ADD A SLASH SPRITE
        _clockWise = !_clockWise;

        return true;
    }

    @Override
    public float getHealthDiff()
    {
        return 10;
    }
}
