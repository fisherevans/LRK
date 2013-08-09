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

    /**
     * calculates the difference between the two angles in degrees.
     * Example: getAngleDifference(30, 350) = -40;
     * @param a1 angle 1 in degrees
     * @param a2 angle 2 in degrees
     * @return the angular distance from a1 to a2 (a1-a2)
     */
    public static float getAngleDifferenceDegrees(float a1, float a2)
    {
        float ad = a2 - a1;

        if (ad > 180)  ad -= 360;
        else if (ad < -180) ad += 360;

        return ad;
    }

    private static float FULL = (float) (Math.PI*2);
    private static float HALF = (float) (Math.PI);
    /**
     * calculates the difference between the two angles in radians.
     * Example: getAngleDifference(30, 350) = -40;
     * @param a1 angle 1 in radians
     * @param a2 angle 2 in radians
     * @return the angular distance from a1 to a2 (a1-a2)
     */
    public static float getAngleDifferenceRadians(float a1, float a2)
    {
        //float ad = fixRadian(a2) - fixRadian(a1);
        float ad = a2 - a1;

        if (ad > HALF)  ad -= FULL;
        else if (ad < -HALF) ad += FULL;

        return ad;
    }

    public static float fixRadian(float a)
    {
        a %= FULL;
        if(a < 0) a += FULL;
        return a;
    }
}
