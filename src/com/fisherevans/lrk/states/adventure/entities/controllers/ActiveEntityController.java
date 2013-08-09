package com.fisherevans.lrk.states.adventure.entities.controllers;

import com.fisherevans.lrk.states.adventure.combat.Skill;
import com.fisherevans.lrk.states.adventure.entities.ActiveEntity;

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

    private Skill _mainSkill, _secondarySkill;

    private float _sinceLastMainSkill = Float.MAX_VALUE;
    private float _sinceLastSecondarySkill = Float.MAX_VALUE;

    public ActiveEntityController(ActiveEntity entity, Skill mainSkill, Skill secondarySkill)
    {
        _entity = entity;
        _mainSkill = mainSkill;
        _secondarySkill = secondarySkill;
    }

    public ActiveEntityController(ActiveEntity entity, Skill mainSkill)
    {
        this(entity, mainSkill, null);
    }

    public ActiveEntityController(ActiveEntity entity)
    {
        this(entity, null, null);
    }

    public void updateController(float delta)
    {
        _sinceLastMainSkill += delta;
        _sinceLastSecondarySkill += delta;

        update(delta);
    }

    public abstract void update(float delta);

    public ActiveEntity getEntity()
    {
        return _entity;
    }

    public boolean executeMainSkill()
    {
        Skill mainSkill = getMainSkill();
        if(mainSkill != null && _sinceLastMainSkill > mainSkill.getCoolDown())
        {
            _sinceLastMainSkill = 0f;
            mainSkill.execute(_entity);
            return true;
        }
        else
            return false;
    }

    public boolean executeSecondarySkill()
    {
        Skill secondarySkill = getSecondarySkill();
        if(secondarySkill != null && _sinceLastSecondarySkill > secondarySkill.getCoolDown())
        {
            _sinceLastSecondarySkill = 0f;
            secondarySkill.execute(_entity);
            return true;
        }
        else
            return false;
    }

    public Skill getMainSkill()
    {
        return _mainSkill;
    }

    public Skill getSecondarySkill()
    {
        return _secondarySkill;
    }

    public float getSinceLastMainSkill()
    {
        return _sinceLastMainSkill;
    }

    public float getSinceLastSecondarySkill()
    {
        return _sinceLastSecondarySkill;
    }
}
