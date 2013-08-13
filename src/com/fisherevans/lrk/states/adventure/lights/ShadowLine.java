package com.fisherevans.lrk.states.adventure.lights;

import com.fisherevans.lrk.LRK;
import org.jbox2d.common.Vec2;

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

    public ShadowLine(float x1, float y1, float x2, float y2)
    {
        _pointA = new Vec2(x1, y1);
        _pointB = new Vec2(x2, y2);
    }

    public Vec2 getPointA()
    {
        return _pointA;
    }

    public Vec2 getPointB()
    {
        return _pointB;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof ShadowLine))
            return equals((ShadowLine)obj);
        else
            return false;
    }

    public boolean equals(ShadowLine line)
    {
        LRK.log("equals?");
        return (_pointA.equals(line.getPointA()) && _pointB.equals(line.getPointB()))
                || (_pointB.equals(line.getPointA()) && _pointA.equals(line.getPointB()));
    }
}