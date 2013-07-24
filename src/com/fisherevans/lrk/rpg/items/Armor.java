package com.fisherevans.lrk.rpg.items;

import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Armor extends Equipment
{
    public enum Position { Head, Chest, Legs }

    private Position _position;

    private int _enduranceBuff, _willPowerBuff;

    public Armor(String name, String description, Image image, Position position, int enduranceBuff, int willPowerBuff)
    {
        super(name, description, image);
        _position = position;
        _enduranceBuff = enduranceBuff;
        _willPowerBuff = willPowerBuff;
    }

    public Position getPosition()
    {
        return _position;
    }

    public void setPosition(Position position)
    {
        _position = position;
    }

    public int getEnduranceBuff()
    {
        return _enduranceBuff;
    }

    public void setEnduranceBuff(int enduranceBuff)
    {
        _enduranceBuff = enduranceBuff;
    }

    public int getWillPowerBuff()
    {
        return _willPowerBuff;
    }

    public void setWillPowerBuff(int willPowerBuff)
    {
        _willPowerBuff = willPowerBuff;
    }
}
