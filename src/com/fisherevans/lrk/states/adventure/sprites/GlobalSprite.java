package com.fisherevans.lrk.states.adventure.sprites;

import org.jbox2d.common.Vec2;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/11/13
 * Time: 2:45 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class GlobalSprite extends Sprite
{
    private Vec2 _wolrdPos;

    public GlobalSprite(float x, float y)
    {
        _wolrdPos = new Vec2(x, y);
    }

    public Vec2 getWolrdPos()
    {
        return _wolrdPos;
    }

    public void setWolrdPos(Vec2 wolrdPos)
    {
        _wolrdPos = wolrdPos;
    }
}
