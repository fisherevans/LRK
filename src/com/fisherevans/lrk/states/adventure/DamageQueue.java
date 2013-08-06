package com.fisherevans.lrk.states.adventure;

import com.fisherevans.lrk.states.adventure.combat.Damage;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/5/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class DamageQueue
{
    private AdventureState _parentState;

    private ArrayList<Damage> _queue;

    public DamageQueue(AdventureState parentState)
    {
        _parentState = parentState;
        _queue = new ArrayList<>();
    }

    public void processQueue()
    {
        for(AdventureEntity ent:_parentState.getEntities())
            for(Damage damage:_queue)
                if(damage.isEligible(ent))
                    damage.dealDamage(ent);
    }
}
