package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;
import org.lwjgl.LWJGLException;

import java.awt.*;
import java.io.PrintWriter;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 1:36 PM
 */
public class DisplayManager
{
    private static int _positionX = 100;
    private static int _positionY = 100;

    private static int _width = 480;
    private static int _height = 320;

    private static float _scale = 1;

    public static void saveProperties(PrintWriter out)
    {
        out.println("display.scale=" + _scale);

        out.println("display.width=" + _width);
        out.println("display.height=" + _height);

        out.println("display.position.x=" + _positionX);
        out.println("display.position.y=" + _positionY);
    }

    public static void setProperty(String key, String value)
    {
        try
        {
            switch(key)
            {
                case "display.scale": _scale = Integer.parseInt(value);
                case "display.width": _width = Integer.parseInt(value);
                case "display.height": _height = Integer.parseInt(value);
                case "display.position.x": _positionX = Integer.parseInt(value);
                case "display.position.y": _positionY = Integer.parseInt(value);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("Failed to load property: " + key + " = " + value);
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
     * calls both setScale and setFullscreen
     * @param scale
     */
    public static void setScreenProperties(int width, int height, int scale)
    {
        _width = width;
        _height = height;
        _scale = scale;
        refreshDisplay();
    }

    // GETTERS

    public static float getScale()
    {
        return _scale;
    }

    /**
     * @return width of actual window
     */
    public static int getWidth()
    {
        return _width;
    }

    /**
     * @return height of actual window
     */
    public static int getHeight()
    {

        return _height;
    }

    /**
     * @return width of render window
     */
    public static float getRenderWidth()
    {
        return _width/((float)_scale);
    }

    /**
     * @return height of render window
     */
    public static float getRenderHeight()
    {

        return _height/((float)_scale);
    }

    public static void setDimensions(int width, int height)
    {
        _width = width;
        _height = height;

        _scale = _width/450f;
        //_scale = _scale > 4 ? 4 : _scale;
        //_scale = _scale < 1 ? 1 : _scale;

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
        return new Dimension(_width, _height);
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
}
