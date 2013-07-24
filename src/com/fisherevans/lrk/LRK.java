package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.AudioManager;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.managers.InputManager;
import com.fisherevans.lrk.notifications.Notifications;
import com.fisherevans.lrk.states.quit.QuitState;
import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.event.JXInputEventManager;
import org.newdawn.slick.*;

import java.sql.Timestamp;

/**
 * User: Fisher
 * Date: 5/5/13
 * Time: 7:08 PM
 */
public class LRK extends BasicGame implements MouseListener
{
    public static boolean DEBUG = true;
    public static final String VERSION = "0.2 Alpha";

    private InputManager _inputManager;
    private AudioManager _audioManager;
    private DisplayManager _displayManager;

    private Notifications _notifications;

    private long pauseEndTime = 0;
    private boolean paused = false;

    private boolean mousePressed = false;

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

        _notifications = new Notifications();

        Options.load();
    }

    @Override
    public void init(GameContainer container) throws SlickException
    {
        StateLibrary.resetStates();

        StateLibrary.setActiveState("splash");

        InputManager.connectInput(Game.getContainer().getInput());
        InputManager.getInput().addMouseListener(this);

        _notifications.init();
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

        _notifications.update(delta);
    }

    @Override
    public void render(GameContainer container, Graphics gfx) throws SlickException
    {
        if(paused)
            return;

        gfx.scale(DisplayManager.getScale(), DisplayManager.getScale());
        StateLibrary.getActiveState().render(gfx);
        _notifications.render(gfx);
        StateLibrary.getActiveState().drawCursor();
        gfx.resetTransform();
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

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
        float x1, y1, x2, y2;
        x1 = oldx/DisplayManager.getScale();
        y1 = oldy/DisplayManager.getScale();
        x2 = newx/DisplayManager.getScale();
        y2 = newy/DisplayManager.getScale();

        InputManager.setMouseX(x1);
        InputManager.setMouseY(y1);

        if(mousePressed)
            StateLibrary.getActiveState().mouseDragged(x1, y1, x2, y2);
        else
            StateLibrary.getActiveState().mouseMoved(x1, y1, x2, y2);
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
        mousePressed = true;
        StateLibrary.getActiveState().mousePressed(x/DisplayManager.getScale(), y/DisplayManager.getScale());
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {
        mousePressed = false;
        StateLibrary.getActiveState().mouseReleased(x/DisplayManager.getScale(), y/DisplayManager.getScale());
    }

    @Override
    public void mouseWheelMoved(int change)
    {
        StateLibrary.getActiveState().mouseWheelMoved(change);
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

    public Notifications getNotifications()
    {
        return _notifications;
    }
}
