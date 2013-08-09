package com.fisherevans.lrk.states.adventure.combat;

import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/5/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class EntityEffect
{
    private boolean _processed = false;

    public void update(float delta) { }

    public boolean complete()
    {
        return _processed;
    }

    public void processEffect(AdventureEntity entity)
    {
        _processed = true;
        if(doesEffect(entity))
            effect(entity);
    }

    public abstract boolean doesEffect(AdventureEntity entity);

    public abstract void effect(AdventureEntity entity);
}
