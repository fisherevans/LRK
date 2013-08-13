package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.entities.PlayerEntity;
import com.fisherevans.lrk.states.adventure.lights.light_controllers.TargetLightController;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/13/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerLight extends Light
{
    private AdventureEntity _player;

    public PlayerLight(AdventureEntity player)
    {
        super(1f, new Color(0.85f, 0.8f, 0.7f), new Vec2(0, 0));
        setController(new TargetLightController(this, player));
        _player = player;
    }

    @Override
    public float getRadius()
    {
        return _player.getState().getRenderDistance();
    }
}
