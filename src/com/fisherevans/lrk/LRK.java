package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.*;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.options.OptionsState;
import com.fisherevans.lrk.states.splash.SplashState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import sun.misc.Launcher;

import java.sql.Timestamp;
import java.util.Date;

/**
 * User: Fisher
 * Date: 5/5/13
 * Time: 7:08 PM
 */
public class LRK extends BasicGame
{
    public static boolean DEBUG = true;
    public static final String VERSION = "0.2 Alpha";

    private InputManager _inputManager;

    /**
     * Create a new basic game
     *
     * @param title The title for the game
     */
    public LRK(String title)
    {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException
    {
        _inputManager = new InputManager(getInput(), this);
        StateLibrary.resetStates();

        StateLibrary.setActiveState("splash");
    }

    @Override
    public void update(GameContainer container, int deltaMS) throws SlickException
    {
        float delta = deltaMS/1000f;
        StateLibrary.getActiveState().update(delta);
    }

    @Override
    public void render(GameContainer container, Graphics gfx) throws SlickException
    {
        StateLibrary.getActiveState().render(gfx);
    }

    public void exit()
    {
        LRK.log("Quitting, goodbye :)");
        Options.save();
        System.exit(0);
    }

    /**
     * @param text text to log
     */
    public static void log(String text)
    {
        if(DEBUG)
        {
            String time = new Timestamp(System.currentTimeMillis()).toString();
            System.out.println("[LRK " + VERSION + " | " + time + "] " + text);
        }
    }

    /**
     * @return keyboard and mouse input of this game
     */
    public Input getInput()
    {
        return Game.app.getInput();
    }
}
