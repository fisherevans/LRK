package com.fisherevans.lrk.rpg.items;

import com.fisherevans.lrk.rpg.items.enchantments.Enchantment;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Equipment extends Item
{
    private Enchantment _enchantment;

    public Equipment(String name, String description, Image image)
    {
        super(name, name, description, image);
    }

    public Enchantment getEnchantment()
    {
        return _enchantment;
    }

    public void setEnchantment(Enchantment enchantment)
    {
        _enchantment = enchantment;
    }
}
