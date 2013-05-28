package com.fisherevans.lrk.states.transitions;

import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * User: Fisher evans
 * Date: 5/27/13
 * Time: 5:09 PM
 */
public class SimpleFadeTransition extends TransitionState
{
    public SimpleFadeTransition(int id, LRKState state1, LRKState state2, float duration) throws SlickException
    {
        super(id, state1, state2, duration);
    }

    @Override
    public void init() throws SlickException
    {
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        float interp = getInterpolation();

        if(interp < 0.5f) // fade out of the first one
        {
            getState1().render(gfx);
            GFX.fill(gfx, new Color(0, 0, 0, (interp*2f)));
        }
        else // fade into the second one
        {
            getState2().render(gfx);
            GFX.fill(gfx, new Color(0, 0, 0, (1f - (interp-0.5f)*2f)));
        }
    }

    @Override
    public void transitionUpdate(float delta) throws SlickException
    {
    }

    @Override
    public void destroy() throws SlickException
    {
    }
}
