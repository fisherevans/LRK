package com.fisherevans.lrk.states.overlays;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.GameStateEnum;
import com.fisherevans.lrk.states.LRKState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/9/13
 * Time: 11:22 PM
 */
public class OverlayState extends LRKState
{
    public final static int ID = GameStateEnum.OVERLAY.ordinal();

    private Overlay _overlay = null;
    private boolean _overlayed = false;

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException
    {
        if(_overlayed && _overlay != null)
        {
            _overlay.render(gameContainer, stateBasedGame, graphics);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException
    {
        if(_overlayed && _overlay != null)
        {
            _overlay.update(gameContainer, stateBasedGame, i);
        }
        else
        {
            LRK.log("Overlay is updating, but it shouldn't be... Exiting overlay... again.");
            Game.lrk.exitOverlayState();
        }
    }

    public void keyPressed(int key, char c)
    {
        if(_overlayed && _overlay != null)
        {
            _overlay.keyPressed(key, c);
        }
    }

    public void setOverlay(Overlay overlay)
    {
        _overlay = overlay;
        _overlayed = true;
    }

    public void destroyOverlay()
    {
        _overlay = null;
        _overlayed = false;
    }

    public Overlay getCurrentOverlay()
    {
        return _overlay;
    }

    public boolean isOverlayed()
    {
        return _overlayed;
    }
}
