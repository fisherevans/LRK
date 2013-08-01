package com.fisherevans.lrk.states.splash;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.managers.DisplayManager;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import com.fisherevans.lrk.states.UIComponent;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 7/28/13
 * Time: 1:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class TitleUI extends UIComponent
{
    private float _fade, _flash;

    public TitleUI(LRKState parent)
    {
        super(parent, false, false);

        _fade = 0f;
        _flash = 0f;
    }

    @Override
    public void update(float delta)
    {
        if(_fade < 1) // fade in until 100% opaque
        {
            _fade += 0.25f*delta;
            _fade = _fade > 1 ? 1 : _fade;
        }
        else // once fully opaque, flash "press select"
        {
            _flash += 3.5f*delta;
        }
    }

    @Override
    public void render(Graphics gfx)
    {
        Color c = new Color(1f, 1f, 1f, (_fade >= 0 ? _fade : 0)); // color of the title
        Color c2 = new Color(1f, 1f, 1f, (float)(-Math.cos(_flash)+1)/4f); // color of the "press select"

        // used for positioning of the text
        float halfHeight = DisplayManager.getForegroundHeight()/2f;
        float quarterHeight = DisplayManager.getForegroundHeight()/4f;

        // draw the title
        GFX.drawText(0, quarterHeight, DisplayManager.getForegroundWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_TOP, Resources.getFont(5), c, "Lost Relics of Kazar");
        GFX.drawText(0, quarterHeight, DisplayManager.getForegroundWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(3), c, "A Prequel");

        // draw the "press select" if the title is 100% visible
        if(_fade >= 1)
            GFX.drawText(0, quarterHeight, DisplayManager.getForegroundWidth(), halfHeight, GFX.TEXT_CENTER, GFX.TEXT_BOTTOM, Resources.getFont(2), c2, ">    Press Select    <");
    }

    @Override
    public void resize()
    {

    }
}
