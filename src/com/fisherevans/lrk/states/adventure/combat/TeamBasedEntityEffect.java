package com.fisherevans.lrk.states.adventure.combat;

import com.fisherevans.lrk.states.adventure.combat.EntityEffect;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TeamBasedEntityEffect extends EntityEffect
{
    private List<AdventureEntity.Team> _effects;

    public TeamBasedEntityEffect(AdventureEntity.Team... effects)
    {
        _effects = Arrays.asList(effects);
    }

    public boolean inEffectedTeam(AdventureEntity entity)
    {
        return _effects.contains(entity.getTeam());
    }
}
