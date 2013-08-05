package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.UIComponent;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.SlickException;

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
    public static float MIN_BACK_WIDTH = 480;
    public static float MIN_BACK_HEIGHT = 320;

    public static float MIN_FORE_WIDTH = 730;
    public static float MIN_FORE_HEIGHT = 450;

    private static float _windowWidth = 730;
    private static float _windowHeight = 480;

    private static float _foregroundScale = 1;
    private static float _backgroundScale = 1;

    private static boolean _fullscreen = false;

    public void saveProperties(PrintWriter out)
    {
    }

    public static void setProperty(String property, String value)
    {
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

        float widthBackRatio = _windowWidth/ MIN_BACK_WIDTH;
        float heightBackRatio = _windowHeight/ MIN_BACK_HEIGHT;
        _backgroundScale = widthBackRatio > heightBackRatio ? heightBackRatio : widthBackRatio;
        _backgroundScale = _backgroundScale < 1 ? 1 : ((int) _backgroundScale);

        float widthForeRatio = _windowWidth/ MIN_FORE_WIDTH;
        float heightForeRatio = _windowHeight/ MIN_FORE_HEIGHT;
        _foregroundScale = (int)(widthForeRatio > heightForeRatio ? heightForeRatio : widthForeRatio);
        _foregroundScale = _foregroundScale < 1 ? 1 :  _foregroundScale;

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
        {
            state.resize();
            for(UIComponent ui:state.getUIComponents())
                ui.resize();
        }
    }

    public static void swapFullScreen()
    {
        try
        {
            if(_fullscreen)
            {
                LRK.log("Going back to windowed");
                GraphicsDevice dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                Game.window.setVisible(false);
                Game.window.setUndecorated(false);
                Game.window.setVisible(true);
                //dev.setFullScreenWindow(null);
                _fullscreen = false;
            }
            else
            {
                LRK.log("Going fullscreened");
                GraphicsDevice dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                Game.window.setVisible(false);
                Game.window.setUndecorated(true);
                Game.window.setVisible(true);
                //dev.setFullScreenWindow(Game.window);
                _fullscreen = true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            LRK.log("Failed to swap fullscreen!");
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
    public static float getWindowWidth()
    {
        return _windowWidth;
    }

    /**
     * @return height of actual window
     */
    public static float getWindowHeight()
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

    public static Dimension getDimensions()
    {
        return new Dimension((int)_windowWidth, (int)_windowHeight);
    }
}
