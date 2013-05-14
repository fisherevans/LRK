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
            _controlUp = Input.KEY_W,
            _controlDown = Input.KEY_S,
            _controlLeft = Input.KEY_A,
            _controlRight = Input.KEY_D,
            _controlSelect = Input.KEY_SPACE,
            _controlBack = Input.KEY_BACK;

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

                        case "controls.up": _controlUp = Integer.parseInt(setting[1]); break;
                        case "controls.down": _controlDown = Integer.parseInt(setting[1]); break;
                        case "controls.left": _controlLeft = Integer.parseInt(setting[1]); break;
                        case "controls.right": _controlRight = Integer.parseInt(setting[1]); break;
                        case "controls.select": _controlSelect = Integer.parseInt(setting[1]); break;
                        case "controls.back": _controlBack = Integer.parseInt(setting[1]); break;

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

    public static boolean isControlSet(int key)
    {
        boolean conflict = false;
        conflict = key == _controlUp ? true : conflict;
        conflict = key == _controlDown ? true : conflict;
        conflict = key == _controlLeft ? true : conflict;
        conflict = key == _controlRight ? true : conflict;
        conflict = key == _controlSelect ? true : conflict;
        conflict = key == _controlBack ? true : conflict;
        return conflict;
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

    public static boolean setControlUp(int controlUp)
    {
        int old = _controlUp;
        _controlUp = -1;
        boolean conflict = isControlSet(controlUp);
        if(conflict)
        {
            _controlUp = old;
            return false;
        }
        _controlUp = controlUp;
        return true;
    }

    public static boolean setControlDown(int controlDown)
    {
        int old = _controlDown;
        _controlDown = -1;
        boolean conflict = isControlSet(controlDown);
        if(conflict)
        {
            _controlDown = old;
            return false;
        }
        _controlDown = controlDown;
        return true;
    }

    public static boolean setControlLeft(int controlLeft)
    {
        int old = _controlLeft;
        _controlLeft = -1;
        boolean conflict = isControlSet(controlLeft);
        if(conflict)
        {
            _controlLeft = old;
            return false;
        }
        _controlLeft = controlLeft;
        return true;
    }

    public static boolean setControlRight(int controlRight)
    {
        int old = _controlRight;
        _controlRight = -1;
        boolean conflict = isControlSet(controlRight);
        if(conflict)
        {
            _controlRight = old;
            return false;
        }
        _controlRight = controlRight;
        return true;
    }

    public static boolean setControlSelect(int controlSelect)
    {
        int old = _controlSelect;
        _controlSelect = -1;
        boolean conflict = isControlSet(controlSelect);
        if(conflict)
        {
            _controlSelect = old;
            return false;
        }
        _controlSelect = controlSelect;
        return true;
    }

    public static boolean setControlBack(int controlBack)
    {
        int old = _controlBack;
        _controlBack = -1;
        boolean conflict = isControlSet(controlBack);
        if(conflict)
        {
            _controlBack = old;
            return false;
        }
        _controlBack = controlBack;
        return true;
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
        return _controlUp == key;
    }

    public static boolean isDown(int key)
    {
        return _controlDown == key;
    }

    public static boolean isLeft(int key)
    {
        return _controlLeft == key;
    }

    public static boolean isRight(int key)
    {
        return _controlRight == key;
    }

    public static boolean isSelect(int key)
    {
        return _controlSelect == key;
    }

    public static boolean isBack(int key)
    {
        return _controlBack == key;
    }

    public static int getControlUp()
    {
        return _controlUp;
    }

    public static int getControlDown()
    {
        return _controlDown;
    }

    public static int getControlLeft()
    {
        return _controlLeft;
    }

    public static int getControlRight()
    {
        return _controlRight;
    }

    public static int getControlSelect()
    {
        return _controlSelect;
    }

    public static int getControlBack()
    {
        return _controlBack;
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
