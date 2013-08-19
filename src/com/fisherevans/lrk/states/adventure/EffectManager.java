package com.fisherevans.lrk.states.adventure;
import com.fisherevans.lrk.LRK;
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
public class EffectManager
{
    private AdventureState _parentState;

    private ArrayList<EntityEffect> _queue;

    public EffectManager(AdventureState parentState)
    {
        _parentState = parentState;
        _queue = new ArrayList<>();
    }

    public void update(float delta)
    {
        for(EntityEffect effect:_queue)
            effect.update(delta);
    }

    public void processEntity(AdventureEntity entity)
    {
        for(EntityEffect effect:_queue)
            effect.processEffect(entity);
    }

    public void clearComplete()
    {
        for(int id = 0;id < _queue.size();id++)
        {
            if(_queue.get(id).complete())
            {
                _queue.remove(id);
                id--;
            }
        }
    }

    public void addEntityEffect(EntityEffect effect)
    {
        _queue.add(effect);
    }

    public int getQueueSize()
    {
        return _queue.size();
    }
}
