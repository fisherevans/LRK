package com.fisherevans.lrk.states.adventure.lights.light_controllers;

import com.fisherevans.lrk.states.adventure.lights.Light;
import com.fisherevans.lrk.states.adventure.lights.LightController;
import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/13/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class TorchController extends LightController
{
    private Color _baseColor;
    private long _nextFlicker;

    private final int FLICKER_DURATION = 150;
    private final float FLICKER_DELTA = 0.15f;

    public TorchController(Light light)
    {
        super(light);
        _baseColor = getLight().getColor();
    }

    @Override
    public void update(float delta)
    {
        if(System.currentTimeMillis() > _nextFlicker)
        {
            getLight().setColor(_baseColor.darker((float) Math.random() * FLICKER_DELTA));
            _nextFlicker = System.currentTimeMillis() + (long)(Math.random()*FLICKER_DURATION);
        }
    }
}
