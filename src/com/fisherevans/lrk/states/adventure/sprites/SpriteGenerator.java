package com.fisherevans.lrk.states.adventure.sprites;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import org.newdawn.slick.SpriteSheet;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/11/13
 * Time: 1:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class SpriteGenerator
{
    public static Sprite getSlash(AdventureEntity owner)
    {
        SpriteSheet spriteSheet = new SpriteSheet(Resources.getImage("animations/sword_swing"), 44, 44); // CREATE THE ANIMATION SPRITESHEET
        Sprite sprite = new EntityImageOverlay(owner, 2, spriteSheet, 20, 11, 22); // CREATE THE SPRITE
        return sprite;
    }
}
