package com.fisherevans.lrk.rpg.items;

import org.newdawn.slick.Image;

/**
 * User: Fisher evans
 * Date: 5/30/13
 * Time: 8:05 PM
 */
public abstract class Item
{
    private Image _image;
    private String _name, _sortName, _description;

    public Item(String name, String sortName, String description, Image image)
    {
        _name = name;
        _sortName = sortName;
        _description = description;
        _image = image;
    }

    public Image getImage()
    {
        return _image;
    }

    public void setImage(Image image)
    {
        _image = image;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public String getSortName()
    {
        return _sortName;
    }

    public void setSortName(String sortName)
    {
        _sortName = sortName;
    }

    public String getDescription()
    {
        return _description;
    }

    public void setDescription(String description)
    {
        _description = description;
    }

    //public abstract String getCreationString();
}
