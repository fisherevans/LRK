package com.fisherevans.lrk.launcher;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.OSCheck;
import com.fisherevans.lrk.Options;
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
    public static LRK lrk;
    public static ScalableGame scalable;
    public static AppGameContainer app;

    public Game()
    {
        checkOS();
        Options.load();
        startGame();
    }

    private void checkOS()
    {
        OSCheck.OSType os = OSCheck.getOSType();
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

    private void startGame()
    {
        try
        {
            lrk = new LRK("Lost Relics of Kazar - A Prequel [ALPHA]");
            scalable = new ScalableGame(lrk, Options.getGameWidth(), Options.getGameHeight(), true);
            app = new AppGameContainer(scalable);
            app.setDisplayMode(Options.getDisplayWidth(), Options.getDisplayHeight(), Options.getDisplayFullscreen());
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

    public static void updateDisplay()
    {
        try
        {
            app.setDisplayMode(Options.getDisplayWidth(), Options.getDisplayHeight(), Options.getDisplayFullscreen());
            scalable.init(app);
            LRK.log("Scalable Size: " + Options.getGameWidth() + "x" + Options.getGameHeight() + " - App Size: " + Options.getDisplayWidth() + "x" + Options.getDisplayHeight());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Game();
    }
}
