package com.fisherevans.lrk.states.overlays;

import com.fisherevans.lrk.Options;
import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.launcher.Game;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.LRKState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: Fisher evans
 * Date: 5/9/13
 * Time: 11:33 PM
 */
public class SinglePopup extends Overlay
{
    private String _message = "404";

    public SinglePopup(LRKState state, boolean update, boolean render, String message)
    {
        super(state, update, render);
        _message = message;
    }

    @Override
    public void renderOverlay(Graphics graphics)
    {
        GFX.fill(graphics, new Color(0f, 0f, 0f, 0.9f));
        GFX.drawTextWrap(Options.getGameWidth() / 4f, 0, Options.getGameWidth() / 2f, Options.getGameHeight(), GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(1), Color.white, _message);
        GFX.drawText(0, 0, Options.getGameWidth(), Options.getGameHeight() - 20, GFX.TEXT_CENTER, GFX.TEXT_BOTTOM, Resources.getFont(1), Color.white, "Press any key to continue...");
    }

    @Override
    public void updateOverlay(int i)
    {

    }

    @Override
    public void keyPressed(int key, char c)
    {
        Game.lrk.exitOverlayState();
    }
}
