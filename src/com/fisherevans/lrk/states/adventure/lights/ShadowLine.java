package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.LRK;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/13/13
 * Time: 6:29 PM
 * To change this template use File | Settings | File Templates.
 */

public class ShadowLine
{
    private Vec2 _pointA, _pointB;
    private Rectangle _boundingBox;

    public ShadowLine(float x1, float y1, float x2, float y2)
    {
        _pointA = new Vec2(x1, y1);
        _pointB = new Vec2(x2, y2);

        float x = Math.min(x1, x2);
        float y = Math.min(y1, y2);
        float width = Math.max(x1, x2) - x;
        float height = Math.max(y1, y2) - y;
        _boundingBox = new Rectangle(x, y, width, height);
    }

    public Vec2 getPointA()
    {
        return _pointA;
    }

    public Vec2 getPointB()
    {
        return _pointB;
    }

    public Rectangle getBoundingBox()
    {
        return _boundingBox;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof ShadowLine)
            return equals((ShadowLine)obj);
        else
            return false;
    }

    public boolean equals(ShadowLine line)
    {
        return (_pointA.equals(line.getPointA()) && _pointB.equals(line.getPointB()))
                || (_pointB.equals(line.getPointA()) && _pointA.equals(line.getPointB()));
    }
}