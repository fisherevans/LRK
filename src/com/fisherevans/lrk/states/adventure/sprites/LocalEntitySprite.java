package com.fisherevans.lrk.states.adventure.sprites;

import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/10/13
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class LocalEntitySprite extends Sprite
{
    private AdventureEntity _entity;
    private float _renderPadding;

    public LocalEntitySprite(AdventureEntity entity, float renderPadding)
    {
        _entity = entity;
        _renderPadding = renderPadding;
    }

    public AdventureEntity getEntity()
    {
        return _entity;
    }

    public void setEntity(AdventureEntity entity)
    {
        _entity = entity;
    }

    public boolean doRender()
    {
        if(_entity == null)
            return false;
        else
            return _entity.getState().inRenderArea(_entity, _renderPadding);
    }
}
