package com.fisherevans.lrk.states.adventure.lights.light_controllers;

import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.states.adventure.lights.Light;
import com.fisherevans.lrk.states.adventure.lights.LightController;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/13/13
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TargetLightController extends LightController
{
    private AdventureEntity _target;

    public TargetLightController(Light light, AdventureEntity target)
    {
        super(light);
        _target = target;
    }

    @Override
    public void update(float delta)
    {
        getLight().setPosition(_target.getBody().getPosition());
    }

    public AdventureEntity getTarget()
    {
        return _target;
    }

    public void setTarget(AdventureEntity target)
    {
        _target = target;
    }
}
