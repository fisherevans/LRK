package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.AudioManager;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.managers.InputManager;
import com.fisherevans.lrk.managers.MusicManager;
import com.fisherevans.lrk.notifications.Notifications;
import com.fisherevans.lrk.rpg.Player;
import com.fisherevans.lrk.rpg.RPGEntityGenerator;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.RenderComponent;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.quit.QuitState;
import org.newdawn.slick.*;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * User: Fisher
 * Date: 5/5/13
 * Time: 7:08 PM
 */
public class LRK extends BasicGame
{
    public static boolean DEBUG = false;
    public static final String VERSION = "0.2 Alpha";

    // Game Component Managers
    private InputManager _inputManager;
    private AudioManager _audioManager;
    private DisplayManager _displayManager;
    private MusicManager _musicManager;
    
    // Notification system
    private Notifications _notifications;

    // Current player
    private Player _player;

    // Pause variables
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

        _musicManager = new MusicManager();
        _musicManager.init(_musicManager);

        _notifications = new Notifications();

        Options.load();
    }

    @Override
    public void init(GameContainer container) throws SlickException
    {
        Resources.loadResources();

        StateLibrary.resetStates();

        StateLibrary.setActiveState("splash");

        InputManager.connectInput(Game.getContainer().getInput());


        _notifications.init();

        _player = RPGEntityGenerator.getAnarok();
    }

    @Override
    public void update(GameContainer container, int deltaMS) throws SlickException
    {
        if(paused && pauseEndTime < System.currentTimeMillis())
            paused = false;
        if(paused)
            return;

        float delta = deltaMS/1000f;

        if(InputManager.getXboxController() != null)
        {
            InputManager.getXboxController().queryAxes(delta);
            InputManager.getXboxController().runKeyQueue();
            InputManager.getXboxController().moveMouse(delta);
        }

        StateLibrary.getActiveState().update(delta);
        for(UIComponent ui:StateLibrary.getActiveState().getUIComponents())
            ui.update(delta);
        _notifications.update(delta);
    }

    @Override
    public void render(GameContainer container, Graphics gfx) throws SlickException
    {
        if(paused)
            return;

        gfx.clear();

        gfx.scale(DisplayManager.getBackgroundScale(), DisplayManager.getBackgroundScale());
        StateLibrary.getActiveState().render(gfx);
        gfx.resetTransform();

        gfx.scale(DisplayManager.getForegroundScale(), DisplayManager.getForegroundScale());
        StateLibrary.getActiveState().renderUI(gfx);
        _notifications.render(gfx);
        gfx.resetTransform();

        if(StateLibrary.getActiveState().getGrabMouse())
            GFX.drawImageCentered(InputManager.getMouseX(), InputManager.getMouseY(), StateLibrary.getActiveState().getCursor());

        if(DEBUG)
            GFX.drawTextAbsolute(10, 10, Resources.getFont(2), Color.white, String.format("FPS: %3d", Game.gameCanvas.getContainer().getFPS()));
    }

    /**
     * Pauses the game from rendering and updating.
     * @param time the time in miliseconds to pause the game for
     */
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
            String time = String.format("%-23s",new Timestamp(System.currentTimeMillis()).toString());
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

    public Notifications getNotifications()
    {
        return _notifications;
    }

    public Player getPlayer()
    {
        return _player;
    }
}
