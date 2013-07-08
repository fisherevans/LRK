package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.AudioManager;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.managers.InputManager;
import com.fisherevans.lrk.states.quit.QuitState;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.sql.Timestamp;

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
    private AudioManager _audioManager;
    private DisplayManager _displayManager;

    private long pauseEndTime = 0;
    private boolean paused = false;

    /**
     * Create a new basic game
     *
     * @param title The title for the game
     */
    public LRK(String title)
    {
        super(title);

        _displayManager = new DisplayManager();
        _inputManager = new InputManager();
        _audioManager = new AudioManager();

        Options.load();
    }

    @Override
    public void init(GameContainer container) throws SlickException
    {
        StateLibrary.resetStates();

        StateLibrary.setActiveState("splash");

        InputManager.connectInput(Game.getContainer().getInput());
    }

    @Override
    public void update(GameContainer container, int deltaMS) throws SlickException
    {
        if(paused && pauseEndTime < System.currentTimeMillis())
            paused = false;
        if(paused)
            return;

        float delta = deltaMS/1000f;

        InputManager.getXboxController().runKeyQueue();
        InputManager.getXboxController().moveMouse(delta);

        StateLibrary.getActiveState().update(delta);
    }

    @Override
    public void render(GameContainer container, Graphics gfx) throws SlickException
    {
        if(paused)
            return;

        gfx.scale(DisplayManager.getScale(), DisplayManager.getScale());
        StateLibrary.getActiveState().render(gfx);
        gfx.resetTransform();
    }

    public void pause(long time)
    {
        paused = true;
        pauseEndTime = System.currentTimeMillis() + time;
    }

    /**
     * Gracefuly closes the program, saves settings and progress, displays a quitting dialogue
     */
    public void exit()
    {
        try
        {
            StateLibrary.setActiveState(new QuitState());
        }
        catch (SlickException e)
        {
            e.printStackTrace();
            LRK.log("Failed to exit the game properly!");
            System.exit(1);
        }
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

    public InputManager getInputManager()
    {
        return _inputManager;
    }

    public DisplayManager getDisplayManager()
    {
        return _displayManager;
    }

    public AudioManager getAudioManager()
    {
        return _audioManager;
    }
}
