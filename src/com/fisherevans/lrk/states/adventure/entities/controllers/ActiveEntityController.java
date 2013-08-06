package com.fisherevans.lrk.states.adventure.entities.controllers;

import com.fisherevans.lrk.states.adventure.entities.ActiveEntity;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/5/13
 * Time: 8:52 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ActiveEntityController
{
    private ActiveEntity _entity;

    public ActiveEntityController(ActiveEntity entity)
    {
        _entity = entity;
    }

    public abstract void update(float delta);

    public ActiveEntity getEntity()
    {
        return _entity;
    }
}
