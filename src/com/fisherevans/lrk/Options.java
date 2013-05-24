package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.Game;
import org.newdawn.slick.Input;
import org.newdawn.slick.ScalableGame;

import java.io.File;
import java.nio.file.*;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/5/13
 * Time: 9:57 PM
 */
public class Options
{
    public static final int
            BASE_SCREEN_WIDTH = 480,
            BASE_SCREEN_HEIGHT = 270;

    public static final String
            DEFAULT_SETTINGS_LOCATION = "res/default/lrk.settings",
            SETTINGS_LOCATION = "lrk.settings";

    // SETTINGS
    private static int _displayScale = 1;
    private static boolean _displayFullscreen = false;

    private static int
            _controlUp1 = Input.KEY_W,
            _controlDown1 = Input.KEY_S,
            _controlLeft1 = Input.KEY_A,
            _controlRight1 = Input.KEY_D,
            _controlSelect1 = Input.KEY_SPACE,
            _controlBack1 = Input.KEY_BACK,
            _controlUp2 = Input.KEY_UP,
            _controlDown2 = Input.KEY_DOWN,
            _controlLeft2 = Input.KEY_LEFT,
            _controlRight2 = Input.KEY_RIGHT,
            _controlSelect2 = Input.KEY_ENTER,
            _controlBack2 = Input.KEY_ESCAPE;

    private static float
        _audioMaster = 1f,
        _audioMusic = 0.8f,
        _audioSFX = 1f;

    /**
     * loads predefined options from the default settings file location
     */
    public static void load()
    {
        load(SETTINGS_LOCATION);
    }


    /**
     * loads predefined options from the default settings file location
     * @param filename location of the settings file
     */
    public static void load(String filename)
    {
        try
        {
            File settings = new File(filename);
            if(!settings.isFile())
            {
                LRK.log("No settings file, creating one.");
                Files.copy(Paths.get(DEFAULT_SETTINGS_LOCATION), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
                settings = new File(filename);
                if(!settings.isFile())
                {
                    LRK.log("Failed to load settings.");
                    System.exit(9);
                }
            }
            System.out.println("Opened Settings file.");

            Scanner input = new Scanner(settings);
            String line;
            String[] setting;
            while(input.hasNextLine())
            {
                line = input.nextLine();
                setting = line.split("=");
                try
                {
                    switch(setting[0])
                    {
                        case "display.scale": _displayScale = Integer.parseInt(setting[1]); break;
                        case "display.fullscreen": _displayFullscreen = Boolean.parseBoolean(setting[1]); break;

                        case "audio.master": _audioMaster = Float.parseFloat(setting[1]); break;
                        case "audio.music": _audioMusic = Float.parseFloat(setting[1]); break;
                        case "audio.sfx": _audioSFX = Float.parseFloat(setting[1]); break;
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    LRK.log("Failed to load a configuration line: " + line);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(2);
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

    /**
     * @param key key id to check
     * @return if the key is an UP key
     */
    public static boolean isUp(int key)
    {
        return (_controlUp1 == key || _controlUp2 == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an DOWN key
     */
    public static boolean isDown(int key)
    {
        return (_controlDown1 == key || _controlDown2 == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an LEFT key
     */
    public static boolean isLeft(int key)
    {
        return (_controlLeft1 == key || _controlLeft2 == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an RIGHT key
     */
    public static boolean isRight(int key)
    {
        return (_controlRight1 == key || _controlRight2 == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an SELECT key
     */
    public static boolean isSelect(int key)
    {
        return (_controlSelect1 == key || _controlSelect2 == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an BACK key
     */
    public static boolean isBack(int key)
    {
        return (_controlBack1 == key || _controlBack2 == key);
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

    public static int getControlUp1()
    {
        return _controlUp1;
    }

    public static int getControlDown1()
    {
        return _controlDown1;
    }

    public static int getControlLeft1()
    {
        return _controlLeft1;
    }

    public static int getControlRight1()
    {
        return _controlRight1;
    }

    public static int getControlSelect1()
    {
        return _controlSelect1;
    }

    public static int getControlBack1()
    {
        return _controlBack1;
    }

    public static int getControlUp2()
    {
        return _controlUp2;
    }

    public static int getControlDown2()
    {
        return _controlDown2;
    }

    public static int getControlLeft2()
    {
        return _controlLeft2;
    }

    public static int getControlRight2()
    {
        return _controlRight2;
    }

    public static int getControlSelect2()
    {
        return _controlSelect2;
    }

    public static int getControlBack2()
    {
        return _controlBack2;
    }

    public static float getAudioMaster()
    {
        return _audioMaster;
    }

    public static float getAudioMusic()
    {
        return _audioMusic;
    }

    public static float getAudioSFX()
    {
        return _audioSFX;
    }
}
