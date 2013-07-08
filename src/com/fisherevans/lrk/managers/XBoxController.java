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

    private Robot _robot;

    private Map<Button, Integer> _buttonMap;
    private Directional _dpad;
    private Axis _aim, _move;
    private Map<Directional, Integer> _directionalMap;
    private Map<Axis, Integer> _axisMap;

    private ArrayList<InputManager.ControlKey> _keyQueue;

    private final double AXIS_THRESHOLD_TRIGGER = 0.5;
    private final double AXIS_THRESHOLD_CENTERED = 0.2;
    private final double MOUSE_MOVE_SCALE = 1000;

    private final int AXIS_MOVE_LR = 0;
    private final int AXIS_MOVE_UD = 1;
    private final int AXIS_AIM_LR = 3;
    private final int AXIS_AIM_UD = 4;
    private final int AXIS_TRIGGER = 2;

    public XBoxController(JXInputDevice controller)
    {
        _keyQueue = new ArrayList<>();
        try
        {
            _robot = new Robot();
        }
        catch(Exception e)
        {
            LRK.log("Failed to grab mouse controller.");
            _robot = null;
        }

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
            case 0:
                _keyQueue.add(InputManager.ControlKey.Select);
                break;
            case 1:
                _keyQueue.add(InputManager.ControlKey.Back);
                break;
            case 7:
                _keyQueue.add(InputManager.ControlKey.Menu);
                break;
            case 6:
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
            case 0:
                _keyQueue.add(InputManager.ControlKey.Up);
                break;
            case 9000:
                _keyQueue.add(InputManager.ControlKey.Right);
                break;
            case 18000:
                _keyQueue.add(InputManager.ControlKey.Down);
                break;
            case 27000:
                _keyQueue.add(InputManager.ControlKey.Left);
                break;
        }
    }

    public void moveMouse(float delta)
    {
        if(_robot == null || _controller == null)
            return;

        try
        {
            Axis lr = _controller.getAxis(AXIS_AIM_LR);
            Axis ud = _controller.getAxis(AXIS_AIM_UD);

            int x = (int) (lr.getValue()*MOUSE_MOVE_SCALE*delta);
            int y = (int) (ud.getValue()*MOUSE_MOVE_SCALE*delta);

            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();

            int currentX = (int) b.getX();
            int currentY = (int) b.getY();

            //LRK.log(currentX + ", " + currentY + " + " + x + ", " + y);

            _robot.mouseMove(x + currentX, y + currentY);
        }
        catch(Exception e)
        {
            LRK.log("EEE");
            e.printStackTrace();
            System.exit(0);
        }
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
    /*
            case Up:
                return (_dpad.getDirection() == 0 && !_dpad.isCentered()) || _controller.getAxis(AXIS_MOVE_UD).getValue() < -AXIS_THRESHOLD;
            case Right:
                return (_dpad.getDirection() == 9000 && !_dpad.isCentered()) || _controller.getAxis(AXIS_MOVE_LR).getValue() > AXIS_THRESHOLD;
            case Down:
                return (_dpad.getDirection() == 18000 && !_dpad.isCentered()) || _controller.getAxis(AXIS_MOVE_UD).getValue() > AXIS_THRESHOLD;
            case Left:
                return (_dpad.getDirection() == 27000 && !_dpad.isCentered()) || _controller.getAxis(AXIS_MOVE_LR).getValue() < -AXIS_THRESHOLD;
    */
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
