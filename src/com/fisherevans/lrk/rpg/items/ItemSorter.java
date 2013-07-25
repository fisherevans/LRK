package com.fisherevans.lrk.rpg.items;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/24/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemSorter implements Comparator<Item>
{
    @Override
    public int compare(Item i1, Item i2)
    {
        return i1.getSortName().compareTo(i2.getSortName());
    }
}
