package com.fisherevans.lrk.states.adventure.sprites;

import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.adventure.AdventureState;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.tools.LRKMath;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/11/13
 * Time: 2:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class GlobalSpinFade extends GlobalSprite
{
    private float _renderPadding;

    private float _spinRate, _duration;
    private float _angle, _timePassed;
    private Image _image;

    private final static float FULL = 360;

    public GlobalSpinFade(float x, float y, float renderPadding, Image image, float spinRate, float duration, float startAngle)
    {
        super(x, y);
        _renderPadding = renderPadding;

        _image = image;
        _spinRate = spinRate;
        _duration = duration;

        _angle = startAngle;
        _timePassed = 0;
    }

    @Override
    public void render(Graphics gfx, float xShift, float yShift)
    {
        Vec2 drawPos = AdventureState.getDrawPosition(getWolrdPos(), xShift, yShift);
        float scale = LRKMath.clamp(0, 1f - (_timePassed/_duration), 1);
        _image.setRotation(_angle);
        GFX.drawImageCentered(drawPos.x, drawPos.y, _image, new Color(1f, 1f, 1f, scale));
    }

    @Override
    public void update(float delta)
    {
        _timePassed += delta;
        _angle += delta*FULL*_spinRate;
    }

    @Override
    public boolean isComplete()
    {
        return _timePassed >= _duration;
    }
}
