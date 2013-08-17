package com.fisherevans.lrk.managers;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.notifications.Notifications;
import com.fisherevans.lrk.notifications.types.Notification;
import de.hardcode.jxinput.*;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.event.*;

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
public class XBoxController implements JXInputDirectionalEventListener, JXInputButtonEventListener
{
    private JXInputDevice _device;

    private Map<Button, Integer> _buttonMap;
    private Directional _dpad;
    private Axis _aim, _move;
    private Map<Directional, Integer> _directionalMap;
    private Map<Axis, Integer> _axisMap;
    private Map<Integer, Axis> _reverseAxisMap;
    private Map<Axis, Double> _axisLastValues;

    private ArrayList<InputManager.ControlKey> _keyQueue;

    private final double AXIS_THRESHOLD_TRIGGER = 0.5;
    private final double AXIS_THRESHOLD_CENTERED = 0.166;
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

    /**
     * creates the xbox controller, sets up the listeners, etc
     * @param controller the controller to base this input method on
     */
    public XBoxController(JXInputDevice controller)
    {
        _keyQueue = new ArrayList<>();

        _device = controller;

        for(int x = 0;x < _device.getNumberOfDirectionals();x++)
        {
            if(_device.getDirectional(x).getName().toLowerCase().contains("hat"))
            {
                _dpad = _device.getDirectional(x);
                JXInputEventManager.addListener(this, _dpad);
                break;
            }
        }

        _buttonMap = new HashMap<Button, Integer> ();
        for(int x = 0;x < _device.getNumberOfButtons();x++)
        {
            JXInputEventManager.addListener(this, _device.getButton(x));
            _buttonMap.put(_device.getButton(x), x);
        }

        _axisMap = new HashMap<Axis, Integer> ();
        _reverseAxisMap = new HashMap<Integer, Axis> ();
        _axisLastValues = new HashMap<Axis, Double> ();
        for(int x = 0;x < _device.getNumberOfAxes();x++)
        {
            _axisMap.put(_device.getAxis(x), x);
            _reverseAxisMap.put(x, _device.getAxis(x));
            _axisLastValues.put(_device.getAxis(x), 0.0);
            LRK.log(_device.getAxis(x).getName() + " - " + _device.getAxis(x));
        }
    }

    @Override
    public void changed(JXInputButtonEvent jxInputButtonEvent)
    {
        if(!_buttonMap.containsKey(jxInputButtonEvent.getButton()))
            return;

        if(!jxInputButtonEvent.getButton().getState())
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

    /**
     * poll axes for changes since the last check
     * @param delta the time since the last check
     */
    public void queryAxes(float delta)
    {
        if(_device == null)
            return;
        
        JXInputManager.updateFeatures();
        for(Axis axis:_axisMap.keySet())
        {
            int id = _axisMap.get(axis);

            double v = axis.getValue();
            double va = Math.abs(v);

            if(va >= AXIS_THRESHOLD_TRIGGER)
            {
                double lv = _axisLastValues.get(axis);
                double lva = Math.abs(lv);

                if(Math.signum(v) != Math.signum(lv) || lva < AXIS_THRESHOLD_TRIGGER)
                {
                    switch(id)
                    {
                        case AXIS_TRIGGER:
                            if(v > 0) // left
                                _keyQueue.add(InputManager.ControlKey.Mouse2);
                            else // right
                                _keyQueue.add(InputManager.ControlKey.Mouse1);
                            break;
                        case AXIS_MOVE_LR:
                            if(!InputManager.isQueryControllerMovement())
                                return;
                            if(v > 0)
                                _keyQueue.add(InputManager.ControlKey.Right);
                            else
                                _keyQueue.add(InputManager.ControlKey.Left);
                            break;
                        case AXIS_MOVE_UD:
                            if(!InputManager.isQueryControllerMovement())
                                return;
                            if(v > 0)
                                _keyQueue.add(InputManager.ControlKey.Down);
                            else
                                _keyQueue.add(InputManager.ControlKey.Up);
                            break;
                    }
                }
            }
            _axisLastValues.put(axis, v);
        }
    }

    /**
     * Updates InputManager's mouse values based on the controller's axes
     * @param delta time since last mouse check
     */
    public void moveMouse(float delta)
    {
        if(_device == null)
            return;

        double lr = _device.getAxis(AXIS_AIM_LR).getValue();
        if(Math.abs(lr) > AXIS_THRESHOLD_CENTERED)
            InputManager.addMouseX((float)lr*delta*MOUSE_MOVE_SCALE*DisplayManager.getBackgroundScale());

        double ud = _device.getAxis(AXIS_AIM_UD).getValue();
        if(Math.abs(ud) > AXIS_THRESHOLD_CENTERED)
            InputManager.addMouseY((float)ud*delta*MOUSE_MOVE_SCALE*DisplayManager.getBackgroundScale());
    }

    /**
     * Checks whether a given ControlKey is down
     * @param key the key to check
     * @return true of the key down
     */
    public boolean isButtonDown(InputManager.ControlKey key)
    {
        if(_device == null)
            return false;

        try
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
                case Mouse1:
                    return (_reverseAxisMap.get(AXIS_TRIGGER).getValue() < -AXIS_THRESHOLD_TRIGGER);
                case Mouse2:
                    return (_reverseAxisMap.get(AXIS_TRIGGER).getValue() > AXIS_THRESHOLD_TRIGGER);
                default:
                    return false;
            }
        }
        catch(Exception e)
        {
            LRK.log("Failed to check if button is down: " + key);
            return false;
        }
    }

    /**
     * @return gets the delta for movement (< 1) for the x axis
     */
    public float getMoveDX()
    {
        if(_device == null)
            return 0f;

        float value = (float) _device.getAxis(AXIS_MOVE_LR).getValue();
        float valueAbsolute = Math.abs(value);
        return valueAbsolute < AXIS_THRESHOLD_CENTERED ? 0 : value;
    }


    /**
     * @return gets the delta for movement (< 1) for the y axis
     */
    public float getMoveDY()
    {
        if(_device == null)
            return 0f;

        float value = (float) _device.getAxis(AXIS_MOVE_UD).getValue();
        float valueAbsolute = Math.abs(value);
        return valueAbsolute < AXIS_THRESHOLD_CENTERED ? 0 : -value;
    }

    /**
     * Calls key presses queued up by this controller
     */
    public void runKeyQueue()
    {
        while(_keyQueue.size() > 0)
            InputManager.sendKeyPress(_keyQueue.remove(0));
    }

    public JXInputDevice getDevice()
    {
        return _device;
    }

    public void setDevice(JXInputDevice device)
    {
        _device = device;
    }
}
