package com.fisherevans.lrk.states.adventure.combat.skills;

import com.fisherevans.lrk.managers.SoundManager;
import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.combat.effects.HealthCone;
import com.fisherevans.lrk.states.adventure.combat.effects.MovingHealthAOE;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.sprites.SpriteGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Whirlwind extends Skill
{
    private AdventureEntity.Team[] _effects;

    public Whirlwind(AdventureEntity.Team... effects)
    {
        super("Whirlwind", "Spin around like a princess.", 5f, 1f);
        _effects = effects;
    }

    @Override
    public boolean execute(AdventureEntity owner)
    {
        SoundManager.play("whirlwind"); // PLAY THE SOUND

        MovingHealthAOE effect = new MovingHealthAOE(1.4f, 1f, 0.25f, -5, owner, _effects);
        owner.getState().getEntityEffectQueue().addEntityEffect(effect); // AND ADD IT TO THE EFFECT QUEUE

        owner.getState().getBackgroundSpriteSystem().addSprite(SpriteGenerator.getWhirlwind(owner)); // ADD A SLASH SPRITE

        return true;
    }
}
