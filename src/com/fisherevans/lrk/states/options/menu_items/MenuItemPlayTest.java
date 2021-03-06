package com.fisherevans.lrk.states.options.menu_items;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.options.Menu;
import com.fisherevans.lrk.states.options.OptionsState;
import com.fisherevans.lrk.states.transitions.SimpleFadeTransition;
import org.newdawn.slick.SlickException;

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
    public Menu select() throws SlickException
    {
        try
        {
            LRKState adventure = new AdventureState("template");
            int adventureId = StateLibrary.addState(adventure);
            StateLibrary.setNewActiveState(new SimpleFadeTransition(StateLibrary.getActiveState().getID(), adventureId, 2f));
        }
        catch (SlickException e)
        {
            e.printStackTrace();
            LRK.log("failed to leave options to playtest state");
            System.exit(1);
        }
        return null;
    }

    @Override
    public boolean isActive()
    {
        return false;
    }
}
