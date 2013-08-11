package com.fisherevans.lrk.states.adventure.sprites;

import com.fisherevans.lrk.Resources;
import com.fisherevans.lrk.states.GFX;
import com.fisherevans.lrk.states.adventure.AdventureState;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/11/13
 * Time: 3:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class Number extends Sprite
{
    private AngelCodeFont _font;
    private String _number;
    private Color _color;
    private Vec2 _worldPosition, _velocity;

    private float _duration = 1f;
    private float _timePassed = 0;

    private static final Vec2 GRAVITY = new Vec2(0, 6f);

    private static final Color POSITIVE = new Color(0f, 1f, 0f);
    private static final Color NEGATIVE = new Color(1f, 0f, 0f);

    public Number(float number, Vec2 worldPosition)
    {
        _font = Resources.getMiniNumberFont();//Resources.getFont(1);
        _number = Math.abs(number)+"";
        _color = number < 0 ? NEGATIVE : POSITIVE;
        _worldPosition = worldPosition;
        _velocity = new Vec2(((float)(Math.random()*0.8f))-0.4f, ((float)(-Math.random()*1.5f)-1.5f));
    }

    @Override
    public void render(Graphics gfx, float xShift, float yShift)
    {
        Vec2 drawPos = AdventureState.getDrawPosition(_worldPosition, xShift, yShift);
        _color.a = 1-(_timePassed/_duration);
        GFX.drawTextAbsolute(drawPos.x, drawPos.y, _font, _color, _number);
    }

    @Override
    public void update(float delta)
    {
        _timePassed += delta;
        _worldPosition.addLocal(_velocity.mul(delta));
        _velocity.addLocal(GRAVITY.mul(delta));
    }

    @Override
    public boolean isComplete()
    {
        return _timePassed >= _duration;
    }
}
