package com.fisherevans.lrk;

import com.fisherevans.lrk.states.LRKState;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/26/13
 * Time: 8:17 PM
 */
public class InputManager implements KeyListener
{
    private Input _input;

    private LRK _lrk;

    public InputManager(Input input, LRK lrk)
    {
        _input = input;
        _lrk = lrk;

        _input.addKeyListener(this);
    }

    @Override
    public void keyPressed(int key, char c)
    {
        LRKState state = StateLibrary.getActiveState();

        if(key == Options.getControlUp())
            state.keyUp();
        else if(key == Options.getControlDown())
            state.keyDown();
        else if(key == Options.getControlLeft())
            state.keyLeft();
        else if(key == Options.getControlRight())
            state.keyRight();
        else if(key == Options.getControlSelect())
            state.keySelect();
        else if(key == Options.getControlBack())
            state.keyBack();

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
}
