package com.fisherevans.lrk.rpg;

import com.fisherevans.lrk.rpg.items.Consumable;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.rpg.items.Item;
import com.fisherevans.lrk.rpg.items.ItemSorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Inventory
{
    public static final int MAX_ITEMS = 25;

    private ArrayList<Item> _equipment;
    private ArrayList<Item> _consumables;

    private Comparator<Item> _itemSorter;

    public Inventory()
    {
        _equipment = new ArrayList<>();
        _consumables = new ArrayList<>();

        _itemSorter = new ItemSorter();
    }

    public ArrayList<Item> getEquipment()
    {
        return _equipment;
    }

    public ArrayList<Item> getConsumables()
    {
        return _consumables;
    }

    public int getInventorySize()
    {
        return _equipment.size() + _consumables.size();
    }

    public boolean addItem(Item item)
    {
        if(getInventorySize() >= MAX_ITEMS)
            return false;
        else if(item instanceof Equipment)
        {
            _equipment.add((Equipment) item);
            Collections.sort(_equipment, _itemSorter);
            return true;
        }
        else if(item instanceof Consumable)
        {
            _consumables.add((Consumable) item);
            Collections.sort(_equipment, _itemSorter);
            return true;
        }
        else
            return false;
    }

    public boolean removeItem(Item item)
    {
        if(getInventorySize() <= 0)
            return false;
        else if(item instanceof Equipment)
            return _equipment.remove(item);
        else if(item instanceof Consumable)
            return _consumables.remove(item);
        else
            return false;
    }
}
