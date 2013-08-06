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
    public abstract boolean doesEffect(AdventureEntity entity);

    public abstract void effect(AdventureEntity entity);
}
