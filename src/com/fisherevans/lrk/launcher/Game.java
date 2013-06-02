package com.fisherevans.lrk.launcher;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.managers.DisplayManager;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.ScalableGame;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/6/13
 * Time: 8:36 PM
 */
public class Game
{
    /**
     * the actual game object
     */
    public static LRK lrk;

    /**
     * scalable game wrapper
     */
    public static ScalableGame scalable;

    /**
     * the window holding the scalable game
     */
    public static AppGameContainer app;

    public enum OSType { Windows, Mac, Linux, Solaris }

    public Game()
    {
        checkOS();
        startGame();
    }

    /**
     * loads the lwjgl natives based on the current os
     */
    private void checkOS()
    {
        OSType os = getOSType();
        System.out.println("OS Type: " + os);

        switch(os)
        {
            case Windows:
                System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/windows").getAbsolutePath());
                break;
            case Mac:
                System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/macosx").getAbsolutePath());
                break;
            case Linux:
                System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/linux").getAbsolutePath());
                break;
            case Solaris:
                System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/solaris").getAbsolutePath());
                break;
            default:
                System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/linux").getAbsolutePath());
                break;
        }
    }

    /**
     * initiates the game objects
     */
    private void startGame()
    {
        try
        {
            DisplayManager.DetermineAspectRatio();
            DisplayManager.SetBaseResolution();
            lrk = new LRK("Lost Relics of Kazar - A Prequel [" + LRK.VERSION + "]");
            scalable = new ScalableGame(lrk, DisplayManager.getGameWidth(), DisplayManager.getGameHeight(), true);
            app = new AppGameContainer(scalable);

            Options.load();

            app.setDisplayMode(DisplayManager.getDisplayWidth(), DisplayManager.getDisplayHeight(), DisplayManager.getDisplayFullscreen());
            app.setUpdateOnlyWhenVisible(false);
            app.setAlwaysRender(true);
            app.setShowFPS(LRK.DEBUG);
            app.setMouseGrabbed(true);
            app.start();
        }
        catch(Exception e)
        {
            System.out.println("Failed to start the game.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * updates the game window based on the static options
     */
    public static void updateDisplay()
    {
        try
        {
            app.setDisplayMode(DisplayManager.getDisplayWidth(), DisplayManager.getDisplayHeight(), DisplayManager.getDisplayFullscreen());
            scalable.updateDisplay(app);
            LRK.log("Scalable Size: " + DisplayManager.getGameWidth() + "x" + DisplayManager.getGameHeight() + " - App Size: " + DisplayManager.getDisplayWidth() + "x" + DisplayManager.getDisplayHeight());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return the OS enum of the current OS
     */
    public static OSType getOSType()
    {
        String OS = System.getProperty("os.name").toLowerCase();

        if(OS.indexOf("win") >= 0) return OSType.Windows;
        if(OS.indexOf("mac") >= 0) return OSType.Mac;
        if(OS.indexOf("nix") >= 0) return OSType.Linux;
        if(OS.indexOf("sunos") >= 0) return OSType.Solaris;

        System.out.println("Waring! OS type is undetermined, defaulting to Linux!!!");
        return OSType.Linux;
    }

    /**
     * starts everything
     * @param args
     */
    public static void main(String[] args)
    {
        new Game();
    }
}
