package com.fisherevans.lrk.rpg.items;

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
    private int _lightStrength = 0;

    public Light(String name, String description, Image image, int lightStrength)
    {
        super(name, description, image, Position.Light, 0, 0);
        _lightStrength = lightStrength;
    }

    public int getLightStrength()
    {
        return _lightStrength;
    }

    public void setLightStrength(int lightStrength)
    {
        _lightStrength = lightStrength;
    }
}
