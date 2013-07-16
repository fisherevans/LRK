package com.fisherevans.lrk.notifications.types;

import com.fisherevans.lrk.Resources;
import org.newdawn.slick.Image;
import org.newdawn.slick.UnicodeFont;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/15/13
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class Notification
{
    public static final float DEFAULT_END_TIME = 4f;

    private String _message;

    private float _endTime, _time = 0;
    private long _startTime = -1;


    public Notification(String message)
    {
        _message = message;

        _endTime = DEFAULT_END_TIME;
    }

    public void update(float delta)
    {
        _time += delta;
    }

    public float getInterpolation()
    {
        return _time/_endTime;
    }

    public boolean isExpired()
    {
        return _time >= _endTime;
    }

    public String getMessage()
    {
        return _message;
    }
}
