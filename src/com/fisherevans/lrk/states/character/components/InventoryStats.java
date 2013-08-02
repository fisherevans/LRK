package com.fisherevans.lrk.states.character.components;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.rpg.items.Equipment;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.character.CharacterState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryStats extends UIComponent
{
    public final int WIDTH = 360;
    public final int HEIGHT = 250;
    public final int X_OFFSET = WIDTH;
    public final int Y_OFFSET = 50;

    public final int TITLE_HEIGHT = 32;

    public final int CONTENT_HEIGHT = HEIGHT-TITLE_HEIGHT;

    public final int LIST_PADDING = 8;
    public final int LIST_MARGIN = 8;

    private CharacterState _parent;

    public InventoryStats(CharacterState parent)
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
        gfx.setColor(new Color(0.3f, 0.3f, 0.3f, 0.5f));
        gfx.fillRect(startX()+LIST_MARGIN, startY()+LIST_MARGIN+TITLE_HEIGHT, WIDTH-LIST_MARGIN*2, HEIGHT-TITLE_HEIGHT-LIST_MARGIN*2);
        GFX.drawText(startX(), startY(), WIDTH, TITLE_HEIGHT,
                GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(3), Color.white, Game.lrk.getPlayer().getEntity().getName());
    }

    @Override
    public void resize()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyUp()
    {

    }

    @Override
    public void keyDown()
    {

    }

    @Override
    public void keyLeft()
    {

    }

    @Override
    public void keyRight()
    {

    }

    @Override
    public void keySelect()
    {

    }

    private float startX()
    {
        return _parent.getForeHalfWidth()-WIDTH+X_OFFSET;
    }

    private float startY()
    {
        return _parent.getForeHalfHeight()-HEIGHT+Y_OFFSET;
    }
}
