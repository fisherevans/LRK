package com.fisherevans.lrk.states.options.menu_items;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.GameStateEnum;
import com.fisherevans.lrk.states.options.Menu;
import com.fisherevans.lrk.states.options.MenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/9/13
 * Time: 7:17 PM
 */
public class MenuItemPlayTest extends MenuItem
{
    public MenuItemPlayTest(String text, String description)
    {
        super(text, description);
    }

    @Override
    public Menu select()
    {
        Game.lrk.enterState(GameStateEnum.ADVENTURE.ordinal());
        return null;
    }

    @Override
    public boolean isActive()
    {
        return false;
    }
}
