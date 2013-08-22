package com.fisherevans.lrk.rpg.items;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/10/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Light extends Equipment
{
    private float _lightStrength = 0;
    private Color _lightColor;

    public Light(String name, String description, Image image, float lightStrength, Color lightColor)
    {
        super(name, description, image, Position.Light, 0, 0);
        _lightStrength = lightStrength;
        _lightColor = lightColor;
    }

    public float getLightStrength()
    {
        return _lightStrength;
    }

    public void setLightStrength(float lightStrength)
    {
        _lightStrength = lightStrength;
    }

    public Color getLightColor()
    {
        return _lightColor;
    }

    public void setLightColor(Color lightColor)
    {
        _lightColor = lightColor;
    }
}
