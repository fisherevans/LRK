package com.fisherevans.lrk.states.quit;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 12:58 PM
 */
public class QuitState extends LRKState
{
    private boolean _rendered = false;

    public QuitState() throws SlickException
    {
        super();
    }

    @Override
    public void init() throws SlickException
    {
    }

    @Override
    public void enter() throws SlickException
    {
    }

    @Override
    public void exit() throws SlickException
    {
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        // draw the quitting dialogue
        GFX.drawText(-20, -20, DisplayManager.getBackgroundWidth(), DisplayManager.getBackgroundHeight(), GFX.TEXT_RIGHT, GFX.TEXT_BOTTOM, Resources.getFont(1), Color.white, "Quitting...");
        _rendered = true; // once the dialogue is drawn, tell the update method it's time to quit
    }

    @Override
    public void update(float delta) throws SlickException
    {
        if(_rendered) // if the dialogue is drawn, quit
        {
            LRK.log("Quitting, goodbye :)");
            Options.save();
            Game.finalClose();
        }
    }

    @Override
    public void destroy() throws SlickException
    {
    }

    @Override
    public void resize()
    {
    }
}
