package com.fisherevans.lrk.states;

import com.fisherevans.lrk.StateLibrary;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 5/5/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class LRKState
{
    private int _id;

    public LRKState(int id) throws SlickException
    {
        setID(id);

        init();
    }

    /**
     * @return the state library id for this state
     */
    public int getID()
    {
        return _id;
    }

    /**
     * sets the ID of this state for the State Library
     * @param id the id to use
     */
    public void setID(int id)
    {
        _id = id;
    }

    /**
     * initiates this state and prepares it to be used
     * @throws SlickException if there is any error.
     */
    public abstract void init() throws SlickException;

    /**
     * ran when this state starts being active.
     * @throws SlickException if there is any error.
     */
    public abstract void enter() throws SlickException;

    /**
     * ran when this state stops being active.
     * @throws SlickException if there is any error.
     */
    public abstract void exit() throws SlickException;

    /**
     * renders this state on gfx
     * @param gfx the graphics element to draw this state on
     * @throws SlickException if there is any error.
     */
    public abstract void render(Graphics gfx) throws SlickException;

    /**
     * updates this state and its elements
     * @param delta time since the last update (in seconds)
     * @throws SlickException if there is any error.
     */
    public abstract void update(float delta) throws SlickException;

    /**
     * method called when the state is done being used (cleanup state)
     * @throws SlickException if there is any error.
     */
    public abstract void destroy() throws SlickException;

    /** called when the user presses the UP key */
    public void keyUp()     { }

    /** called when the user presses the DOWN key */
    public void keyDown()   { }

    /** called when the user presses the LEFT key */
    public void keyLeft()   { }

    /** called when the user presses the RIGHT key */
    public void keyRight()  { }

    /** called when the user presses the SELECT key */
    public void keySelect() { }

    /** called when the user presses the BACK key */
    public void keyBack()   { }

    /** called when the user presses the BACK key */
    public void keyMenu()   { StateLibrary.setActiveState("options"); }

    /**
     * called whenever a character key is pressed
     * @param c the character pressed
     */
    public void keyTyped(char c) { }

    public abstract void resize();
}
