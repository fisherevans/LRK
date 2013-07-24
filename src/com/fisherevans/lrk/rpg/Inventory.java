package com.fisherevans.lrk.rpg;

import com.fisherevans.lrk.rpg.items.Item;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Inventory
{
    private ArrayList<Item> _items;
    private int _maxItems = 25;

    public Inventory()
    {
        _items = new ArrayList<>();
    }

    public ArrayList<Item> getItems()
    {
        return _items;
    }

    public void setItems(ArrayList<Item> items)
    {
        _items = items;
    }
}
