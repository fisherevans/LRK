package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.launcher.Game;

import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 1:36 PM
 */
public class DisplayManager extends ComponentManager
{
    public static final int
            BASE_SCREEN_WIDTH = 480,
            BASE_SCREEN_HEIGHT = 270;

    private static int _displayScale = 1;
    private static boolean _displayFullscreen = false;
    
    @Override
    public void saveProperties(PrintWriter out)
    {
        out.println("display.scale=" + getDisplayScale());
        out.println("display.fullscreen=" + getDisplayFullscreen());
    }

    @Override
    public void setProperty(String key, String value)
    {
        switch(key)
        {
            case "scale": _displayScale = Integer.parseInt(value); break;
            case "fullscreen": _displayFullscreen = Boolean.parseBoolean(value); break;
        }
    }

    /**
     * sets the scale of the display window and then resizes the screen
     * @param scale new scale to be used based on the BASE SCREEN vars
     */
    public static void setScale(int scale)
    {
        boolean changed = (scale != _displayScale);
        if(changed)
        {
            _displayScale = scale;
            Game.updateDisplay();
        }
    }

    /**
     * turns fullscreen on and off
     * @param fullscreen
     */
    public static void setFullscreen(boolean fullscreen)
    {
        _displayFullscreen = fullscreen;
    }

    /**
     * calls both setScale and setFullscreen
     * @param scale
     * @param fullscreen
     */
    public static void setScreenProperties(int scale, boolean fullscreen)
    {
        setScale(scale);
        setFullscreen(fullscreen);
    }

    // GETTERS

    public static int getDisplayScale()
    {
        return _displayScale;
    }

    /**
     * @return width of render window
     */
    public static int getGameWidth()
    {
        return BASE_SCREEN_WIDTH;
    }


    /**
     * @return height of render window
     */
    public static int getGameHeight()
    {
        return BASE_SCREEN_HEIGHT;
    }


    /**
     * @return width of the actual window
     */
    public static int getDisplayWidth()
    {
        return BASE_SCREEN_WIDTH*_displayScale;
    }

    /**
     * @return height of the actual window
     */
    public static int getDisplayHeight()
    {
        return BASE_SCREEN_HEIGHT*_displayScale;
    }

    public static boolean getDisplayFullscreen()
    {
        return _displayFullscreen;
    }
}
