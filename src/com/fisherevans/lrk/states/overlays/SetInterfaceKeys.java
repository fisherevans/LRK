package com.fisherevans.lrk.states.overlays;

import com.fisherevans.lrk.LRK;
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
 * Date: 5/11/13
 * Time: 9:48 PM
 */
public class SetInterfaceKeys extends Overlay
{
    private String[] _controls = { "select", "back" };
    private String[] _displays = { "Select", "Back" };
    private int _current = 0;
    private float _error = 0;

    public SetInterfaceKeys(LRKState state, boolean update, boolean render)
    {
        super(state, update, render);
        Options.setControlSelect(-100);
        Options.setControlBack(-101);
    }

    @Override
    public void renderOverlay(Graphics graphics)
    {
        GFX.fill(graphics, new Color(0f, 0f, 0f, 0.9f));

        GFX.drawTextWrap(Options.getGameWidth() / 4f, 0, Options.getGameWidth() / 2f, Options.getGameHeight(), GFX.TEXT_CENTER, GFX.TEXT_CENTER, Resources.getFont(1),
                Color.white, "Please press a key to assign to " + _displays[_current] + ".");
        GFX.drawText(0, 0, Options.getGameWidth(), Options.getGameHeight() - 20, GFX.TEXT_CENTER, GFX.TEXT_BOTTOM, Resources.getFont(1),
                new Color(1f, 0f, 0f, _error), "That key is already assigned to something else.");
    }

    @Override
    public void updateOverlay(int delta)
    {
        _error -= 0.0008*delta;
        _error = _error > 0 ? _error : 0;
    }

    private boolean setKey(int key)
    {
        boolean worked = false;
        switch(_controls[_current])
        {
            case "select": worked = Options.setControlSelect(key); break;
            case "back": worked = Options.setControlBack(key); break;
            default: LRK.log("Error setting keys. #9"); break;
        }
        if(worked == false)
            _error = 1f;
        return worked;
    }

    @Override
    public void keyPressed(int key, char c)
    {
        boolean next = setKey(key);
        if(next)
        {
            _current++;
            if(_current >= _controls.length)
                Game.lrk.exitOverlayState();
        }
    }
}
