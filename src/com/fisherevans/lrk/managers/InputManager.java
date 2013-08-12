package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.RenderComponent;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.tools.LRKMath;
import de.hardcode.jxinput.JXInputDevice;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.*;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/26/13
 * Time: 8:17 PM
 */
public class InputManager implements KeyListener, MouseListener
{
    public enum ControlKey { Up, Down, Left, Right, Select, Back, Menu, ForceQuit, Fullscreen, Mouse1, Mouse2 }

    private static Input _input;
    private static Map<ControlKey, Integer> _keyboardMap;
    private static InputManager _itself;

    public static boolean jxNativesLoaded = false;
    protected static XBoxController xboxController = null;
    private static boolean _queryControllerMovement = true;
    private static XBoxControllerDiscoverer _contollerDiscoverer;
    private static Thread _contollerDiscovererThread;

    private static float _mouseX, _mouseY;

    // Mouse moved/dragged variable
    private static boolean leftMousePressed = false;
    private static boolean rightMousePressed = false;

    public InputManager()
    {
        loadDefaultControls();
        _itself = this;

        _mouseX = DisplayManager.getWindowWidth()/2f;
        _mouseY = DisplayManager.getWindowHeight()/2f;

        if(InputManager.jxNativesLoaded)
        {
            _contollerDiscoverer = new XBoxControllerDiscoverer();
            _contollerDiscovererThread = new Thread(_contollerDiscoverer);
            _contollerDiscovererThread.start();
        }
    }

    /**
     * Tells this object what input object to pull triggers from
     * @param input the input object to use
     */
    public static void connectInput(Input input)
    {
        _input = input;
        _input.addKeyListener(_itself);
        _input.addMouseListener(_itself);
    }

    /**
     * Loads the default controls (for first run or control resets)
     */
    public static void loadDefaultControls()
    {
        _keyboardMap = new HashMap<>();

        _keyboardMap.put(ControlKey.Up, Input.KEY_W);
        _keyboardMap.put(ControlKey.Down, Input.KEY_S);
        _keyboardMap.put(ControlKey.Left, Input.KEY_A);
        _keyboardMap.put(ControlKey.Right, Input.KEY_D);
        _keyboardMap.put(ControlKey.Select, Input.KEY_SPACE);
        _keyboardMap.put(ControlKey.Back, Input.KEY_LCONTROL);
        _keyboardMap.put(ControlKey.Menu, Input.KEY_ESCAPE);
        _keyboardMap.put(ControlKey.ForceQuit, Input.KEY_F10);
        _keyboardMap.put(ControlKey.Fullscreen, Input.KEY_F11);
    }

    /**
     * gets the enum of a given key (hard codded mapping)
     * @param key the string of the key to look for
     * @return the corresponding enum. null if no string key is defined
     */
    public static ControlKey stringToControlKey(String key)
    {
        switch(key)
        {
            case "input.up": return ControlKey.Up;
            case "input.down": return ControlKey.Down;
            case "input.left": return ControlKey.Left;
            case "input.right": return ControlKey.Right;
            case "input.select": return ControlKey.Select;
            case "input.back": return ControlKey.Back;
            case "input.menu": return ControlKey.Menu;
            case "input.forcequit": return ControlKey.ForceQuit;
        }

        return null;
    }

    /**
     * Updates a setting based on a string key and value
     * @param key the corresponding key to determine what value to set
     * @param value the string value to set it to.
     */
    public static void setProperty(String key, String value)
    {
        ControlKey inputKey = stringToControlKey(key);

        if(inputKey == null)
            return;

        try
        {
            _keyboardMap.put(inputKey, Integer.parseInt(value));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("Failed to set input property: " + key + " to: " + key);
        }
    }

    public static void saveProperties(PrintWriter out)
    {
        out.println("input.up=" + getControlKey(ControlKey.Up));
        out.println("input.down=" + getControlKey(ControlKey.Down));
        out.println("input.left=" + getControlKey(ControlKey.Left));
        out.println("input.right=" + getControlKey(ControlKey.Right));
        out.println("input.select=" + getControlKey(ControlKey.Select));
        out.println("input.back=" + getControlKey(ControlKey.Back));
        out.println("input.menu=" + getControlKey(ControlKey.Menu));
        out.println("input.forcequit=" + getControlKey(ControlKey.ForceQuit));
    }

    @Override
    public void keyPressed(int key, char c)
    {
        if(key == getControlKey(ControlKey.ForceQuit))
            Game.lrk.exit();

        for(ControlKey keyType:ControlKey.values())
        {
            if(key == getControlKey(keyType))
            {
                sendKeyPress(keyType);
                break;
            }
        }

        StateLibrary.getActiveState().keyTyped(c);
    }

    /**
     * Sends a ContronKey press to the current state
     * @param key
     */
    public static void sendKeyPress(ControlKey key)
    {
        LRKState state = StateLibrary.getActiveState();
        sendKeyPress(state, key);

        for(UIComponent ui:state.getUIComponents())
            if(ui.acceptsKeyboard())
                sendKeyPress(ui, key);
    }

    /**
     * Sends a ContronKey press to the current state
     * @param key
     */
    public static void sendKeyPress(RenderComponent ui, ControlKey key)
    {
        switch(key)
        {
            case Up:
                ui.keyUp();
                break;
            case Down:
                ui.keyDown();
                break;
            case Left:
                ui.keyLeft();
                break;
            case Right:
                ui.keyRight();
                break;
            case Select:
                ui.keySelect();
                break;
            case Back:
                ui.keyBack();
                break;
            case Menu:
                ui.keyMenu();
                break;
            case ForceQuit:
                break;
            case Fullscreen:
                DisplayManager.swapFullScreen();
                break;
            case Mouse1:
                ui.mouseEvent(RenderComponent.MouseInputType.LeftPressed, InputManager.getMouseX(), InputManager.getMouseY());
            case Mouse2:
                ui.mouseEvent(RenderComponent.MouseInputType.RightPressed, InputManager.getMouseX(), InputManager.getMouseY());
        }
    }

    @Override
    public void keyReleased(int key, char c)
    {

    }

    @Override
    public void setInput(Input input)
    {

    }

    @Override
    public boolean isAcceptingInput()
    {
        return true;
    }

    @Override
    public void inputEnded()
    {

    }

    public static Input getInput()
    {
        return _input;
    }

    /**
     * gets the key id of the given enum
     * @param key the key enum to look for
     * @return the key id. returns -1 if the key is not found.
     */
    public static int getControlKey(ControlKey key)
    {
        if(!_keyboardMap.containsKey(key))
            return -1;

        return _keyboardMap.get(key);
    }

    /**
     * gets the key id of the given string
     * @param key the key string to look for
     * @return the key id. returns -1 if the key is not found.
     */
    public static int getControlKey(String key)
    {
        return getControlKey(stringToControlKey(key));
    }

    /**
     * Checks to see if a key is down
     * @param key the key to check for
     * @return true if it's down, false if not
     */
    public static boolean isControlKeyDown(ControlKey key)
    {
        // TODO
        if(getInput().isKeyDown(getControlKey(key)))
            return true;

        if(xboxController != null)
        {
            if(xboxController.isButtonDown(key))
                return true;
        }

        return false;
    }

    public static XBoxController getXboxController() {
        return xboxController;
    }

    public static float getMouseX()
    {
        return _mouseX;
    }

    public static void setMouseX(float mouseX)
    {
        _mouseX = mouseX;
        checkMouseX();
    }

    public static void addMouseX(float mouseX)
    {
        _mouseX += mouseX;
        checkMouseX();
    }

    public static float getMouseY()
    {
        return _mouseY;
    }

    public static void setMouseY(float mouseY)
    {
        _mouseY = mouseY;
        checkMouseY();
    }

    public static void addMouseY(float mouseY)
    {
        _mouseY += mouseY;
        checkMouseY();
    }

    /**
     * Keeps the x and y mouse values in check with the current render dimension
     */
    private static void checkMouse()
    {
        checkMouseX();
        checkMouseY();
    }

    /**
     * Keeps the x mouse value in check with the current render dimension
     */
    private static void checkMouseX()
    {
        _mouseX = LRKMath.clamp(0, _mouseX, DisplayManager.getWindowWidth());
    }

    /**
     * Keeps the y mouse value in check with the current render dimension
     */
    private static void checkMouseY()
    {
        _mouseY = LRKMath.clamp(0, _mouseY, DisplayManager.getWindowHeight());
    }

    /**
     * returns the x/y movement vector with a length < 1 based on controller input
     * @return the move vector
     */
    public static Vec2 getMoveVector()
    {
        Vec2 v = new Vec2(0, 0);

        // if a movement key is pressed, adjustt he movement vector
        if(isControlKeyDown(ControlKey.Up))   v.y -= 1f;
        if(isControlKeyDown(ControlKey.Down)) v.y += 1f;
        if(getXboxController() != null)       v.y -= getXboxController().getMoveDY();

        if(isControlKeyDown(ControlKey.Right)) v.x += 1f;
        if(isControlKeyDown(ControlKey.Left))  v.x -= 1f;
        if(getXboxController() != null)        v.x += getXboxController().getMoveDX();

        if(v.length() > 1);
            v.normalize();

        return v;
    }

    /**
     * @return the x distance of the mouse pointer from the middle of the window
     */
    public static float getMouseXOrigin()
    {
        return _mouseX - DisplayManager.getWindowWidth()/2f;
    }

    /**
     * @return the y distance of the mouse pointer from the middle of the window
     */
    public static float getMouseYOrigin()
    {
        return _mouseY - DisplayManager.getWindowHeight()/2f;
    }

    public static boolean isQueryControllerMovement()
    {
        return _queryControllerMovement;
    }

    public static void setQueryControllerMovement(boolean queryControllerMovement)
    {
        _queryControllerMovement = queryControllerMovement;
    }

    public static boolean controllerMatches(JXInputDevice dev)
    {
        return xboxController != null && xboxController.getDevice() != dev;
    }

    public static void setXboxController(XBoxController xboxController)
    {
        InputManager.xboxController = xboxController;
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy)
    {
        InputManager.setMouseX(InputManager.getInput().getMouseX());
        InputManager.setMouseY(InputManager.getInput().getMouseY());

        if(leftMousePressed)
            sendMouseEvent(LRKState.MouseInputType.LeftDragged, InputManager.getMouseX(), InputManager.getMouseY());

        if(rightMousePressed)
            sendMouseEvent(LRKState.MouseInputType.RightDragged, InputManager.getMouseX(), InputManager.getMouseY());

        sendMouseEvent(LRKState.MouseInputType.Moved, InputManager.getMouseX(), InputManager.getMouseY());
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
        if(button == 0) // left
        {
            leftMousePressed = true;
            sendMouseEvent(LRKState.MouseInputType.LeftPressed, x, y);
        }
        else // right
        {
            rightMousePressed = true;
            sendMouseEvent(LRKState.MouseInputType.RightPressed, x, y);
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {
        if(button == 0) // left
        {
            leftMousePressed = false;
            sendMouseEvent(LRKState.MouseInputType.LeftReleased, x, y);
        }
        else // right
        {
            rightMousePressed = false;
            sendMouseEvent(LRKState.MouseInputType.RightReleased, x, y);
        }
    }

    public void sendMouseEvent(LRKState.MouseInputType type, float x, float y)
    {
        LRKState state = StateLibrary.getActiveState();
        state.mouseEvent(type, x, y);

        for(UIComponent ui:state.getUIComponents())
            if(ui.acceptsMouse())
                ui.mouseEvent(type, x, y);
    }

    @Override
    public void mouseWheelMoved(int change)
    {
        StateLibrary.getActiveState().mouseWheelMoved(change);
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) { }

    public static boolean isLeftMousePressed()
    {
        return leftMousePressed;
    }

    public static boolean isRightMousePressed()
    {
        return rightMousePressed;
    }
}
