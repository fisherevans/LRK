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
    public enum Position { MainHand, OffHand }

    private Position _position;

    private int _strengthBuff, _intelligenceBuff;

    private boolean _twoHanded;

    public Weapon(String name, String description, Image image, Position position, int strengthBuff, int intelligenceBuff, boolean twoHanded)
    {
        super(name, description, image);
        _position = position;
        _strengthBuff = strengthBuff;
        _intelligenceBuff = intelligenceBuff;
        _twoHanded = twoHanded;
    }

    public Position getPosition()
    {
        return _position;
    }

    public void setPosition(Position position)
    {
        _position = position;
    }

    public int getStrengthBuff()
    {
        return _strengthBuff;
    }

    public void setStrengthBuff(int strengthBuff)
    {
        _strengthBuff = strengthBuff;
    }

    public int getIntelligenceBuff()
    {
        return _intelligenceBuff;
    }

    public void setIntelligenceBuff(int intelligenceBuff)
    {
        _intelligenceBuff = intelligenceBuff;
    }

    public boolean isTwoHanded()
    {
        return _twoHanded;
    }

    public void setTwoHanded(boolean twoHanded)
    {
        _twoHanded = twoHanded;
    }
}
