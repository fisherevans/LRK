package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 1:36 PM
 */
public class DisplayManager
{
    private static boolean _startMaximized = false;

    public static float WIDTH = 450f;

    private static int _positionX = 100;
    private static int _positionY = 100;

    private static int _windowWidth = 480;
    private static int _windowHeight = 320;

    private static float _scale = 1;

    public static void saveProperties(PrintWriter out)
    {
        out.println("display.width=" + _windowWidth);
        out.println("display.height=" + _windowHeight);

        out.println("display.position.x=" + _positionX);
        out.println("display.position.y=" + _positionY);

        out.println("display.maximized=" + (Game.window.getExtendedState() == JFrame.MAXIMIZED_BOTH));
    }

    public static void setProperty(String key, String value)
    {
        try
        {
            switch(key)
            {
                case "display.width": _windowWidth = Integer.parseInt(value);
                case "display.height": _windowHeight = Integer.parseInt(value);
                case "display.position.x": _positionX = Integer.parseInt(value);
                case "display.position.y": _positionY = Integer.parseInt(value);
                case "display.maximized": _startMaximized = Boolean.parseBoolean(value);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("Failed to load property: " + key + " = " + value);
        }
    }

    // GETTERS

    public static float getScale()
    {
        return _scale;
    }

    /**
     * @return width of actual window
     */
    public static int getWindowWidth()
    {
        return _windowWidth;
    }

    /**
     * @return height of actual window
     */
    public static int getWindowHeight()
    {

        return _windowHeight;
    }

    /**
     * @return width of render window
     */
    public static float getRenderWidth()
    {
        return _windowWidth/_scale;
    }

    /**
     * @return height of render window
     */
    public static float getRenderHeight()
    {

        return _windowHeight/_scale;
    }

    public static void updateCanvasStats()
    {
        setDimensions(Game.gameCanvas.getWidth(), Game.gameCanvas.getHeight());
    }

    public static void setDimensions(int width, int height)
    {
        _windowWidth = width;
        _windowHeight = height;

        _scale = _windowWidth/WIDTH;

        /*
        _scale = _windowWidth/(int)WIDTH;
        _scale = _scale < 1 ? 1 : _scale;
        _scale = _scale > 4 ? 4 : _scale;
        // */

        refreshDisplay();
    }

    public static void refreshDisplay()
    {
        LRKState state = StateLibrary.getActiveState();
        if(state != null)
            state.resize();

        Game.window.setLocation(getPositionX(), getPositionY());
    }

    public static Dimension getDimensions()
    {
        return new Dimension(_windowWidth, _windowHeight);
    }

    public static int getPositionX()
    {
        return _positionX;
    }

    public static void setPositionX(int positionX)
    {
        _positionX = positionX;
    }

    public static int getPositionY()
    {
        return _positionY;
    }

    public static void setPositionY(int positionY)
    {
        _positionY = positionY;
    }

    public static boolean getStartMaximized()
    {
        return _startMaximized;
    }

    public static void setStartMaximized(boolean startMaximized)
    {
        _startMaximized = startMaximized;
    }
}
