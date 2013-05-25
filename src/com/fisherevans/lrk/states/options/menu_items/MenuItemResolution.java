package com.fisherevans.lrk.states.options.menu_items;

import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.states.options.Menu;
import com.fisherevans.lrk.states.options.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/9/13
 * Time: 7:17 PM
 */
public class MenuItemResolution extends MenuItem
{
    private int _scale = 1;

    /**
     * @param text
     * @param description
     * @param scale the new scale to set the display window to
     */
    public MenuItemResolution(String text, String description, int scale)
    {
        super(text, description);
        _scale = scale;
    }

    @Override
    public Menu select()
    {
        Options.setScale(_scale);
        return null;
    }

    @Override
    public boolean isActive()
    {
        return Options.getDisplayScale() == _scale;
    }
}
