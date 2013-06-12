package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;

import java.awt.*;
import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 1:36 PM
 */
public class DisplayManager extends ComponentManager
{
    private static int _width = 800;
    private static int _height = 600;
    private static int _scale = 4;
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
        // TODO
        switch(key)
        {

        }
    }

    /**
     * sets the scale of the display window and then resizes the screen
     * @param scale new scale to be used based on the BASE SCREEN vars
     */
    public static void setScale(int scale)
    {
        _scale = scale;
        refreshDisplay();
    }

    /**
     * turns fullscreen on and off
     * @param fullscreen
     */
    public static void setFullscreen(boolean fullscreen)
    {
        _fullscreen = fullscreen;
        refreshDisplay();
    }

    /**
     * calls both setScale and setFullscreen
     * @param scale
     * @param fullscreen
     */
    public static void setScreenProperties(int width, int height, int scale, boolean fullscreen)
    {
        _fullscreen = fullscreen;
        _width = width;
        _height = height;
        _scale = scale;
        refreshDisplay();
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
        refreshDisplay();
    }

    public static void refreshDisplay()
    {
        LRKState state = StateLibrary.getActiveState();
        if(state != null)
            state.resize();
        Game.gameCanvas.getContainer().getGraphics().scale(_scale, _scale);
    }

    public static Dimension getDimensions()
    {
        return new Dimension(_width, _height);
    }
}
