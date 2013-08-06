package com.fisherevans.lrk.states.adventure.combat.skills;

import com.fisherevans.lrk.LRK;
import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class Splash extends Skill
{
    public  Splash()
    {
        super("Splash", "It's just a splash... Has no effect whatsoever.");
    }

    @Override
    public boolean execute(AdventureEntity owner)
    {
        LRK.log("Splash skill executed...");
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
