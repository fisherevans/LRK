package com.fisherevans.lrk.states.adventure.entities;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.managers.SoundManager;
import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.rpg.entitycomponents.Health;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.adventure.entities.controllers.MindlessZombieController;
import com.fisherevans.lrk.states.adventure.lights.Light;
import com.fisherevans.lrk.states.adventure.lights.light_controllers.TargetLightController;
import com.fisherevans.lrk.states.adventure.sprites.GlobalSpinFade;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 3:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class DumbBlob extends ActiveEntity implements Health.HealthListener
{
    public DumbBlob(RPGEntity rpgEntity, float x, float y, World world, AdventureState state)
    {
        super(rpgEntity, state, 2.25f);
        setTeam(Team.Hostile);
        setController(new MindlessZombieController(this, getState().getEntityManager().getPlayer(), 15));
        setBody(JBox2DUtils.getCircleBody(world, x, y, JBox2DUtils.DEFAULT_CIRCLE_RADIUS));
        setImage(Resources.getImage("entities/dummy-blob"));

        Light light = new Light(3, new Color(0, 0.1f, 0.166f), getBody().getPosition().clone(), state.getLightManager());
        light.setController(new TargetLightController(light, this));
        state.getLightManager().addLight(light);
    }

    @Override
    public void healthDecreased(RPGEntity entity, float amount)
    {
        super.healthDecreased(entity, amount);
        SoundManager.play("squish_01");
    }

    @Override
    public void destroy()
    {
        super.destroy();
        SoundManager.play("monster-death_01");
        getState().getBackgroundSpriteManager().addSprite(new GlobalSpinFade(getX(), getY(), 1, getImage(), 6, 1, getDegrees()));
    }
}
