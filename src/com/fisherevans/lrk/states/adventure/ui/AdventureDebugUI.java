package com.fisherevans.lrk.states.adventure.ui;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.rpg.entitycomponents.Health;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.UIComponent;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.adventure.EntityManager;
import com.fisherevans.lrk.states.adventure.entities.controllers.ActiveEntityController;
import com.fisherevans.lrk.tools.LRKMath;
import org.newdawn.slick.*;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdventureDebugUI extends UIComponent
{
    public AdventureDebugUI(LRKState parent)
    {
        super(parent, false, false);
        resize();
    }

    @Override
    public void update(float delta) throws SlickException
    {
    }

    @Override
    public void render(Graphics gfx) throws SlickException
    {
        if(LRK.DEBUG)
        {
            String x = String.format("X%.1f", ((AdventureState) getParent()).getEntityManager().getCamera().getX());
            String y = String.format("Y%.1f", ((AdventureState)getParent()).getEntityManager().getCamera().getY());
            GFX.drawTextAbsolute(10, 30, Resources.getMiniNumberBgFont(), Color.black, x);
            GFX.drawTextAbsolute(10, 40, Resources.getMiniNumberBgFont(), Color.black, y);
            GFX.drawTextAbsolute(10, 30, Resources.getMiniNumberFont(), Color.white, x);
            GFX.drawTextAbsolute(10, 40, Resources.getMiniNumberFont(), Color.white, y);
        }
    }

    @Override
    public void resize()
    {
    }
}
