package com.fisherevans.lrk.states.options.menu_items;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.options.Menu;
import com.fisherevans.lrk.states.options.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/9/13
 * Time: 7:17 PM
 */
public class MenuItemQuit extends MenuItem
{
    public MenuItemQuit(String text, String description)
    {
        super(text, description);
    }

    @Override
    public Menu select()
    {
        Game.lrk.exit();
        return null;
    }

    @Override
    public boolean isActive()
    {
        return false;
    }
}
