package com.fisherevans.lrk.launcher;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.managers.DisplayManager;
import org.newdawn.slick.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/6/13
 * Time: 8:36 PM
 */
public class Game
{
    public static Window window;
    public static CanvasGameContainer gameCanvas;
    public static LRK lrk;

    public Game()
    {
        loadNatives();
        startGame();
    }

    /**
     * loads the lwjgl natives based on the current os
     */
    private void loadNatives()
    {
        String os = getOS();
        System.out.println("OS Type: " + os);
        System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl/native/" + os).getAbsolutePath());
    }

    /**
     * initiates the game objects
     */
    private void startGame()
    {
        try
        {
            lrk = new LRK("Lost Relics of Kazar - A Prequel [" + LRK.VERSION + "]");
            gameCanvas = new CanvasGameContainer(lrk);
            gameCanvas.setBackground(Color.black);
            gameCanvas.setPreferredSize(DisplayManager.getDimensions());

            window = new Window();
            window.setBackground(Color.black);
            window.add(gameCanvas);
            window.pack();
            window.setVisible(true);

            getContainer().setAlwaysRender(true);
            getContainer().setUpdateOnlyWhenVisible(false);

            gameCanvas.start();

            window.setLocation(DisplayManager.getPositionX(), DisplayManager.getPositionY());

            window.addComponentListener(window);
            window.addWindowListener(window);
            gameCanvas.addComponentListener(window);
        }
        catch(Exception e)
        {
            System.out.println("Failed to start the game.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void centerWindow()
    {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (dim.width-window.getSize().width)/2;
        int y = (dim.height-window.getSize().height)/2;

        window.setLocation(x, y);
    }

    public static GameContainer getContainer()
    {
        return gameCanvas.getContainer();
    }

    /**
     * @return the OS enum of the current OS
     */
    public static String getOS()
    {
        String OS = System.getProperty("os.name").toLowerCase();

        if(OS.indexOf("win") >= 0) return "windows";
        if(OS.indexOf("mac") >= 0) return "mac";
        if(OS.indexOf("nix") >= 0) return "linux";
        if(OS.indexOf("sunos") >= 0) return "solaris";

        System.out.println("Waring! OS type is undetermined, defaulting to Linux!!!");
        return "linux";
    }

    public static void finalClose()
    {
        try
        {
            Options.save();
            lrk.exit();
            gameCanvas.setEnabled(false);
            gameCanvas.dispose();
            window.dispose();
            System.exit(0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
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
