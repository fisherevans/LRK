package com.fisherevans.lrk.states.splash;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.transitions.SimpleFadeTransition;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 5/5/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class SplashState extends LRKState
{
    private float _fade, _flash;

    public SplashState() throws SlickException
    {
        super(StateLibrary.getID("splash"));
    }

    @Override
    public void init() throws SlickException
    {
        _fade = 0f;
        _flash = 0f;
    }

    @Override
    public void enter() throws SlickException
    {
    }

    @Override
    public void exit() throws SlickException
    {
        Game.lrk.getNotifications().setBlockNotifications(false);
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        Color c = new Color(1f, 1f, 1f, (_fade >= 0 ? _fade : 0)); // color of the title
        Color c2 = new Color(1f, 1f, 1f, (float)(-Math.cos(_flash)+1)/4f); // color of the "press select"

        // used for positioning of the text
        float halfHeight = DisplayManager.getRenderHeight()/2f;
        float quarterHeight = DisplayManager.getRenderHeight()/4f;

        // draw the title
        GFX.drawText(0, quarterHeight, DisplayManager.getRenderWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_TOP, Resources.getFont(3), c, "Lost Relics of Kazar");
        GFX.drawText(0, quarterHeight, DisplayManager.getRenderWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(2), c, "A Prequel");

        // draw the "press select" if the title is 100% visible
        if(_fade >= 1)
            GFX.drawText(0, quarterHeight, DisplayManager.getRenderWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_BOTTOM, Resources.getFont(1), c2, ">    Press Select    <");
    }

    @Override
    public void update(float delta) throws SlickException
    {
        if(_fade < 1) // fade in until 100% opaque
        {
            _fade += 0.25f*delta;
            _fade = _fade > 1 ? 1 : _fade;
        }
        else // once fully opaque, flash "press select"
        {
            _flash += 3.5f*delta;
        }
    }

    @Override
    public void destroy() throws SlickException
    {
    }

    @Override
    public void keySelect()
    {
        LRKState options = StateLibrary.getState("options");

        try
        {
            StateLibrary.setActiveState(new SimpleFadeTransition(StateLibrary.getTempID(), this, options, 0.5f));
        }
        catch (SlickException e)
        {
            e.printStackTrace();
            LRK.log("failed to leave splash state");
            System.exit(1);
        }
    }

    @Override
    public void resize()
    {

    }
}
