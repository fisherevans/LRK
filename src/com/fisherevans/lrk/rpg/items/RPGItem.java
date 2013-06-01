package com.fisherevans.lrk.rpg.items;

import org.newdawn.slick.Image;

/**
 * User: Fisher evans
 * Date: 5/30/13
 * Time: 8:33 PM
 */
public abstract class RPGItem extends InventoryItem
{
    public RPGItem(String name, String description, Image smallImage, Image largeImage)
    {
        super(name, description, smallImage, largeImage);
    }
}
