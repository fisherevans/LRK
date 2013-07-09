package com.fisherevans.lrk.tools;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/9/13
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class LRKMath
{
    public static float clamp(float min, float x, float max)
    {
        if(x < min) x = min;
        else if(x > max) x = max;
        return x;
    }
}
