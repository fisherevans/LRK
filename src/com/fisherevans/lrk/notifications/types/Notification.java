package com.fisherevans.lrk.notifications.types;

import com.fisherevans.lrk.Resources;
import org.newdawn.slick.Color;
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
    public static final Color GREY = new Color(0.7f, 0.7f, 0.7f, 0.45f);
    public static final Color BLUE = new Color(0.4f, 0.4f, 0.7f, 0.45f);
    public static final Color RED = new Color(0.7f, 0.4f, 0.4f, 0.45f);
    public static final Color YELLOW = new Color(0.7f, 0.7f, 0.4f, 0.45f);
    public static final Color GREEN = new Color(0.4f, 0.7f, 0.4f, 0.45f);

    public static final float DEFAULT_END_TIME = 4f;
    public static final Color DEFAULT_BACKGROUND = GREY;
    public static final Color DEFAULT_FOREGROUND = new Color(1f, 1f, 1f);

    private String _message;
    private float _duration, _time;
    private Color _background, _foreground;

    public Notification(String message, float duration, Color foreground, Color background)
    {
        _message = message;
        _duration = duration;
        _foreground = foreground;
        _background = background;

        _time = 0;
    }

    public Notification(String message, float duration)
    {
        this(message, duration, DEFAULT_FOREGROUND, DEFAULT_BACKGROUND);
    }

    public Notification(String message, Color foreground, Color background)
    {
        this(message, DEFAULT_END_TIME, foreground, background);
    }

    public Notification(String message, Color background)
    {
        this(message, DEFAULT_END_TIME, DEFAULT_FOREGROUND, background);
    }

    public Notification(String message)
    {
        this(message, DEFAULT_END_TIME, DEFAULT_FOREGROUND, DEFAULT_BACKGROUND);
    }

    public void update(float delta)
    {
        _time += delta;
    }

    public float getInterpolation()
    {
        return _time/_duration;
    }

    public boolean isExpired()
    {
        return _time >= _duration;
    }

    public String getMessage()
    {
        return _message;
    }

    public Color getBackground()
    {
        return _background;
    }

    public Color getForeground()
    {
        return _foreground;
    }
}
