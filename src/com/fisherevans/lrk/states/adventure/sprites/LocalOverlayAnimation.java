package com.fisherevans.lrk.states.adventure.sprites;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/10/13
 * Time: 7:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocalOverlayAnimation extends LocalEntitySprite
{
    private SpriteSheet _spriteSheet;
    private Animation _animation;
    private float _centerX, _centerY;

    public LocalOverlayAnimation(AdventureEntity entity, float renderPadding, SpriteSheet spriteSheet, int timePerFrame, float centerX, float centerY)
    {
        super(entity, renderPadding);
        _spriteSheet = spriteSheet;
        _centerX = centerX;
        _centerY = centerY;

        _animation = new Animation(_spriteSheet, timePerFrame);
        _animation.setLooping(false);
    }

    @Override
    public void render(Graphics gfx, float xShift, float yShift)
    {
        Image img = _animation.getCurrentFrame();
        img.setCenterOfRotation(_centerX, _centerY);

        img.setRotation(getEntity().getDegrees());
        GFX.drawImage(getEntity().getDrawPosition().x-_centerX,
                getEntity().getDrawPosition().y-_centerY,
                img);
    }

    @Override
    public void update(float delta)
    {
        _animation.update((long) (delta*1000));
    }

    @Override
    public boolean isComplete()
    {
        return _animation.isStopped();
    }
}
