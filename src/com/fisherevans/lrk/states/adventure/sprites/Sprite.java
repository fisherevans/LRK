package com.fisherevans.lrk.states.adventure.sprites;

import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/10/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Sprite
{
    public abstract void render(Graphics gfx, float xShift, float yShift);

    public abstract void update(float delta);

    public abstract boolean isComplete();

    public boolean doRender()
    {
        return true;
    }
}
