package com.fisherevans.lrk.rpg.items;

import org.newdawn.slick.Image;

/**
 * User: Fisher evans
 * Date: 5/30/13
 * Time: 8:05 PM
 */
public abstract class InventoryItem
{
    private Image _smallImage, _largeImage;
    private String _name, _description;

    public InventoryItem(String name, String description, Image smallImage, Image largeImage)
    {
        setAll(name, description, smallImage, largeImage);
    }

    public void setAll(String name, String description, Image smallImage, Image largeImage)
    {
        _name = name;
        _description = description;
        _smallImage = smallImage;
        _largeImage = largeImage;
    }

    public abstract boolean useItem();

    public Image getSmallImage()
    {
        return _smallImage;
    }

    public void setSmallImage(Image smallImage)
    {
        _smallImage = smallImage;
    }

    public Image getLargeImage()
    {
        return _largeImage;
    }

    public void setLargeImage(Image largeImage)
    {
        _largeImage = largeImage;
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
