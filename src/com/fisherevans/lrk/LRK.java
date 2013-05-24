package com.fisherevans.lrk;

import com.fisherevans.lrk.launcher.*;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.options.OptionsState;
import com.fisherevans.lrk.states.overlays.Overlay;
import com.fisherevans.lrk.states.overlays.OverlayState;
import com.fisherevans.lrk.states.profile.ProfileState;
import com.fisherevans.lrk.states.splash.SplashState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import sun.misc.Launcher;

/**
 * User: Fisher
 * Date: 5/5/13
 * Time: 7:08 PM
 */
public class LRK extends StateBasedGame
{
    public static final boolean DEBUG = true;

    private OverlayState _overlayState;

    public LRK(String name)
    {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException
    {
        addState(new SplashState());
        addState(new ProfileState());
        addState(new OptionsState());
        addState(new AdventureState());

        _overlayState = new OverlayState();
        addState(_overlayState);
    }

    /**
     * starts an overlay
     * @param overlay the overlay to use and render
     */
    public void setOverlayState(Overlay overlay)
    {
        _overlayState.setOverlay(overlay);
        this.enterState(_overlayState.getID());
    }

    /**
     * return from the current overlay to the previous state
     */
    public void exitOverlayState()
    {
        if(!_overlayState.isOverlayed()) return;

        int id = _overlayState.getCurrentOverlay().getLastState().getID();
        _overlayState.destroyOverlay();
        this.enterState(id);
    }

    /**
     * @param text text to log
     */
    public static void log(String text)
    {
        System.out.println(text);
    }

    /**
     * @return keyboard and mouse input of this game
     */
    public Input getInput()
    {
        return Game.app.getInput();
    }
}
