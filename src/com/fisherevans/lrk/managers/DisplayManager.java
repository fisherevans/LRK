package com.fisherevans.lrk.managers;

import java.awt.*;
import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 1:36 PM
 */
public class DisplayManager extends ComponentManager
{
    public static int
            BASE_SCREEN_WIDTH,
            BASE_SCREEN_HEIGHT;

    private static double _displayScale = 1;
    private static boolean _displayFullscreen = false;


    public static double _aspectRatio;


    private static int _width = 800;
    private static int _height = 600;
    private static int _scale = 1;
    private static boolean _fullscreen = false;

    @Override
    public void saveProperties(PrintWriter out)
    {
        out.println("display.scale=" + getScale());
        out.println("display.fullscreen=" + getFullscreen());

        out.println("new.display.scale=" + _scale);
        out.println("new.display.width=" + _width);
        out.println("new.display.height=" + _height);
        out.println("new.display.fullscreen=" + _fullscreen);
    }

    @Override
    public void setProperty(String key, String value)
    {
        switch(key)
        {
            case "scale": _displayScale = Double.parseDouble(value); break;
            case "fullscreen": _displayFullscreen = Boolean.parseBoolean(value); break;
        }
    }

    /**
     * sets the scale of the display window and then resizes the screen
     * @param scale new scale to be used based on the BASE SCREEN vars
     */
    public static void setScale(int scale)
    {
        _scale = scale;
    }

    /**
     * turns fullscreen on and off
     * @param fullscreen
     */
    public static void setFullscreen(boolean fullscreen)
    {
        _fullscreen = fullscreen;
    }

    /**
     * calls both setScale and setFullscreen
     * @param scale
     * @param fullscreen
     */
    public static void setScreenProperties(int width, int height, int scale, boolean fullscreen)
    {
        setScale(scale);
        setFullscreen(fullscreen);
    }

    // GETTERS

    public static double getScale()
    {
        return _scale;
    }

    /**
     * @return width of render window
     */
    public static int getWidth()
    {
        return _width;
    }

    /**
     * @return height of render window
     */
    public static int getHeight()
    {

        return _height;
    }

    public static boolean getFullscreen()
    {
        return _fullscreen;
    }

    public static void setDimensions(int width, int height)
    {
        _width = width;
        _height = height;
    }

    public static Dimension getDimensions()
    {
        return new Dimension(_width, _height);
    }
}
