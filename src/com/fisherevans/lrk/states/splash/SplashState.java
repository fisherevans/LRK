package com.fisherevans.lrk.states.splash;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.StateLibrary;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.managers.MusicManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.transitions.SimpleFadeTransition;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
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
    public SplashState() throws SlickException
    {
        super(StateLibrary.getID("splash"));
        addUIComponent(new TitleUI(this));
    }

    @Override
    public void init() throws SlickException
    {
        MusicManager.play("isaks_epic_journey");
    }

    @Override
    public void enter() throws SlickException
    {
    }

    @Override
    public void update(float delta) throws SlickException
    {
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
    }

    @Override
    public void exit() throws SlickException
    {
        Game.lrk.getNotifications().setBlockNotifications(false);
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
