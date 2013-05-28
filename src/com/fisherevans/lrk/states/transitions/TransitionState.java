package com.fisherevans.lrk.states.transitions;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.states.LRKState;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 5:02 PM
 */
public abstract class TransitionState extends LRKState
{
    private LRKState _state1, _state2;
    private float _duration, _current;

    public TransitionState(int id, LRKState state1, LRKState state2, float duration) throws SlickException
    {
        super(id);

        _state1 = state1;
        _state2 = state2;

        _duration = duration;
        _current = 0;
    }

    @Override
    public void update(float delta) throws SlickException
    {
        _current += delta;

        if(getInterpolation() >= 1f)
            StateLibrary.setActiveState(_state2);

        //LRK.log("Duration: " + _duration + " - Current: " + _current + " - Interp: " + getInterpolation());
    }

    public float getInterpolation()
    {
        return _current/_duration;
    }

    public LRKState getState1()
    {
        return _state1;
    }

    public LRKState getState2()
    {
        return _state2;
    }

    public float getDuration()
    {
        return _duration;
    }
}
