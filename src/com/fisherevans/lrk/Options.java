package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.Game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/5/13
 * Time: 9:57 PM
 */
public class Options
{
    public static final String
            DEFAULT_SETTINGS_LOCATION = "res/default/lrk.settings",
            SETTINGS_LOCATION = "lrk.settings";

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

            Game.lrk.getDisplayManager().saveProperties(out);
            Game.lrk.getInputManager().saveProperties(out);
            Game.lrk.getAudioManager().saveProperties(out);

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
            File settings = new File(filename); // create the file object
            if(!settings.isFile()) // if it's not a file
            {
                LRK.log("No settings file, creating one.");
                // copy the default settings file to the settings location
                Files.copy(Paths.get(DEFAULT_SETTINGS_LOCATION), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
                settings = new File(filename);
                if(!settings.isFile()) // if there was an error copying the file, stop loading and tell the log
                {
                    LRK.log("Failed to load settings.");
                    throw new Exception("log file error");
                }
            }
            System.out.println("Opened Settings file.");

            Scanner input = new Scanner(settings);
            String line, value;
            String[] setting, property;
            while(input.hasNextLine()) // read each line
            {
                // split it up into a tree array of the property key, and the string value
                line = input.nextLine();
                setting = line.split("=");
                property = setting[0].split("\\.");
                value = setting[1];

                switch(property[0]) // set them appropriately
                {
                    case "input":
                        Game.lrk.getInputManager().setProperty(property[1], value);
                        break;
                    case "audio":
                        Game.lrk.getAudioManager().setProperty(property[1], value);
                        break;
                    case "display":
                        Game.lrk.getDisplayManager().setProperty(property[1], value);
                        break;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("Failed to load settings from: " + filename);
        }
    }
}
