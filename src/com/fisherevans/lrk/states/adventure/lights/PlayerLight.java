package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.rpg.items.Equipment;
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
        super(Resources.getImage("lights/player"), 1f, Color.black, new Vec2(0, 0), manager);
        setController(new TargetLightController(this, getManager().getState().getEntityManager().getPlayer()));
    }

    @Override
    public Color getColor()
    {
        Equipment light = Game.lrk.getPlayer().getEntity().getEquipment(Equipment.Position.Light);
        if(light != null && light instanceof com.fisherevans.lrk.rpg.items.Light)
            return ((com.fisherevans.lrk.rpg.items.Light)light).getLightColor();
        else
            return super.getColor();
    }

    @Override
    public float getRadius()
    {
        return getManager().getState().getRenderDistance()*0.666f;
    }
}
