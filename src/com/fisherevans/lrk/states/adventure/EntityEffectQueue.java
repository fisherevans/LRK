package com.fisherevans.lrk.states.adventure;
import com.fisherevans.lrk.states.adventure.combat.EntityEffect;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/5/13
 * Time: 6:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityEffectQueue
{
    private AdventureState _parentState;

    private ArrayList<EntityEffect> _queue;

    public EntityEffectQueue(AdventureState parentState)
    {
        _parentState = parentState;
        _queue = new ArrayList<>();
    }

    public void processEntity(AdventureEntity entity)
    {
        for(EntityEffect effect:_queue)
            if(effect.doesEffect(entity))
                effect.effect(entity);
    }

    public void clearQueue()
    {
        if(_queue.size() > 0)
            _queue.clear();
    }

    public void addEntityEffect(EntityEffect effect)
    {
        _queue.add(effect);
    }
}
