package com.fisherevans.lrk.states.options;

import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/9/13
 * Time: 6:20 PM
 */
public class MenuItemStub extends MenuItem
{
    public MenuItemStub(String text, String description)
    {
        super(text, description);
    }

    @Override
    public Menu select()
    {
        //Game.lrk.setOverlayState(new SinglePopup((LRKState) Game.lrk.getCurrentState(), false, true, "This menu item is not yet implemented. Please, check back later."));
        return null;
    }

    @Override
    public boolean isActive()
    {
        return false;
    }
}
