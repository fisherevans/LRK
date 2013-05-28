package com.fisherevans.lrk.states.splash;

import com.fisherevans.lrk.*;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.transitions.SimpleFadeTransition;
import org.newdawn.slick.*;

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
    public void render(Graphics gfx) throws SlickException
    {
        Color c = new Color(1f, 1f, 1f, (_fade >= 0 ? _fade : 0)); // color of the title
        Color c2 = new Color(1f, 1f, 1f, (float)(-Math.cos(_flash)+1)/4f); // color of the "press select"

        // used for positioning of the text
        float halfHeight = DisplayManager.getGameHeight()/2f;
        float quarterHeight = DisplayManager.getGameHeight()/4f;

        // draw the title
        GFX.drawText(0, quarterHeight, DisplayManager.getGameWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_TOP, Resources.getFont(3), c, "Lost Relics of Kazar");
        GFX.drawText(0, quarterHeight, DisplayManager.getGameWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(2), c, "A Prequel");

        // draw the "press select" if the title is 100% visible
        if(_fade >= 1)
            GFX.drawText(0, quarterHeight, DisplayManager.getGameWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_BOTTOM, Resources.getFont(1), c2, ">    Press Select    <");
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
            StateLibrary.setActiveState(new SimpleFadeTransition(StateLibrary.getTempID(), this, options, 2f));
        }
        catch (SlickException e)
        {
            e.printStackTrace();
            LRK.log("failed to leave splash state");
            System.exit(1);
        }
    }
}
