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

    /**
     * creates the transition state
     * @param id see @getID(int id)
     * @param state1 the original state (transition from)
     * @param state2 the new state (transition to)
     * @param duration how long to make the transition last
     * @throws SlickException if any graphics error occurs
     */
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

        if(_current >= _duration)
            StateLibrary.setActiveState(_state2);
        else
            transitionUpdate(delta);
    }

    public abstract void transitionUpdate(float delta) throws SlickException;

    /**
     * gets the percentage of how complete the transition is done time wise
     * @return 0-1 float. 1 being done, 0 being haven't started
     */
    public float getInterpolation()
    {
        return _current/_duration;
    }

    /**
     * @return the original state
     */
    public LRKState getState1()
    {
        return _state1;
    }

    /**
     * @return the new state
     */
    public LRKState getState2()
    {
        return _state2;
    }

    /**
     * @return how long the transition will last (total)
     */
    public float getDuration()
    {
        return _duration;
    }
}
