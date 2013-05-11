package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.Launcher;
import org.newdawn.slick.ScalableGame;

import java.io.File;
import java.nio.file.*;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private static int _scale = 1;
    private static boolean _fullscreen = false, _firstRun = true;

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
            while(input.hasNextLine())
            {
                String[] setting = input.nextLine().split("=");
                switch(setting[0])
                {
                    case "scale": _scale = Integer.parseInt(setting[1]); break;
                    case "fullscreen": _fullscreen = Boolean.parseBoolean(setting[1]); break;
                    case "firstRun": _firstRun = Boolean.parseBoolean(setting[1]); break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(2);
        }
    }

    public static void updateDependencies()
    {
        Resources.generateFonts();
    }

    public static void setScreenProperties(int scale, boolean fullscreen)
    {
        _scale = scale;
        _fullscreen = fullscreen;
        Launcher.scalable = new ScalableGame(Launcher.lrk, getDisplayWidth(), getDisplayHeight(), true);
    }

    public static int getScale()
    {
        return _scale;
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
        return BASE_SCREEN_WIDTH*_scale;
    }

    public static int getDisplayHeight()
    {
        return BASE_SCREEN_HEIGHT*_scale;
    }

    public static boolean getFullscreen()
    {
        return _fullscreen;
    }

    public static void setScale(int scale)
    {
        boolean changed = (scale != _scale);
        if(changed)
        {
            _scale = scale;
            updateDependencies();
            Launcher.updateDisplay();
        }
    }

    public static void setFullscreen(boolean fullscreen)
    {
        _fullscreen = fullscreen;
    }

    public static boolean isFirstRun()
    {
        return _firstRun;
    }

    public static void setFirstRun(boolean firstRun)
    {
        _firstRun = firstRun;
    }
}
