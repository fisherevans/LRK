package com.fisherevans.lrk.rpg.items;

import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class Weapon extends Equipment
{
    private float _speed;

    private boolean _twoHanded;

    public Weapon(String name, String description, Image image, Position position, int power, int defence, float speed, boolean twoHanded)
    {
        super(name, description, image, position, power, defence);
        _speed = speed;
        _twoHanded = twoHanded;
    }

    public Weapon(String name, String description, Image image, Position position, int strengthBuff, int intelligenceBuff, float speed)
    {
        this(name, description, image, position, strengthBuff, intelligenceBuff, speed, false);
    }

    public boolean isTwoHanded()
    {
        return _twoHanded;
    }

    public void setTwoHanded(boolean twoHanded)
    {
        _twoHanded = twoHanded;
    }

    public float getSpeed()
    {
        return _speed;
    }
}
