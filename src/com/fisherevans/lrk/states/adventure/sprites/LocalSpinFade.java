package com.fisherevans.lrk.states.adventure.sprites;

import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/11/13
 * Time: 12:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocalSpinFade extends LocalEntitySprite
{
    private float _centerX, _centerY, _rate, _duration;
    private float _timePassed, _angle;
    private Image _image;

    public LocalSpinFade(AdventureEntity entity, float renderPadding, float centerX, float centerY, float rate, float duration, Image image)
    {
        super(entity, renderPadding);
        _centerX = centerX;
        _centerY = centerY;
        _rate = rate;
        _duration = duration;
        _image = image;

    }

    @Override
    public void render(Graphics gfx, float xShift, float yShift)
    {
        _image.setCenterOfRotation(_centerX, _centerY);
        _image.setRotation(_angle);
        float interp = 1-(_timePassed/_duration);
        Color color = new Color(1f, 1f, 1f, (float)-Math.pow(interp*2-1, 4)+1);
        GFX.drawImage(getEntity().getDrawPosition().x - _centerX,
                getEntity().getDrawPosition().y - _centerY,
                _image, color);
    }

    @Override
    public void update(float delta)
    {
        _angle += _rate*360f*delta;
        _timePassed += delta;
    }

    @Override
    public boolean isComplete()
    {
        return _timePassed >= _duration;
    }
}
