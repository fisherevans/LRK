package com.fisherevans.lrk.rpg.items;

import com.fisherevans.lrk.launcher.Game;
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
    public enum Position { AAA_First, Head, Chest, Legs, MainHand, OffHand, Light }

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

    public boolean isEquipped()
    {
        return Game.lrk.getPlayer().getEntity().getEquipmentMap().get(_position) == this;
    }

    public String getPositionName()
    {
        if(this instanceof Weapon && ((Weapon)this).isTwoHanded())
            return "Two Handed";
        else
        {
            switch(getPosition())
            {
                case Head: return "Head";
                case Chest: return "Chest";
                case Legs: return "Legs";
                case MainHand: return "Main Hand";
                case OffHand: return "Off Hand";
            }
        }
        return "Index Finger";
    }
}
