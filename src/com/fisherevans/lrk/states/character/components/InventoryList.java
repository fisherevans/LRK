package com.fisherevans.lrk.states.character.components;

import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.character.CharacterState;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryList extends UIComponent
{
    private final int NAME = 0;
    private final int NAME = 0;
    private final int NAME = 0;
    private final int NAME = 0;
    private final int NAME = 0;
    private final int NAME = 0;
    private final int NAME = 0;

    private CharacterState _parent;

    public InventoryList(CharacterState parent)
    {
        super(parent, true, false);
        _parent = parent;
    }

    @Override
    public void update(float delta) throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resize()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyUp()
    {
        super.keyUp();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void keyDown()
    {
        super.keyDown();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void keyLeft()
    {
        super.keyLeft();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void keyRight()
    {
        super.keyRight();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void keySelect()
    {
        super.keySelect();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
