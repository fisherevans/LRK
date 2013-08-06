package com.fisherevans.lrk.states.adventure.combat;

import com.fisherevans.lrk.rpg.RPGEntity;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/4/13
 * Time: 8:42 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Attack
{
    public abstract void execute(AdventureEntity owner);
}
