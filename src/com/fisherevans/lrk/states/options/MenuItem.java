package com.fisherevans.lrk.states.options;

import org.newdawn.slick.Color;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/8/13
 * Time: 9:50 PM
 */
public abstract class MenuItem
{
    public static Color
        IDLE = new Color(0.5f, 0.5f, 0.5f),
        SELECTED = new Color(1f, 0f, 0f),
        HOVER = new Color(1f, 1f, 1f);

    private String _text, _description = "";
    private Menu _parent = null;
    private Color _idle = IDLE, _selected = SELECTED, _hover = HOVER;

    public MenuItem(String text, String description)
    {
        _text = text;
        _description = description;
    }

    public abstract Menu select();

    public abstract boolean isSelected();

    public String getText()
    {
        return _text;
    }

    public String getDescription()
    {
        return _description;
    }

    public Menu getParent()
    {
        return _parent;
    }

    public void setParent(Menu parent)
    {
        _parent = parent;
    }

    public Color getColor()
    {
        if(isSelected())
            return _selected;
        else
            return _idle;
    }

    public Color getColorHover()
    {
        return _hover;
    }
}
