package com.fisherevans.lrk.states.splash;

import com.fisherevans.lrk.KeyMapping;
import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.GameStateEnum;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.options.OptionsState;
import com.fisherevans.lrk.states.profile.ProfileState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.CrossStateTransition;
import org.newdawn.slick.state.transition.EmptyTransition;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 5/5/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class SplashState extends LRKState
{
    public final static int ID = GameStateEnum.SPLASH.ordinal();
    private StateBasedGame _game;

    private float _fade = -0.1f, _flash = 0f;

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame game) throws SlickException
    {
        _game = game;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException
    {
        Color c = new Color(1f, 1f, 1f, (_fade >= 0 ? _fade : 0));
        Color c2 = new Color(1f, 1f, 1f, (float)(-Math.cos(_flash)+1)/4f);
        float halfHeight = Options.getGameHeight()/2f;
        float quarterHeight = Options.getGameHeight()/4f;
        GFX.drawText(0, quarterHeight, Options.getGameWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_TOP, Resources.getFont(3), c, "Lost Relics of Kazar");
        GFX.drawText(0, quarterHeight, Options.getGameWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(2), c, "A Prequel");

        if(_fade >= 1)
            GFX.drawText(0, quarterHeight, Options.getGameWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_BOTTOM, Resources.getFont(1), c2, ">    Press Select    <");
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {
        if(_fade < 1)
        {
            _fade += 0.00023*delta;
            _fade = _fade > 1 ? 1 : _fade;
        }
        else
        {
            _flash += 0.0025f*delta;
        }
    }

    public void keyReleased(int key, char c)
    {
        if(KeyMapping.isSelect(key))
            _game.enterState(OptionsState.ID);
    }
}
