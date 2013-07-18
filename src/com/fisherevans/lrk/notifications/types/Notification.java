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
    private static final float COLOR_ON = 1f;
    private static final float COLOR_OFF = 0.8f;
    private static final float COLOR_HALF = 0.9f;
    private static final float COLOR_ALPHA = 1f;
    
    public static final Color GREY = new Color(COLOR_ON, COLOR_ON, COLOR_ON, COLOR_ALPHA);
    public static final Color BLUE = new Color(COLOR_OFF, COLOR_OFF, COLOR_ON, COLOR_ALPHA);
    public static final Color PURPLE = new Color(COLOR_ON, COLOR_OFF, COLOR_ON, COLOR_ALPHA);
    public static final Color RED = new Color(COLOR_ON, COLOR_OFF, COLOR_OFF, COLOR_ALPHA);
    public static final Color ORANGE = new Color(COLOR_ON, COLOR_HALF, COLOR_OFF, COLOR_ALPHA);
    public static final Color YELLOW = new Color(COLOR_ON, COLOR_ON, COLOR_OFF, COLOR_ALPHA);
    public static final Color GREEN = new Color(COLOR_OFF, COLOR_ON, COLOR_OFF, COLOR_ALPHA);

    public static final float DEFAULT_END_TIME = 4f;
    public static final Color DEFAULT_BACKGROUND = GREY;
    public static final Color DEFAULT_FOREGROUND = new Color(1f, 1f, 1f);

    private String _message;
    private float _duration, _time;
    private Color _background, _foreground;
    private Image _icon;

    public Notification(String message, float duration, Color foreground, Color background, Image icon)
    {
        _message = message;
        _duration = duration;
        _foreground = foreground;
        _background = background;
        _icon = icon;

        _time = 0;
    }

    public Notification(String message, float duration, Color foreground, Color background)
    {
        this(message, duration, foreground, background, null);
    }

    public Notification(String message, float duration)
    {
        this(message, duration, DEFAULT_FOREGROUND, DEFAULT_BACKGROUND);
    }

    public Notification(String message, Color foreground, Color background)
    {
        this(message, DEFAULT_END_TIME, foreground, background);
    }

    public Notification(String message, Color background, Image icon)
    {
        this(message, DEFAULT_END_TIME, DEFAULT_FOREGROUND, background, icon);
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

    public Image getIcon()
    {
        return _icon;
    }

    public void setIcon(Image icon)
    {
        _icon = icon;
    }
}
