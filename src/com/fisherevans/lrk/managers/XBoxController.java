package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.launcher.Game;
import de.hardcode.jxinput.*;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.event.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/7/13
 * Time: 8:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class XBoxController implements JXInputDirectionalEventListener, JXInputAxisEventListener, JXInputButtonEventListener
{
    private JXInputDevice _controller;

    private Map<Button, Integer> _buttonMap;
    private Directional _dpad;
    private Axis _aim, _move;
    private Map<Directional, Integer> _directionalMap;
    private Map<Axis, Integer> _axisMap;

    private ArrayList<InputManager.ControlKey> _keyQueue;

    private final double AXIS_THRESHOLD_TRIGGER = 0.5;
    private final double AXIS_THRESHOLD_CENTERED = 0.2;
    private final float MOUSE_MOVE_SCALE = 350f;

    private final int AXIS_MOVE_LR = 0;
    private final int AXIS_MOVE_UD = 1;
    private final int AXIS_AIM_LR = 3;
    private final int AXIS_AIM_UD = 4;
    private final int AXIS_TRIGGER = 2;

    private final int BUTTON_A = 0;
    private final int BUTTON_B = 1;
    private final int BUTTON_X = 2;
    private final int BUTTON_Y = 3;
    private final int BUTTON_RB = 5;
    private final int BUTTON_LB = 4;
    private final int BUTTON_R3 = 9;
    private final int BUTTON_L3 = 8;
    private final int BUTTON_START = 7;
    private final int BUTTON_BACK = 6;

    private final int DIRECTIONAL_UP = 0;
    private final int DIRECTIONAL_RIGHT = 9000;
    private final int DIRECTIONAL_DOWN = 18000;
    private final int DIRECTIONAL_LEFT = 27000;

    public XBoxController(JXInputDevice controller)
    {
        _keyQueue = new ArrayList<>();

        _controller = controller;
        LRK.log("Found XBox 360 Controller! Buttons: " + _controller.getNumberOfButtons() + " - Dir's: " + _controller.getNumberOfDirectionals() + " - Axes: " + _controller.getNumberOfAxes());

        for(int x = 0;x < _controller.getNumberOfDirectionals();x++)
        {
            if(_controller.getDirectional(x).getName().toLowerCase().contains("hat"))
            {
                _dpad = _controller.getDirectional(x);
                JXInputEventManager.addListener(this, _dpad);
                break;
            }
        }

        _buttonMap = new HashMap<Button, Integer> ();
        for(int x = 0;x < _controller.getNumberOfButtons();x++)
        {
            JXInputEventManager.addListener(this, _controller.getButton(x));
            _buttonMap.put(_controller.getButton(x), x);
        }

        _axisMap = new HashMap<Axis, Integer> ();
        for(int x = 0;x < _controller.getNumberOfAxes();x++)
        {
            _axisMap.put(_controller.getAxis(x), x);
            LRK.log(_controller.getAxis(x).getName() + " - " + _controller.getAxis(x));
            JXInputEventManager.addListener(this, _controller.getAxis(x), AXIS_THRESHOLD_TRIGGER);
        }
    }

    @Override
    public void changed(JXInputAxisEvent jxInputAxisEvent)
    {
        Axis axis = jxInputAxisEvent.getAxis();
        if(!_axisMap.containsKey(axis))
            return;

        int id = _axisMap.get(axis);
        double v = axis.getValue();

        switch(id)
        {
            case AXIS_TRIGGER:
                if(v > 0) // left
                    LRK.log("Trigger L");
                else // right
                    LRK.log("Trigger R");
                break;
            case AXIS_MOVE_LR:
                if(v > 0)
                    _keyQueue.add(InputManager.ControlKey.Right);
                else
                    _keyQueue.add(InputManager.ControlKey.Left);
                break;
            case AXIS_MOVE_UD:
                if(v > 0)
                    _keyQueue.add(InputManager.ControlKey.Down);
                else
                    _keyQueue.add(InputManager.ControlKey.Up);
                break;
        }
    }

    @Override
    public void changed(JXInputButtonEvent jxInputButtonEvent)
    {
        if(!_buttonMap.containsKey(jxInputButtonEvent.getButton()))
            return;

        switch(_buttonMap.get(jxInputButtonEvent.getButton()))
        {
            case BUTTON_A:
                _keyQueue.add(InputManager.ControlKey.Select);
                break;
            case BUTTON_B:
                _keyQueue.add(InputManager.ControlKey.Back);
                break;
            case BUTTON_START:
                _keyQueue.add(InputManager.ControlKey.Menu);
                break;
            case BUTTON_BACK:
                Game.lrk.exit();
                break;
        }
    }

    @Override
    public void changed(JXInputDirectionalEvent jxInputDirectionalEvent)
    {
        if(jxInputDirectionalEvent.getDirectional().isCentered())
            return;

        switch(jxInputDirectionalEvent.getDirectional().getDirection())
        {
            case DIRECTIONAL_UP:
                _keyQueue.add(InputManager.ControlKey.Up);
                break;
            case DIRECTIONAL_RIGHT:
                _keyQueue.add(InputManager.ControlKey.Right);
                break;
            case DIRECTIONAL_DOWN:
                _keyQueue.add(InputManager.ControlKey.Down);
                break;
            case DIRECTIONAL_LEFT:
                _keyQueue.add(InputManager.ControlKey.Left);
                break;
        }
    }

    public void moveMouse(float delta)
    {
        if(_controller == null)
            return;

        double lr = _controller.getAxis(AXIS_AIM_LR).getValue();
        if(Math.abs(lr) > AXIS_THRESHOLD_CENTERED)
            InputManager.addMouseX((float)lr*delta*MOUSE_MOVE_SCALE);

        double ud = _controller.getAxis(AXIS_AIM_UD).getValue();
        if(Math.abs(ud) > AXIS_THRESHOLD_CENTERED)
            InputManager.addMouseY((float)ud*delta*MOUSE_MOVE_SCALE);
    }

    public boolean isButtonDown(InputManager.ControlKey key)
    {
        switch(key)
        {
            case Up:
                return (_dpad.getDirection() == 0 && !_dpad.isCentered());
            case Right:
                return (_dpad.getDirection() == 9000 && !_dpad.isCentered());
            case Down:
                return (_dpad.getDirection() == 18000 && !_dpad.isCentered());
            case Left:
                return (_dpad.getDirection() == 27000 && !_dpad.isCentered());
        }

        return false;
    }

    public float getMoveDX()
    {
        Axis moveLR = _controller.getAxis(AXIS_MOVE_LR);

        if(Math.abs(moveLR.getValue()) < AXIS_THRESHOLD_CENTERED)
            return 0;
        LRK.log(moveLR.getValue() +"");
        return (float)moveLR.getValue();
    }

    public float getMoveDY()
    {
        Axis moveUD = _controller.getAxis(AXIS_MOVE_UD);

        if(Math.abs(moveUD.getValue()) < AXIS_THRESHOLD_CENTERED)
            return 0;

        return -(float)moveUD.getValue();
    }

    public void runKeyQueue()
    {
        if(_keyQueue.size() > 0)
        {
            for(InputManager.ControlKey key:_keyQueue)
            {
                LRK.log("XBox Press: " + key);
                InputManager.sendKeyPress(key);
            }

            _keyQueue = new ArrayList<>();
        }
    }
}