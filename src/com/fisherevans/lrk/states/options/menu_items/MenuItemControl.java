package com.fisherevans.lrk.states.options.menu_items;

import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.options.Menu;
import com.fisherevans.lrk.states.options.MenuItem;
import com.fisherevans.lrk.states.overlays.SetMovementKeys;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/11/13
 * Time: 7:58 PM
 */
public class MenuItemControl extends MenuItem
{
    private String _control, _display;

    public MenuItemControl(String control)
    {
        super("Key Not Set", "%name% is currently set to %key%.");
        _control = control;
        _display = _control.replace(_control.charAt(0) + "", (_control.charAt(0) + "").toUpperCase());
    }

    @Override
    public Menu select()
    {

        return null;
    }

    @Override
    public String getText()
    {
        return _display;
    }

    @Override
    public String getDescription()
    {
        String key = "ERROR";
        switch(_control)
        {
            case "up": key = "\"" + Options.getControlUp() + "\""; break;
            case "down": key = "\"" + Options.getControlDown() + "\""; break;
            case "left": key = "\"" + Options.getControlLeft() + "\""; break;
            case "right": key = "\"" + Options.getControlRight() + "\""; break;
            case "select": key = "\"" + Options.getControlSelect() + "\""; break;
            case "back": key = "\"" + Options.getControlBack() + "\""; break;
        }
        return super.getDescription().replaceAll("%name%", _display).replaceAll("%key%", key);
    }

    @Override
    public boolean isSelected()
    {
        return false;
    }
}
