package com.fisherevans.lrk.states.overlays;

import com.fisherevans.lrk.states.LRKState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/10/13
 * Time: 12:01 AM
 */
public abstract class Overlay
{
    private LRKState _lastState;
    private boolean _update, _render;

    public Overlay(LRKState state, boolean update, boolean render)
    {
        _lastState = state;
        _update = update;
        _render = render;
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException
    {
        if(_render)
            _lastState.render(gameContainer, stateBasedGame, graphics);

        renderOverlay(graphics);
    }

    public abstract void renderOverlay(Graphics graphics);

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {
        if(_update)
            _lastState.update(gameContainer, stateBasedGame, delta);

        updateOverlay(delta);
    }

    public abstract void updateOverlay(int delta);

    public abstract void keyPressed(int key, char c);

    public LRKState getLastState()
    {
        return _lastState;
    }

    public boolean isUpdate()
    {
        return _update;
    }

    public boolean isRender()
    {
        return _render;
    }
}
