package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.Game;
import org.newdawn.slick.Input;
import org.newdawn.slick.ScalableGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    /**
     * saves the current settings to a the default settings file location
     */
    public static void save()
    {
        save(SETTINGS_LOCATION);
    }

    /**
     * saves the current settings to a settings file location
     * @param filename location of the settings file
     */
    public static void save(String filename)
    {
        try
        {
            FileWriter outFile = new FileWriter(filename);
            PrintWriter out = new PrintWriter(outFile);

            out.println("display.scale=" + Options.getDisplayScale());
            out.println("display.fullscreen=" + Options.getDisplayFullscreen());

            out.println("audio.master=" + Options.getAudioMaster());
            out.println("audio.music=" + Options.getAudioMusic());
            out.println("audio.sfx=" + Options.getAudioSFX());

            out.println("control.up=" + Options.getControlUp());
            out.println("control.down=" + Options.getControlDown());
            out.println("control.left=" + Options.getControlLeft());
            out.println("control.right=" + Options.getControlRight());
            out.println("control.select=" + Options.getControlSelect());
            out.println("control.back=" + Options.getControlBack());

            out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

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

                        case "control.up": _controlUp = Integer.parseInt(setting[1]); break;
                        case "control.down": _controlDown = Integer.parseInt(setting[1]); break;
                        case "control.left": _controlLeft = Integer.parseInt(setting[1]); break;
                        case "control.right": _controlRight = Integer.parseInt(setting[1]); break;
                        case "control.select": _controlSelect = Integer.parseInt(setting[1]); break;
                        case "control.back": _controlBack = Integer.parseInt(setting[1]); break;
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
        return (_controlUp == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an DOWN key
     */
    public static boolean isDown(int key)
    {
        return (_controlDown == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an LEFT key
     */
    public static boolean isLeft(int key)
    {
        return (_controlLeft == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an RIGHT key
     */
    public static boolean isRight(int key)
    {
        return (_controlRight == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an SELECT key
     */
    public static boolean isSelect(int key)
    {
        return (_controlSelect == key);
    }

    /**
     * @param key key id to check
     * @return if the key is an BACK key
     */
    public static boolean isBack(int key)
    {
        return (_controlBack == key);
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

    public static void setControlUp(int controlUp)
    {
        _controlUp = controlUp;
    }

    public static void setControlDown(int controlDown)
    {
        _controlDown = controlDown;
    }

    public static void setControlLeft(int controlLeft)
    {
        _controlLeft = controlLeft;
    }

    public static void setControlRight(int controlRight)
    {
        _controlRight = controlRight;
    }

    public static void setControlSelect(int controlSelect)
    {
        _controlSelect = controlSelect;
    }

    public static void setControlBack(int controlBack)
    {
        _controlBack = controlBack;
    }

    public static void setAudioMaster(float audioMaster)
    {
        _audioMaster = audioMaster;
    }

    public static void setAudioMusic(float audioMusic)
    {
        _audioMusic = audioMusic;
    }

    public static void setAudioSFX(float audioSFX)
    {
        _audioSFX = audioSFX;
    }
}
