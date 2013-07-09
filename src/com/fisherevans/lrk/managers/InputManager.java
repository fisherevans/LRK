package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;
import de.hardcode.jxinput.JXInputDevice;
import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.directinput.DirectInputDevice;
import de.hardcode.jxinput.event.*;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/26/13
 * Time: 8:17 PM
 */
public class InputManager implements KeyListener
{
    public enum ControlKey { Up, Down, Left, Right, Select, Back, Menu, ForceQuit }

    private static Input _input;
    private static Map<ControlKey, Integer> _keyboardMap;
    private static InputManager _itself;

    public static boolean jxNativesLoaded = false;
    private static XBoxController xboxController = null;

    private static float _mouseX, _mouseY;

    public InputManager()
    {
        loadDefaultControls();
        _itself = this;

        _mouseX = DisplayManager.getRenderWidth()/2f;
        _mouseY = DisplayManager.getRenderHeight()/2f;
    }

    public static void loadControllers()
    {
        if(jxNativesLoaded)
        {
            JXInputEventManager.setTriggerIntervall(-1);
            String controllerName;
            for(int controllerId = 0;controllerId < JXInputManager.getNumberOfDevices();controllerId++)
            {
                controllerName = JXInputManager.getJXInputDevice(controllerId).getName().toLowerCase();
                if(controllerName.contains("xbox") && controllerName.contains("360"))
                {
                    xboxController = new XBoxController(JXInputManager.getJXInputDevice(controllerId));
                    break;
                }
            }
        }
        else
            xboxController = null;

        LRK.log("Slick Controllers: " + _input.getControllerCount());
    }

    /**
     * Tells this object what input object to pull triggers from
     * @param input the input object to use
     */
    public static void connectInput(Input input)
    {
        _input = input;
        _input.addKeyListener(_itself);
        loadControllers();
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
        LRKState state = StateLibrary.getActiveState();

        if(key == getControlKey(ControlKey.Up))
            state.keyUp();
        else if(key == getControlKey(ControlKey.Down))
            state.keyDown();
        else if(key == getControlKey(ControlKey.Left))
            state.keyLeft();
        else if(key == getControlKey(ControlKey.Right))
            state.keyRight();
        else if(key == getControlKey(ControlKey.Select))
            state.keySelect();
        else if(key == getControlKey(ControlKey.Back))
            state.keyBack();
        else if(key == getControlKey(ControlKey.Menu))
            state.keyMenu();
        else if(key == getControlKey(ControlKey.ForceQuit))
            Game.lrk.exit();

        state.keyTyped(c);
    }

    public static void sendKeyPress(ControlKey key)
    {
        LRKState state = StateLibrary.getActiveState();

        switch(key)
        {
            case Up:
                state.keyUp();
                break;
            case Down:
                state.keyDown();
                break;
            case Left:
                state.keyLeft();
                break;
            case Right:
                state.keyRight();
                break;
            case Select:
                state.keySelect();
                break;
            case Back:
                state.keyBack();
                break;
            case Menu:
                state.keyMenu();
                break;
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

    public static boolean isControlKeyDown(ControlKey key)
    {
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
    }

    public static void addMouseX(float mouseX)
    {
        _mouseX += mouseX;
    }

    public static float getMouseY()
    {
        return _mouseY;
    }

    public static void setMouseY(float mouseY)
    {
        _mouseY = mouseY;
    }

    public static void addMouseY(float mouseY)
    {
        _mouseY += mouseY;
    }
}
