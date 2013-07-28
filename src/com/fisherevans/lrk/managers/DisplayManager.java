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

    public static float MIN_WIDTH = 560f;
    public static float MIN_HEIGHT = 315f;

    private static int _positionX = 100;
    private static int _positionY = 100;

    private static int _windowWidth = 800;
    private static int _windowHeight = 600;

    private static float _foregroundScale = 1;
    private static float _backgroundScale = 1;

    private static boolean _scaleAuto = true;
    private static final boolean _floatScale = true;

    public static void saveProperties(PrintWriter out)
    {
        out.println("display.width=" + _windowWidth);
        out.println("display.height=" + _windowHeight);

        out.println("display.position.x=" + _positionX);
        out.println("display.position.y=" + _positionY);

        out.println("display.maximized=" + (Game.window.getExtendedState() == JFrame.MAXIMIZED_BOTH));

        out.println("display.scale.foreground=" + _foregroundScale);
        out.println("display.scale.background=" + _backgroundScale);
    }

    public static void setProperty(String key, String value)
    {
        try
        {
            switch(key)
            {
                case "display.width": _windowWidth = Integer.parseInt(value); break;
                case "display.height": _windowHeight = Integer.parseInt(value); break;
                case "display.position.x": _positionX = Integer.parseInt(value); break;
                case "display.position.y": _positionY = Integer.parseInt(value); break;
                case "display.maximized": _startMaximized = Boolean.parseBoolean(value); break;
                case "display.scale.foreground": _foregroundScale = Float.parseFloat(value); break;
                case "display.scale.background": _backgroundScale = Float.parseFloat(value); break;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("Failed to load property: " + key + " = " + value);
        }
    }

    // GETTERS

    public static float getBackgroundScale()
    {
        return _backgroundScale;
    }

    public static float getForegroundScale()
    {
        return _foregroundScale;
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
    public static float getBackgroundWidth()
    {
        return _windowWidth/ _backgroundScale;
    }

    /**
     * @return height of render window
     */
    public static float getBackgroundHeight()
    {

        return _windowHeight/ _backgroundScale;
    }

    /**
     * @return width of render window
     */
    public static float getForegroundWidth()
    {
        return _windowWidth/ _foregroundScale;
    }

    /**
     * @return height of render window
     */
    public static float getForegroundHeight()
    {

        return _windowHeight/ _foregroundScale;
    }

    /**
     * update the game canvas based on the window size
     */
    public static void updateCanvasStats()
    {
        setDimensions(Game.gameCanvas.getWidth(), Game.gameCanvas.getHeight());
    }

    /**
     * set the dimensions of the window
     * @param width the width
     * @param height the height
     */
    public static void setDimensions(int width, int height)
    {
        _windowWidth = width;
        _windowHeight = height;

        float widthRatio = _windowWidth/MIN_WIDTH;
        float heightRatio = _windowHeight/MIN_HEIGHT;

        if(_scaleAuto)
        {
            _backgroundScale = widthRatio > heightRatio ? heightRatio : widthRatio;
            if(!_floatScale)
                _backgroundScale = _backgroundScale < 1 ? 1 : ((int) _backgroundScale);
        }

        /*
        _foregroundScale = _windowWidth/(int)WIDTH;
        _foregroundScale = _foregroundScale < 1 ? 1 : _foregroundScale;
        _foregroundScale = _foregroundScale > 4 ? 4 : _foregroundScale;
        // */

        Game.lrk.pause(100);
        refreshDisplay();
    }

    /**
     * resize the current state
     */
    public static void refreshDisplay()
    {
        LRKState state = StateLibrary.getActiveState();
        if(state != null)
            state.resize();
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
