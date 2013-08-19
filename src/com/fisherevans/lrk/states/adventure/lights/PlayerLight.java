package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.states.adventure.lights.light_controllers.TargetLightController;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/13/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerLight extends Light
{
    public PlayerLight(LightManager manager)
    {
        super(1f, new Color(0.75f, 0.6f, 0.4f), new Vec2(0, 0), manager);
        //super(1f, new Color(1f, 1f, 0f), new Vec2(0, 0), manager);
        setController(new TargetLightController(this, getManager().getState().getEntityManager().getPlayer()));
    }

    @Override
    public float getRadius()
    {
        return getManager().getState().getRenderDistance()*0.666f;
    }
}
