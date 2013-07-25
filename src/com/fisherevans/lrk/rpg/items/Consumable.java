package com.fisherevans.lrk.rpg.items;

import com.fisherevans.lrk.rpg.items.effects.Effect;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Consumable extends Item
{
    private Effect _effect;
    private int _count, _maxCount;

    public Consumable(String name, String sortName, String description, Image image, Effect effect, int maxCount)
    {
        super(name, sortName, description, image);
        _effect = effect;
        _maxCount = maxCount;
    }

    public Consumable(String name, String description, Image image, Effect effect, int maxCount)
    {
        this(name, name, description, image, effect, maxCount);
    }

    public Effect getEffect()
    {
        return _effect;
    }

    public void setEffect(Effect effect)
    {
        _effect = effect;
    }

    public int getCount()
    {
        return _count;
    }

    public void setCount(int count)
    {
        _count = count;
    }

    public int getMaxCount()
    {
        return _maxCount;
    }

    public void setMaxCount(int maxCount)
    {
        _maxCount = maxCount;
    }
}
