package com.fisherevans.lrk.states.profile;

import com.fisherevans.lrk.states.GameStateEnum;
import com.fisherevans.lrk.states.LRKState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tests.states.TestState2;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 5/5/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProfileState extends LRKState
{
    public final static int ID = GameStateEnum.PROFILE.ordinal();
    private StateBasedGame _game;

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
        graphics.setColor(Color.blue);
        graphics.drawRect(10, 10, 20, 20);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException
    {

    }

    public void keyReleased(int key, char c)
    {

    }
}
