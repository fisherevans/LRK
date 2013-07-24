package com.fisherevans.lrk.rpg;

import com.fisherevans.lrk.rpg.items.Item;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 9:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Character
{
    private ArrayList<Item> _inventory;
    private RPGEntity _entity;

    public Character(RPGEntity entity)
    {
        _entity = entity;

        _inventory = new ArrayList<>();
    }

    public ArrayList<Item> getInventory()
    {
        return _inventory;
    }

    public void setInventory(ArrayList<Item> inventory)
    {
        _inventory = inventory;
    }

    public RPGEntity getEntity()
    {
        return _entity;
    }

    public void setEntity(RPGEntity entity)
    {
        _entity = entity;
    }
}
