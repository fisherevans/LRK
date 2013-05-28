package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;
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
public class InputManager extends ComponentManager implements KeyListener
{
    public enum ControlKey { Up, Down, Left, Right, Select, Back, Menu, ForceQuit }

    private static Input _input;
    private static Map<ControlKey, Integer> _controls;
    private static InputManager _itself;

    private static int
            _controlUp = Input.KEY_W,
            _controlDown = Input.KEY_S,
            _controlLeft = Input.KEY_A,
            _controlRight = Input.KEY_D,
            _controlSelect = Input.KEY_SPACE,
            _controlBack = Input.KEY_BACK,
            _controlMenu = Input.KEY_ESCAPE;

    public InputManager()
    {
        loadDefaultControls();
        _itself = this;
    }

    public static void connectInput(Input input)
    {
        _input = input;
        _input.addKeyListener(_itself);
    }

    public static void loadDefaultControls()
    {
        _controls = new HashMap<>();

        _controls.put(ControlKey.Up, Input.KEY_W);
        _controls.put(ControlKey.Down, Input.KEY_S);
        _controls.put(ControlKey.Left, Input.KEY_A);
        _controls.put(ControlKey.Right, Input.KEY_D);
        _controls.put(ControlKey.Select, Input.KEY_SPACE);
        _controls.put(ControlKey.Back, Input.KEY_LCONTROL);
        _controls.put(ControlKey.Menu, Input.KEY_ESCAPE);

        _controls.put(ControlKey.ForceQuit, Input.KEY_F10);
    }

    public static ControlKey stringTotControlKey(String key)
    {
        switch(key)
        {
            case "up": return ControlKey.Up;
            case "down": return ControlKey.Down;
            case "left": return ControlKey.Left;
            case "right": return ControlKey.Right;
            case "select": return ControlKey.Select;
            case "back": return ControlKey.Back;
            case "menu": return ControlKey.Menu;
            case "forcequit": return ControlKey.ForceQuit;
        }

        return null;
    }

    @Override
    public void setProperty(String key, String value)
    {
        ControlKey inputKey = stringTotControlKey(key);

        if(inputKey == null)
            return;

        try
        {
            _controls.put(inputKey, Integer.parseInt(value));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LRK.log("Failed to set input property: " + key + " to: " + key);
        }
    }

    @Override
    public void saveProperties(PrintWriter out)
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

    public static int getControlKey(ControlKey key)
    {
        if(!_controls.containsKey(key))
            return -1;

        return _controls.get(key);
    }

    public static int getControlKey(String key)
    {
        return getControlKey(stringTotControlKey(key));
    }
}
