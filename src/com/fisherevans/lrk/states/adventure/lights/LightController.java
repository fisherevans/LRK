package com.fisherevans.lrk.states.adventure.lights;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/13/13
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class LightController
{
    private Light _light;

    public LightController(Light light)
    {
        _light = light;
    }

    public abstract void update(float delta);

    public Light getLight()
    {
        return _light;
    }

    public void setLight(Light light)
    {
        _light = light;
    }
}
