package com.fisherevans.lrk.states;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 1:47 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class RenderComponent
{
    public enum MouseInputType { Moved, LeftPressed, LeftDragged, LeftReleased, RightPressed, RightDragged, RightReleased }

    /**
     * updates this state and its elements
     * @param delta time since the last update (in seconds)
     * @throws SlickException if there is any error.
     */
    public abstract void update(float delta) throws SlickException;

    /**
     * renders this state on gfx
     * @param gfx the graphics element to draw this state on
     * @throws org.newdawn.slick.SlickException if there is any error.
     */
    public abstract void render(Graphics gfx) throws SlickException;

    /**
     * Called whenever the state is resized. Used for re-evaluating rendering values
     */
    public abstract void resize();

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
    public void keyMenu()   { }

    /**
     * called whenever a character key is pressed
     * @param c the character pressed
     */
    public void keyTyped(char c) { }

    public void mouseEvent(MouseInputType type, float x, float y) { }

    public void mouseWheelMoved(float delta) { }
}
