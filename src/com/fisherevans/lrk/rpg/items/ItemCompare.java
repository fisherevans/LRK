package com.fisherevans.lrk.rpg.items;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/10/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemCompare implements Comparator<Item>
{
    @Override
    public int compare(Item i1, Item i2)
    {
        Equipment.Position ip1 = (i1 instanceof Equipment) ? ((Equipment)i1).getPosition() : Equipment.Position.AAA_First;
        Equipment.Position ip2 = (i2 instanceof Equipment) ? ((Equipment)i2).getPosition() : Equipment.Position.AAA_First;

        int posCompare = ip1.name().compareTo(ip2.toString());

        if(posCompare == 0)
        {
            return i1.getName().compareTo(i2.getName());
        }
        else
            return posCompare;
    }
}
