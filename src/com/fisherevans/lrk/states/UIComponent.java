package com.fisherevans.lrk.states;

import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 1:25 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class UIComponent extends RenderComponent
{
    private LRKState _parent;
    private boolean _acceptsKeyboard, _acceptsMouse;

    public UIComponent(LRKState parent, boolean acceptsKeyboard, boolean acceptsMouse)
    {
        _parent = parent;
        _acceptsKeyboard = acceptsKeyboard;
        _acceptsMouse = acceptsMouse;
    }

    public LRKState getParent()
    {
        return _parent;
    }

    public boolean acceptsKeyboard()
    {
        return _acceptsKeyboard;
    }

    public boolean acceptsMouse()
    {
        return _acceptsMouse;
    }
}
