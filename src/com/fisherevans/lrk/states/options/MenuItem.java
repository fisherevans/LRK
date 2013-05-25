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
    private Color _idle = IDLE, _active = SELECTED, _hover = HOVER;

    /**
     * initializes this menu item's text properties
     * @param text the label of this menu item
     * @param description a short desciption of this menu option's function
     */
    public MenuItem(String text, String description)
    {
        _text = text;
        _description = description;
    }

    /**
     * executes this menu items select function (command action)
     * @return if the item is a sub menu, it will return the net menu to use. null other wise
     */
    public abstract Menu select();

    /**
     * USed to detirmine if this menu item is the currently set property (settings, etc) used for display colors
     * @return true if this setting is activated or in use
     */
    public abstract boolean isActive();

    /**
     * @return the label of this menu item
     */
    public String getText()
    {
        return _text;
    }

    /**
     * @return a short description of this menu item
     */
    public String getDescription()
    {
        return _description;
    }

    /**
     * @return the parent menu of this menu item (that this item is a child of)
     */
    public Menu getParent()
    {
        return _parent;
    }

    /**
     * @param parent the new menu parent of this object
     */
    public void setParent(Menu parent)
    {
        _parent = parent;
    }

    /**
     * Returns the color base dont he status of the menu item. IF the item isActive, it reutnrs the selectd color, otherwise th e idle color
     * @return the color og this menu item
     */
    public Color getColor()
    {
        if(isActive())
            return _active;
        else
            return _idle;
    }

    /**
     * @return gets the color of the item when it's hovered over
     */
    public Color getColorHover()
    {
        return _hover;
    }
}
