package com.fisherevans.lrk.rpg.items.enchantments;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 11:23 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Enchantment
{
    private String _name, _description;

    public Enchantment(String name, String description)
    {
        _name = name;
        _description = description;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public String getDescription()
    {
        return _description;
    }

    public void setDescription(String description)
    {
        _description = description;
    }
}
