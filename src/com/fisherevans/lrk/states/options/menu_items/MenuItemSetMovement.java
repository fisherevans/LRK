package com.fisherevans.lrk.states.options.menu_items;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.options.Menu;
import com.fisherevans.lrk.states.options.MenuItem;
import com.fisherevans.lrk.states.overlays.SetMovementKeys;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/11/13
 * Time: 10:48 PM
 */
public class MenuItemSetMovement extends MenuItem
{
    public MenuItemSetMovement(String text, String description)
    {
        super(text, description);
    }

    @Override
    public Menu select()
    {
        Game.lrk.setOverlayState(new SetMovementKeys((LRKState) Game.lrk.getCurrentState(), false, true));
        return null;
    }

    @Override
    public boolean isSelected()
    {
        return false;
    }
}
