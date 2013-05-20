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

    public static void load()
    {
        try
        {
            File settings = new File(SETTINGS_LOCATION);
            if(!settings.isFile())
            {
                LRK.log("No settings file, creating one.");
                Files.copy(Paths.get(DEFAULT_SETTINGS_LOCATION), Paths.get(SETTINGS_LOCATION), StandardCopyOption.REPLACE_EXISTING);
                settings = new File(SETTINGS_LOCATION);
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

    public static void setScale(int scale)
    {
        boolean changed = (scale != _displayScale);
        if(changed)
        {
            _displayScale = scale;
            Game.updateDisplay();
        }
    }

    public static void setFullscreen(boolean fullscreen)
    {
        _displayFullscreen = fullscreen;
    }

    public static void setScreenProperties(int scale, boolean fullscreen)
    {
        _displayScale = scale;
        _displayFullscreen = fullscreen;
        Game.scalable = new ScalableGame(Game.lrk, getDisplayWidth(), getDisplayHeight(), true);
    }

    // GETTERS

    public static int getDisplayScale()
    {
        return _displayScale;
    }

    public static int getGameWidth()
    {
        return BASE_SCREEN_WIDTH;
    }

    public static int getGameHeight()
    {
        return BASE_SCREEN_HEIGHT;
    }

    public static int getDisplayWidth()
    {
        return BASE_SCREEN_WIDTH*_displayScale;
    }

    public static int getDisplayHeight()
    {
        return BASE_SCREEN_HEIGHT*_displayScale;
    }

    public static boolean getDisplayFullscreen()
    {
        return _displayFullscreen;
    }

    public static boolean isUp(int key)
    {
        return (_controlUp1 == key || _controlUp2 == key);
    }

    public static boolean isDown(int key)
    {
        return (_controlDown1 == key || _controlDown2 == key);
    }

    public static boolean isLeft(int key)
    {
        return (_controlLeft1 == key || _controlLeft2 == key);
    }

    public static boolean isRight(int key)
    {
        return (_controlRight1 == key || _controlRight2 == key);
    }

    public static boolean isSelect(int key)
    {
        return (_controlSelect1 == key || _controlSelect2 == key);
    }

    public static boolean isBack(int key)
    {
        return (_controlBack1 == key || _controlBack2 == key);
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
