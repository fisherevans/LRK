package com.fisherevans.lrk.states.adventure.combat.effects;

import com.fisherevans.lrk.states.adventure.combat.TeamBasedEntityEffect;
import com.fisherevans.lrk.states.adventure.entities.AdventureEntity;
import com.fisherevans.lrk.tools.LRKMath;
import org.jbox2d.common.Vec2;

/**
 * Created with IntelliJ IDEA.
 * User: immortal
 * Date: 8/6/13
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Cone extends TeamBasedEntityEffect
{
    private float _aimAngle, _widthAngle, _length;
    private Vec2 _pos;

    public Cone(float aimAngle, float widthAngle, float length, Vec2 pos, AdventureEntity.Team... effects)
    {
        super(effects);
        _aimAngle = (float) Math.toRadians(aimAngle);
        _widthAngle = (float) Math.toRadians(widthAngle);
        _length = length;
        _pos = pos;
    }

    @Override
    public boolean doesEffect(AdventureEntity entity)
    {
        return inEffectedTeam(entity)
                && _pos.sub(new Vec2(entity.getBody().getPosition())).length() <= _length
                && Math.abs(LRKMath.getAngleDifferenceRadians(_aimAngle, (float)Math.atan2(entity.getY(), entity.getX()))) <= _widthAngle/2f;
    }
}
