package com.fisherevans.lrk.states;

import com.fisherevans.lrk.Resources;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 5/5/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class LRKState extends RenderComponent
{
    private int _id = -1;
    private Image _cursor = null;
    private boolean _grabMouse = false;
    private ArrayList<UIComponent> _uiComponents;

    public LRKState() throws SlickException
    {
        _cursor = Resources.getImage("gui/default_mouse");
        _uiComponents = new ArrayList<>();
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

    public void renderUI(Graphics gfx) throws SlickException
    {
        for(UIComponent ui:getUIComponents())
            ui.render(gfx);
    }

    /**
     * ran when this state stops being active.
     * @throws SlickException if there is any error.
     */
    public abstract void exit() throws SlickException;

    /**
     * method called when the state is done being used (cleanup state)
     * @throws SlickException if there is any error.
     */
    public abstract void destroy() throws SlickException;

    public Image getCursor()
    {
        return _cursor;
    }

    public void setCursor(Image cursor)
    {
        _cursor = cursor;
    }

    public boolean getGrabMouse()
    {
        return _grabMouse;
    }

    public void setGrabMouse(boolean grabMouse)
    {
        _grabMouse = grabMouse;
    }

    public ArrayList<UIComponent> getUIComponents()
    {
        return _uiComponents;
    }

    public void addUIComponent(UIComponent ui)
    {
        _uiComponents.add(ui);
    }

    public void clearUIComponents()
    {
        _uiComponents.clear();
    }
}
