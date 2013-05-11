package com.fisherevans.lrk.states.options;

import com.fisherevans.lrk.launcher.Launcher;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.overlays.SinglePopup;

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
        Launcher.lrk.setOverlayState(new SinglePopup((LRKState) Launcher.lrk.getCurrentState(), false, true, "This menu item is not yet implemented. Please, check back later."));
        return null;
    }

    @Override
    public boolean isSelected()
    {
        return false;
    }
}
