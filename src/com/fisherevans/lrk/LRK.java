package com.fisherevans.lrk;

import com.fisherevans.lrk.states.options.OptionsState;
import com.fisherevans.lrk.states.overlays.Overlay;
import com.fisherevans.lrk.states.overlays.OverlayState;
import com.fisherevans.lrk.states.profile.ProfileState;
import com.fisherevans.lrk.states.splash.SplashState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * User: Fisher
 * Date: 5/5/13
 * Time: 7:08 PM
 */
public class LRK extends StateBasedGame
{
    public static final boolean DEBUG = false;

    private OverlayState _overlayState;

    public LRK(String name)
    {
        super(name);
        Options.load();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException
    {
        addState(new SplashState());
        addState(new ProfileState());
        addState(new OptionsState());

        _overlayState = new OverlayState();
        addState(_overlayState);
    }

    public void setOverlayState(Overlay overlay)
    {
        _overlayState.setOverlay(overlay);
        this.enterState(_overlayState.getID());
    }

    public void exitOverlayState()
    {
        if(!_overlayState.isOverlayed()) return;

        int id = _overlayState.getCurrentOverlay().getLastState().getID();
        _overlayState.destroyOverlay();
        this.enterState(id);
    }

    public static void log(String text)
    {
        System.out.println(text);
    }
}
