package com.fisherevans.lrk.states.quit;

import com.fisherevans.lrk.*;
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
        super(StateLibrary.getTempID());
    }

    @Override
    public void init() throws SlickException
    {
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        GFX.drawText(-20, -20, DisplayManager.getGameWidth(), DisplayManager.getGameHeight(), GFX.TEXT_RIGHT, GFX.TEXT_BOTTOM, Resources.getFont(1), Color.white, "Quitting...");
        _rendered = true;
    }

    @Override
    public void update(float delta) throws SlickException
    {
        if(_rendered)
        {
            LRK.log("Quitting, goodbye :)");
            Options.save();
            System.exit(0);
        }
    }

    @Override
    public void destroy() throws SlickException
    {
    }
}
