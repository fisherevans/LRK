package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.states.adventure.combat.TeamBasedEntityEffect;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import org.jbox2d.common.Vec2;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AOE extends TeamBasedEntityEffect
{
    private float _radius, _duration, _rate;
    private Vec2 _pos;

    private float _runTime = 0;
    private float _sinceLast = 0;

    private boolean effect = true;

    public AOE(float x, float y, float radius, float duration, float rate, AdventureEntity.Team... effects)
    {
        super(effects);
        _pos = new Vec2(x, y);
        _radius = radius;
        _duration = duration;
        _rate = rate;
        _sinceLast = _rate;
    }

    @Override
    public void update(float delta)
    {
        if(effect)
            effect = false;

         _runTime += delta;
        _sinceLast += delta;

        if(_sinceLast >= _rate)
        {
            effect = true;
            _sinceLast -= _rate;
        }
    }

    @Override
    public boolean complete()
    {
        return _runTime >= _duration;
    }

    @Override
    public boolean doesEffect(AdventureEntity entity)
    {
        return effect
                && inEffectedTeam(entity)
                && _pos.sub(new Vec2(entity.getBody().getPosition())).length() <= _radius;
    }

    public Vec2 getPos()
    {
        return _pos;
    }

    public void setPos(Vec2 pos)
    {
        _pos = pos;
    }
}
