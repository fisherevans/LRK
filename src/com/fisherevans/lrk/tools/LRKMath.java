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
    /**
     * clamps a given number between two extremes
     * @param min the minimum value to return
     * @param x the base value
     * @param max the max value to return.
     * @return returns x if it is within min and max. If x is below the min, returns min. Above max, returns max
     */
    public static float clamp(float min, float x, float max)
    {
        if(x < min) x = min;
        else if(x > max) x = max;
        return x;
    }
}
