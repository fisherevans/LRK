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
public abstract class Skill
{
    /** Executes a skill from an AdventureEntity.
     * param owner the owner of the skill
     * returns true if the Skill is finished (false for lating effect - ward, etc)
     */
    public abstract boolean execute(AdventureEntity owner);
}
