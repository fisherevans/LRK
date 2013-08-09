package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/9/13
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class EffectSystem
{
    private AdventureState _parent;

    private final int HEALTHBAR_WIDTH = 18;
    private final int HEALTHBAR_HEIGHT = 2;
    private final int HEALTHBAR_PADDING = 1;
    private final int HEALTHBAR_BOTTOM_PADDING = 2;
    private final Color HEALTHBAR_COLOR = new Color(0.8f, 0f, 0f);
    private final Color HEALTHBAR_PADDING_COLOR = new Color(0.15f, 0.15f, 0.15f);

    public EffectSystem(AdventureState parent)
    {
        _parent = parent;
    }

    public void update(float detla)
    {

    }

    public void render(Graphics gfx)
    {
        for(AdventureEntity entity:_parent.getEntities())
        {

        }
    }
}
