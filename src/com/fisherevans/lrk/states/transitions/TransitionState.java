package com.fisherevans.lrk.states.transitions;

import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.RenderComponent;
import org.newdawn.slick.SlickException;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 5:02 PM
 */
public abstract class TransitionState extends LRKState
{
    private int _stateId1, _stateId2;
    private float _duration, _current;
    private boolean _started = false;

    /**
     * creates the transition state
     * @param stateId1 the original state (transition from)
     * @param stateId2 the new state (transition to)
     * @param duration how long to make the transition last
     * @throws SlickException if any graphics error occurs
     */
    public TransitionState(int stateId1, int stateId2, float duration) throws SlickException
    {
        super();

        _duration = duration;
        _current = 0;

        _stateId1 = stateId1;
        _stateId2 = stateId2;
    }

    @Override
    public void update(float delta) throws SlickException
    {
        if(_started)
            _current += delta;
        else
            _started = true;

        if(_current >= _duration)
            StateLibrary.setActiveState(_stateId2);
        else
            transitionUpdate(delta);
    }

    /**
     * Updates the transition much like the LRK update.
     * @param delta the time since the last update
     * @throws SlickException
     */
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
        return StateLibrary.getState(_stateId1);
    }

    /**
     * @return the new state
     */
    public LRKState getState2()
    {
        return StateLibrary.getState(_stateId2);
    }

    /**
     * @return how long the transition will last (total)
     */
    public float getDuration()
    {
        return _duration;
    }

    @Override
    public void resize() {
        getState1().resize();
        getState2().resize();
    }
}
