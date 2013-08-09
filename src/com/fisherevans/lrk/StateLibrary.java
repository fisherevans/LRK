package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.options.OptionsState;
import com.fisherevans.lrk.states.splash.SplashState;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/26/13
 * Time: 8:50 PM
 */
public class StateLibrary
{
    public static final int START_TEMP_ID = 1000;
    private static Integer _nextTempID = START_TEMP_ID;
    private static Map<Integer, LRKState> _states;

    private static LRKState _activeState;

    /**
     * destroys all current states and recreates any permanent states (splash, options, etc);
     */
    public static void resetStates() throws SlickException
    {
        _activeState = null;

        _nextTempID = START_TEMP_ID;
        _states = new HashMap<Integer, LRKState>();

        addState(getID("splash"), new SplashState());
        addState(getID("options"), new OptionsState());
    }

    /**
     * sets the current active state for the LRK object to use
     * @param id the int id of the state to set acive
     * @return returns false if the id doesn't exist. The active state is not set if this happens.
     */
    public static boolean setActiveState(String id)
    {
        return setActiveState(getID(id));
    }

    /**
     * sets the current active state for the LRK object to use
     * @param id the int id of the state to set acive
     * @return returns false if the id doesn't exist. The active state is not set if this happens.
     */
    public static boolean setActiveState(int id)
    {
        LRKState newState = getState(id);
        if(newState == null)
        {
            LRK.log("State: " + id + " does not exist!");
            return false;
        }

        return setActiveState(newState);
    }

    /**
     * sets the active state to the given state
     * @param state the new state to set as active
     * @return the state passed to this method (for ease of use)
     */
    public static boolean setActiveState(LRKState state)
    {
        if(state == null) return false;

        if(!_states.containsKey(state.getID()))
            _states.put(state.getID(), state);

        try
        {
            state.enter();
            Game.getContainer().setMouseGrabbed(state.getGrabMouse());
        }
        catch (Exception e) { }

        try
        {
            _activeState.exit();
        }
        catch (Exception e) { }

        _activeState = state;


        if(_activeState.getCursor() != null)
        {
            try
            {
                Game.getContainer().setMouseCursor(_activeState.getCursor(), _activeState.getCursor().getWidth()/2, _activeState.getCursor().getHeight()/2);
            }
            catch(Exception e)
            {
                Game.getContainer().setDefaultMouseCursor();
            }
        }
        else
            Game.getContainer().setDefaultMouseCursor();

        _activeState.resize();
        for(UIComponent ui:_activeState.getUIComponents())
            ui.resize();

        return true;
    }

    /**
     * @return the current active state
     */
    public static LRKState getActiveState()
    {
        return _activeState;
    }

    /**
     * Adds a state to the library for later use
     * @param id the id to map the state with
     * @param state the state to add to the map
     * @return state you passed (for ease of use)
     */
    public static LRKState addState(int id, LRKState state)
    {
        _states.put(id, state);
        return state;
    }

    /**
     * get the state at a given id
     * @param id the id of the state to look up
     * @return returns the the state you're looking for - null if the ID isn't mapped.
     */
    public static LRKState getState(String id)
    {
        return getState(getID(id));
    }

    /**
     * get the state at a given id
     * @param id the id of the state to look up
     * @return returns the the state you're looking for - null if the ID isn't mapped.
     */
    public static LRKState getState(int id)
    {
        LRKState state = null;

        if(_states.containsKey(id))
            state = _states.get(id);

        return state;
    }

    /**
     * destroys the state at the given id (to free up RAM)
     * @param id the id of the state to remove
     * @return true if a state was removed - false if there was no state at that id
     */
    public static boolean destroyState(int id)
    {
        if(_states.containsKey(id))
        {
            _states.remove(id);
            return true;
        }
        else
            return false;
    }

    /**
     * destroys all temporary states (anything below START_TEMP_ID). DOES NOT destroy the active state een if it's a temp state
     */
    public static void destroyTempStates()
    {
        for(Integer key:_states.keySet())
        {
            if(key >= START_TEMP_ID && (_activeState == null || key != _activeState.getID()))
                destroyState(key);
        }
    }

    /** get a temp ID for a temp state to use. garunteed to be unique in the library */
    public static Integer getTempID()
    {
        return _nextTempID++;
    }

    /**
     * get an integer it of a predefined string id
     * @param id the id you're looking for in string form
     * @return the int mapping. returns -1 if the string id does not exist.
     */
    public static Integer getID(String id)
    {
        switch(id)
        {
            case "splash": return 1;
            case "profile": return 2;
            case "options": return 3;
            default: LRK.log("State: " + id + " does not exist"); return -1;
        }
    }
}
