package com.fisherevans.lrk.rpg.items;

import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/23/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Armor extends Equipment
{
    public Armor(String name, String description, Image image, Position position, int power, int defence)
    {
        super(name, description, image, position, power, defence);
    }
}
