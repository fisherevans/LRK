package com.fisherevans.lrk.rpg.items;

import com.fisherevans.lrk.rpg.items.enchantments.Enchantment;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Equipment extends Item
{
    public enum Position { Head, Chest, Legs, MainHand, OffHand }

    private Position _position;

    private Enchantment _enchantment;

    private int _power, _defence;

    public Equipment(String name, String description, Image image, Position position, int power, int defence)
    {
        super(name, name, description, image);
        _position = position;
        _power = power;
        _defence = defence;
    }

    public Enchantment getEnchantment()
    {
        return _enchantment;
    }

    public void setEnchantment(Enchantment enchantment)
    {
        _enchantment = enchantment;
    }

    public Position getPosition()
    {
        return _position;
    }

    public void setPosition(Position position)
    {
        _position = position;
    }

    public int getPower()
    {
        return _power;
    }

    public void setPower(int power)
    {
        _power = power;
    }

    public int getDefence()
    {
        return _defence;
    }

    public void setDefence(int defence)
    {
        _defence = defence;
    }

    public boolean isEnchanted()
    {
        return _enchantment != null;
    }
}
